package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.NamedThing;

class NoSillyPredicate implements SillyPredicate {

    @Override
    public boolean test(NamedThing thing) {
        throw new IllegalStateException("NoSillyPredicate isn't designed to test anything");
    }
}
