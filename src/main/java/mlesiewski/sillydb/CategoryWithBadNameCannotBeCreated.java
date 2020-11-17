package mlesiewski.sillydb;

public final class CategoryWithBadNameCannotBeCreated extends SillyDbError {

    public CategoryWithBadNameCannotBeCreated(String offender) {
        super("cannot create a category name containing [" + offender + "]");
    }
}
