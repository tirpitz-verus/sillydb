package mlesiewski.sillydb.testinfrastructure;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

public class BadValidationTestsNamesProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of("~"),
                Arguments.of("`"),
                Arguments.of("!"),
                Arguments.of("@"),
                Arguments.of("#"),
                Arguments.of("$"),
                Arguments.of("%"),
                Arguments.of("^"),
                Arguments.of("&"),
                Arguments.of("*"),
                Arguments.of("("),
                Arguments.of(")"),
                Arguments.of(""),
                Arguments.of("("),
                Arguments.of("{"),
                Arguments.of("}"),
                Arguments.of("["),
                Arguments.of("]"),
                Arguments.of("\\"),
                Arguments.of("|"),
                Arguments.of(":"),
                Arguments.of(";"),
                Arguments.of("\""),
                Arguments.of("\'"),
                Arguments.of("<"),
                Arguments.of(">"),
                Arguments.of(","),
                Arguments.of("/"),
                Arguments.of("?"),
                Arguments.of(" ")
        );
    }
}
