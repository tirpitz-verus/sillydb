package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;

import java.util.*;

public interface Thing {

    Single<Thing> setProperty(Property property);
    Maybe<Property> getProperty(PropertyName name);
    Single<Thing> removeProperty(PropertyName name);

    Map<PropertyName, Property> properties();
}
