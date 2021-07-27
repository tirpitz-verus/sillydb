/**
 * Main module for the SillyDB - a database written in Java for lulz.
 */
module sillydb.main {
    exports mlesiewski.sillydb;
    exports mlesiewski.sillydb.builder;
    exports mlesiewski.sillydb.predicate;

    requires org.slf4j;
    requires io.reactivex.rxjava3;

}