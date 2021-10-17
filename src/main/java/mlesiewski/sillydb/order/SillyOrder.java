package mlesiewski.sillydb.order;

import mlesiewski.sillydb.NamedThing;

import java.util.Comparator;

/**
 * Defines the required order of the things to be returned from a category.
 */
public interface SillyOrder extends Comparator<NamedThing> {
}
