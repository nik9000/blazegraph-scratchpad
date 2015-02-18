## References

* http://wiki.bigdata.com/wiki/index.php/InferenceAndTruthMaintenance
* http://wiki.bigdata.com/wiki/index.php/GettingStarted

## Overview

Create a new `Rule`:

```java
// http://wiki.bigdata.com/wiki/index.php/InferenceAndTruthMaintenance#Writing_your_own_inference_rules
// http://www.blazegraph.com/docs/api/index.html?com/bigdata/relation/rule/Rule.html
public class RuleRdfs10<A> extends Rule<A> {

  private static final long serialVersionUID = -2964784545354974663L;

  public RuleRdfs10(String relationName, Vocabulary vocab) {

    // (?u,rdfs:subClassOf,?u) <= (?u,rdf:type,rdfs:Class)
    super("rdfs10",//
        new SPOPredicate(relationName, var("u"),
            vocab.getConstant(RDFS.SUBCLASSOF), var("u")),//
        new SPOPredicate[] { new SPOPredicate(relationName, var("u"),
            vocab.getConstant(RDF.TYPE),
            vocab.getConstant(RDFS.CLASS)) //
        },//
        null // constraints
    );

  }

}
```

Create a `FullClosure` that uses your `Rule`:

```java
package rules;

public class FullerClosure extends FullClosure {

  public FullerClosure(AbstractTripleStore db) {
    super(db);
  }

  @Override
  protected List<Rule> getCustomRules(String database) {
    List<Rule> customRules = new ArrayList<Rule>();
    customRules.addAll(super.getCustomRules(database));
    customRules.add(new RuleRdfs10(database, vocab));
    System.out.println("*** " + customRules.size() + " CUSTOM RULES");
    return customRules;
  }
}
```

Add your closure class to your configuration:

*fullfeature.properties:*

```
com.bigdata.rdf.store.AbstractTripleStore.closureClass=rules.FullerClosure
```

Load up the configuration, and create a new repository instance:

```java
Properties properties = ...

File journal = File.createTempFile("bigdata", ".jnl");
properties.setProperty(BigdataSail.Options.FILE, journal.getAbsolutePath());

BigdataSail sail = new BigdataSail(properties);
Repository repo = new BigdataSailRepository(sail);
repo.initialize();
```
