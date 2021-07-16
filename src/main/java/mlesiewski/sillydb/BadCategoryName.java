package mlesiewski.sillydb;

public final class BadCategoryName extends SillyDbError {

    public BadCategoryName(String offender) {
        super("cannot create a category name containing [" + offender + "]");
    }
}
