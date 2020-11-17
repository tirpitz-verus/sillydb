package mlesiewski.sillydb;

import java.util.*;

public interface Thing {

    void property(PropertyName name, String value);
    Optional<String> property(PropertyName name);
}
