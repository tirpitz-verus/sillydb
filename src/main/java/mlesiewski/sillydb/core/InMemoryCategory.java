package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;
import mlesiewski.sillydb.order.*;
import mlesiewski.sillydb.predicate.*;
import mlesiewski.sillydb.propertyvalue.*;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;
import java.util.stream.*;

import static io.reactivex.rxjava3.core.BackpressureStrategy.*;
import static mlesiewski.sillydb.order.SillyOrder.*;

class InMemoryCategory implements Category {

    private final CategoryName name;
    private final SillyDb db;
    private final Map<ThingName, NamedThing> things;
    private final Map<PropertyName, Class<? extends PropertyValue>> valueTypes;
    private final AtomicLong thingCounter = new AtomicLong();

    InMemoryCategory(CategoryName name, SillyDb db) {
        this.name = name;
        this.db = db;
        this.things = new ConcurrentHashMap<>();
        this.valueTypes = new ConcurrentHashMap<>();
    }

    @Override
    public CategoryName name() {
        return name;
    }

    @Override
    public Thing createNewThing() {
        return new InMemoryThing();
    }

    @Override
    public Single<NamedThing> put(Thing thing) {
        validPropertyTypes(thing);
        var namedThing = namedThingFrom(thing);
        things.put(namedThing.name(), namedThing);
        return Single.just(namedThing);
    }

    private NamedThing namedThingFrom(Thing thing) {
        if (thing instanceof InMemoryNamedThing namedThing) {
            return namedThing;
        } else {
            var name = createThingName();
            return new InMemoryNamedThing(thing, name);
        }
    }

    private ThingName createThingName() {
        return new ThingName(String.valueOf(thingCounter.incrementAndGet()));
    }

    private void validPropertyTypes(Thing thing) {
        thing.properties().forEach((propertyName, value) -> {
            var newValueType = value.getClass();
            var previous = valueTypes.getOrDefault(propertyName, newValueType);
            if (!previous.equals(newValueType)) {
                throw new PropertyValueTypeChangeIsIllegal(propertyName, previous, newValueType, this);
            }
            valueTypes.put(propertyName, newValueType);
        });
    }

    @Override
    public Maybe<NamedThing> findBy(ThingName name) {
        if (things.containsKey(name)) {
            return Maybe.just(things.get(name));
        } else {
            return Maybe.empty();
        }
    }

    @Override
    public Flowable<NamedThing> findAllBy(SillyPredicate predicate) {
        return findAllBy(predicate, NO_ORDER);
    }

    @Override
    public Flowable<NamedThing> findAllBy(SillyPredicate predicate, SillyOrder order) {
        return Flowable.create(new FlowableFromThings(predicate, order), BUFFER);
    }

    @Override
    public Completable remove(ThingName name) {
        things.remove(name);
        return Completable.complete();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InMemoryCategory that)) {
            return false;
        }
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    class FlowableFromThings implements FlowableOnSubscribe<NamedThing> {

        private final SillyPredicate predicate;
        private final SillyOrder order;

        FlowableFromThings(SillyPredicate predicate, SillyOrder order) {
            this.predicate = predicate;
            this.order = order;
        }

        @Override
        public void subscribe(@NonNull FlowableEmitter<NamedThing> emitter) {
            var values = things.values()
                    .stream()
                    .filter(thing -> {
                        var result = predicate.test(thing);
                        return result;
                    });
            if (order != NO_ORDER) {
                values = values.sorted(order);
            }
            values.forEach(thing -> {
                emitter.onNext(thing);
            });
            emitter.onComplete();
        }
    }
}
