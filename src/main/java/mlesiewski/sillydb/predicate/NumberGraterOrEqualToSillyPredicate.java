package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

/**
 * A predicate that tests if the value is grater or equal to the threshold.
 */
public class NumberGraterOrEqualToSillyPredicate extends NumberComparingSillyPredicate {

    <T extends Number & Comparable<T>> NumberGraterOrEqualToSillyPredicate(PropertyName propertyName, T threshold) {
        super(propertyName, threshold);
    }

    @Override
    protected boolean test(int compareResult) {
        return compareResult >= 0;
    }
}
