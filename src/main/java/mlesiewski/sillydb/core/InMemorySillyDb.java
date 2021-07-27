package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;
import org.slf4j.*;

import java.util.*;

import static org.slf4j.LoggerFactory.*;

/**
 * In-memory variant of the {@link mlesiewski.sillydb.SillyDb}.
 */
public class InMemorySillyDb implements SillyDb {

    /** to be made private when {@link FileSillyDb} gets implemented */
    protected final Logger logger;
    /** to be made private when {@link FileSillyDb} gets implemented */
    protected final Map<CategoryName, Category> categories;

    /**
     * Constructs instances of this class.
     */
    public InMemorySillyDb() {
        logger = getLogger(this.getClass());
        categories = new HashMap<>();
        logger.info("InMemorySillyDb created");
    }

    @Override
    public Single<Category> createCategory(CategoryName name) {
        if (categories.containsKey(name)) {
            return categoryAlreadyExistsError(name);
        } else {
            return docCreateCategory(name);
        }
    }

    private Single<Category> categoryAlreadyExistsError(CategoryName name) {
        logger.debug("category {} already exists", name);
        var throwable = new CannotCreateCategory(name);
        return Single.error(throwable);
    }

    private Single<Category> docCreateCategory(CategoryName name) {
        var category = new InMemoryCategory(name, this);
        categories.put(name, category);
        logger.debug("category {} created", name);
        return Single.just(category);
    }

    @Override
    public Maybe<Category> findCategory(CategoryName name) {
        if (categories.containsKey(name)) {
            return existingCollection(name);
        } else {
            return Maybe.empty();
        }
    }

    private Maybe<Category> existingCollection(CategoryName name) {
        var collection = categories.get(name);
        return Maybe.just(collection);
    }

    @Override
    public Completable deleteCategory(CategoryName name) {
        if (categories.containsKey(name)) {
            return doDeleteCategory(name);
        } else {
            return categoryDoesNotExistsError(name);
        }
    }

    private Completable doDeleteCategory(CategoryName name) {
        categories.remove(name);
        return Completable.complete();
    }

    private Completable categoryDoesNotExistsError(CategoryName name) {
        var throwable = new CategoryDoesNotExist(name);
        return Completable.error(throwable);
    }

    @Override
    public boolean categoryExists(CategoryName categoryName) {
        return categories.containsKey(categoryName);
    }

    @Override
    public String toString() {
        return "InMemorySillyDb{}";
    }
}
