package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

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
    }

    public SillyPredicate build() {
        return current;
    }
}
