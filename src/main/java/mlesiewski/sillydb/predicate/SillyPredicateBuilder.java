package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.util.regex.*;

/**
 * Fluent builder for {@link SillyPredicate SillyPredicates}.
 *
 * <pre>{@code
 * // example usage:
 * predicateWhere()
 *      .property(propertyName("taste"))
 *      .valueIsEqualTo("sweet")
 *      .build();
 * }</pre>
 */
public final class SillyPredicateBuilder {

    SillyPredicate current;

    /**
     * A convenience method to start the builder call chain. Can be statically imported.
     *
     * @return next part of the call chain
     */
    public static SillyPredicateBuilder predicateWhere() {
        return new SillyPredicateBuilder();
    }

    /**
     * Starts a call chain for a single predicate.
     *
     * @param propertyName name of the property that the predicate will test against
     * @return next part of the call chain
     */
    public PropertySillyPredicateBuilder property(PropertyName propertyName) {
        return new PropertySillyPredicateBuilder(propertyName);
    }

    /**
     * Creates specific predicate types.
     */
    public class PropertySillyPredicateBuilder {

        private final PropertyName propertyName;

        PropertySillyPredicateBuilder(PropertyName propertyName) {
            this.propertyName = propertyName;
        }

        /**
         * Creates a {@link StringEqualitySillyPredicate} instance and stores it.
         *
         * @param value pattern to use
         * @return next part of the call chain
         */
        public SillyPredicateBuilder valueIsEqualTo(String value) {
            current = new StringEqualitySillyPredicate(propertyName, value);
            return SillyPredicateBuilder.this;
        }

        /**
         * Creates a {@link Pattern} instance form the regexp provided and calls {@link PropertySillyPredicateBuilder#valueMatches(Pattern)}.
         *
         * @param regexp pattern to use
         * @return next part of the call chain
         */
        public SillyPredicateBuilder valueMatches(String regexp) {
            var pattern = Pattern.compile(regexp);
            return valueMatches(pattern);
        }

        /**
         * Creates a {@link RegExpSillyPredicate} instance and stores it.
         *
         * @param pattern pattern to use
         * @return next part of the call chain
         */
        public SillyPredicateBuilder valueMatches(Pattern pattern) {
            current = new RegExpSillyPredicate(propertyName, pattern);
            return SillyPredicateBuilder.this;
        }
    }

    /**
     * Ends the call chain.
     *
     * @return predicate result
     */
    public SillyPredicate build() {
        return current;
    }
}
