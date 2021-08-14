package mlesiewski.sillydb.propertyvalue;

import io.reactivex.rxjava3.annotations.*;
import mlesiewski.sillydb.*;

import java.util.*;

import static java.util.Objects.*;

/**
 * Value of the property in a {@link Thing}.
 *
 * @param <T> type of the property value
 */
public abstract class PropertyValue <T> implements Comparable<PropertyValue<T>> {

    /**
     * value hold by this object
     */
    protected final T value;

    /**
     * Creates a new instance.
     *
     * @param value new value
     * @throws NullPointerException if the value provided is null
     */
    protected PropertyValue(@NonNull T value) {
        this.value = requireNonNull(value);
    }

    /**
     * Returns the value hold.
     *
     * @return value hold
     */
    public T value() {
        return value;
    }

    /**
     * Converts the value hold to {@link String} and returns it.
     * Conversion is made using `String.valueOf(value());`
     *
     * @return String representation of the value
     */
    public String valueAsString() {
        return String.valueOf(value());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        var that = (PropertyValue<?>) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return hash(value);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{" +
                "value=" + value +
                '}';
    }
}
