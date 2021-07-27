package mlesiewski.sillydb;

/**
 * An error that happens if a new {@link Category} cannot be created because of a bad name.
 */
public final class CategoryWithBadNameCannotBeCreated extends SillyDbError {

    /**
     * Creates a new instance.
     *
     * @param offender a string representation of the name part that is not allowed
     */
    public CategoryWithBadNameCannotBeCreated(String offender) {
        super("cannot create a category name containing [" + offender + "]");
    }
}
