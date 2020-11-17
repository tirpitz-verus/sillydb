package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;

import java.util.*;

class BaseCategory implements Category {

    private final CategoryName name;
    private final SillyDb db;
    private final Map<ThingName, NamedThing> things;

    BaseCategory(CategoryName name, SillyDb db) {
        this.name = name;
        this.db = db;
        things = new HashMap<>();
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
        return null;
    }

    @Override
    public Maybe<NamedThing> findBy(ThingName name) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseCategory)) {
            return false;
        }
        BaseCategory that = (BaseCategory) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
