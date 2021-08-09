package mlesiewski.sillydb.predicate;

import mlesiewski.sillydb.*;

import java.math.*;
import java.time.temporal.*;
import java.util.function.*;
import java.util.regex.*;

/**
 * Fluent builder for {@link SillyPredicate SillyPredicates}.
 *
 * <pre>{@code
 * // example usage:
 * var sweetAndNotRed = predicateWhere()
 *      .property(propertyName("taste"))
 *          .valueIsEqualTo("sweet")
 *      .and()
 *      not().property(propertyName("color"))
 *          .valueIsEqualTo("red")
 *      .build();
 * }</pre>
 */
public final class SillyPredicateBuilder {

    SillyPredicate currentPredicate;
    Function<PropertyName, PropertySillyPredicateBuilder> nextPredicateBuilder = SinglePredicateBuilder::new;

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
        var predicateBuilder = nextPredicateBuilder.apply(propertyName);
        nextPredicateBuilder = SinglePredicateBuilder::new;
        return predicateBuilder;
    }

    class SinglePredicateBuilder implements PropertySillyPredicateBuilder {

        private final PropertyName propertyName;

        SinglePredicateBuilder(PropertyName propertyName) {
            this.propertyName = propertyName;
        }

        @Override
        public <T> SillyPredicateBuilder valueIsEqualTo(T value) {
            setCurrent(new StringEqualitySillyPredicate<T>(propertyName, value));
            return SillyPredicateBuilder.this;
        }

        @Override
        public SillyPredicateBuilder valueMatches(String regexp) {
            var pattern = Pattern.compile(regexp);
            return valueMatches(pattern);
        }

        @Override
        public SillyPredicateBuilder valueMatches(Pattern pattern) {
            setCurrent(new RegExpSillyPredicate(propertyName, pattern));
            return SillyPredicateBuilder.this;
        }

        @Override
        public SillyPredicateBuilder exists() {
            setCurrent(new PropertyExistenceSillyPredicate(propertyName));
            return SillyPredicateBuilder.this;
        }

        @Override
        public <T extends Number & Comparable<T>> SillyPredicateBuilder valueIsGraterThan(T threshold) {
            setCurrent(new NumberGraterThanSillyPredicate(propertyName, threshold));
            return SillyPredicateBuilder.this;
        }

        @Override
        public <T extends Number & Comparable<T>> SillyPredicateBuilder valueIsGraterThanOrEqualTo(T threshold) {
            setCurrent(new NumberGraterOrEqualToSillyPredicate(propertyName, threshold));
            return SillyPredicateBuilder.this;
        }

        @Override
        public <T extends Number & Comparable<T>> SillyPredicateBuilder valueIsLessThan(T threshold) {
            setCurrent(new NumberLessThanSillyPredicate(propertyName, threshold));
            return SillyPredicateBuilder.this;
        }

        @Override
        public <T extends Number & Comparable<T>> SillyPredicateBuilder valueIsLessThanOrEqualTo(T threshold) {
            setCurrent(new NumberLessOrEqualToSillyPredicate(propertyName, threshold));
            return SillyPredicateBuilder.this;
        }

        @Override
        public <T extends Temporal> SillyPredicateBuilder valueIsAfter(T threshold) {
            setCurrent(new TemporalIsAfterSillyPredicate(propertyName, threshold));
            return SillyPredicateBuilder.this;
        }

        @Override
        public <T extends Temporal> SillyPredicateBuilder valueIsAfterOrEqualTo(T threshold) {
            setCurrent(new TemporalIsAfterOrEqualSillyPredicate(propertyName, threshold));
            return SillyPredicateBuilder.this;
        }

        @Override
        public <T extends Temporal> SillyPredicateBuilder valueIsBefore(T threshold) {
            setCurrent(new TemporalIsBeforeSillyPredicate(propertyName, threshold));
            return SillyPredicateBuilder.this;
        }

        @Override
        public <T extends Temporal> SillyPredicateBuilder valueIsBeforeOrEqualTo(T threshold) {
            setCurrent(new TemporalIsBeforeOrEqualSillyPredicate(propertyName, threshold));
            return SillyPredicateBuilder.this;
        }

        void setCurrent(SillyPredicate predicate) {
            currentPredicate = predicate;
        }
    }

    /**
     * Ends the call chain.
     *
     * @return predicate result
     */
    public SillyPredicate build() {
        return currentPredicate;
    }

    /**
     * Combines the current predicate with the next one
     *
     * @return next part of the call chain
     */
    public SillyPredicateBuilder and() {
        nextPredicateBuilder = AndPredicateBuilder::new;
        return this;
    }

    class AndPredicateBuilder extends SinglePredicateBuilder {

        AndPredicateBuilder(PropertyName propertyName) {
            super(propertyName);
        }

        @Override
        void setCurrent(SillyPredicate nextPredicate) {
            currentPredicate = currentPredicate.and(nextPredicate);
        }
    }

    /**
     * Combines the current predicate with the next one
     *
     * @return next part of the call chain
     */
    public SillyPredicateBuilder or() {
        nextPredicateBuilder = OrPredicateBuilder::new;
        return this;
    }

    class OrPredicateBuilder extends SinglePredicateBuilder {

        OrPredicateBuilder(PropertyName propertyName) {
            super(propertyName);
        }

        @Override
        void setCurrent(SillyPredicate nextPredicate) {
            currentPredicate = currentPredicate.or(nextPredicate);
        }
    }

    /**
     * Negates the next predicate.
     *
     * @return next part of the call chain
     */
    public SillyPredicateBuilder not() {
        nextPredicateBuilder = NotPredicateBuilder::new;
        return this;
    }

    class NotPredicateBuilder extends SinglePredicateBuilder {

        NotPredicateBuilder(PropertyName propertyName) {
            super(propertyName);
        }

        @Override
        void setCurrent(SillyPredicate nextPredicate) {
            currentPredicate = nextPredicate.not();
        }
    }
}
