package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

/**
 * An error that happens when one tries to test non-temporal value with a temporal predicate.
 */
public final class TemporalComparingSillyPredicateUsedToTestNonTemporalValue extends SillyDbError {

    /**
     * Creates a new instance.
     *
     * @param propertyClass class of the non-temporal property value being tested with a temporal predicate
     */
    public TemporalComparingSillyPredicateUsedToTestNonTemporalValue(
            Class<? extends PropertyValue> propertyClass) {
        super("TemporalComparingSillyPredicate was used to test the value of " + propertyClass.getSimpleName() + " which isn't assignable from TemporalPropertyValue");
    }
}
