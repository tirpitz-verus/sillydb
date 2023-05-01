package mlesiewski.sillydb.core;

/**
 * File-based variant of the {@link mlesiewski.sillydb.SillyDb}.
 */
public class FileSillyDb extends InMemorySillyDb {

    /**
     * Creates new database that will be backed by a file.
     */
    public FileSillyDb() {
    }

    @Override
    public String toString() {
        return "FileSillyDb{}";
    }
}
