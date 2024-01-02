package mlesiewski.sillydb;

import mlesiewski.sillydb.core.FileSillyRunDirectoryDoesNotExist;
import mlesiewski.sillydb.core.FileSillyRunDirectoryIsNotWritable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermissions;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.Files.*;
import static mlesiewski.sillydb.builder.SillyDbBuilder.sillyDb;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FileSillyDbCreationTest {

    private static final String RUN_DIRECTORY = "target_db_dir";
    private Path testDir;

    @BeforeEach
    void setup() throws IOException {
        testDir = createTempDirectory("cli_test_");
    }

    @AfterEach
    void teardown() throws IOException {
        Files.walkFileTree(testDir, new DirectoryDeleter());
    }

    class DirectoryDeleter implements FileVisitor<Path> {

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            delete(file);
            return CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            return CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            delete(dir);
            return CONTINUE;
        }
    }

    @Test
    void errorOnRunDirectoryNotExisting() {
        var notExisting = testDir.resolve(RUN_DIRECTORY);

        assertThatThrownBy(() -> sillyDb()
                .inAFile()
                .withRunDirectory(notExisting)
                .create())
                .isInstanceOf(FileSillyRunDirectoryDoesNotExist.class)
                .hasMessageContaining(notExisting.toString());
    }

    @Test
    void errorOnRunDirectoryNotBeingWritable() throws IOException {
        var readOnlyDir = testDir.resolve(RUN_DIRECTORY);
        createReadOnlyDir(readOnlyDir);

        assertThatThrownBy(() -> sillyDb()
                .inAFile()
                .withRunDirectory(readOnlyDir)
                .create())
                .isInstanceOf(FileSillyRunDirectoryIsNotWritable.class)
                .hasMessageContaining(readOnlyDir.toString());
    }

    private void createReadOnlyDir(Path path) throws IOException {
        var permissions = PosixFilePermissions.fromString("r--------");
        var attr = PosixFilePermissions.asFileAttribute(permissions);
        createDirectory(path, attr);
    }
}
