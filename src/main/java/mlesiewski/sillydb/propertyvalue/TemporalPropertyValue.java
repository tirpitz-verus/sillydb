package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;

import java.time.temporal.*;

/**
 * Represents a temporal property value in a {@link mlesiewski.sillydb.Thing}
 *
 * @param <T> a number class
 */
public abstract class TemporalPropertyValue<T extends Temporal> extends PropertyValue<T> {

    /**
     * Creates new objects.
     *
     * @see PropertyValue#PropertyValue(Object)
     * @param value to be wrapped
     */
    protected TemporalPropertyValue(@NonNull T value) {
        super(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T value() {
        return super.value();
    }
}
