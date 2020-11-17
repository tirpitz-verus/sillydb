package mlesiewski.sillydb;

import static java.util.Objects.requireNonNull;

public final class ThingName extends Name {

    public static ThingName thingName(String name) {
        return new ThingName(name);
    }

    public ThingName(String name) {
        super(requireNonNull(name, "thing name cannot be null"));
    }
}
