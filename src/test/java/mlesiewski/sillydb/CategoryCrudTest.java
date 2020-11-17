package mlesiewski.sillydb;

import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("category")
@TestMethodOrder(OrderAnnotation.class)
class CategoryCrudTest {

    public static final CategoryName CATEGORY_NAME = new CategoryName("pets");

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be created")
    @ArgumentsSource(AllDbTypes.class)
    @Order(1)
    void canCreateCategory(SillyDb sillyDb) {
        // when - creating category
        var newCategory = sillyDb.createCategory(CATEGORY_NAME)
                .blockingGet();

        // then - category si created
        assertThat(newCategory).isNotNull();
        assertThat(newCategory.name()).isEqualTo(CATEGORY_NAME);
    }


    @ParameterizedTest(name = "{0}")
    @DisplayName("cannot be overwritten")
    @ArgumentsSource(AllDbTypes.class)
    @Order(2)
    void cannotOverwriteCategory(SillyDb sillyDb) {
        // when
        var response = sillyDb.createCategory(CATEGORY_NAME);

        // then
        response
                .test()
                .assertError(CannotCreateCategory.class)
                .assertError(t -> t.getMessage().contains(CATEGORY_NAME.value));
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be found by name")
    @ArgumentsSource(AllDbTypes.class)
    @Order(3)
    void canBeFoundByName(SillyDb sillyDb) {
        // when - searching for the category
        var categoryMaybe = sillyDb.findCategory(CATEGORY_NAME);
        var existingCategory = categoryMaybe
                .blockingGet();

        // then - category was found
        assertThat(existingCategory).isNotNull();
        assertThat(existingCategory.name()).isEqualTo(CATEGORY_NAME);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be deleted by name")
    @ArgumentsSource(AllDbTypes.class)
    @Order(4)
    void canDeleteCategoryByName(SillyDb sillyDb) {
        // when
        var response = sillyDb.deleteCategory(CATEGORY_NAME);

        // then
        response
                .test()
                .assertNoErrors();

        // when - searching for deleted category
        var categoryMaybe = sillyDb.findCategory(CATEGORY_NAME);

        // then - nothing returned
        categoryMaybe.test().assertComplete().assertNoValues();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("cannot be deleted if it does not exists")
    @ArgumentsSource(AllDbTypes.class)
    @Order(6)
    void cannotDeleteNullCategory(SillyDb sillyDb) {
        // when
        var response = sillyDb.deleteCategory(CATEGORY_NAME);

        // then
        response
                .test()
                .assertError(CategoryDoesNotExist.class)
                .assertError(t -> t.getMessage().contains(CATEGORY_NAME.value));
    }
}