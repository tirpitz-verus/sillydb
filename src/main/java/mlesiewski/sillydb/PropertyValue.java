package mlesiewski.sillydb;

import io.reactivex.rxjava3.annotations.*;

import java.util.*;

import static java.util.Objects.*;

/**
 * Value of the property in a {@link Thing}.
 */
public record PropertyValue(String value) {

    /**
     * Creates a new instance.
     *
     * @param value new value
     * @throws NullPointerException if the value provided is null
     */
    public PropertyValue(@NonNull String value) {
        this.value = requireNonNull(value);
    }

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param value new value
     * @return new instance
     * @throws NullPointerException if the value provided is null
     */
    public static PropertyValue propertyValue(@NonNull String value) {
        return new PropertyValue(value);
    }
}
