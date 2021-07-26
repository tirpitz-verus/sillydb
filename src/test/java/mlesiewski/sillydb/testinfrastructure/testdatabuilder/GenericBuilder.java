package mlesiewski.sillydb.testinfrastructure.testdatabuilder;

import mlesiewski.sillydb.*;

public interface GenericBuilder {

    GenericBuilder withCategory(CategoryName shoes);

    PropertyBuilder withThing();

    Category getCategory();

    GenericBuilder withoutCategory(CategoryName categoryName);
}
