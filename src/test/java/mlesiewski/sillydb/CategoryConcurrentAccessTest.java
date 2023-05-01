package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.schedulers.*;
import mlesiewski.sillydb.predicate.*;
import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.reactivestreams.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.stream.IntStream;

import static java.lang.Boolean.*;
import static mlesiewski.sillydb.CategoryName.*;
import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.predicate.SillyPredicateBuilder.*;
import static mlesiewski.sillydb.propertyvalue.BooleanPropertyValue.*;
import static mlesiewski.sillydb.propertyvalue.LongPropertyValue.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;

@Tag("slow")
class CategoryConcurrentAccessTest {

    static final int TASKS_COUNT = 100_000;
    static final int PARALLELISM = 4;
    static final boolean DEBUG = true;
    static final int TASK_WAIT_A_BIT_MILLIS = 0;

    @ArgumentsSource(AllDbTypes.class)
    @ParameterizedTest(name = "{0}")
    void concurrentAccess(SillyDb sillyDb) throws InterruptedException {
        // given
        var birds = using(sillyDb)
                .withCategory(categoryName("birds"))
                .getCategory();
        var executor = Executors.newFixedThreadPool(PARALLELISM);
        var scheduler = Schedulers.from(executor);
        var arrayOfSubscribes = IntStream.range(0, PARALLELISM)
                .mapToObj(i -> new TestSubscriber(birds))
                .toArray(TestSubscriber[]::new);

        // when
        var thread = new Thread(() -> Flowable.range(1, TASKS_COUNT)
                .parallel(PARALLELISM)
                .runOn(scheduler)
                .subscribe(arrayOfSubscribes));
        thread.start();
        thread.join(0);

        // then - all tasks finished
        executor.shutdown();
        var terminated = executor.awaitTermination(1, TimeUnit.MINUTES);
        if (!terminated) {
            fail("the test did not finish in time");
        }

        printStats(arrayOfSubscribes);

        // then - all tasks completed
        var completed = Arrays.stream(arrayOfSubscribes)
                .mapToInt(TestSubscriber::getCompletedCount)
                .sum();
        assertEquals(TASKS_COUNT, completed);
    }

    private void printStats(TestSubscriber[] arrayOfSubscribers) {
        if (DEBUG) {
            System.out.println();
            Arrays.stream(arrayOfSubscribers)
                    .map(TestSubscriber::getStats)
                    .peek(System.out::println)
                    .reduce(Stats::add)
                    .ifPresent(System.out::println);
        }
    }

    static class TestSubscriber implements Subscriber<Integer> {

        static final PropertyName CURRENT_INTEGER = propertyName("currentInteger");
        static final PropertyName IS_EVEN = propertyName("is_even");
        static final SillyPredicate PREDICATE = predicateWhere()
                .property(IS_EVEN)
                .valueIsEqualTo(TRUE)
                .build();

        private final Random random = new Random();
        private final Category category;
        private final Stats stats = new Stats();

        private Subscription subscription;
        private ThingName lastCreated = null;

        public TestSubscriber(Category category) {
            this.category = category;
        }

        @Override
        public void onSubscribe(Subscription s) {
            this.subscription = s;
            this.subscription.request(1);
        }

        @Override
        public synchronized void onNext(Integer integer) {
            stats.scheduled();

            var chance = random.nextInt(101);
            if (chance < 25) {
                searchByPredicate();
            } else if (chance < 50) {
                readLastCreated(integer);
            } else if (chance < 70) {
                createNewThing(integer);
            } else if (chance < 85) {
                updateLastCreated(integer);
            } else {
                deleteLastCreated(integer);
            }

            this.subscription.request(1);
            waitABit();
        }

        private void waitABit() {
            try {
                Thread.sleep(TASK_WAIT_A_BIT_MILLIS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void searchByPredicate() {
            category.findAllBy(PREDICATE)
                    .doOnError(stats::errors)
                    .doOnComplete(stats::searched)
                    .blockingSubscribe();
        }

        private void readLastCreated(Integer integer) {
            if (lastCreated == null) {
                stats.findNull();
                createNewThing(integer);
            } else {
                debugOperation("read");
                category.findBy(lastCreated)
                        .switchIfEmpty(Maybe.error(() -> new IllegalArgumentException("could not find " + lastCreated)))
                        .doOnError(stats::errors)
                        .doOnSuccess(stats::read)
                        .blockingSubscribe();
            }
        }

        private void updateLastCreated(Integer integer) {
            if (lastCreated == null) {
                stats.findNull();
                createNewThing(integer);
            } else {
                debugOperation("update");
                category.findBy(lastCreated)
                        .switchIfEmpty(Maybe.error(() -> new IllegalArgumentException("could not find " + lastCreated)))
                        .toSingle()
                        .flatMap(t -> t.setProperty(CURRENT_INTEGER, longPropertyValue(integer.longValue())))
                        .flatMap(category::put)
                        .doOnError(stats::errors)
                        .doOnSuccess(stats::updated)
                        .blockingSubscribe();
            }
        }

        private void deleteLastCreated(Integer integer) {
            if (lastCreated == null) {
                stats.findNull();
                createNewThing(integer);
            } else {
                debugOperation("delete");
                category.remove(lastCreated)
                        .doOnError(stats::errors)
                        .doOnComplete(stats::deleted)
                        .blockingSubscribe();
                lastCreated = null;
            }
        }

        private void debugOperation(String operation) {
            if (DEBUG) {
                System.out.printf("[%3$s] %1$s %2$s\n", operation, lastCreated, Thread.currentThread().getName());
            }
        }

        private void createNewThing(Integer integer) {
            var namedThing = category.createNewThing()
                    .setProperty(CURRENT_INTEGER, longPropertyValue(integer.longValue()))
                    .flatMap(t -> t.setProperty(IS_EVEN, booleanPropertyValue(integer % 2 == 0)))
                    .flatMap(category::put)
                    .doOnError(stats::errors)
                    .doOnSuccess(stats::created)
                    .blockingGet();
            lastCreated = namedThing.name();
            debugOperation("create");
        }

        @Override
        public void onError(Throwable t) {
            // nothing
        }

        @Override
        public void onComplete() {
            // nothing
        }

        int getCompletedCount() {
            return stats.completedCount();
        }

        Stats getStats() {
            return stats;
        }
    }

    static class Stats {

        static final String FORMAT = "searched: %1$-5s read: %2$-5s created: %3$-5s updated: %4$-5s deleted: %5$-5s errors: %6$-5s scheduled: %7$-5s completed: %8$-5s findNull: %9$-5s";

        private final Collection<Throwable> errors = new ConcurrentLinkedQueue<>();

        private final AtomicInteger scheduled = new AtomicInteger();

        private final AtomicInteger searched = new AtomicInteger();
        private final AtomicInteger read = new AtomicInteger();
        private final AtomicInteger created = new AtomicInteger();
        private final AtomicInteger updated = new AtomicInteger();
        private final AtomicInteger deleted = new AtomicInteger();

        private final AtomicInteger findNull = new AtomicInteger();

        @Override
        public String toString() {
            return FORMAT.formatted(searched, read, created, updated, deleted, errorsCount(), scheduled, completedCount(), findNull);
        }

        void scheduled() {
            scheduled.incrementAndGet();
        }

        void errors(Throwable throwable) {
            errors.add(throwable);
        }

        int errorsCount() {
            return errors.size();
        }

        void created(NamedThing namedThing) {
            created.incrementAndGet();
        }

        void deleted() {
            deleted.incrementAndGet();
        }

        void updated(NamedThing namedThing) {
            updated.incrementAndGet();
        }

        void read(NamedThing namedThing) {
            read.incrementAndGet();
        }

        void searched() {
            searched.incrementAndGet();
        }

        void findNull() {
            findNull.incrementAndGet();
        }

        int completedCount() {
            return searched.get() + read.get() + created.get() + updated.get() + deleted.get();
        }

        Stats add(Stats other) {
            var result = new Stats();
            result.errors.addAll(this.errors);
            result.errors.addAll(other.errors);
            result.scheduled.addAndGet(this.scheduled.get());
            result.scheduled.addAndGet(other.scheduled.get());
            result.searched.addAndGet(this.searched.get());
            result.searched.addAndGet(other.searched.get());
            result.read.addAndGet(this.read.get());
            result.read.addAndGet(other.read.get());
            result.created.addAndGet(this.created.get());
            result.created.addAndGet(other.created.get());
            result.updated.addAndGet(this.updated.get());
            result.updated.addAndGet(other.updated.get());
            result.deleted.addAndGet(this.deleted.get());
            result.deleted.addAndGet(other.deleted.get());
            result.findNull.addAndGet(this.findNull.get());
            result.findNull.addAndGet(other.findNull.get());
            return result;
        }
    }
}
