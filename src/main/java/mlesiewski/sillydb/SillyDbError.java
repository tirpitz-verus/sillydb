package mlesiewski.sillydb;

/**
 * Abstract class for all the errors.
 */
public abstract class SillyDbError extends RuntimeException {

    /**
     * Creates a new instance.
     *
     * @param message a message to pass to the {@link Exception} constructor
     */
    public SillyDbError(String message) {
        super(message);
    }

    /**
     * Creates a new instance.
     *
     * @param message a message to pass to the {@link Exception} constructor
     * @param cause a cause to pass to the {@link Exception} constructor
     */
    public SillyDbError(String message, Throwable cause) {
        super(message, cause);
    }
}
