package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

import java.time.temporal.*;

import static java.time.temporal.ChronoUnit.*;
import static java.util.Objects.*;

/**
 * Base class for predicates that compare temporal types.
 */
public abstract class TemporalComparingSillyPredicate implements SillyPredicate {

    private final PropertyName propertyName;
    private final Temporal threshold;

    TemporalComparingSillyPredicate(PropertyName propertyName, Temporal threshold) {
        this.propertyName = propertyName;
        this.threshold = threshold;
    }

    @Override
    public boolean test(NamedThing thing) {
        var property = thing.getProperty(propertyName).blockingGet();
        if (isNull(property)) {
            return false;
        } else if (property instanceof TemporalPropertyValue npv) {
            var value = npv.value();
            var difference = NANOS.between(value, threshold);
            return test(difference);
        } else {
            throw new TemporalComparingSillyPredicateUsedToTestNonTemporalValue(property.getClass());
        }
    }

    /**
     * Each implementing class needs to make a decision based on the {@code NANOS.between(value, threshold)} call.
     *
     * @param difference result of the {@code NANOS.between(value, threshold)} call
     * @return whether the predicate criteria were met or not
     */
    protected abstract boolean test(long difference);
}
