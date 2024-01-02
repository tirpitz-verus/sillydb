package mlesiewski.sillydb.builder;

import mlesiewski.sillydb.SillyDb;
import mlesiewski.sillydb.core.FileSillyDb;
import mlesiewski.sillydb.core.FileSillyRunDirectory;

final class FileSillyDbBuilder implements WithRunDirectory, Final {

    private FileSillyRunDirectory fileSillyRunDirectory;

    @Override
    public Final withRunDirectory(FileSillyRunDirectory fileSillyRunDirectory) {
        this.fileSillyRunDirectory = fileSillyRunDirectory;
        return this;
    }

    @Override
    public SillyDb create() {
        return new FileSillyDb(fileSillyRunDirectory);
    }
}
