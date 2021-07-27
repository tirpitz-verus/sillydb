package mlesiewski.sillydb;

import mlesiewski.sillydb.testinfrastructure.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import static org.assertj.core.api.Assertions.*;

class PropertyNameTest {

    @ParameterizedTest
    @ArgumentsSource(GoodValidationTestsNamesProvider.class)
    void propertyNamesWithAllowedCharactersAreAccepted(String name) {
        assertThat(new PropertyName(name).toString()).isEqualTo(name);
    }

    @ParameterizedTest
    @ArgumentsSource(BadValidationTestsNamesProvider.class)
    void propertyNamesWithDisallowedCharactersResultInException(String name) {
        assertThatExceptionOfType(BadPropertyNameCannotBeCreated.class)
                .isThrownBy(() -> new PropertyName(name))
                .withMessageContaining(name);
    }
}
