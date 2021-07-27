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
}
