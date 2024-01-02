package mlesiewski.sillydb;

import mlesiewski.sillydb.core.FileSillyRunDirectoryDoesNotExist;
import mlesiewski.sillydb.core.FileSillyRunDirectoryIsNotWritable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.PosixFilePermissions;

import static java.nio.file.Files.createDirectory;
import static java.nio.file.Files.createTempDirectory;
import static mlesiewski.sillydb.builder.SillyDbBuilder.sillyDb;
import static mlesiewski.sillydb.testinfrastructure.DirectoryTreeDeleter.deleteDirectoryTree;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FileSillyDbCreationTest {

    private static final String RUN_DIRECTORY = "target_db_dir";
    private Path testDir;

    @BeforeEach
    void setup() throws IOException {
        testDir = createTempDirectory("cli_test_");
    }

    @AfterEach
    void teardown() {
        deleteDirectoryTree(testDir);
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
        var readOnly = "r--------";
        var permissions = PosixFilePermissions.fromString(readOnly);
        var attr = PosixFilePermissions.asFileAttribute(permissions);
        createDirectory(path, attr);
    }
}
