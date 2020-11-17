package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;

public interface Category {

    CategoryName name();

    Thing createNewThing();

    Single<NamedThing> put(Thing thing);

    Maybe<NamedThing> findBy(ThingName name);
}
