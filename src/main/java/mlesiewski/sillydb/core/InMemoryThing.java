package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

import java.util.*;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;
import static java.util.Objects.*;

class InMemoryThing implements Thing {

    private final Map<PropertyName, PropertyValue<?>> properties;

    InMemoryThing(Thing thing) {
        this(thing.properties());
    }

    InMemoryThing() {
        this(emptyMap());
    }

    protected InMemoryThing(Map<PropertyName, PropertyValue<?>> properties) {
        this.properties = unmodifiableMap(properties);
    }

    @Override
    public <T> @NonNull Single<Thing> setProperty(@NonNull PropertyName propertyName, @NonNull PropertyValue<T> propertyValue) {
        var temp = newMapWithValue(propertyName, propertyValue);
        final var newThing = new InMemoryThing(temp);
        return Single.just(newThing);
    }

    protected  <T> HashMap<PropertyName, PropertyValue<?>> newMapWithValue(PropertyName propertyName, PropertyValue<T> propertyValue) {
        requireNonNull(propertyName);
        requireNonNull(propertyValue);
        var temp = new HashMap<>(properties);
        temp.put(propertyName, propertyValue);
        return temp;
    }

    @Override
    public @NonNull Maybe<PropertyValue<?>> getProperty(@NonNull PropertyName name) {
        requireNonNull(name);
        if (properties.containsKey(name)) {
            return getExistingProperty(name);
        } else {
            return Maybe.empty();
        }
    }

    private Maybe<PropertyValue<?>> getExistingProperty(PropertyName name) {
        var property = properties.get(name);
        return Maybe.just(property);
    }

    @Override
    public @NonNull Map<PropertyName, PropertyValue<?>> properties() {
        return new HashMap<>(properties);
    }

    @Override
    public @NonNull Single<Thing> removeProperty(@NonNull PropertyName name) {
        var temp = newMapWithoutValue(name);
        var newThing = new InMemoryThing(temp);
        return Single.just(newThing);
    }

    protected HashMap<PropertyName, PropertyValue<?>> newMapWithoutValue(PropertyName name) {
        requireNonNull(name);
        var temp = new HashMap<>(properties);
        temp.remove(name);
        return temp;
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
