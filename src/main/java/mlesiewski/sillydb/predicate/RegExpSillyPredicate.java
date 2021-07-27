package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.util.regex.*;

public class RegExpSillyPredicate implements SillyPredicate {

    private final PropertyName propertyName;
    private final Pattern pattern;

    public RegExpSillyPredicate(PropertyName propertyName, Pattern pattern) {

        this.propertyName = propertyName;
        this.pattern = pattern;
    }

    @Override
    public boolean test(NamedThing thing) {
        return thing.getProperty(propertyName)
                .filter(p -> pattern.matcher(p.value()).matches())
                .isEmpty()
                .blockingGet();
    }
}
