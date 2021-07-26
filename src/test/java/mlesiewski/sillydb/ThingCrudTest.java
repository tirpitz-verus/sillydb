package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;

import static mlesiewski.sillydb.CategoryName.*;
import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;
import static org.assertj.core.api.Assertions.*;

@DisplayName("thing")
@TestMethodOrder(OrderAnnotation.class)
class ThingCrudTest {

    static final CategoryName CATEGORY_NAME = new CategoryName("pets");

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
                .withProperty(propertyName("taste"), "sweet")
                .getThing();
        var sweets = result.category;
        var savedMarshmallow = result.thing;
        var marshmallowName = savedMarshmallow.name();

        // when - searching for a marshmallow
        sweets.findBy(marshmallowName).test()

        // then - search succeeds
                .assertValue(v -> v.name().equals(marshmallowName))
                .assertValue(v -> {
                    v.getProperty(propertyName("taste"))
                            .test()
                            .assertValue(p -> p.value().equals("sweet"))
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
        var marshmallowWithoutProperties = sweets.findBy(marshmallowName)
                .blockingGet();
        var sweetTaste = createSweetTaste();
        marshmallowWithoutProperties.setProperty(sweetTaste)
                .flatMap(sweets::put)
                .test()
                .assertValueCount(1)
                .dispose();

        // then - taste was saved
        var namedSweetMarshmallow = sweets.findBy(marshmallowName)
                .blockingGet();
        namedSweetMarshmallow.getProperty(sweetTaste.name())
                .test()
                .assertResult(sweetTaste)
                .dispose();

        // when - change taste
        var sourTaste = createSourTaste();
        namedSweetMarshmallow.setProperty(sourTaste)
                .flatMap(sweets::put)
                .test()
                .assertComplete()
                .dispose();

        // then - taste is changed
        var namedSourMarshmallow = sweets.findBy(marshmallowName).blockingGet();
        namedSourMarshmallow.getProperty(sourTaste.name())
                .test()
                .assertValue(sourTaste)
                .dispose();

        // when - removing taste
        namedSourMarshmallow.removeProperty(sourTaste.name())
                .flatMap(sweets::put)
                .test()
                .assertComplete()
                .assertNoErrors()
                .dispose();

        // then - there is no taste
        sweets.findBy(marshmallowName)
                .flatMap(thing -> thing.getProperty(sourTaste.name()))
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

    private Property createSweetTaste() {
        return new Property(propertyName("taste"), "sweet");
    }

    private Property createSourTaste() {
        return new Property(propertyName("taste"), "sour");
    }
}
