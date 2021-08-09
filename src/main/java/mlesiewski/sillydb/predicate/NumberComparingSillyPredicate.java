package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

import java.math.*;

import static java.util.Objects.*;

/**
 * Base class for predicates that compare numbers.
 */
public abstract class NumberComparingSillyPredicate implements SillyPredicate {

    private final PropertyName propertyName;
    private final BigDecimal threshold;

    /**
     * Creates a new instance.
     *
     * @param propertyName name of the property to compare
     * @param threshold value to compare to
     * @param <T> a number class
     */
    protected <T extends Number & Comparable<T>> NumberComparingSillyPredicate(PropertyName propertyName, T threshold) {
        this.propertyName = requireNonNull(propertyName);
        this.threshold = toBigDecimal(requireNonNull(threshold));
    }

    @Override
    public boolean test(NamedThing thing) {
        var property = thing.getProperty(propertyName).blockingGet();
        if (isNull(property)) {
            return false;
        } else if (property instanceof NumericPropertyValue npv) {
            var value = npv.value();
            var bigDecimal = toBigDecimal(value);
            var compareResult = bigDecimal.compareTo(threshold);
            return test(compareResult);
        } else {
            throw new NumberComparingSillyPredicateUsedToTestNonNumericValue(property.getClass());
        }
    }

    BigDecimal toBigDecimal(Number number) {
        if (Float.class.equals(number.getClass())) {
            return BigDecimal.valueOf(number.doubleValue());
        } else if (Double.class.equals(number.getClass())) {
            return BigDecimal.valueOf(number.doubleValue());
        } else if (BigDecimal.class.equals(number.getClass())) {
            return (BigDecimal) number;
        } else {
            return BigDecimal.valueOf(number.longValue());
        }
    }

    /**
     * Each class needs to compare the result of {@code value.compareTo(threshold)} call on its own.
     *
     * @param compareResult result of the {@code value.compareTo(threshold)} call
     * @return whether the test passed or not
     */
    protected abstract boolean test(int compareResult);
}
