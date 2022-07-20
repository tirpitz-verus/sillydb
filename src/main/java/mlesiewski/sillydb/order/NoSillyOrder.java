package mlesiewski.sillydb.order;

import mlesiewski.sillydb.NamedThing;

class NoSillyOrder implements SillyOrder {
    @Override
    public int compare(NamedThing o1, NamedThing o2) {
        throw new IllegalStateException("NoSillyOrder isn't designed to compare anything");
    }
}
