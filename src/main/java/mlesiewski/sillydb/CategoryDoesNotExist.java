package mlesiewski.sillydb;

/**
 * An error that happens if a new {@link Category} does not exist.
 */
public final class CategoryDoesNotExist extends SillyDbError {

    /**
     * Creates a new instance.
     *
     * @param name name of the category that does not exist
     */
    public CategoryDoesNotExist(CategoryName name) {
        super("category " + name + " does not exist");
    }
}
