package mlesiewski.sillydb;

import static java.util.Objects.requireNonNull;

/**
 * Name for the {@link NamedThing}. It is assigned by the database.
 */
public final class ThingName extends Name {

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param name name for the thing
     * @return a new instance
     */
    public static ThingName thingName(String name) {
        return new ThingName(name);
    }

    /**
     * Creates new instances.
     *
     * @param name name for the thing
     */
    public ThingName(String name) {
        super(requireNonNull(name, "thing name cannot be null"));
    }
}
