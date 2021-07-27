package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

@FunctionalInterface
public interface SillyPredicate {

    boolean test(NamedThing thing);
}
