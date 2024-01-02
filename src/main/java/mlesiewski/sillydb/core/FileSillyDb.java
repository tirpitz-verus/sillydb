package mlesiewski.sillydb.core;

/**
 * File-based variant of the {@link mlesiewski.sillydb.SillyDb}.
 */
public class FileSillyDb extends InMemorySillyDb {

    private final FileSillyRunDirectory runDirectory;

    /**
     * Creates new database that will be backed by a file.
     */
    public FileSillyDb(FileSillyRunDirectory runDirectory) {
        this.runDirectory = assertWritable(runDirectory);

    }

    private static FileSillyRunDirectory assertWritable(FileSillyRunDirectory runDirectory) {
        if (runDirectory.doesNotExist()) {
            throw new FileSillyRunDirectoryDoesNotExist(runDirectory);
        }
        if (runDirectory.isNotWritable()) {
            throw new FileSillyRunDirectoryIsNotWritable(runDirectory);
        }
        return runDirectory;
    }

    @Override
    public String toString() {
        return "FileSillyDb{" +runDirectory.toString() +"}";
    }
}
