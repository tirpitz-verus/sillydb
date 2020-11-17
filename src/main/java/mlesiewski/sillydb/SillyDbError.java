package mlesiewski.sillydb;

public abstract class SillyDbError extends RuntimeException {

    public SillyDbError(String message) {
        super(message);
    }

    public SillyDbError(String message, Throwable cause) {
        super(message, cause);
    }
}
