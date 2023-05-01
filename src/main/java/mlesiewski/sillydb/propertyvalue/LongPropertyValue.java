package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;
import mlesiewski.sillydb.*;

/**
 * Long value of the property in a {@link Thing}.
 */
public class LongPropertyValue extends NumericPropertyValue<Long> {

    /**
     * Creates new {@link LongPropertyValue} objects.
     *
     * @see NumericPropertyValue#NumericPropertyValue(Number) 
     * @param value to be wrapped
     */
    public LongPropertyValue(@NonNull Long value) {
        super(value);
    }

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param value new value
     * @return new instance
     * @throws NullPointerException if the value provided is null
     */
    public static LongPropertyValue longPropertyValue(@NonNull Long value) {
        return new LongPropertyValue(value);
    }
}
