package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import static java.lang.Boolean.*;
import static java.util.Objects.*;

class StringEqualitySillyPredicate <T> implements SillyPredicate {

    private final PropertyName propertyName;
    private final T value;

    StringEqualitySillyPredicate(PropertyName propertyName, T value) {
        this.propertyName = requireNonNull(propertyName);
        this.value = requireNonNull(value);
    }

    @Override
    public boolean test(NamedThing thing) {
        return thing.getProperty(propertyName)
                .map(p -> value.equals(p.value()))
                .defaultIfEmpty(FALSE)
                .blockingGet();
    }
}
