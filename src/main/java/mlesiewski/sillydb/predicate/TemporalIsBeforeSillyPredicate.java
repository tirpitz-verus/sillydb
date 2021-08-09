package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.time.temporal.*;

class TemporalIsBeforeSillyPredicate extends TemporalComparingSillyPredicate {

    TemporalIsBeforeSillyPredicate(PropertyName propertyName, Temporal threshold) {
        super(propertyName, threshold);
    }

    @Override
    protected boolean test(long difference) {
        return difference > 0;
    }
}
