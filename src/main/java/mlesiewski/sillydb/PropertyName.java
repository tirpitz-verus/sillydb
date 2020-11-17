package mlesiewski.sillydb;

import static java.util.Objects.requireNonNull;

public final class PropertyName extends Name {

    public static PropertyName propertyName(String name) {
        return new PropertyName(name);
    }

    public PropertyName(String name) {
        super(requireNonNull(name, "property name cannot be null"));
    }
}
