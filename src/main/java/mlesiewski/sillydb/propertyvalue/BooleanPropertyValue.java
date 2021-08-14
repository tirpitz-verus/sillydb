package mlesiewski.sillydb.propertyvalue;

import mlesiewski.sillydb.*;

/**
 * Boolean value of the property in a {@link Thing}.
 */
public class BooleanPropertyValue extends PropertyValue<Boolean> {

    /**
     * {@inheritDoc}
     */
    public BooleanPropertyValue(boolean value) {
        super(value);
    }

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param value new value
     * @return new instance
     */
    public static BooleanPropertyValue booleanPropertyValue(boolean value) {
        return new BooleanPropertyValue(value);
    }

    @Override
    public int compareTo(PropertyValue<Boolean> o) {
        return value().compareTo(o.value());
    }
}
