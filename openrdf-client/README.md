# OpenRDF Java client for Blazegraph

This is a toy project to play around with Blazegraph's [Sesame](http://rdf4j.org/) client library.

## Getting started

Get Blazegraph:

```
git clone git://git.code.sf.net/p/bigdata/git blazegraph
```

Start Blazegraph:

```
cd blazegraph
ant start-blazegraph
```

## Running the client

The client code runs as JUnit test(s).  Run it with Maven:

```
$ mvn test

...

Results :

Tests run: 1, Failures: 0, Errors: 0, Skipped: 0

[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 4.245 s
[INFO] Finished at: 2015-02-19T16:31:23-08:00
[INFO] Final Memory: 20M/218M
[INFO] ------------------------------------------------------------------------
```

## Overview

To get a [SAIL](https://github.com/tinkerpop/blueprints/blob/master/doc/Sail-Implementation.textile connection to Blazegraph:

```java
Repository repo = BigdataSailFactory.connect("localhost", 9999);
RepositoryConnection con = LocalConnection.get();
```

### Insert

One way to insert a small volume of data is by streaming it in from a file:

```java
FileInputStream fis = new FileInputStream(LocalDump.file());
GZIPInputStream gis = new GZIPInputStream(fis);
con.add(gis, null, RDFFormat.N3);
```

To insert big volumes of data without blowing the heap, tell Blazegraph to load it:

```java
String query = "LOAD <file://" + LocalDump.path() + ">";
Update update = con.prepareUpdate(QueryLanguage.SPARQL, query);
update.execute();
```

### Search

To perform a SPARQL query:

```java
String queryS =
     "SELECT ?place ?capital WHERE { "
   + "  ?place <http://www.wikidata.org/entity/P36s> ?capital. "
   + "}";

TupleQuery query = con.prepareTupleQuery(QueryLanguage.SPARQL, queryS);
TupleQueryResult rows = query.evaluate();
```

And iterate over the resulting `rows`.

## References

* [Sesame docs](http://rdf4j.org/documentation.docbook?view)
* [Sesame API guide](http://rdf4j.org/sesame/2.8/docs/programming.docbook?view)
* [Sesame 2.8 Javadoc](http://rdf4j.org/sesame/2.8/apidocs/)
* [Blazegraph 1.4.0 Javadoc](http://www.blazegraph.com/docs/api/index.html)