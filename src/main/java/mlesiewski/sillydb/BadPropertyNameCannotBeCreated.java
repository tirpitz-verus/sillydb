package mlesiewski.sillydb;

/**
 * An error that happens if a new {@link PropertyName} cannot be created because of a bad name.
 */
public final class BadPropertyNameCannotBeCreated extends SillyDbError {

    /**
     * Creates a new instance.
     *
     * @param offender a string representation of the name part that is not allowed
     */
    public BadPropertyNameCannotBeCreated(String offender) {
        super("cannot create a property name containing [" + offender + "]");
    }

    /**
     * Creates a new instance for a situation where the empty property name
     */
    public BadPropertyNameCannotBeCreated() {
        super("cannot create empty property name");
    }
}
