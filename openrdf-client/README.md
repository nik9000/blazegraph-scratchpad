# OpenRDF Java client for Blazegraph

*February 17, 2015*

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
