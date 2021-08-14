package mlesiewski.sillydb.order;

import mlesiewski.sillydb.*;

import static mlesiewski.sillydb.order.SillyOrderType.*;

/**
 * Builds an instance of {@link SillyOrder}.
 */
public class SillyOrderBuilder {

    private PropertyName propertyName;

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

    /**
     * Defines the type of the ordering by a single property.
     */
    public class SinglePropertySillyOrderBuilder {

        /**
         * Sets the order type as {@link SillyOrderType#ASCENDING}.
         *
         * @return new order instance
         */
        public SillyOrder desc() {
            return new SillyOrder(propertyName, DESCENDING);
        }

        /**
         * Sets the order type as {@link SillyOrderType#DESCENDING}.
         *
         * @return new order instance
         */
        public SillyOrder asc() {
            return new SillyOrder(propertyName, ASCENDING);
        }
    }
}
