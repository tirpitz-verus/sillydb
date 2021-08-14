package mlesiewski.sillydb.testinfrastructure.testdatabuilder;

import mlesiewski.sillydb.*;

public interface GenericBuilder {

    GenericBuilder withCategory(CategoryName categoryName);

    PropertyBuilder withThing();

    Category getCategory();

    GenericBuilder withoutCategory(CategoryName categoryName);
}
