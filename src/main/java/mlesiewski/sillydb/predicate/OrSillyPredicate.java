package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

class OrSillyPredicate implements SillyPredicate {

    final SillyPredicate first;
    final SillyPredicate second;

    OrSillyPredicate(SillyPredicate first, SillyPredicate second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean test(NamedThing thing) {
        return first.test(thing) || second.test(thing);
    }
}
