package mlesiewski.sillydb.builder;

public class SillyDbBuilder implements OfType {

    public static OfType sillyDb() {
        return new SillyDbBuilder();
    }

    private SillyDbBuilder() {
    }

    @Override
    public Final inMemory() {
        return new InMemorySillyDbBuilder();
    }

    @Override
    public Final inAFile() {
        return new FileSillyDbBuilder();
    }
}
