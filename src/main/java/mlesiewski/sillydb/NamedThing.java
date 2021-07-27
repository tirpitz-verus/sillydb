package mlesiewski.sillydb;

/**
 * Interface for {@link Thing Things} that were put to a {@link Category}.
 */
public interface NamedThing extends Thing {

    /**
     * Returns a name assigned to this instance by the database.
     *
     * @return name of this instance
     */
    ThingName name();
}
