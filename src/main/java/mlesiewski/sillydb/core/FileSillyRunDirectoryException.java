package mlesiewski.sillydb.core;

abstract class FileSillyRunDirectoryException extends RuntimeException {

    private final FileSillyRunDirectory runDirectory;

    protected FileSillyRunDirectoryException(String message, FileSillyRunDirectory runDirectory) {
        super(message);
        this.runDirectory = runDirectory;
    }
}
