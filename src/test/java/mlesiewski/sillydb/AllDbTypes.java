package mlesiewski.sillydb;

import org.junit.jupiter.api.extension.*;
import org.junit.jupiter.params.provider.*;

import java.util.stream.*;

import static mlesiewski.sillydb.builder.SillyDbBuilder.sillyDb;

public class AllDbTypes implements ArgumentsProvider {

    static SillyDb inMemoryDb;
    static SillyDb fileDb;

    public AllDbTypes() {
        initDbIfRequired();
    }

    private static void initDbIfRequired() {
        if (inMemoryDb == null) {
            inMemoryDb = sillyDb()
                    .inMemory()
                    .create();
        }
        if (fileDb == null) {
            fileDb = sillyDb()
                    .inAFile()
                    .create();
        }
    }

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
        return Stream.of(inMemoryDb, fileDb).map(Arguments::of);
    }
}
