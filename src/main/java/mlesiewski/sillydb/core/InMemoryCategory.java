package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;

import java.util.*;
import java.util.concurrent.*;

class InMemoryCategory implements Category {

    private final CategoryName name;
    private final SillyDb db;
    private final Map<ThingName, NamedThing> things;
    private long thingCounter = 0;

    InMemoryCategory(CategoryName name, SillyDb db) {
        this.name = name;
        this.db = db;
        things = new ConcurrentHashMap<>();
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
        var namedThing = namedThingFrom(thing);
        things.put(namedThing.name(), namedThing);
        return Single.just(namedThing);
    }

    private InMemoryNamedThing namedThingFrom(Thing thing) {
        if (thing instanceof InMemoryNamedThing namedThing) {
            return namedThing;
        } else {
            var name = createThingName();
            return new InMemoryNamedThing(thing, name);
        }
    }

    private ThingName createThingName() {
        thingCounter++;
        return new ThingName(String.valueOf(thingCounter));
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
}
