package mlesiewski.sillydb;

import static java.util.Objects.requireNonNull;
import static mlesiewski.sillydb.CategoryNameValidator.returnValidNameOrThrow;

public final class CategoryName extends Name {

    public static CategoryName categoryName(String name) {
        return new CategoryName(name);
    }

    public CategoryName(String name) {
        super(
                returnValidNameOrThrow(
                    requireNonNull(name, "category name cannot be null")
                )
        );
    }

}
