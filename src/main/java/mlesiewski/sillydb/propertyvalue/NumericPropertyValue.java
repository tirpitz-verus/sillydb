package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;

/**
 * Represents a number property value in a {@link mlesiewski.sillydb.Thing}
 *
 * @param <T> a number class
 */
public abstract class NumericPropertyValue <T extends Number & Comparable<T>> extends PropertyValue<T> {

    /**
     * Creates new objects.
     *
     * @param value to be wrapped
     */
    protected NumericPropertyValue(@NonNull T value) {
        super(value);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T value() {
        return super.value();
    }

    @Override
    public int compareTo(PropertyValue<T> o) {
        return value().compareTo(o.value());
    }
}
