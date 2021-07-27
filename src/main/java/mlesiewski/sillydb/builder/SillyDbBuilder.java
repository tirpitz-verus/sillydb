package mlesiewski.sillydb.builder;

/**
 * Builder for the {@link mlesiewski.sillydb.SillyDb}.
 *
 * <pre>{@code
 * // example usage:
 * sillyDb()
 *      .inMemory()
 *      .create();
 * }</pre>
 */
public class SillyDbBuilder implements OfType {

    /**
     * This method starts the builder call chain.
     *
     * @return next part of the {@link SillyDbBuilder} call chain
     */
    public static OfType sillyDb() {
        return new SillyDbBuilder();
    }

    private SillyDbBuilder() {
    }

    @Override
    public Final inMemory() {
        return new InMemorySillyDbBuilder();
    }

    @Override
    public Final inAFile() {
        return new FileSillyDbBuilder();
    }
}
