package mlesiewski.sillydb;

import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CategoryNameTest {

    @ParameterizedTest
    @ArgumentsSource(GoodValidationTestsNamesProvider.class)
    void categoryNamesWithAllowedCharactersAreAccepted(String name) {
        assertThat(new CategoryName(name).toString()).isEqualTo(name);
    }

    @ParameterizedTest
    @ArgumentsSource(BadValidationTestsNamesProvider.class)
    void categoryNamesWithDisallowedCharactersResultInException(String name) {
        assertThatExceptionOfType(CategoryWithBadNameCannotBeCreated.class)
                .isThrownBy(() -> new CategoryName(name))
                .withMessageContaining(name);
    }
}