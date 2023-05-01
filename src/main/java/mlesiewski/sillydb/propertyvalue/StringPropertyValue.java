package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;
import mlesiewski.sillydb.*;

/**
 * String value of the property in a {@link Thing}.
 */
public class StringPropertyValue extends PropertyValue<String> {

    /**
     * Creates new objects.
     *
     * @see PropertyValue#PropertyValue(Object)
     * @param value to be wrapped
     */
    public StringPropertyValue(@NonNull String value) {
        super(value);
    }

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param value new value
     * @return new instance
     * @throws NullPointerException if the value provided is null
     */
    public static StringPropertyValue stringPropertyValue(@NonNull String value) {
        return new StringPropertyValue(value);
    }

    @Override
    public int compareTo(PropertyValue<String> o) {
        return value().compareTo(o.value());
    }
}
