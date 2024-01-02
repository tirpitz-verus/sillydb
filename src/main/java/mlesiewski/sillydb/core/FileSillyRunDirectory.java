package mlesiewski.sillydb.core;

import java.io.File;
import java.nio.file.Path;

public record FileSillyRunDirectory (File file) {

    public static FileSillyRunDirectory of(Path path) {
        return new FileSillyRunDirectory(path.toFile());
    }

    public boolean isNotWritable() {
        return !file.canWrite();
    }

    public boolean doesNotExist() {
        return !file.exists();
    }
}
