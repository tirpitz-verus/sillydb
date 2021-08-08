package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import static java.lang.Boolean.*;
import static java.util.Objects.*;

class StringEqualitySillyPredicate <T> implements SillyPredicate {

    private final PropertyName propertyName;
    private final String value;

    StringEqualitySillyPredicate(PropertyName propertyName, T value) {
        this.propertyName = requireNonNull(propertyName);
        this.value = valueAsString(requireNonNull(value));
    }

    private String valueAsString(T value) {
        return String.valueOf(value);
    }

    @Override
    public boolean test(NamedThing thing) {
        return thing.getProperty(propertyName)
                .map(p -> value.equals(p.valueAsString()))
                .defaultIfEmpty(FALSE)
                .blockingGet();
    }
}
