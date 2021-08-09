package mlesiewski.sillydb;

import mlesiewski.sillydb.propertyvalue.*;

/**
 * An error that happens whe one tries to change a type of property within a context of a {@link Category}.
 */
public final class PropertyValueTypeChangeIsIllegal extends SillyDbError {

    /**
     * Creates a new instance.
     *
     * @param propertyName name of the property in question
     * @param oldType the type of the property value
     * @param newType the would be new type of the property value
     * @param category category in which it happened
     */
    public PropertyValueTypeChangeIsIllegal(PropertyName propertyName, Class<? extends PropertyValue> oldType, Class<? extends PropertyValue> newType, Category category) {
        super("you cannot change a value type of a property '" + propertyName.value + "' from '" + oldType.getSimpleName() + "' to '" + newType.getSimpleName() + "' in the category '" + category.name() + "'");
    }
}
