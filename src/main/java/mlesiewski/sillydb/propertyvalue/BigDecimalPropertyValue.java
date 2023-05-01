package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;
import mlesiewski.sillydb.*;

import java.math.*;

/**
 * BigDecimal value of the property in a {@link Thing}.
 */
public class BigDecimalPropertyValue extends  NumericPropertyValue<BigDecimal> {

    /**
     * Creates new objects.
     *
     * @see NumericPropertyValue#NumericPropertyValue(Number)
     * @param value to be wrapped
     */
    public BigDecimalPropertyValue(@NonNull BigDecimal value) {
        super(value);
    }

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param value new value
     * @return new instance
     * @throws NullPointerException if the value provided is null
     */
    public static BigDecimalPropertyValue bigDecimalPropertyValue(@NonNull BigDecimal value) {
        return new BigDecimalPropertyValue(value);
    }
}
