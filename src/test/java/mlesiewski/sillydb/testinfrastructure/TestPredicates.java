package mlesiewski.sillydb.testinfrastructure;

import io.reactivex.rxjava3.functions.*;
import mlesiewski.sillydb.*;
import mlesiewski.sillydb.propertyvalue.*;

public class TestPredicates {

    public static boolean propertyHasValue(Thing thing, PropertyName name, Object value) {
        return value.equals(thing.getProperty(name).map(PropertyValue::value).blockingGet());
    }

    public static Predicate<Throwable> errorMessageNamesClass(Class<?> aClass) {
        return (throwable) -> throwable.getMessage().contains(aClass.getSimpleName());
    }
}
