package mlesiewski.sillydb;

import mlesiewski.sillydb.propertyvalue.*;
import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.math.*;

import static mlesiewski.sillydb.propertyvalue.BigDecimalPropertyValue.*;
import static mlesiewski.sillydb.propertyvalue.BooleanPropertyValue.*;
import static mlesiewski.sillydb.CategoryName.*;
import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.propertyvalue.StringPropertyValue.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;

class PropertyValueTypesTest {

    static final CategoryName SWEETS = categoryName("sweets");

    @ParameterizedTest(name = "{0}")
    @DisplayName("string property value type is retained")
    @ArgumentsSource(AllDbTypes.class)
    void aString(SillyDb sillyDb) {
        // given
        var thingWithCategory = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .getThing();
        var sweets = thingWithCategory.category;
        var thing = thingWithCategory.thing;
        var thingName = thing.name();

        // when
        var taste = propertyName("taste");
        var sweet = stringPropertyValue("sweet");
        thing.setProperty(taste, sweet)
                .flatMap(sweets::put)
                .test()
                .assertComplete()
                .dispose();

        // then
        sweets.findBy(thingName)
                .flatMap(t -> t.getProperty(taste))
                .test()
                .assertValue(v -> propertyHasType(v, StringPropertyValue.class))
                .assertComplete()
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("boolean property value type is retained")
    @ArgumentsSource(AllDbTypes.class)
    void aBoolean(SillyDb sillyDb) {
        // given
        var thingWithCategory = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .getThing();
        var sweets = thingWithCategory.category;
        var thing = thingWithCategory.thing;
        var thingName = thing.name();

        // when
        var isSweet = propertyName("isSweet");
        var yes = booleanPropertyValue(true);
        thing.setProperty(isSweet, yes)
                .flatMap(sweets::put)
                .test()
                .assertComplete()
                .dispose();

        // then
        sweets.findBy(thingName)
                .flatMap(t -> t.getProperty(isSweet))
                .test()
                .assertValue(v -> propertyHasType(v, BooleanPropertyValue.class))
                .assertComplete()
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("BigDecimal property value type is retained")
    @ArgumentsSource(AllDbTypes.class)
    void aBigDecimal(SillyDb sillyDb) {
        // given
        var thingWithCategory = using(sillyDb)
                .withCategory(SWEETS)
                .withThing()
                .getThing();
        var sweets = thingWithCategory.category;
        var thing = thingWithCategory.thing;
        var thingName = thing.name();

        // when
        var weight = propertyName("weight");
        var oneOFive = bigDecimalPropertyValue(BigDecimal.valueOf(1.05));
        thing.setProperty(weight, oneOFive)
                .flatMap(sweets::put)
                .test()
                .assertComplete()
                .dispose();

        // then
        sweets.findBy(thingName)
                .flatMap(t -> t.getProperty(weight))
                .test()
                .assertValue(v -> propertyHasType(v, BigDecimalPropertyValue.class))
                .assertComplete()
                .dispose();
    }

    private boolean propertyHasType(PropertyValue<?> v, Class<? extends PropertyValue<?>> aClass) {
        return aClass.isInstance(v);
    }
}
