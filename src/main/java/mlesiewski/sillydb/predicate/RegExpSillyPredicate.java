package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.util.regex.*;

import static java.lang.Boolean.*;

/**
 * A {@link SillyPredicate} implementation that tests if the specified property has a value matching the pattern.
 */
public class RegExpSillyPredicate implements SillyPredicate {

    private final PropertyName propertyName;
    private final Pattern pattern;

    /**
     * Constructs new instance.
     *
     * @param propertyName name of the property to test
     * @param pattern pattern to test the property value against
     */
    public RegExpSillyPredicate(PropertyName propertyName, Pattern pattern) {
        this.propertyName = propertyName;
        this.pattern = pattern;
    }

    @Override
    public boolean test(NamedThing thing) {
        return thing.getProperty(propertyName)
                .map(p -> pattern.matcher(p.valueAsString()).matches())
                .defaultIfEmpty(FALSE)
                .blockingGet();
    }
}
