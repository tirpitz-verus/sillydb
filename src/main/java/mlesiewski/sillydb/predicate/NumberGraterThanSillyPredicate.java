package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

/**
 * A predicate that tests if the value is grater than the threshold.
 */
public class NumberGraterThanSillyPredicate extends NumberComparingSillyPredicate {

    <T extends Number & Comparable<T>> NumberGraterThanSillyPredicate(PropertyName propertyName, T threshold) {
        super(propertyName, threshold);
    }

    @Override
    protected boolean test(int compareResult) {
        return compareResult > 0;
    }
}
