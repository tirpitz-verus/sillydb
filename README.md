![badge for check_main_branch](https://github.com/tirpitz-verus/sillydb/actions/workflows/after_main_merge.yml/badge.svg)

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
  * 0.8 - Boolean properties
  * 0.9 - BigDecimal properties (greater/lower predicates)
  * 0.10 - ZonedDateTime properties (greater/lower predicates)
* 2.0 - File save/load
  * 1.1 - information schema (structure_info)
  * 1.2 - logs for every flow (test slf4j properties)
  * 1.3 - save to file
  * 1.4 - load from file
* 3.0 - FileBased
* 4.0 - Metrics
* 5.0 - Silly Database Language
