package mlesiewski.sillydb.builder;

import mlesiewski.sillydb.SillyDb;

/**
 * This interface finalizes {@link SillyDbBuilder} call chain.
 */
public interface Final {

    /**
     * Creates a new {@link SillyDb} instance.
     *
     * @return new {@link SillyDb} instance
     */
    SillyDb create();
}
