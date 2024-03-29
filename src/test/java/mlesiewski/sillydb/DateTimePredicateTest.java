package mlesiewski.sillydb;

import mlesiewski.sillydb.predicate.*;
import mlesiewski.sillydb.propertyvalue.*;
import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.time.*;

import static java.util.Objects.*;
import static mlesiewski.sillydb.CategoryName.*;
import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.predicate.SillyPredicateBuilder.*;
import static mlesiewski.sillydb.testinfrastructure.TestPredicates.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;

class DateTimePredicateTest {

    static final CategoryName SWEETS = categoryName("sweets");
    static final PropertyName CREATED = propertyName("created");
    static final PropertyName TASTE = propertyName("taste");
    static final String SWEET = "sweet";
    static final ZonedDateTime Y_2007 = dateTimeFromUtc("2007-01-01", "00:00:00");
    static final ZonedDateTime Y_2008 = dateTimeFromUtc("2008-01-01", "00:00:00");
    static final ZonedDateTime Y_2009 = dateTimeFromUtc("2009-01-01", "00:00:00");

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by ZonedDateTime equality")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByZonedDateTimeEquality(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(CREATED, Y_2007)
                .withThing()
                .withProperty(CREATED, Y_2008)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(CREATED)
                .valueIsEqualTo(Y_2007)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, CREATED, Y_2007))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by ZonedDateTime after")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByZonedDateTimeAfter(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(CREATED, Y_2007)
                .withThing()
                .withProperty(CREATED, Y_2009)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(CREATED)
                .valueIsAfter(Y_2008)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, CREATED, Y_2009))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("comparing by ZonedDateTime after with a non-numeric property value yields error")
    @ArgumentsSource(AllDbTypes.class)
    void comparingByZonedDateTimeAfterWithNonTemporalPropertyValueYieldsError(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsAfter(Y_2008)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertError(TemporalComparingSillyPredicateUsedToTestNonTemporalValue.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(TemporalComparingSillyPredicate.class))
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by ZonedDateTime after or equal to")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByZonedDateTimeAfterOrEqualTo(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(CREATED, Y_2007)
                .withThing()
                .withProperty(CREATED, Y_2009)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(CREATED)
                .valueIsAfterOrEqualTo(Y_2009)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, CREATED, Y_2009))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("comparing by ZonedDateTime after or equal to with a non-temporal property value yields error")
    @ArgumentsSource(AllDbTypes.class)
    void comparingByZonedDateTimeAfterThanOrEqualToWithNonTemporalPropertyValueYieldsError(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsAfterOrEqualTo(Y_2007)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertError(TemporalComparingSillyPredicateUsedToTestNonTemporalValue.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(TemporalComparingSillyPredicate.class))
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by ZonedDateTime before")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByZonedDateTimeBefore(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(CREATED, Y_2007)
                .withThing()
                .withProperty(CREATED, Y_2009)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(CREATED)
                .valueIsBefore(Y_2008)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, CREATED, Y_2007))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("comparing by ZonedDateTime before than with a non-temporal property value yields error")
    @ArgumentsSource(AllDbTypes.class)
    void comparingByZonedDateTimeBeforeThanWithNonTemporalPropertyValueYieldsError(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsBefore(Y_2007)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertError(TemporalComparingSillyPredicateUsedToTestNonTemporalValue.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(TemporalComparingSillyPredicate.class))
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can find things by ZonedDateTime before or equal to")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByZonedDateTimeBeforeThanOrEqualTo(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(CREATED, Y_2007)
                .withThing()
                .withProperty(CREATED, Y_2009)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(CREATED)
                .valueIsBeforeOrEqualTo(Y_2007)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertValue(v -> propertyHasValue(v, CREATED, Y_2007))
                .assertComplete()
                .cancel();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("comparing by ZonedDateTime before or equal to with a non-temporal property value yields error")
    @ArgumentsSource(AllDbTypes.class)
    void comparingByZonedDateTimeBeforeOrEqualToWithNonTemporalPropertyValueYieldsError(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .withProperty(TASTE, SWEET)
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(TASTE)
                .valueIsBeforeOrEqualTo(Y_2007)
                .build();
        sweets.findAllBy(predicate).test()

        // then
                .assertError(TemporalComparingSillyPredicateUsedToTestNonTemporalValue.class)
                .assertError(errorMessageNamesClass(StringPropertyValue.class))
                .assertError(errorMessageNamesClass(TemporalComparingSillyPredicate.class))
                .cancel();
    }

    private static ZonedDateTime dateTimeFromUtc(String utcDate, String utcTime) {
        return ZonedDateTime.parse(requireNonNull(utcDate) + "T" + requireNonNull(utcTime) + "Z");
    }
}
