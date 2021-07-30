package mlesiewski.sillydb;

import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;

import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.StringPropertyValue.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;

@DisplayName("thing")
@TestMethodOrder(OrderAnnotation.class)
class ThingCrudTest {

    static final CategoryName CATEGORY_NAME = new CategoryName("pets");
    public static final PropertyName TASTE = propertyName("taste");
    public static final PropertyValue<String> SWEET = stringPropertyValue("sweet");
    public static final PropertyValue<String> SOUR = stringPropertyValue("sour");

    @ParameterizedTest(name = "{0}")
    @DisplayName("when saved it gets a name")
    @ArgumentsSource(AllDbTypes.class)
    void thingCanBeSaved(SillyDb sillyDb) {
        // given - a category
        var sweets = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .getCategory();

        // when - creating new thing
        var marshmallow = sweets.createNewThing();

        // when - saving new thing
        var saveResult = sweets.put(marshmallow);

        // then - save made no error
        saveResult.test()
                .assertNoErrors()
                .dispose();

        // then - saved thing has a name
        saveResult.test()
                .assertValue(Objects::nonNull)
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be found by name")
    @ArgumentsSource(AllDbTypes.class)
    void thingCanBeFoundByName(SillyDb sillyDb) {
        // given - new thing with a name
        var result = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .getThing();
        var savedMarshmallow = result.thing;
        var sweets = result.category;
        var marshmallowName = savedMarshmallow.name();

        // when - searching for a marshmallow
        sweets.findBy(marshmallowName).test()

                // then - search succeeds
                .assertValue(v -> v.name().equals(marshmallowName))
                .assertComplete()
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("retains properties")
    @ArgumentsSource(AllDbTypes.class)
    void thingRetainsProperties(SillyDb sillyDb) {
        // given - new thing with a property
        var result = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getThing();
        var sweets = result.category;
        var savedMarshmallow = result.thing;
        var marshmallowName = savedMarshmallow.name();

        // when - searching for a marshmallow
        sweets.findBy(marshmallowName).test()

                // then - search succeeds
                .assertValue(v -> v.name().equals(marshmallowName))
                .assertValue(v -> {
                    v.getProperty(TASTE)
                            .test()
                            .assertValue(p -> p.equals(SWEET))
                            .dispose();
                    return true;
                })
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be updated")
    @ArgumentsSource(AllDbTypes.class)
    void namedThingCanBeUpdated(SillyDb sillyDb) {
        // given - new named thing with no properties
        var result = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .getThing();
        var savedMarshmallow = result.thing;
        var sweets = result.category;
        var marshmallowName = savedMarshmallow.name();

        // when - add a property to the thing
        sweets.findBy(marshmallowName)
                .toSingle()
                .flatMap(t -> t.setProperty(TASTE, SWEET))
                .flatMap(sweets::put)
                .test()
                .assertValueCount(1)
                .assertNoErrors()
                .dispose();

        // then - taste was saved
        var namedSweetMarshmallow = sweets.findBy(marshmallowName)
                .blockingGet();
        namedSweetMarshmallow.getProperty(TASTE)
                .test()
                .assertResult(SWEET)
                .dispose();

        // when - change taste
        namedSweetMarshmallow.setProperty(TASTE, SOUR)
                .flatMap(sweets::put)
                .test()
                .assertComplete()
                .dispose();

        // then - taste is changed
        var namedSourMarshmallow = sweets.findBy(marshmallowName).blockingGet();
        namedSourMarshmallow.getProperty(TASTE)
                .test()
                .assertValue(SOUR)
                .dispose();

        // when - removing taste
        namedSourMarshmallow.removeProperty(TASTE)
                .flatMap(sweets::put)
                .test()
                .assertComplete()
                .assertNoErrors()
                .dispose();

        // then - there is no taste
        sweets.findBy(marshmallowName)
                .flatMap(thing -> thing.getProperty(TASTE))
                .test()
                .assertNoValues()
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be removed")
    @ArgumentsSource(AllDbTypes.class)
    void thingCanBeRemoved(SillyDb sillyDb) {
        // given - a category and a thing
        var result = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .getThing();
        var namedThing = result.thing;
        var sweets = result.category;

        // when
        sweets.remove(namedThing)
                .test()
                .assertComplete()
                .dispose();
        var maybe = sweets.findBy(namedThing.name());

        // then
        maybe.test()
                .assertComplete()
                .assertNoValues()
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can remove nonexistent property")
    @ArgumentsSource(AllDbTypes.class)
    void canRemoveNonexistentProperty(SillyDb sillyDb) {
        // given - a category and a thing and a property name
        var result = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .getThing();
        var tasteName = new PropertyName("taste");

        // when
        var single = result.thing.removeProperty(tasteName);

        // then
        single.test()
                .assertResult(result.thing)
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("getting nonexistent property yields empty Maybe")
    @ArgumentsSource(AllDbTypes.class)
    void gettingNonexistentPropertyYieldsEmptyMaybe(SillyDb sillyDb) {
        // given - a category and a thing and a property name
        var result = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .withThing()
                .getThing();
        var tasteName = new PropertyName("taste");

        // when - get
        var maybe = result.thing.getProperty(tasteName);

        // then
        maybe.test()
                .assertComplete()
                .assertNoValues()
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be removed if it was not in the category")
    @ArgumentsSource(AllDbTypes.class)
    void canRemoveNonexistentThing(SillyDb sillyDb) {
        // given - a category and a thing name
        var sweets = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .getCategory();
        var name = new ThingName("placebo");

        // when
        var completable = sweets.remove(name);

        // then
        completable.test()
                .assertComplete()
                .assertNoErrors()
                .dispose();
    }
}
