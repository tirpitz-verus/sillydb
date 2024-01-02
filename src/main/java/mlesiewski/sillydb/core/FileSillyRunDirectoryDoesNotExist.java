package mlesiewski.sillydb.core;

public class FileSillyRunDirectoryDoesNotExist extends FileSillyRunDirectoryException {

    FileSillyRunDirectoryDoesNotExist(FileSillyRunDirectory runDirectory) {
        super("RunDirectory " + runDirectory + " does not exist", runDirectory);
    }
}
