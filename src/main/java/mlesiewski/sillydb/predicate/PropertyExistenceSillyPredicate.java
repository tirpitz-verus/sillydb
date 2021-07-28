package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

class PropertyExistenceSillyPredicate implements SillyPredicate {

    private final PropertyName propertyName;

    PropertyExistenceSillyPredicate(PropertyName propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public boolean test(NamedThing thing) {
        return !thing.getProperty(propertyName)
                .isEmpty()
                .blockingGet();
    }
}
