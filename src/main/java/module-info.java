/**
 * Main module for the SillyDB - a database written in Java for lulz.
 */
module sillydb.main {

    exports mlesiewski.sillydb;
    exports mlesiewski.sillydb.builder;
    exports mlesiewski.sillydb.predicate;
    exports mlesiewski.sillydb.propertyvalue;
    exports mlesiewski.sillydb.order;

    requires org.slf4j;
    requires io.reactivex.rxjava3;

}