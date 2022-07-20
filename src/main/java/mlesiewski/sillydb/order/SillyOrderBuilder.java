package mlesiewski.sillydb.order;

import mlesiewski.sillydb.*;

import java.util.Optional;

import static mlesiewski.sillydb.order.SillyOrderType.*;

/**
 * Builds an instance of {@link SillyOrder}.
 */
public class SillyOrderBuilder {

    /**
     * No order should be forced on the results.
     */
    public static final SillyOrder NO_ORDER = new NoSillyOrder();

    private PropertyName propertyName;
    private SillyOrder current;

    /**
     * Starts the builder chain
     *
     * @return next step in the builder call chain
     */
    public static SillyOrderBuilder orderBy() {
        return new SillyOrderBuilder();
    }

    /**
     * Allows defining of the name of the property to order by.
     *
     * @param propertyName name of the property by which the result will be ordered by
     * @return next step in the builder call chain
     */
    public SinglePropertySillyOrderBuilder property(PropertyName propertyName) {
        this.propertyName = propertyName;
        return new SinglePropertySillyOrderBuilder();
    }

    void add(SillyOrder order) {
        current = Optional.ofNullable(current)
                .map(o -> ((SillyOrder) new CombinedSillyOrder(current, order)))
                .orElse(order);
    }

    /**
     * Defines the type of the ordering by a single property.
     */
    public class SinglePropertySillyOrderBuilder {

        /**
         * Sets the order type as {@link SillyOrderType#ASCENDING}.
         *
         * @return builder instance
         */
        public SillyOrderBuilderFinished asc() {
            add(new SinglePropertySillyOrder(propertyName, ASCENDING));
            return new SillyOrderBuilderFinished();
        }

        /**
         * Sets the order type as {@link SillyOrderType#DESCENDING}.
         *
         * @return builder instance
         */
        public SillyOrderBuilderFinished desc() {
            add(new SinglePropertySillyOrder(propertyName, DESCENDING));
            return new SillyOrderBuilderFinished();
        }
    }

    /**
     * Returned after an order for a property was defined.
     */
    public class SillyOrderBuilderFinished {

        /**
         * Allows to combine properties.
         *
         * @return builder for ordering by a new property
         */
        public SillyOrderBuilder and() {
            return SillyOrderBuilder.this;
        }

        /**
         * Builds returns the {@link SillyOrder} based on the provided criteria.
         *
         * @return resulting order
         */
        public SillyOrder build() {
            return current;
        }
    }
}
