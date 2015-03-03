package rules;

import org.openrdf.model.vocabulary.RDF;

import schemas.Inference;

import com.bigdata.rdf.spo.SPOPredicate;
import com.bigdata.rdf.vocab.Vocabulary;
import com.bigdata.relation.rule.Rule;

/**
 * rdfs3:
 * 
 * <pre>
 *   triple(v rdf:type x) :-
 *     triple(a rdfs:range x),
 *     triple(u a v).
 * </pre>
 */
public class RuleEx03 extends Rule {
	public RuleEx03(String name, Vocabulary vocab) {
		super("rdfs03",//
				new SPOPredicate(name, var("v"), vocab.getConstant(RDF.TYPE), var("x")),
				new SPOPredicate[] {
						new SPOPredicate(name, var("a"), vocab.getConstant(Inference.RANGE), var("x")),
						new SPOPredicate(name, var("u"), var("a"), var("v"))
				},
				null
		);
	}
}
