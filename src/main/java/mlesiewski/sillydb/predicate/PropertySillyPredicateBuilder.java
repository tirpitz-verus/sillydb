package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.propertyvalue.*;

import java.time.temporal.*;
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
     * Creates a {@link NumberGraterThanSillyPredicate} instance and stores it.
     *
     * @param threshold a threshold value to compare to
     * @param <T> a number class
     * @return next part of the call chain
     */
    <T extends Number & Comparable<T>> SillyPredicateBuilder valueIsGraterThan(T threshold);

    /**
     * Creates a {@link NumberGraterOrEqualToSillyPredicate} instance and stores it.
     *
     * @param threshold a threshold value to compare to
     * @param <T> a number class
     * @return next part of the call chain
     */
    <T extends Number & Comparable<T>> SillyPredicateBuilder valueIsGraterThanOrEqualTo(T threshold);

    /**
     * Creates a {@link NumberLessThanSillyPredicate} instance and stores it.
     *
     * @param threshold a threshold value to compare to
     * @param <T> a number class
     * @return next part of the call chain
     */
    <T extends Number & Comparable<T>> SillyPredicateBuilder valueIsLessThan(T threshold);

    /**
     * Creates a {@link NumberLessOrEqualToSillyPredicate} instance and stores it.
     *
     * @param threshold a threshold value to compare to
     * @param <T> a number class
     * @return next part of the call chain
     */
    <T extends Number & Comparable<T>> SillyPredicateBuilder valueIsLessThanOrEqualTo(T threshold);

    /**
     * Creates a {@link TemporalIsAfterSillyPredicate} instance and stores it.
     *
     * @param threshold a threshold value to compare to
     * @param <T> a temporal class
     * @return next part of the call chain
     */
    <T extends Temporal> SillyPredicateBuilder valueIsAfter(T threshold);

    /**
     * Creates a {@link TemporalIsAfterOrEqualSillyPredicate} instance and stores it.
     *
     * @param threshold a threshold value to compare to
     * @param <T> a temporal class
     * @return next part of the call chain
     */
    <T extends Temporal> SillyPredicateBuilder valueIsAfterOrEqualTo(T threshold);

    /**
     * Creates a {@link TemporalIsBeforeSillyPredicate} instance and stores it.
     *
     * @param threshold a threshold value to compare to
     * @param <T> a temporal class
     * @return next part of the call chain
     */
    <T extends Temporal> SillyPredicateBuilder valueIsBefore(T threshold);

    /**
     * Creates a {@link TemporalIsBeforeOrEqualSillyPredicate} instance and stores it.
     *
     * @param threshold a threshold value to compare to
     * @param <T> a temporal class
     * @return next part of the call chain
     */
    <T extends Temporal> SillyPredicateBuilder valueIsBeforeOrEqualTo(T threshold);
}
