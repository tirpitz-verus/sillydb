package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.order.*;
import mlesiewski.sillydb.predicate.*;

/**
 * Categories hold things.
 * If a thing is to be stored in the database it needs to be first put into a category.
 * Categories create new things.
 */
public interface Category {

    /**
     * Returns the name of this category.
     *
     * @return name of this category
     */
    CategoryName name();

    /**
     * Creates a new {@link Thing} specific to this database variant.
     * The new thing needs to put to the category to be stored in the database.
     *
     * @return a new empty thing
     */
    Thing createNewThing();

    /**
     * Puts a {@link Thing} in the category.
     * If the thing wasn't a {@link NamedThing} than a new named thing will be created, stored and returned.
     * Will overwrite existing thing.
     *
     * @param thing thing to put to this category
     * @return a named thing if it was supplied or a new named thing created from the thing supplied
     */
    Single<NamedThing> put(Thing thing);

    /**
     * Removes a thing with the name specified from the category.
     * If there is no thing with that name than it will not cause an error.
     *
     * @param name name of thing to remove
     * @return result of the operation
     */
    Completable remove(ThingName name);

    /**
     * Calls {@link Category#remove(ThingName)}.
     *
     * @param namedThing thing to be removed from the category
     * @return result of the operation
     */
    default Completable remove(NamedThing namedThing) {
        return remove(namedThing.name());
    }

    /**
     * Finds a thing with the name specified.
     *
     * @param name name to search for
     * @return a thing with a name specified
     */
    Maybe<NamedThing> findBy(ThingName name);

    /**
     * Finds all things that match the predicate in no explicit order.
     *
     * @param predicate decides which things will be returned
     * @return things that match the predicate
     */
    Flowable<NamedThing> findAllBy(SillyPredicate predicate);

    /**
     *  Finds all things that match the predicate in the desired order.
     *
     * @param predicate decides which things will be returned
     * @param order decides which things will be returned first
     * @return things that match the predicate in the desired order
     */
    Flowable<NamedThing> findAllBy(SillyPredicate predicate, SillyOrder order);
}
