package mlesiewski.sillydb;

import java.util.regex.*;

import static java.lang.Character.toChars;

class CategoryNameValidator {

    static String returnValidNameOrThrow(String name) {

        if (name.length() == 0) {
            throw new BadCategoryName(name);
        }

        var badCharacters = Pattern.compile("^[a-zA-Z0-9=+_.-]+$").asMatchPredicate().negate();

        name.codePoints()
                .mapToObj(CategoryNameValidator::codePointToString)
                .filter(badCharacters)
                .forEach(offendingString -> {throw new BadCategoryName(offendingString);});

        return name;
    }



    private static String codePointToString(int codePoint) {
        var chars = toChars(codePoint);
        return String.copyValueOf(chars);
    }
}
