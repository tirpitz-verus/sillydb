package mlesiewski.sillydb.core;

public class FileSillyRunDirectoryIsNotWritable extends FileSillyRunDirectoryException {

    FileSillyRunDirectoryIsNotWritable(FileSillyRunDirectory runDirectory) {
        super("Cannot write to RunDirectory " + runDirectory, runDirectory);
    }
}
