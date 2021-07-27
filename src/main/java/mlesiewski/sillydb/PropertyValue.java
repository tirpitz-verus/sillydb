package mlesiewski.sillydb;

public record PropertyValue(String value) {

    public static PropertyValue propertyValue(String value) {
        return new PropertyValue(value);
    }
}
