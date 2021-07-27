package mlesiewski.sillydb;

import io.reactivex.rxjava3.annotations.*;
import io.reactivex.rxjava3.core.*;

import java.util.*;

/**
 * Thing represent data stored in the database.
 * Things have properties that contain the specific data.
 * A thing can be called a "group of properties".
 * <br>
 * Things are always immutable.
 */
public interface Thing {

    /**
     * Creates a new thing that has all of the properties of this thing plus the new one.
     *
     * @param name name of the property
     * @param propertyValue value of the property
     * @return a new thing wit the property required
     * @throws NullPointerException if name or value are null
     */
    Single<Thing> setProperty(@NonNull PropertyName name, @NonNull PropertyValue propertyValue);

    /**
     * Used to retrieve a value of a property.
     *
     * @param name name of the property
     * @return maybe a value of the property
     * @throws NullPointerException if the name is null
     */
    Maybe<PropertyValue> getProperty(@NonNull PropertyName name);

    /**
     * Creates a new thing with all the properties of this thing minus the one indicated.
     *
     * @param name name of the property to remove
     * @return a new thing without the property specified
     * @throws NullPointerException if the name is null
     */
    Single<Thing> removeProperty(@NonNull PropertyName name);

    /**
     * Returns a copy of the internal property map.
     *
     * @return a copy of the internal property map
     */
    Map<PropertyName, PropertyValue> properties();
}
