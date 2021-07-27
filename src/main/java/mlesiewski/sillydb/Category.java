package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;
import mlesiewski.sillydb.predicate.*;

import java.util.function.*;

public interface Category {

    CategoryName name();

    Thing createNewThing();

    Single<NamedThing> put(Thing thing);

    Completable remove(ThingName name);

    default Completable remove(NamedThing namedThing) {
        return remove(namedThing.name());
    }

    Maybe<NamedThing> findBy(ThingName name);

    <T> Flowable<NamedThing> findAllBy(SillyPredicate predicate);
}
