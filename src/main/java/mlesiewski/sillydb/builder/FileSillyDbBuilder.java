package mlesiewski.sillydb.builder;

import mlesiewski.sillydb.SillyDb;
import mlesiewski.sillydb.core.FileSillyDb;
import mlesiewski.sillydb.core.FileSillyRunDirectory;

import java.nio.file.Path;

final class FileSillyDbBuilder implements WithRunDirectory, Final {

    private FileSillyRunDirectory fileSillyRunDirectory;

    @Override
    public Final withRunDirectory(Path runDirectoryPath) {
        this.fileSillyRunDirectory = FileSillyRunDirectory.of(runDirectoryPath);
        return this;
    }

    @Override
    public SillyDb create() {
        return new FileSillyDb(fileSillyRunDirectory);
    }
}
