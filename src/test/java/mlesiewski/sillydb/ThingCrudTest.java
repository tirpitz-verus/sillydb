package mlesiewski.sillydb;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static mlesiewski.sillydb.CategoryName.categoryName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.fail;

@DisplayName("thing")
@TestMethodOrder(OrderAnnotation.class)
class ThingCrudTest {

    private static final CategoryName SWEETS = categoryName("sweets");

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be saved")
    @ArgumentsSource(AllDbTypes.class)
    @Order(1)
    void thingCanBeSaved(SillyDb sillyDb) {
        // given - a category
        var sweets = sillyDb.createCategory(SWEETS)
                .blockingGet();

        // when - creating new thing
        var marshmallow = sweets.createNewThing();

        // when - saving new thing
        var saveResult = sweets.put(marshmallow);

        // then - save made no error
        saveResult
                .test()
                .assertNoErrors();

        // then - saved thing has a name
        var savedThing = saveResult.blockingGet();
        var name = savedThing.name();
        assertThat(name).isNotNull();
        assertThat(name.toString()).isNotNull();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be found")
    @ArgumentsSource(AllDbTypes.class)
    void thingCanBeFound(SillyDb sillyDb) {
        // given - a thing saved to a category
        var sweets = sillyDb.findCategory(SWEETS)
                .blockingGet();
        var candy = sweets.createNewThing();
        var savedCandy = sweets.put(candy)
                .blockingGet();
        var candyName = savedCandy.name();

        // when - searching for a candy
        var findResult = sweets.findBy(candyName);

        // then - search succeeds
        var foundThing = findResult.blockingGet();
        assertThat(foundThing.name()).isEqualTo(candyName);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be updated")
    @ArgumentsSource(AllDbTypes.class)
    void thingCanBeUpdated() {
        // given

        // when

        // then
        fail("not implemented");
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be deleted")
    @ArgumentsSource(AllDbTypes.class)
    void thingCanBeDeleted() {
        // given

        // when

        // then
        fail("not implemented");
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("retains properties")
    @ArgumentsSource(AllDbTypes.class)
    void thingRetainsProperties() {
        // given

        // when

        // then
        fail("not implemented");
    }
}
