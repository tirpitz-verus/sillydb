package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import static java.util.Objects.*;

class StringEqualitySillyPredicate implements SillyPredicate {

    private final PropertyName propertyName;
    private final String value;

    StringEqualitySillyPredicate(PropertyName propertyName, String value) {
        this.propertyName = requireNonNull(propertyName);
        this.value = requireNonNull(value);
    }

    @Override
    public boolean test(NamedThing thing) {
        return thing.getProperty(propertyName)
                .filter(p -> value.equals(p.value()))
                .isEmpty()
                .blockingGet();
    }
}
