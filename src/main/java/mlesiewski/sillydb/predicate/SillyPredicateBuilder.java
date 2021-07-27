package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.util.regex.*;

public final class SillyPredicateBuilder {

    SillyPredicate current;

    public static SillyPredicateBuilder predicateWhere() {
        return new SillyPredicateBuilder();
    }

    public PropertySillyPredicateBuilder property(PropertyName propertyName) {
        return new PropertySillyPredicateBuilder(propertyName);
    }

    public class PropertySillyPredicateBuilder {

        private final PropertyName propertyName;

        PropertySillyPredicateBuilder(PropertyName propertyName) {
            this.propertyName = propertyName;
        }

        public SillyPredicateBuilder valueIsEqualTo(String value) {
            current = new StringEqualitySillyPredicate(propertyName, value);
            return SillyPredicateBuilder.this;
        }

        public SillyPredicateBuilder valueMatches(String regexp) {
            var pattern = Pattern.compile(regexp);
            return valueMatches(pattern);
        }

        public SillyPredicateBuilder valueMatches(Pattern pattern) {
            current = new RegExpSillyPredicate(propertyName, pattern);
            return SillyPredicateBuilder.this;
        }
    }

    public SillyPredicate build() {
        return current;
    }
}
