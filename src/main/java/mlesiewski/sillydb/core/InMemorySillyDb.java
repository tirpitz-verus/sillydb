package mlesiewski.sillydb.core;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.*;
import org.slf4j.*;

import java.util.*;

import static org.slf4j.LoggerFactory.*;

public class InMemorySillyDb implements SillyDb {

    protected final Logger logger;
    protected final HashMap<CategoryName, Category> collections;

    public InMemorySillyDb() {
        logger = getLogger(this.getClass());
        collections = new HashMap<>();

        logger.info("sillydb created");
    }

    @Override
    public Single<Category> createCategory(CategoryName name) {
        if (collections.containsKey(name)) {
            logger.debug("collection {} already exists", name);
            return Single.error(new CannotCreateCategory(name));
        } else {
            var collection = new BaseCategory(name, this);
            collections.put(name, collection);
            logger.debug("collection {} created", name);
            return Single.just(collection);
        }
    }

    @Override
    public Maybe<Category> findCategory(CategoryName name) {
        if (collections.containsKey(name)) {
            var collection = collections.get(name);
            return Maybe.just(collection);
        } else {
            return Maybe.empty();
        }
    }

    @Override
    public Completable deleteCategory(CategoryName name) {
        if (collections.containsKey(name)) {
            collections.remove(name);
            return Completable.complete();
        } else {
            return Completable.error(new CategoryDoesNotExist(name));
        }
    }

    @Override
    public String toString() {
        return "InMemorySillyDb{}";
    }
}
