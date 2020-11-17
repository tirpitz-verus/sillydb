package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;

import java.util.*;

class InMemoryNamedThing extends InMemoryThing implements NamedThing {

    private final ThingName name;

    public InMemoryNamedThing(Thing thing, ThingName name) {
        super(thing);
        this.name = name;
    }

    private InMemoryNamedThing(Map<PropertyName, Property> properties, ThingName name) {
        super(properties);
        this.name = name;
    }

    @Override
    public Single<Thing> setProperty(Property property) {
        var temp = properties();
        temp.put(property.name(), property);
        final var newThing = new InMemoryNamedThing(temp, name());
        return Single.just(newThing);
    }

    @Override
    public Single<Thing> removeProperty(PropertyName name) {
        var temp = properties();
        temp.remove(name);
        final var newThing = new InMemoryNamedThing(temp, name());
        return Single.just(newThing);
    }

    @Override
    public ThingName name() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InMemoryNamedThing that)) {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
