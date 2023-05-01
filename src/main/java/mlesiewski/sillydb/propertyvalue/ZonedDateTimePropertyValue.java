package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;
import mlesiewski.sillydb.*;

import java.time.*;
import java.time.temporal.Temporal;

/**
 * ZonedDateTime value of the property in a {@link Thing}.
 */
public class ZonedDateTimePropertyValue extends TemporalPropertyValue<ZonedDateTime> {

    /**
     * Creates new objects.
     *
     * @see TemporalPropertyValue#TemporalPropertyValue(Temporal)
     * @param value to be wrapped
     */
    public ZonedDateTimePropertyValue(@NonNull ZonedDateTime value) {
        super(value);
    }

    @Override
    public int compareTo(PropertyValue<ZonedDateTime> o) {
        return value().compareTo(o.value());
    }
}
