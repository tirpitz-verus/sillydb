package mlesiewski.sillydb.testinfrastructure.testdatabuilder;

import mlesiewski.sillydb.*;

public class ThingWithCategory {

    public final NamedThing thing;
    public final Category category;

    ThingWithCategory(NamedThing thing, Category category) {
        this.thing = thing;
        this.category = category;
    }
}
