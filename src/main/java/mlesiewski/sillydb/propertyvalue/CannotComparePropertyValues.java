package mlesiewski.sillydb.propertyvalue;

import mlesiewski.sillydb.*;

/**
 * Indicates that two {@link PropertyValue} instances of different types were being compared.
 */
public class CannotComparePropertyValues extends SillyDbError {

    /**
     * Creates a new instance.
     *
     * @param pv1 value to which pv2 was compared to
     * @param pv2 value to which pv1 was compared to
     * @param root the root exception
     */
    public CannotComparePropertyValues(PropertyValue<?> pv1, PropertyValue<?> pv2, ClassCastException root) {
        super(makeMessage(pv1, pv2), root);
    }

    private static String makeMessage(PropertyValue<?> pv1, PropertyValue<?> pv2) {
        return "Cannot compare %s to %s".formatted(pv1.getClass().getSimpleName(), pv2.getClass().getSimpleName());
    }
}
