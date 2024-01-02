package mlesiewski.sillydb.builder;

import mlesiewski.sillydb.core.FileSillyRunDirectory;

import java.nio.file.Path;

public interface WithRunDirectory {

    default Final withRunDirectory(Path runDirectoryPath) {
        return withRunDirectory(FileSillyRunDirectory.of(runDirectoryPath));
    }

    Final withRunDirectory(FileSillyRunDirectory fileSillyRunDirectory);
}
