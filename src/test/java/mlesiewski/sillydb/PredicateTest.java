package mlesiewski.sillydb;

import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static mlesiewski.sillydb.CategoryName.*;
import static mlesiewski.sillydb.PropertyName.*;
import static mlesiewski.sillydb.predicate.SillyPredicateBuilder.*;
import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;

class PredicateTest {

    static final PropertyName TASTE = propertyName("taste");

    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(AllDbTypes.class)
    void canFindThingsByStringEquality(SillyDb sillyDb) {
        // given
        var sweets = using(sillyDb)
                .withCategory(categoryName("sweets"))
                .withThing()
                .withProperty(TASTE, "sweet")
                .withThing()
                .withProperty(TASTE, "sour")
                .getCategory();

        // when
        var predicate = predicateWhere()
                .property(propertyName("taste"))
                .valueIsEqualTo("sweet")
                .build();
        sweets.findAllBy(predicate).test()

                // then
                .assertValueCount(1)
                .assertValue(v -> v.getProperty(TASTE).filter(p -> p.value().equals("sweet")).isEmpty().blockingGet())
                .assertComplete()
                .cancel();
    }
}
