package mlesiewski.sillydb;

import mlesiewski.sillydb.propertyvalue.*;
import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.*;

import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.order.SillyOrderBuilder.*;
import static mlesiewski.sillydb.predicate.SillyPredicateBuilder.*;
import static mlesiewski.sillydb.propertyvalue.StringPropertyValue.*;
import static mlesiewski.sillydb.testinfrastructure.TestPredicates.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("category")
@TestMethodOrder(OrderAnnotation.class)
class CategoryCrudTest {

    static final CategoryName CATEGORY_NAME = new CategoryName("pets");

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be created")
    @ArgumentsSource(AllDbTypes.class)
    void canCreateCategory(SillyDb sillyDb) {
        // given no category of that name
        if (sillyDb.categoryExists(CATEGORY_NAME)) {
            sillyDb.deleteCategory(CATEGORY_NAME)
                    .test()
                    .assertComplete()
                    .dispose();
        }

        // when - creating category
        sillyDb.createCategory(CATEGORY_NAME)
                .test()

        // then - value has a correct name
                .assertValue(v -> v.name().equals(CATEGORY_NAME))
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }


    @ParameterizedTest(name = "{0}")
    @DisplayName("cannot be overwritten")
    @ArgumentsSource(AllDbTypes.class)
    void cannotOverwriteCategory(SillyDb sillyDb) {
        // given
        using(sillyDb).withCategory(CATEGORY_NAME);

        // when
        sillyDb.createCategory(CATEGORY_NAME).test()

        // then
                .assertError(CannotCreateCategory.class)
                .assertError(t -> t.getMessage().contains(CATEGORY_NAME.value))
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be found by name")
    @ArgumentsSource(AllDbTypes.class)
    void canBeFoundByName(SillyDb sillyDb) {
        // given - existing category
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .getCategory();

        // when - searching for the category
        sillyDb.findCategory(CATEGORY_NAME).test()

        // then - category was found
                .assertValueSequence(List.of(category))
                .assertComplete();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be deleted by name")
    @ArgumentsSource(AllDbTypes.class)
    void canDeleteCategoryByName(SillyDb sillyDb) {
        // given - existing category
        using(sillyDb).withCategory(CATEGORY_NAME);

        // when
        sillyDb.deleteCategory(CATEGORY_NAME).test()

        // then
                .assertNoErrors();

        // when - searching for deleted category
        sillyDb.findCategory(CATEGORY_NAME).test()

        // then - nothing returned
                .assertComplete()
                .assertNoValues();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("cannot be deleted if it does not exists")
    @ArgumentsSource(AllDbTypes.class)
    void cannotDeleteNullCategory(SillyDb sillyDb) {
        // given - without category
        using(sillyDb).withoutCategory(CATEGORY_NAME);

        // when
        sillyDb.deleteCategory(CATEGORY_NAME).test()

        // then
                .assertError(CategoryDoesNotExist.class)
                .assertError(t -> t.getMessage().contains(CATEGORY_NAME.value));
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be checked if it exists")
    @ArgumentsSource(AllDbTypes.class)
    void canCheckIfCategoryExists(SillyDb sillyDb) {
        // given - without category
        using(sillyDb).withoutCategory(CATEGORY_NAME);

        // when
        var existsAfterDeletion = sillyDb.categoryExists(CATEGORY_NAME);

        // then
        assertThat(existsAfterDeletion).isFalse();

        // when
        sillyDb.createCategory(CATEGORY_NAME)
                .test()
                .assertComplete()
                .dispose();

        var existsAfterCreation = sillyDb.categoryExists(CATEGORY_NAME);

        // then
        assertThat(existsAfterCreation).isTrue();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("will not allow to change a property type once it was set on one of the things")
    @ArgumentsSource(AllDbTypes.class)
    void guardPropertyType(SillyDb sillyDb) {
        // given - a thing with some property
        var likesCats = propertyName("likesCats");
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .withProperty(likesCats, true)
                .getCategory();

        // when - creating a thing with the same property but a different value type
        category.createNewThing()
                .setProperty(likesCats, stringPropertyValue("false"))
                .flatMap(category::put)

        // then
                .test()
                .assertError(PropertyValueTypeChangeIsIllegal.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(BooleanPropertyValue.class))
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can order results by one property in ascending order")
    @ArgumentsSource(AllDbTypes.class)
    void orderByOnePropertyAsc(SillyDb sillyDb) {
        // given - two things
        var ordinal = propertyName("ordinal");
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                    .withProperty(ordinal, 1)
                .withThing()
                    .withProperty(ordinal, 2)
                .getCategory();

        // when - ordering results
        var predicate = predicateWhere()
                .property(ordinal)
                .exists()
                .build();
        var ordering = orderBy().property(ordinal).asc().build();
        category.findAllBy(predicate, ordering)

        // then
                .test()
                .assertValueAt(0, t -> propertyHasValue(t, ordinal, 1L))
                .assertValueAt(1, t -> propertyHasValue(t, ordinal, 2L))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can order results by one property in descending order")
    @ArgumentsSource(AllDbTypes.class)
    void orderByOnePropertyDesc(SillyDb sillyDb) {
        // given - two things
        var ordinal = propertyName("ordinal");
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                    .withProperty(ordinal, 1L)
                .withThing()
                    .withProperty(ordinal, 2L)
                .getCategory();

        // when - ordering results
        var predicate = predicateWhere()
                .property(ordinal)
                .exists()
                .build();
        var ordering = orderBy().property(ordinal).desc().build();
        category.findAllBy(predicate, ordering)

        // then
                .test()
                .assertValueAt(0, t -> propertyHasValue(t, ordinal, 2L))
                .assertValueAt(1, t -> propertyHasValue(t, ordinal, 1L))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can order results by more than one property one field")
    @ArgumentsSource(AllDbTypes.class)
    void orderByMoreThanOnePropertyAsc(SillyDb sillyDb) {
        // given - two things
        var age = propertyName("age");
        var name = propertyName("name");
        var ordinal = propertyName("ordinal");
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                    .withProperty(name, "Pinky")
                    .withProperty(age, 8)
                    .withProperty(ordinal, 3L)
                .withThing()
                    .withProperty(name, "Pinky")
                    .withProperty(age, 5)
                    .withProperty(ordinal, 2L)
                .withThing()
                    .withProperty(name, "Adam")
                    .withProperty(age, 5)
                    .withProperty(ordinal, 1L)
                .getCategory();

        // when - ordering results
        var predicate = predicateWhere()
                .property(age)
                .exists()
                .build();
        var ordering = orderBy()
                .property(name).asc()
                .and()
                .property(age).asc()
                .build();
        category.findAllBy(predicate, ordering)

        // then
                .test()
                .assertValueAt(0, t -> propertyHasValue(t, ordinal, 1L))
                .assertValueAt(1, t -> propertyHasValue(t, ordinal, 2L))
                .assertValueAt(2, t -> propertyHasValue(t, ordinal, 3L))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("returns all category things in natural order")
    @ArgumentsSource(AllDbTypes.class)
    void returnsAlCategoryThingsInNaturalOrder(SillyDb sillyDb) {
        // given
        var ordinal = propertyName("ordinal");
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .withProperty(ordinal, 3L)
                .withThing()
                .withProperty(ordinal, 2L)
                .withThing()
                .withProperty(ordinal, 1L)
                .getCategory();

        // when
        category.findAll()

        // then
                .test()
                .assertValueAt(0, t -> propertyHasValue(t, ordinal, 3L))
                .assertValueAt(1, t -> propertyHasValue(t, ordinal, 2L))
                .assertValueAt(2, t -> propertyHasValue(t, ordinal, 1L))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("returns all category things in specified order")
    @ArgumentsSource(AllDbTypes.class)
    void returnsAlCategoryThingsInSpecifiedOrder(SillyDb sillyDb) {
        // given
        var ordinal = propertyName("ordinal");
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .withProperty(ordinal, 3L)
                .withThing()
                .withProperty(ordinal, 2L)
                .withThing()
                .withProperty(ordinal, 1L)
                .getCategory();

        // when
        var ordering = orderBy()
                .property(ordinal).asc()
                .build();
        category.findAll(ordering)

        // then
                .test()
                .assertValueAt(0, t -> propertyHasValue(t, ordinal, 1L))
                .assertValueAt(1, t -> propertyHasValue(t, ordinal, 2L))
                .assertValueAt(2, t -> propertyHasValue(t, ordinal, 3L))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("handles subscription cancellation")
    @ArgumentsSource(AllDbTypes.class)
    void handlesSubscriptionCancellation(SillyDb sillyDb) {
        // given
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .withThing()
                .getCategory();
        var subscriber = new CancellationTestingSubscriber();

        // when
        category.findAll().subscribe(subscriber);

        // then
        assertThat(subscriber.oneThingReceived).isTrue();
        assertThat(subscriber.noThingsReceivedAfterCancellation).isTrue();
        assertThat(subscriber.noErrors).isTrue();
        assertThat(subscriber.notCompleted).isTrue();
    }

    private static class CancellationTestingSubscriber implements Subscriber<NamedThing> {

        boolean notCompleted = true;
        boolean noErrors = true;
        boolean noThingsReceivedAfterCancellation = true;
        boolean oneThingReceived = false;
        Subscription subscription;

        @Override
        public void onSubscribe(Subscription s) {
            subscription = s;
            subscription.request(2);
        }

        @Override
        public void onNext(NamedThing namedThing) {
            if (!oneThingReceived) {
                subscription.cancel();
                oneThingReceived = true;
            } else {
                noThingsReceivedAfterCancellation = false;
            }
        }

        @Override
        public void onError(Throwable t) {
            noErrors = false;
        }

        @Override
        public void onComplete() {
            notCompleted = false;
        }
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("handles backpressure")
    @ArgumentsSource(AllDbTypes.class)
    void handlesBackpressure(SillyDb sillyDb) {
        // given
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .withThing()
                .withThing()
                .getCategory();
        var subscriber = new BackpressureTestingSubscriber();

        // when
        category.findAll().subscribe(subscriber);

        // then
        assertThat(subscriber.thingReceived).isEqualTo(2);
        assertThat(subscriber.noErrors).isTrue();
        assertThat(subscriber.notCompleted).isTrue();
    }

    private static class BackpressureTestingSubscriber implements Subscriber<NamedThing> {

        boolean notCompleted = true;
        boolean noErrors = true;
        int thingReceived = 0;
        Subscription subscription;

        @Override
        public void onSubscribe(Subscription s) {
            subscription = s;
            subscription.request(1);
        }

        @Override
        public void onNext(NamedThing namedThing) {
            thingReceived++;
            if (thingReceived == 1) {
                subscription.request(1);
            }
        }

        @Override
        public void onError(Throwable t) {
            noErrors = false;
        }

        @Override
        public void onComplete() {
            notCompleted = false;
        }
    }
}
