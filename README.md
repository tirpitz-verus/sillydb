![badge for after_main_merge](https://github.com/tirpitz-verus/sillydb/actions/workflows/after_main_merge.yml/badge.svg)

# sillydb
a silly db in java for lulz

## roadmap
* 1.0 - InMemory
  * 0.1 - **CRUD for things with properties and categories**
  * 0.2 - **find by property value string equality (ThingPredicate api)**
  * 0.3 - **find by property value regexp**
  * 0.4 - **find by property existence**
  * 0.5 - **find by multiple predicates**
  * 0.6 - **find by predicate negation**
  * 0.7 - **property types (generify property values, StringPropertyValue)**
  * 0.8 - **Boolean properties**
  * 0.9 - **BigDecimal properties (greater/lower predicates)**
  * 0.10 - Long properties (greater/lower predicates)
  * 0.11 - ZonedDateTime properties (before/after predicates)
  * 0.12 - ZonedDate properties (before/after predicates, allow comparing to ZonedDateTime)
  * 0.13 - guard against changing value type in a scope of a category
  * 0.14 - handle concurrent operations (optional long-running tests to be always executed by CI)
  * 0.15 - ordering by one field
  * 0.16 - ordering by many fields
  * 0.17 - batch put
* 2.0 - File save/load
  * 1.1 - directory parameter (also in builder, check if dir in use by other instance, create it if needs be, write version and validate it)
  * 1.2 - information schema (structure_info)
  * 1.3 - logs for every flow (test slf4j properties)
  * 1.4 - save to file
  * 1.5 - load from file
  * 1.6 - handle concurrent file operations
* 3.0 - FileBased
  * 2.1 - test r/w speeds (both by timing each operation and by having perf tests - run optionally)
  * 2.2 - create and remove files for all category operations
  * 2.3 - write to a file on every put
  * 2.4 - read from a file on every read (completely cut the ties between in-memory and file-based)
* 4.0 - Metrics
  * r/w speeds
  * category and things count
  * RAM usage
  * HD usage
