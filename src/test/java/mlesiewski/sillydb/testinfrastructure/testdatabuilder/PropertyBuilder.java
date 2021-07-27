package mlesiewski.sillydb.testinfrastructure.testdatabuilder;

import mlesiewski.sillydb.*;

public interface PropertyBuilder extends GenericBuilder {

    PropertyBuilder withProperty(PropertyName name, String stringValue);
    PropertyBuilder withProperty(PropertyName name, PropertyValue value);
    ThingWithCategory getThing();
}
