package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;
import mlesiewski.sillydb.*;

/**
 * Boolean value of the property in a {@link Thing}.
 */
public class BooleanPropertyValue extends  PropertyValue<Boolean> {

    /**
     * {@inheritDoc}
     */
    public BooleanPropertyValue(@NonNull Boolean value) {
        super(value);
    }

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param value new value
     * @return new instance
     * @throws NullPointerException if the value provided is null
     */
    public static BooleanPropertyValue booleanPropertyValue(@NonNull Boolean value) {
        return new BooleanPropertyValue(value);
    }
}
