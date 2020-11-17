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
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@DisplayName("thing")
@TestMethodOrder(OrderAnnotation.class)
@TestInstance(PER_CLASS)
class ThingCrudTest {

    @ParameterizedTest(name = "{0}")
    @DisplayName("when saved it gets a name")
    @ArgumentsSource(AllDbTypes.class)
    @Order(1)
    void thingCanBeSaved(SillyDb sillyDb) {
        // given - a category
        var sweets = sweets(sillyDb);

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
    @Order(2)
    void thingCanBeFoundByName(SillyDb sillyDb) {
        // given - new thing with a name
        var sweets = sweets(sillyDb);
        var marshmallow = sweets.createNewThing();
        var savedMarshmallow = sweets
                .put(marshmallow)
                .blockingGet();
        var marshmallowName = savedMarshmallow.name();

        // when - searching for a marshmallow
        var findResult = sweets.findBy(marshmallowName);

        // then - search succeeds
        var foundThing = findResult.blockingGet();
        assertThat(foundThing).isNotNull();
        assertThat(foundThing.name()).isEqualTo(marshmallowName);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("retains properties")
    @ArgumentsSource(AllDbTypes.class)
    @Order(3)
    void thingRetainsProperties(SillyDb sillyDb) {
        // given - new thing with a property
        var taste = createSweetTaste();
        var sweets = sweets(sillyDb);
        var marshmallow = sweets.createNewThing();
        var savedMarshmallow = marshmallow.setProperty(taste)
                .flatMap(sweets::put)
                .blockingGet();
        var marshmallowName = savedMarshmallow.name();

        // when - searching for a marshmallow
        var findResult = sweets.findBy(marshmallowName);

        // then - search succeeds
        var foundThing = findResult.blockingGet();
        assertThat(foundThing.name()).isEqualTo(marshmallowName);
        foundThing.getProperty(taste.name())
                .test()
                .assertResult(taste)
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be updated")
    @ArgumentsSource(AllDbTypes.class)
    @Order(4)
    void namedThingCanBeUpdated(SillyDb sillyDb) {
        // given - new named thing with no properties
        var sweets = sweets(sillyDb);
        var nameMarshmallow = sweets.createNewThing();
        var namedMarshmallow = sweets.put(nameMarshmallow).blockingGet();
        var marshmallowName = namedMarshmallow.name();

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
    @Order(4)
    void thingCanBeRemoved(SillyDb sillyDb) {
        // given - a category and a thing
        var sweets = sweets(sillyDb);
        var thing = sweets.createNewThing();
        var namedThing = sweets.put(thing).blockingGet();

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
    @Order(5)
    void canRemoveNonexistentProperty(SillyDb sillyDb) {
        // given - a category and a thing and a property name
        var sweets = sweets(sillyDb);
        var thing = sweets.createNewThing();
        var tasteName = new PropertyName("taste");

        // when
        var single = thing.removeProperty(tasteName);

        // then
        single.test()
                .assertResult(thing)
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("getting nonexistent property yields empty Maybe")
    @ArgumentsSource(AllDbTypes.class)
    @Order(6)
    void gettingNonexistentPropertyYieldsEmptyMaybe(SillyDb sillyDb) {
        // given - a category and a thing and a property name
        var sweets = sweets(sillyDb);
        var thing = sweets.createNewThing();
        var tasteName = new PropertyName("taste");

        // when - get
        var maybe = thing.getProperty(tasteName);

        // then
        maybe.test()
                .assertComplete()
                .assertNoValues()
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be removed if it was not in the category")
    @ArgumentsSource(AllDbTypes.class)
    @Order(7)
    void canRemoveNonexistentThing(SillyDb sillyDb) {
        // given - a category and a thing name
        var sweets = sweets(sillyDb);
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

    private Category sweets(SillyDb sillyDb) {
        var name = categoryName("sweets");
        SingleSource<Category> newCategory = observer -> sillyDb.createCategory(name)
                .subscribe(observer);
        return sillyDb.findCategory(name)
                .switchIfEmpty(newCategory)
                .blockingGet();
    }
}
