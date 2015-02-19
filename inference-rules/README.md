## Background

*The following is from [SPARQL 1.1 Entailment Regimes](http://www.w3.org/TR/2009/WD-sparql11-entailment-20091022/#t12), section 1.2.*

...consider the following data:

```
(1) ex:book1 rdf:type ex:Publication .
(2) ex:book2 rdf:type ex:Article .
(3) ex:Article rdfs:subClassOf ex:Publication .
(4) ex:publishes rdfs:range ex:Publication .
(5) ex:MITPress ex:publishes ex:book3 .
```

We use the following query that asks for a list of all publications:

```
SELECT ?pub WHERE { ?pub rdf:type ex:Publication . }
```

Under both simple entailment and RDF entailment,`ex:book1` is the only answer, because no reasoning steps (required to find that `ex:book2` and `ex:book3` are publications) are performed.

To derive that `ex:book2` and `ex:book3` are publications, we need a system that supports RDFS entailment, under which a set of RDFS entailment rules is applied to the *explicitly given* data to derive new consequences.

The rule *rdfs9* can be applied to the triples (3) and (2) to derive:

```
(6) ex:book2 rdf:type ex:Publication
```
 
The rule rdfs3 can be applied to (4) and (5) to derive:

```
(7) ex:book3 rdf:type ex:Publication .
```

The triples (6) and (7) can then be used to find that `ex:book2` and `ex:book3` are also answers to the query under an RDFS entailment regime. 

## Walkthrough

Define your schema:

```java
package schemas;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

public class EXAMPLE {

  public static final String NAMESPACE = "http://www.example.org/#";

  public static final URI SUBCLASSOF = new URIImpl(NAMESPACE + "subClassOf");
  public static final URI RANGE = new URIImpl(NAMESPACE + "range");

}
```

Incorporate it into a `Vocabulary`:

```java
package schemas;

public class ExampleVocabulary extends DefaultBigdataVocabulary {

  public ExampleVocabulary() {
    super();
  }

  public ExampleVocabulary(final String namespace) {
    super(namespace);
  }

  @Override
  protected void addValues() {
    super.addValues();
    addDecl(new BaseVocabularyDecl(
        EXAMPLE.SUBCLASSOF,
        EXAMPLE.RANGE
    ));
  }
}
```

Add your vocabulary class to your configuration:

*exampleclosure.properties:*

```
com.bigdata.rdf.store.AbstractTripleStore.vocabularyClass=schemas.ExampleVocabulary
```

Create a new `Rule`:

```java
package rules;

public class RuleEx09 extends Rule {
  public RuleEx09(String relationName, Vocabulary vocab) {
    super("ex09", new SPOPredicate(relationName, var("v"),
        vocab.getConstant(RDF.TYPE), var("x")),
        new SPOPredicate[] {
            new SPOPredicate(relationName, var("u"), vocab.getConstant(EXAMPLE.SUBCLASSOF), var("x")),
            new SPOPredicate(relationName, var("v"), vocab.getConstant(RDF.TYPE), var("u"))
        },
        new IConstraint[] { Constraint.wrap(new NE(var("u"), var("x"))) });
  }
}
```

Create a `FullClosure` that uses your `Rule`:

```java
package rules;

public class ExampleClosure extends FullClosure {

  public FullerClosure(AbstractTripleStore db) {
    super(db);
  }

  @Override
  protected List<Rule> getCustomRules(String database) {
    List<Rule> customRules = new ArrayList<Rule>();
    customRules.addAll(super.getCustomRules(database));
    customRules.add(new RuleEx09(database, vocab));
    return customRules;
  }
}
```

Add your closure class to your configuration:

*exampleclosure.properties:*

```
com.bigdata.rdf.store.AbstractTripleStore.closureClass=rules.ExampleClosure
```

Load up the configuration, and create a new repository instance:

```java
File jnl = File.createTempFile("bigdata", ".jnl");
Properties properties = new Properties();
String resource = "/exampleclosure.properties";
InputStream propertiesStream = getClass().getResourceAsStream(resource);
properties.load(propertiesStream);
properties.setProperty(BigdataSail.Options.FILE, jnl.getAbsolutePath());
BigdataSail sail = new BigdataSail(properties);
Repository repo = new BigdataSailRepository(sail);
repo.initialize();
```

## References

* http://wiki.bigdata.com/wiki/index.php/InferenceAndTruthMaintenance
* http://wiki.bigdata.com/wiki/index.php/GettingStarted
* http://www.w3.org/TR/2009/WD-sparql11-entailment-20091022/
