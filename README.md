![badge for after_main_merge](https://github.com/tirpitz-verus/sillydb/actions/workflows/after_main_merge.yml/badge.svg)

# sillydb
a silly db in java for lulz

## roadmap

* 0.1 - **CRUD for things with properties and categories**
* 0.2 - **find by property value string equality (ThingPredicate api)**
* 0.3 - **find by property value regexp**
* 0.4 - **find by property existence**
* 0.5 - **find by multiple predicates**
* 0.6 - **find by predicate negation**
* 0.7 - **property types (generify property values, StringPropertyValue)**
* 0.8 - **Boolean properties**
* 0.9 - **BigDecimal properties (greater/lower predicates)**
* 0.10 - **Long properties (greater/lower predicates)**
* 0.11 - **ZonedDateTime properties (before/after predicates)**
* 0.12 - **guard against changing value type in a scope of a category**
* 0.13 - **handle concurrent operations (optional long-running tests to be always executed by CI)**
* 0.14 - **ordering by one property**
* 0.15 - ordering by many properties
* 0.16 - batch put
* 0.17 - add a method to return whole category (ordered and unordered)
* 0.18 - handle subscription cancellation in find methods
* 0.19 - handle backpressure in find methods
* 1.0 - InMemory release
* 1.1 - directory parameter (also in builder, check if dir in use by other instance, create it if needs be, write version and validate it)
* 1.2 - information schema (structure_info)
* 1.3 - logs for every flow (test slf4j properties)
* 1.4 - save to file
* 1.5 - load from file
* 1.6 - handle concurrent file operations
* 2.0 - File save/load release
* 2.1 - test r/w speeds (both by timing each operation and by having perf tests - run optionally)
* 2.2 - create and remove files for all category operations
* 2.3 - write to a file on every put
* 2.4 - read from a file on every read (completely cut the ties between in-memory and file-based)
* 3.0 - FileBased release
* 3.1 - r/w speeds
* 3.2 - category and things count
* 3.3 - RAM usage
* 3.4 - HD usage
* 3.5 - predicates speeds
* 3.6 - ordering speeds
* 4.0 - Metrics release
