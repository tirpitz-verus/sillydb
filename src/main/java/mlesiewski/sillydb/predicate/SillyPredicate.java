package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

/**
 * Interface for predicates to specify what {@link NamedThing Things} to find in a {@link Category}.
 */
@FunctionalInterface
public interface SillyPredicate {

    /**
     * Test if the thing matches the requirements.
     *
     * @param thing a {@link NamedThing} to test
     * @return result of the test
     */
    boolean test(NamedThing thing);

    /**
     * Combines the current predicate with the next one using the logical AND operation.
     *
     * @param secondPredicate a predicate to combine this one with
     * @return new predicate that combines this predicate and the second one
     */
    default SillyPredicate and(SillyPredicate secondPredicate) {
        return new AndSillyPredicate(this, secondPredicate);
    }

    /**
     * Combines the current predicate with the next one using the logical OR operation.
     *
     * @param secondPredicate a predicate to combine this one with
     * @return new predicate that combines this predicate and the second one
     */
    default SillyPredicate or(SillyPredicate secondPredicate) {
        return new OrSillyPredicate(this, secondPredicate);
    }

    /**
     * Negates this predicate.
     *
     * @return new predicate that negates this predicate
     */
    default SillyPredicate not() {
        return new NotSillyPredicate(this);
    }
}
