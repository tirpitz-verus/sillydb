package mlesiewski.sillydb.builder;

/**
 * This interface chooses the {@link mlesiewski.sillydb.SillyDb} type in the {@link SillyDbBuilder} call chain.
 */
public interface OfType {

    /**
     * Chooses the in-memory variant of the SillyDB.
     *
     * @return next part of the {@link SillyDbBuilder} call chain
     */
    Final inMemory();

    /**
     * Chooses the file-based variant of the SillyDB.
     *
     * @return next part of the {@link SillyDbBuilder} call chain
     */
    Final inAFile();
}
