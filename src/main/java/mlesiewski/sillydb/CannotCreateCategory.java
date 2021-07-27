package mlesiewski.sillydb;

/**
 * An error that happens if a new {@link Category} cannot be created.
 */
public final class CannotCreateCategory extends SillyDbError {

    /**
     * Creates a new instance.
     *
     * @param name name of the category that cannot be created
     */
    public CannotCreateCategory(CategoryName name) {
        super("cannot create category " + name);
    }
}
