package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

class NotSillyPredicate implements SillyPredicate {

    final SillyPredicate negated;

    NotSillyPredicate(SillyPredicate negated) {
        this.negated = negated;
    }

    @Override
    public boolean test(NamedThing thing) {
        return !negated.test(thing);
    }
}
