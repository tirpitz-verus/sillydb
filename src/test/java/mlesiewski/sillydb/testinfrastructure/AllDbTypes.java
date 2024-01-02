package mlesiewski.sillydb.testinfrastructure;

import mlesiewski.sillydb.*;
import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;

import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.*;

import static mlesiewski.sillydb.builder.SillyDbBuilder.sillyDb;

public class AllDbTypes implements ArgumentsProvider {

    static SillyDb inMemoryDb;
    static SillyDb fileDb;

    public AllDbTypes() throws IOException {
        initDbIfRequired();
    }

    private static void initDbIfRequired() throws IOException {
        if (inMemoryDb == null) {
            inMemoryDb = sillyDb()
                    .inMemory()
                    .create();
        }
        if (fileDb == null) {
            fileDb = sillyDb()
                    .inAFile()
                    .withRunDirectory(Files.createTempDirectory("allDbTypes"))
                    .create();
        }
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(inMemoryDb, fileDb).map(Arguments::of);
    }
}
