package mlesiewski.sillydb;

import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.*;

import static mlesiewski.sillydb.testinfrastructure.testdatabuilder.TestDataBuilder.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("category")
@TestMethodOrder(OrderAnnotation.class)
class CategoryCrudTest {

    static final CategoryName CATEGORY_NAME = new CategoryName("pets");

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be created")
    @ArgumentsSource(AllDbTypes.class)
    void canCreateCategory(SillyDb sillyDb) {
        // when - creating category
        sillyDb.createCategory(CATEGORY_NAME)
                .test()
        // then - value has a correct name
                .assertValue(v -> v.name().equals(CATEGORY_NAME))
                .assertNoErrors()
                .assertComplete()
                .dispose();
    }


    @ParameterizedTest(name = "{0}")
    @DisplayName("cannot be overwritten")
    @ArgumentsSource(AllDbTypes.class)
    void cannotOverwriteCategory(SillyDb sillyDb) {
        // given
        using(sillyDb).withCategory(CATEGORY_NAME);

        // when
        sillyDb.createCategory(CATEGORY_NAME).test()

        // then
                .assertError(CannotCreateCategory.class)
                .assertError(t -> t.getMessage().contains(CATEGORY_NAME.value))
                .dispose();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be found by name")
    @ArgumentsSource(AllDbTypes.class)
    void canBeFoundByName(SillyDb sillyDb) {
        // given - existing category
        var category = using(sillyDb)
                .withCategory(CATEGORY_NAME)
                .getCategory();

        // when - searching for the category
        sillyDb.findCategory(CATEGORY_NAME).test()

        // then - category was found
                .assertValueSequence(List.of(category))
                .assertComplete();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be deleted by name")
    @ArgumentsSource(AllDbTypes.class)
    void canDeleteCategoryByName(SillyDb sillyDb) {
        // given - existing category
        using(sillyDb).withCategory(CATEGORY_NAME);

        // when
        sillyDb.deleteCategory(CATEGORY_NAME).test()

        // then
                .assertNoErrors();

        // when - searching for deleted category
        sillyDb.findCategory(CATEGORY_NAME).test()

        // then - nothing returned
                .assertComplete()
                .assertNoValues();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("cannot be deleted if it does not exists")
    @ArgumentsSource(AllDbTypes.class)
    void cannotDeleteNullCategory(SillyDb sillyDb) {
        // given - without category
        using(sillyDb).withoutCategory(CATEGORY_NAME);

        // when
        sillyDb.deleteCategory(CATEGORY_NAME).test()

        // then
                .assertError(CategoryDoesNotExist.class)
                .assertError(t -> t.getMessage().contains(CATEGORY_NAME.value));
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be checked if it exists")
    @ArgumentsSource(AllDbTypes.class)
    void canCheckIfCategoryExists(SillyDb sillyDb) {
        // given - without category
        using(sillyDb).withoutCategory(CATEGORY_NAME);

        // when
        var existsAfterDeletion = sillyDb.categoryExists(CATEGORY_NAME);

        // then
        assertThat(existsAfterDeletion).isFalse();

        // when
        sillyDb.createCategory(CATEGORY_NAME)
                .test()
                .assertComplete()
                .dispose();

        var existsAfterCreation = sillyDb.categoryExists(CATEGORY_NAME);

        // then
        assertThat(existsAfterCreation).isTrue();
    }
}