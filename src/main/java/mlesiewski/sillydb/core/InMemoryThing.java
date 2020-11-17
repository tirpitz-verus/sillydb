package mlesiewski.sillydb.core;

import mlesiewski.sillydb.*;

import java.util.*;

class InMemoryThing implements Thing {

    private final HashMap<PropertyName, String> properties = new HashMap<>();

    @Override
    public void property(PropertyName name, String value) {
        properties.put(name, value);
    }
}
