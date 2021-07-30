package mlesiewski.sillydb;

import io.reactivex.rxjava3.annotations.*;

/**
 * String value of the property in a {@link Thing}.
 */
public class StringPropertyValue extends  PropertyValue<String> {

    /**
     * {@inheritDoc}
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
}
