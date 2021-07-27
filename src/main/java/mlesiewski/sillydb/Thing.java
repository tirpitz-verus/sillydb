package mlesiewski.sillydb;

import io.reactivex.rxjava3.core.*;

import java.util.*;

public interface Thing {

    Single<Thing> setProperty(PropertyName name, PropertyValue propertyValue);
    Maybe<PropertyValue> getProperty(PropertyName name);
    Single<Thing> removeProperty(PropertyName name);

    Map<PropertyName, PropertyValue> properties();
}
