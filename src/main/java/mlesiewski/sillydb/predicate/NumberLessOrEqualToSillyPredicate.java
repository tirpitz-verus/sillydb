package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

/**
 * A predicate that tests if the value is less or equal to the threshold.
 */
public class NumberLessOrEqualToSillyPredicate extends NumberComparingSillyPredicate {

    <T extends Number & Comparable<T>> NumberLessOrEqualToSillyPredicate(PropertyName propertyName, T threshold) {
        super(propertyName, threshold);
    }

    @Override
    protected boolean test(int compareResult) {
        return compareResult <= 0;
    }
}
