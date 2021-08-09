package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.time.temporal.*;

class TemporalIsAfterSillyPredicate extends TemporalComparingSillyPredicate {

    TemporalIsAfterSillyPredicate(PropertyName propertyName, Temporal threshold) {
        super(propertyName, threshold);
    }

    @Override
    protected boolean test(long difference) {
        return difference < 0;
    }
}
