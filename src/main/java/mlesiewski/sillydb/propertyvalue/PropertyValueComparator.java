package mlesiewski.sillydb.propertyvalue;

import java.util.*;

/**
 * Compares generic {@link PropertyValue} instances.
 * In case of a type mismatch it will throw {@link CannotComparePropertyValues} instead of a {@link ClassCastException}.
 */
public class PropertyValueComparator implements Comparator<PropertyValue<?>> {

    @Override
    public int compare(PropertyValue<?> propertyValue1, PropertyValue<?> propertyValue2) {
        if (propertyValue1 == null) {
            if (propertyValue2 == null) {
                return 0;
            } else {
                return -1;
            }
        } else if (propertyValue2 == null) {
            return 1;
        } else {
            return genericAwareCompareValues(propertyValue1, propertyValue2);
        }
    }

    @SuppressWarnings("unchecked")
    private int genericAwareCompareValues(PropertyValue<?> propertyValue1, PropertyValue<?> propertyValue2) {
        try {
            var cast = propertyValue1.getClass().cast(propertyValue2);
            return propertyValue1.compareTo(cast);
        } catch (ClassCastException e) {
            throw new CannotComparePropertyValues(propertyValue1, propertyValue2, e);
        }
    }
}
