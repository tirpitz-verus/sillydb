package mlesiewski.sillydb.builder;

import mlesiewski.sillydb.SillyDb;
import mlesiewski.sillydb.core.FileSillyDb;

final class FileSillyDbBuilder implements Final {

    @Override
    public SillyDb create() {
        return new FileSillyDb();
    }
}
