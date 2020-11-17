package mlesiewski.sillydb;

import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CategoryNameTest {

    @ParameterizedTest
    @MethodSource("goodCategoryNames")
    void categoryNamesWithAllowedCharactersAreAccepted(String name) {
        assertThat(new CategoryName(name).toString()).isEqualTo(name);
    }

    @ParameterizedTest
    @MethodSource("badCategoryNames")
    void categoryNamesWithDisallowedCharactersResultInException(String name) {
        assertThatExceptionOfType(CategoryWithBadNameCannotBeCreated.class)
                .isThrownBy(() -> new CategoryName(name))
                .withMessageContaining(name);
    }

    static Stream<String> badCategoryNames() {
        return Stream.of(
                "~",
                "`",
                "!",
                "@",
                "#",
                "$",
                "%",
                "^",
                "&",
                "*",
                "(",
                ")",
                "",
                "(",
                "{",
                "}",
                "[",
                "]",
                "\\",
                "|",
                ":",
                ";",
                "\"",
                "\'",
                "<",
                ">",
                ",",
                "/",
                "?",
                " "
        );
    }

    static Stream<String> goodCategoryNames() {
        return Stream.of(
                "UPPERCASE",
                "lowercase",
                "numbers1234567890",
                "dots...",
                "dashes---",
                "underscores____",
                "pluses+++",
                "equals==="
        );
    }
}