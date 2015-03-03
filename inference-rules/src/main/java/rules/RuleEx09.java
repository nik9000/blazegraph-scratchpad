package rules;

import org.openrdf.model.vocabulary.RDF;

import schemas.Inference;

import com.bigdata.bop.IConstraint;
import com.bigdata.bop.constraint.Constraint;
import com.bigdata.bop.constraint.NE;
import com.bigdata.rdf.spo.SPOPredicate;
import com.bigdata.rdf.vocab.Vocabulary;
import com.bigdata.relation.rule.Rule;

/**
 * rdfs9:
 * 
 * <pre>
 *   triple(?v,rdf:type,?x) :-
 *     triple(?u,rdfs:subClassOf,?x),
 *     triple(?v,rdf:type,?u).
 * </pre>
 */
public class RuleEx09 extends Rule {
	public RuleEx09(String relationName, Vocabulary vocab) {
		super("ex09",//
				new SPOPredicate(relationName, var("v"), vocab.getConstant(RDF.TYPE), var("x")),
				new SPOPredicate[] {
						new SPOPredicate(relationName, var("u"), vocab.getConstant(Inference.SUBCLASSOF), var("x")),
						new SPOPredicate(relationName, var("v"), vocab.getConstant(RDF.TYPE), var("u"))
				},
				new IConstraint[] { Constraint.wrap(new NE(var("u"), var("x"))) });
	}
}