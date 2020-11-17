package mlesiewski.sillydb;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("category")
@TestMethodOrder(OrderAnnotation.class)
class CategoryCrudTest {

    private static final String PETS = "pets";

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be created")
    @ArgumentsSource(AllDbTypes.class)
    @Order(1)
    void canCreateCategory(SillyDb sillyDb) {
        // given
        var name = new CategoryName(PETS);

        // when - creating category
        var newCategory = sillyDb.createCategory(name)
                .blockingGet();

        // then - category si created
        assertThat(newCategory).isNotNull();
        assertThat(newCategory.name()).isEqualTo(name);
    }


    @ParameterizedTest(name = "{0}")
    @DisplayName("cannot be overwritten")
    @ArgumentsSource(AllDbTypes.class)
    @Order(2)
    void cannotOverwriteCategory(SillyDb sillyDb) {
        // given
        var name = new CategoryName(PETS);

        // when
        var response = sillyDb.createCategory(name);

        // then
        response
                .test()
                .assertError(CannotCreateCategory.class)
                .assertError(t -> t.getMessage().contains(name.toString()));
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be found by name")
    @ArgumentsSource(AllDbTypes.class)
    @Order(3)
    void canBeFoundByName(SillyDb sillyDb) {
        // given
        var name = new CategoryName(PETS);

        // when - searching for the category
        var existingCategory = sillyDb.findCategory(name)
                .blockingGet();

        // then - category was found
        assertThat(existingCategory).isNotNull();
        assertThat(existingCategory.name()).isEqualTo(name);
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("can be deleted by name")
    @ArgumentsSource(AllDbTypes.class)
    @Order(4)
    void canDeleteCategoryByName(SillyDb sillyDb) {
        // given - existing category
        var name = new CategoryName(PETS);

        // when
        var response = sillyDb.deleteCategory(name);

        // then
        response
                .test()
                .assertNoErrors();
    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("cannot be deleted if it does not exists")
    @ArgumentsSource(AllDbTypes.class)
    @Order(6)
    void cannotDeleteNullCategory(SillyDb sillyDb) {
        // given
        var name = new CategoryName(PETS);

        // when
        var response = sillyDb.deleteCategory(name);

        // then
        response
                .test()
                .assertError(CategoryDoesNotExist.class)
                .assertError(t -> t.getMessage().contains(name.value));
    }
}