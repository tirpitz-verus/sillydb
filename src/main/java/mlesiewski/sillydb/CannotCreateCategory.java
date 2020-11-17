package mlesiewski.sillydb;

public final class CannotCreateCategory extends SillyDbError {

    public CannotCreateCategory(CategoryName name) {
        super("cannot create category " + name);
    }
}
