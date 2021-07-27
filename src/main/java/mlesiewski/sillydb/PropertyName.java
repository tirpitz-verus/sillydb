package mlesiewski.sillydb;

import static java.util.Objects.requireNonNull;

/**
 * Name of the property of a {@link Thing}.
 */
public final class PropertyName extends Name {

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param name name for the property
     * @return new instance
     */
    public static PropertyName propertyName(String name) {
        return new PropertyName(name);
    }

    /**
     * Creates a new instance.
     *
     * @param name name for the property
     * @throws NullPointerException if the name is null
     */
    public PropertyName(String name) {
        // TODO some name validation should happen
        super(requireNonNull(name, "property name cannot be null"));
    }
}
