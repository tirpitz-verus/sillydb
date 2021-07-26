package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;

public interface SillyDb {

    Single<Category> createCategory(CategoryName name);

    Maybe<Category> findCategory(CategoryName name);

    Completable deleteCategory(CategoryName name);

    boolean categoryExists(CategoryName categoryName);
}
