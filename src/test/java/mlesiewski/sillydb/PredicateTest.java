package mlesiewski.sillydb;

import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static mlesiewski.sillydb.CategoryName.*;
import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.predicate.SillyPredicateBuilder.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;

class PredicateTest {

    static final PropertyName TASTE = propertyName("taste");
    static final PropertyName COLOR = propertyName("color");
    static final CategoryName SWEETS = categoryName("sweets");
    static final String SWEET = "sweet";
    static final String SOUR = "sour";
    public static final String RED = "red";

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by string equality")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByStringEquality(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .withThing()
                .withProperty(TASTE, SOUR)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsEqualTo(SWEET)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValueCount(1)
                .assertValue(v -> SWEET.equals(v.getProperty(TASTE).map(PropertyValue::value).blockingGet()))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by regular expressions")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByRegExp(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .withThing()
                .withProperty(TASTE, SOUR)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueMatches("^" + SWEET + "$")
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValueCount(1)
                .assertValue(v -> SWEET.equals(v.getProperty(TASTE).map(PropertyValue::value).blockingGet()))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things for which a property exists")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByPropertyExistence(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .withProperty(COLOR, RED)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(COLOR)
                .exists()
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValueCount(1)
                .assertValue(v -> RED.equals(v.getProperty(COLOR).map(PropertyValue::value).blockingGet()))
                .assertComplete()
                .cancel();
    }
}
