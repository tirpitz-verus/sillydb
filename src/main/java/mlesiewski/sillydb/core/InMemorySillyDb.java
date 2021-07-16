package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;
import org.slf4j.*;

import java.util.*;

import static org.slf4j.LoggerFactory.*;

public class InMemorySillyDb implements SillyDb {

    protected final Logger logger;
    protected final HashMap<CategoryName, Category> categories;

    public InMemorySillyDb() {
        logger = getLogger(this.getClass());
        categories = new HashMap<>();

        logger.info("sillydb created");
    }

    @Override
    public Single<Category> createCategory(CategoryName name) {
        if (categories.containsKey(name)) {
            logger.debug("category {} already exists", name);
            var throwable = new CannotCreateCategory(name);
            return Single.error(throwable);
        } else {
            var category = new InMemoryCategory(name, this);
            categories.put(name, category);
            logger.debug("category {} created", name);
            return Single.just(category);
        }
    }

    @Override
    public Maybe<Category> findCategory(CategoryName name) {
        if (categories.containsKey(name)) {
            var collection = categories.get(name);
            return Maybe.just(collection);
        } else {
            return Maybe.empty();
        }
    }

    @Override
    public Completable deleteCategory(CategoryName name) {
        if (categories.containsKey(name)) {
            categories.remove(name);
            return Completable.complete();
        } else {
            var throwable = new CategoryDoesNotExist(name);
            return Completable.error(throwable);
        }
    }

    @Override
    public String toString() {
        return "InMemorySillyDb{}";
    }
}
