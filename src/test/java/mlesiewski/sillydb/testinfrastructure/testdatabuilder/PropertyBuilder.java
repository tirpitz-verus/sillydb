package mlesiewski.sillydb.testinfrastructure.testdatabuilder;

import mlesiewski.sillydb.*;

public interface PropertyBuilder extends GenericBuilder {

    PropertyBuilder withProperty(PropertyName taste, String sweet);
    ThingWithCategory getThing();
}
