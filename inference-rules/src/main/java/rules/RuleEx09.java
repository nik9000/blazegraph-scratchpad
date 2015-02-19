package rules;


import org.openrdf.model.URI;
import org.openrdf.model.ValueFactory;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.model.impl.ValueFactoryImpl;
import org.openrdf.model.vocabulary.RDF;

import com.bigdata.bop.Constant;
import com.bigdata.bop.IConstant;
import com.bigdata.bop.IConstraint;
import com.bigdata.bop.constraint.Constraint;
import com.bigdata.bop.constraint.NE;
import com.bigdata.rdf.internal.IV;
import com.bigdata.rdf.internal.impl.uri.FullyInlineURIIV;
import com.bigdata.rdf.spo.SPOPredicate;
import com.bigdata.rdf.vocab.Vocabulary;
import com.bigdata.relation.rule.Rule;

/**
 * rdfs9:
 * <pre>
 *       triple(?v,rdf:type,?x) :-
 *          triple(?u,rdfs:subClassOf,?x),
 *          triple(?v,rdf:type,?u). 
 * </pre>
 */
public class RuleEx09 extends Rule {

	private static IConstant<IV> ex(Vocabulary vocab) {
		ValueFactory factory = ValueFactoryImpl.getInstance();
		URI uri =
				new URIImpl("http://www.example.org/#subClassOf");
		        // new URIImpl("http://www.w3.org/2000/01/rdf-schema#subPropertyOf");
		IConstant<IV> constant = 
				vocab.getConstant(uri);
		        //new Constant(new FullyInlineTypedLiteralIV("ex:subClassOf", null, uri));
		        //new Constant(new FullyInlineURIIV(uri));
		
		return constant;
    }

    public RuleEx09( String relationName, Vocabulary vocab) {

        super( "ex09", new SPOPredicate(relationName,var("v"), vocab.getConstant(RDF.TYPE), var("x")),//
                new SPOPredicate[] {//
                    new SPOPredicate(relationName,var("u"), ex(vocab), var("x")),//
                    new SPOPredicate(relationName,var("v"), vocab.getConstant(RDF.TYPE), var("u"))//
                },
                new IConstraint[] {
        			Constraint.wrap(new NE(var("u"),var("x")))
                });

    }

}