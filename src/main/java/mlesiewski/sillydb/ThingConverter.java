package mlesiewski.sillydb;

public interface ThingConverter<T> {

    T fromThing(Thing thing);
    Thing toThing(T t);
}
