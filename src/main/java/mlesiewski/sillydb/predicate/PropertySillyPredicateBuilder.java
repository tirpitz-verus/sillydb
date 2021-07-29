package mlesiewski.sillydb.predicate;

import java.util.regex.*;

/**
 * Creates specific predicate types.
 */
public interface PropertySillyPredicateBuilder {

    /**
     * Creates a {@link StringEqualitySillyPredicate} instance and stores it.
     *
     * @param value pattern to use
     * @return next part of the call chain
     */
    SillyPredicateBuilder valueIsEqualTo(String value);

    /**
     * Creates a {@link Pattern} instance form the regexp provided and calls {@link PropertySillyPredicateBuilder#valueMatches(Pattern)}.
     *
     * @param regexp pattern to use
     * @return next part of the call chain
     */
    SillyPredicateBuilder valueMatches(String regexp);

    /**
     * Creates a {@link RegExpSillyPredicate} instance and stores it.
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
}
