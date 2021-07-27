package mlesiewski.sillydb;

import io.reactivex.rxjava3.annotations.*;

import static java.util.Objects.requireNonNull;
import static mlesiewski.sillydb.CategoryNameValidator.*;

/**
 * Name for a {@link Category}.
 * <p>
 * A name is required to have one or more of the characters below:
 * <ul>
 *     <li>lowercase and uppercase letters</li>
 *     <li>numbers</li>
 *     <li>dosts '.'</li>
 *     <li>dashes '-'</li>
 *     <li>underscores '_'</li>
 *     <li>plus signs '+'</li>
 *     <li>equality signs '='</li>
 * </ul>
 */
public final class CategoryName extends Name {

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param name string value to be used as a name
     * @return new category name
     */
    public static CategoryName categoryName(String name) {
        return new CategoryName(name);
    }

    /**
     * Creates a new instance.
     *
     * @param name new name
     * @throws NullPointerException               if the name is null
     * @throws CategoryWithBadNameCannotBeCreated if the name does not meet requirements
     */
    public CategoryName(@NonNull String name) {
        super(
                returnValidCategoryNameOrThrow(
                        requireNonNull(name, "category name cannot be null")
                )
        );
    }

}
