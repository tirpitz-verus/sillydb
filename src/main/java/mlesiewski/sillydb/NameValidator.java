package mlesiewski.sillydb;

import java.util.function.*;
import java.util.regex.*;

import static java.lang.Character.*;

abstract class NameValidator {

    public static final Predicate<String> BAD_CHARACTERS = Pattern.compile("^[a-zA-Z0-9=+_.-]+$")
            .asMatchPredicate()
            .negate();

    abstract void onEmptyName();

    abstract void onBadCharacter(String offendingString);

    protected String returnValidNameOrThrow(String name) {
        if (name.length() == 0) {
            onEmptyName();
        }

        name.codePoints()
                .mapToObj(NameValidator::codePointToString)
                .filter(BAD_CHARACTERS)
                .forEach(this::onBadCharacter);

        return name;
    }

    private static String codePointToString(int codePoint) {
        var chars = toChars(codePoint);
        return String.copyValueOf(chars);
    }
}
