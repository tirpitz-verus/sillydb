package mlesiewski.sillydb.testinfrastructure;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

public class GoodValidationTestsNamesProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                "UPPERCASE",
                "lowercase",
                "numbers1234567890",
                "dots...",
                "dashes---",
                "underscores____",
                "pluses+++",
                "equals===")
                .map(Arguments::of);
    }
}
