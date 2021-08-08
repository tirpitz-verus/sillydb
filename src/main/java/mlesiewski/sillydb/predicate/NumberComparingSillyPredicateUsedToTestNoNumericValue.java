package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

/**
 * An error that happens when you try to test non-numeric value with a number comparing predicate.
 */
public final class NumberComparingSillyPredicateUsedToTestNoNumericValue extends SillyDbError {

    NumberComparingSillyPredicateUsedToTestNoNumericValue(
            Class<? extends PropertyValue> propertyClass) {
        super("NumberComparingSillyPredicate was used to test the value of " + propertyClass.getSimpleName() + " which isn't assignable from NumericPropertyValue");
    }
}
