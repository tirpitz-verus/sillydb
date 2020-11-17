package mlesiewski.sillydb.builder;

import mlesiewski.sillydb.SillyDb;
import mlesiewski.sillydb.core.InMemorySillyDb;

class InMemorySillyDbBuilder implements Final {

    @Override
    public SillyDb create() {
        return new InMemorySillyDb();
    }
}
