package rules;

import org.openrdf.model.vocabulary.RDF;
import org.openrdf.model.vocabulary.RDFS;

import com.bigdata.rdf.spo.SPOPredicate;
import com.bigdata.rdf.vocab.Vocabulary;
import com.bigdata.relation.rule.Rule;

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
