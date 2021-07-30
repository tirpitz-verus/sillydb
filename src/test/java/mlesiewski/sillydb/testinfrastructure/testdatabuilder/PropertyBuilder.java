package mlesiewski.sillydb.testinfrastructure.testdatabuilder;

import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

import java.math.*;

public interface PropertyBuilder extends GenericBuilder {

    PropertyBuilder withProperty(PropertyName name, String value);

    PropertyBuilder withProperty(PropertyName name, boolean value);

    PropertyBuilder withProperty(PropertyName name, BigDecimal value);

    <T> PropertyBuilder withProperty(PropertyName name, PropertyValue<T> value);

    ThingWithCategory getThing();
}
