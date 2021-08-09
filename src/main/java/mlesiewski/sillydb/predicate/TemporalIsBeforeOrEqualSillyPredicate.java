package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.time.temporal.*;

class TemporalIsBeforeOrEqualSillyPredicate extends TemporalComparingSillyPredicate {

    TemporalIsBeforeOrEqualSillyPredicate(PropertyName propertyName, Temporal threshold) {
        super(propertyName, threshold);
    }

    @Override
    protected boolean test(long difference) {
        return difference >= 0;
    }
}
