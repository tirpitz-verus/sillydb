package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

/**
 * A predicate that tests if the value is less than to the threshold.
 */
public class NumberLessThanSillyPredicate extends NumberComparingSillyPredicate {

    <T extends Number & Comparable<T>> NumberLessThanSillyPredicate(PropertyName propertyName, T threshold) {
        super(propertyName, threshold);
    }

    @Override
    protected boolean test(int compareResult) {
        return compareResult < 0;
    }
}
