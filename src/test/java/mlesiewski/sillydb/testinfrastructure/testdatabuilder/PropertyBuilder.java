package mlesiewski.sillydb.testinfrastructure.testdatabuilder;

import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

public interface PropertyBuilder extends GenericBuilder {

    PropertyBuilder withProperty(PropertyName name, String value);

    PropertyBuilder withProperty(PropertyName name, Boolean value);

    <T> PropertyBuilder withProperty(PropertyName name, PropertyValue<T> value);
    ThingWithCategory getThing();
}
