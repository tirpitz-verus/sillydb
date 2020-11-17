package mlesiewski.sillydb;

import java.util.*;

abstract class Name {

    protected final String value;

    protected Name(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CategoryName)) {
            return false;
        }
        var that = (CategoryName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
