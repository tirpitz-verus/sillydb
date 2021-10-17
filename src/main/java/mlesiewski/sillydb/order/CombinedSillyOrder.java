package mlesiewski.sillydb.order;

import mlesiewski.sillydb.NamedThing;

class CombinedSillyOrder implements SillyOrder {

    private final SillyOrder previous;
    private final SillyOrder next;

    CombinedSillyOrder(SillyOrder previous, SillyOrder next) {
        this.previous = previous;
        this.next = next;
    }

    @Override
    public int compare(NamedThing o1, NamedThing o2) {
        int priorityResult = previous.compare(o1, o2);
        if (priorityResult != 0) {
            return priorityResult;
        } else {
            return next.compare(o1, o2);
        }
    }
}
