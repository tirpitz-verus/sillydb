package mlesiewski.sillydb;

import io.reactivex.rxjava3.annotations.*;

import static java.util.Objects.requireNonNull;
import static mlesiewski.sillydb.PropertyNameValidator.*;

/**
 * Name of the property of a {@link Thing}.
 * <p>
 * A name is required to have one or more of the characters below:
 * <ul>
 *     <li>lowercase and uppercase letters</li>
 *     <li>numbers</li>
 *     <li>dosts '.'</li>
 *     <li>dashes '-'</li>
 *     <li>underscores '_'</li>
 *     <li>plus signs '+'</li>
 *     <li>equality signs '='</li>
 * </ul>
 */
public final class PropertyName extends Name {

    /**
     * A convenience method to create a new instance. Can be statically imported.
     *
     * @param name name for the property
     * @return new instance
     */
    public static PropertyName propertyName(@NonNull String name) {
        return new PropertyName(name);
    }

    /**
     * Creates a new instance.
     *
     * @param name name for the property
     * @throws NullPointerException           if the name is null
     * @throws BadPropertyNameCannotBeCreated if the name does not meet requirements
     */
    public PropertyName(@NonNull String name) {
        super(
                returnValidPropertyNameOrThrow(
                        requireNonNull(name, "property name cannot be null")
                )
        );
    }
}
