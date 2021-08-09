package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.time.temporal.*;

class TemporalIsAfterOrEqualSillyPredicate extends TemporalComparingSillyPredicate {

    TemporalIsAfterOrEqualSillyPredicate(PropertyName propertyName, Temporal threshold) {
        super(propertyName, threshold);
    }

    @Override
    protected boolean test(long difference) {
        return difference <= 0;
    }
}
