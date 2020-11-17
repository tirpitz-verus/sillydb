package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;

import java.util.*;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

class InMemoryThing implements Thing {

    private final Map<PropertyName, Property> properties;

    InMemoryThing(Thing thing) {
        this(thing.properties());
    }

    InMemoryThing() {
        this(emptyMap());
    }

    protected InMemoryThing(Map<PropertyName, Property> properties) {
        this.properties = unmodifiableMap(properties);
    }

    @Override
    public Single<Thing> setProperty(Property property) {
        var temp = new HashMap<>(properties);
        temp.put(property.name(), property);
        final var newThing = new InMemoryThing(temp);
        return Single.just(newThing);
    }

    @Override
    public Maybe<Property> getProperty(PropertyName name) {
        if (properties.containsKey(name)) {
            return getExistingProprty(name);
        } else {
            return Maybe.empty();
        }
    }

    private Maybe<Property> getExistingProprty(PropertyName name) {
        var property = properties.get(name);
        return Maybe.just(property);
    }

    @Override
    public Map<PropertyName, Property> properties() {
        return new HashMap<>(properties);
    }

    @Override
    public Single<Thing> removeProperty(PropertyName name) {
        var temp = new HashMap<>(properties);
        temp.remove(name);
        var newThing = new InMemoryThing(temp);
        return Single.just(newThing);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InMemoryThing that)) {
            return false;
        }
        return Objects.equals(properties, that.properties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties);
    }
}
