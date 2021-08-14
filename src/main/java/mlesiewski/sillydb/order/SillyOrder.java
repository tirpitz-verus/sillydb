package mlesiewski.sillydb.order;

import mlesiewski.sillydb.*;
import mlesiewski.sillydb.predicate.*;
import mlesiewski.sillydb.propertyvalue.*;

import java.util.*;

import static mlesiewski.sillydb.order.SillyOrderType.*;

/**
 * Defines the order of the {@link Thing Things} in the result of the {@link Category#findAllBy(SillyPredicate, SillyOrder)}
 */
public record SillyOrder(PropertyName propertyName, SillyOrderType orderType) implements Comparator<NamedThing> {

    /**
     * No order should be forced on the results.
     */
    public static final SillyOrder NO_ORDER = new SillyOrder(null, null);

    @Override
    public int compare(NamedThing thing1, NamedThing thing2) {
        var propertyValue1 = thing1.getProperty(propertyName).blockingGet();
        var propertyValue2 = thing2.getProperty(propertyName).blockingGet();
        var valueComparator = getComparator();
        return valueComparator.compare(propertyValue1, propertyValue2);
    }

    private Comparator<PropertyValue<?>> getComparator() {
        var valueComparator = new PropertyValueComparator();
        if (orderType == DESCENDING) {
            return valueComparator.reversed();
        }
        return valueComparator;
    }
}
