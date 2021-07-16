package mlesiewski.sillydb.core;

import mlesiewski.sillydb.*;

import java.util.*;

class InMemoryNamedThing extends InMemoryThing implements NamedThing {

    private final ThingName name;

    InMemoryNamedThing(NamedThing namedThing) {
        super(namedThing);
        this.name = namedThing.name();
    }

    public InMemoryNamedThing(Thing thing, ThingName name) {
        super(thing);
        this.name = name;
    }

    private InMemoryNamedThing(HashMap<PropertyName, Property> properties, ThingName name) {
        super(properties);
        this.name = name;
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
