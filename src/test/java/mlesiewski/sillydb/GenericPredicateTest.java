package mlesiewski.sillydb;

import mlesiewski.sillydb.propertyvalue.*;
import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static mlesiewski.sillydb.CategoryName.*;
import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.predicate.SillyPredicateBuilder.*;
import static mlesiewski.sillydb.testinfrastructure.TestPredicates.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;

class GenericPredicateTest {

    static final PropertyName TASTE = propertyName("taste");
    static final PropertyName COLOR = propertyName("color");
    static final CategoryName SWEETS = categoryName("sweets");
    static final String SWEET = "sweet";
    static final String SOUR = "sour";
    static final String RED = "red";

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
                .assertValue(v -> propertyHasValue(v, TASTE, SWEET))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by boolean equality")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByBooleanEquality(SillyDb sillyDb) {
        // given
        var isSweet = propertyName("isSweet");
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(isSweet, true)
                .withThing()
                .withProperty(isSweet, false)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(isSweet)
                .valueIsEqualTo(true)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, isSweet, true))
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
                .assertValue(v -> propertyHasValue(v, TASTE, SWEET))
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
                .assertValue(v -> propertyHasValue(v, COLOR, RED))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can combine predicates")
    @ArgumentsSource(AllDbTypes.class)
    void canCombinePredicates(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .withThing()
                .withProperty(TASTE, SOUR)
                .withProperty(COLOR, RED)
                .withThing()
                .withProperty(COLOR, RED)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE).valueIsEqualTo(SOUR)
                .and()
                .property(COLOR).valueIsEqualTo(RED)
                .or()
                .property(TASTE).valueIsEqualTo(SWEET)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValueCount(2)
                .assertValueAt(0, v -> propertyHasValue(v, TASTE, SWEET))
                .assertValueAt(1, v -> propertyHasValue(v, TASTE, SOUR) && propertyHasValue(v, COLOR, RED) )
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can negate predicates")
    @ArgumentsSource(AllDbTypes.class)
    void canNegatePredicates(SillyDb sillyDb) {
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
                .not()
                .property(TASTE).valueIsEqualTo(SWEET)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, TASTE, SOUR))
                .assertComplete()
                .cancel();
    }
}
