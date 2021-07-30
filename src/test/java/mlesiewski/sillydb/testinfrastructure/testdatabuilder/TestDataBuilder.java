package mlesiewski.sillydb.testinfrastructure.testdatabuilder;

import mlesiewski.sillydb.*;

public class TestDataBuilder implements GenericBuilder, PropertyBuilder {

    private final SillyDb db;

    private Category lastCategory;
    private NamedThing lastThing;

    private TestDataBuilder(SillyDb db) {
        this.db = db;
    }

    public static GenericBuilder using(SillyDb db) {
        return new TestDataBuilder(db);
    }

    @Override
    public GenericBuilder withCategory(CategoryName categoryName) {
        forceNewCategory(categoryName);
        return this;
    }

    private void forceNewCategory(CategoryName categoryName) {
        withoutCategory(categoryName);
        db.createCategory(categoryName)
                .doAfterSuccess(this::setLastCategory)
                .test()
                .assertComplete()
                .dispose();
    }

    private void setLastCategory(Category object) {
        lastCategory = object;
    }

    @Override
    public PropertyBuilder withThing() {
        var thing = lastCategory.createNewThing();
        lastCategory.put(thing)
                .doAfterSuccess(this::setLastThing)
                .test()
                .assertComplete()
                .dispose();
        return this;
    }

    @Override
    public Category getCategory() {
        return lastCategory;
    }

    @Override
    public GenericBuilder withoutCategory(CategoryName categoryName) {
        if (db.categoryExists(categoryName)) {
            db.deleteCategory(categoryName)
                    .test()
                    .assertComplete()
                    .dispose();
        }
        return this;
    }

    private void setLastThing(NamedThing namedThing) {
        lastThing = namedThing;
    }

    @Override
    public PropertyBuilder withProperty(PropertyName name, String value) {
        var propertyValue = new StringPropertyValue(value);
        return withProperty(name, propertyValue);
    }

    @Override
    public <T> PropertyBuilder withProperty(PropertyName name, PropertyValue<T> value) {
        var thing = lastThing.setProperty(name, value)
                .flatMap(t -> lastCategory.put(t))
                .blockingGet();
        setLastThing(thing);
        return this;
    }

    @Override
    public ThingWithCategory getThing() {
        return new ThingWithCategory(lastThing, lastCategory);
    }
}
