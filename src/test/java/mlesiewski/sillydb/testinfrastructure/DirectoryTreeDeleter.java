package mlesiewski.sillydb.testinfrastructure;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.Files.delete;

public class DirectoryTreeDeleter implements FileVisitor<Path> {

    public static void deleteDirectoryTree(Path directory) {
        try {
            Files.walkFileTree(directory, new DirectoryTreeDeleter());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        delete(file);
        return CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) {
        return CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        delete(dir);
        return CONTINUE;
    }
}
