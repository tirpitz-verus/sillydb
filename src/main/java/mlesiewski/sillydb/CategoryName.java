package mlesiewski.sillydb;

import static java.util.Objects.requireNonNull;

public final class CategoryName extends Name {

    public static CategoryName categoryName(String name) {
        return new CategoryName(name);
    }

    public CategoryName(String name) {
        super(requireNonNull(name, "category name cannot be null"));
    }

}
