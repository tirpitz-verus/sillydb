package mlesiewski.sillydb;

public final class CategoryDoesNotExist extends SillyDbError {

    public CategoryDoesNotExist(CategoryName name) {
        super("category " + name + " does not exist");
    }
}
