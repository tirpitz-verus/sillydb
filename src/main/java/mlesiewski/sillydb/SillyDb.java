package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;

/**
 * Interface for all db variants.
 */
public interface SillyDb {

    /**
     * Creates a {@link Category} instance specific to this database variant.
     * New categories are automatically added to the database.
     * Possible errors:
     * <ul>
     *     <li>{@link CannotCreateCategory} if a category with exactly that name already exists</li>
     * </ul>
     *
     * @param name category name
     * @return a {@link Single} with new category
     */
    Single<Category> createCategory(CategoryName name);

    /**
     * Finds a category with the name specified.
     *
     * @param name category name
     * @return a {@link Maybe} with requested category
     */
    Maybe<Category> findCategory(CategoryName name);

    /**
     * Tries to delete the category with all the things inside it.
     * Possible errors:
     * <ul>
     *     <li>{@link CategoryDoesNotExist} if there is no category with the name specified</li>
     * </ul>
     *
     * @param name category name
     * @return a {@link Completable} to mark the success or failure
     */
    Completable deleteCategory(CategoryName name);

    /**
     * Checks whether a category with the name specified exists.
     *
     * @param categoryName category name
     * @return result
     */
    boolean categoryExists(CategoryName categoryName);
}
