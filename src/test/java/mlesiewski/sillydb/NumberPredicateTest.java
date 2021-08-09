package mlesiewski.sillydb;

import mlesiewski.sillydb.predicate.*;
import mlesiewski.sillydb.propertyvalue.*;
import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static java.math.BigDecimal.*;
import static mlesiewski.sillydb.CategoryName.*;
import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.predicate.SillyPredicateBuilder.*;
import static mlesiewski.sillydb.testinfrastructure.TestPredicates.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;

class NumberPredicateTest {

    static final CategoryName SWEETS = categoryName("sweets");
    static final PropertyName WEIGHT = propertyName("weight");
    static final PropertyName TASTE = propertyName("taste");
    static final String SWEET = "sweet";

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by BigDecimal equality")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByBigDecimalEquality(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(WEIGHT, ONE)
                .withThing()
                .withProperty(WEIGHT, TEN)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(WEIGHT)
                .valueIsEqualTo(ONE)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, WEIGHT, ONE))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by BigDecimal grater than")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByBigDecimalGraterThan(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(WEIGHT, ONE)
                .withThing()
                .withProperty(WEIGHT, ZERO)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(WEIGHT)
                .valueIsGraterThan(ZERO)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, WEIGHT, ONE))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("comparing by BigDecimal grater than with a non-numeric property value yields error")
    @ArgumentsSource(AllDbTypes.class)
    void comparingByBigDecimalGraterThanWithNonNumericPropertyValueYieldsError(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsGraterThan(ZERO)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertError(NumberComparingSillyPredicateUsedToTestNonNumericValue.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(NumberComparingSillyPredicate.class))
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by BigDecimal grater than or equal to")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByBigDecimalGraterThanOrEqualTo(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(WEIGHT, ONE)
                .withThing()
                .withProperty(WEIGHT, ZERO)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(WEIGHT)
                .valueIsGraterThanOrEqualTo(ONE)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, WEIGHT, ONE))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("comparing by BigDecimal grater than or equal to with a non-numeric property value yields error")
    @ArgumentsSource(AllDbTypes.class)
    void comparingByBigDecimalGraterThanOrEqualToWithNonNumericPropertyValueYieldsError(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsGraterThanOrEqualTo(ZERO)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertError(NumberComparingSillyPredicateUsedToTestNonNumericValue.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(NumberComparingSillyPredicate.class))
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by BigDecimal less than")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByBigDecimalLessThan(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(WEIGHT, TEN)
                .withThing()
                .withProperty(WEIGHT, ONE)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(WEIGHT)
                .valueIsLessThan(TEN)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, WEIGHT, ONE))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("comparing by BigDecimal less than with a non-numeric property value yields error")
    @ArgumentsSource(AllDbTypes.class)
    void comparingByBigDecimalLessThanWithNonNumericPropertyValueYieldsError(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsLessThan(ZERO)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertError(NumberComparingSillyPredicateUsedToTestNonNumericValue.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(NumberComparingSillyPredicate.class))
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by BigDecimal less than or equal to")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByBigDecimalLessThanOrEqualTo(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(WEIGHT, TEN)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(WEIGHT)
                .valueIsLessThanOrEqualTo(TEN)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, WEIGHT, TEN))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("comparing by BigDecimal less than or equal to with a non-numeric property value yields error")
    @ArgumentsSource(AllDbTypes.class)
    void comparingByBigDecimalLessThanOrEqualToWithNonNumericPropertyValueYieldsError(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsLessThanOrEqualTo(ZERO)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertError(NumberComparingSillyPredicateUsedToTestNonNumericValue.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(NumberComparingSillyPredicate.class))
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can compare long to BigDecimal using number predicate")
    @ArgumentsSource(AllDbTypes.class)
    void canCompareLongToBigDecimalUsingNumberPredicate(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(WEIGHT, 10L)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(WEIGHT)
                .valueIsEqualTo(TEN)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, WEIGHT, 10L))
                .assertComplete()
                .cancel();
    }
}
