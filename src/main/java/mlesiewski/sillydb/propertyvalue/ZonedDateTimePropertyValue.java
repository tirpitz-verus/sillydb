package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;
import mlesiewski.sillydb.*;

import java.time.*;

/**
 * ZonedDateTime value of the property in a {@link Thing}.
 */
public class ZonedDateTimePropertyValue extends TemporalPropertyValue<ZonedDateTime> {

    /**
     * {@inheritDoc}
     */
    public ZonedDateTimePropertyValue(@NonNull ZonedDateTime value) {
        super(value);
    }
}
