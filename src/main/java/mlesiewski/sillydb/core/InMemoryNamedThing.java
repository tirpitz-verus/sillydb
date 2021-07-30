package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

import java.util.*;

class InMemoryNamedThing extends InMemoryThing implements NamedThing {

    private final ThingName name;

    public InMemoryNamedThing(Thing thing, ThingName name) {
        super(thing);
        this.name = name;
    }

    private InMemoryNamedThing(Map<PropertyName, PropertyValue<?>> properties, ThingName name) {
        super(properties);
        this.name = name;
    }

    @Override
    public <T> @NonNull Single<Thing> setProperty(@NonNull PropertyName propertyName, @NonNull PropertyValue<T> propertyValue) {
        var temp = newMapWithValue(propertyName, propertyValue);
        final var newThing = new InMemoryNamedThing(temp, name());
        return Single.just(newThing);
    }

    @Override
    public @NonNull Single<Thing> removeProperty(@NonNull PropertyName name) {
        var temp = newMapWithoutValue(name);
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
