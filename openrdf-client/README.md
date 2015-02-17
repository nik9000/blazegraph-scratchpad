## About

This is a scratch project to play around with Blazegraph's [Sesame](http://rdf4j.org/) client.

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

The easiest way to run the client is to load it as a Maven project in Eclipse and run its various `main()` classes directly.

If the command line is your bag, use Maven:

```
mvn exec:java -Dexec.mainClass="client.UpdateViaLoad"
```
