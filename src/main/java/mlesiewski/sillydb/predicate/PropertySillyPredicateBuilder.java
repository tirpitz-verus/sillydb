package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.propertyvalue.*;

import java.math.*;
import java.util.regex.*;

/**
 * Creates specific predicate types.
 */
public interface PropertySillyPredicateBuilder {

    /**
     * Creates a {@link StringEqualitySillyPredicate} instance and stores it.
     *
     * @param value pattern to use
     * @param <T> type of the pattern to use
     * @return next part of the call chain
     */
    <T> SillyPredicateBuilder valueIsEqualTo(T value);

    /**
     * Creates a {@link Pattern} instance form the regexp provided and calls {@link PropertySillyPredicateBuilder#valueMatches(Pattern)}.
     *
     * @param regexp pattern to use
     * @return next part of the call chain
     * @see PropertySillyPredicateBuilder#valueMatches(Pattern)
     */
    SillyPredicateBuilder valueMatches(String regexp);

    /**
     * Creates a {@link RegExpSillyPredicate} instance and stores it.
     * {@link PropertyValue PropertyValues} will be tested by calling {@link PropertyValue#valueAsString()}.
     * This means that even non-String values can be tested against without error.
     *
     * @param pattern pattern to use
     * @return next part of the call chain
     */
    SillyPredicateBuilder valueMatches(Pattern pattern);

    /**
     * Creates a {@link PropertyExistenceSillyPredicate} instance and stores it.
     *
     * @return next part of the call chain
     */
    SillyPredicateBuilder exists();

    /**
     *
     * @param threshold
     * @return
     */
    SillyPredicateBuilder valueIsGraterThan(BigDecimal threshold);
}
