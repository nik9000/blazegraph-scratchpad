package rewrite;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openrdf.model.vocabulary.RDF;

import schemas.Rewrite;

import com.bigdata.bop.IBindingSet;
import com.bigdata.rdf.internal.IV;
import com.bigdata.rdf.sparql.ast.ConstantNode;
import com.bigdata.rdf.sparql.ast.IGroupMemberNode;
import com.bigdata.rdf.sparql.ast.JoinGroupNode;
import com.bigdata.rdf.sparql.ast.PathNode;
import com.bigdata.rdf.sparql.ast.PathNode.PathElt;
import com.bigdata.rdf.sparql.ast.PathNode.PathMod;
import com.bigdata.rdf.sparql.ast.PathNode.PathSequence;
import com.bigdata.rdf.sparql.ast.PropertyPathNode;
import com.bigdata.rdf.sparql.ast.StatementPatternNode;
import com.bigdata.rdf.sparql.ast.StaticAnalysis;
import com.bigdata.rdf.sparql.ast.TermNode;
import com.bigdata.rdf.sparql.ast.eval.AST2BOpContext;
import com.bigdata.rdf.sparql.ast.optimizers.AbstractJoinGroupOptimizer;

/**
 * Its <strong>supposed to do this:
 * <pre>
 *   triple(?v,rdf:type,?x) :-
 *     triple(?u,rdfs:subClassOf,?x),
 *     triple(?v,rdf:type,?u).
 * </pre>
 * But it currently only works for basic statements.
 */
public class ExampleRewrite extends AbstractJoinGroupOptimizer {
	@Override
	protected void optimizeJoinGroup(AST2BOpContext ctx, StaticAnalysis sa,
			IBindingSet[] bSets, JoinGroupNode op) {
		Iterator<IGroupMemberNode> itr = op.iterator();
		List<IGroupMemberNode> replacements = new ArrayList<>();
		while (itr.hasNext()) {
			IGroupMemberNode node = itr.next();
			if (node instanceof StatementPatternNode) {
				if (rewrite(ctx, (StatementPatternNode)node, replacements)) {
					itr.remove();
				}
			}
		}
		for (IGroupMemberNode replacement: replacements) {
			op.addChild(replacement);
		}
	}

	/**
	 * Rewrites <code>?s rdf:type ?o</code> to <code>?s rdf:type/rdf:subClassOf*</code>.
	 * @return should node be removed from its group
	 */
	private boolean rewrite(AST2BOpContext context, StatementPatternNode node, List<IGroupMemberNode> replacements) {
		TermNode p = node.p();
		@SuppressWarnings("rawtypes")
		IV oldValue = p.getValueExpression().get();
		if (oldValue == null || !oldValue.asValue(context.getAbstractTripleStore().getLexiconRelation()).equals(RDF.TYPE)) {
			return false;
		}
		
		@SuppressWarnings("rawtypes")
		IV newValue = context.getAbstractTripleStore().getVocabulary().get(Rewrite.SUBCLASSOF);		
		PathSequence pathSequence = new PathSequence(new PathElt(new ConstantNode(oldValue)), new PathElt(new ConstantNode(newValue), PathMod.ZERO_OR_MORE));
		PathNode path = new PathNode(new PathNode.PathAlternative(pathSequence));
		replacements.add(new PropertyPathNode(node.s(), path, node.o(), node.c(), node.getScope()));
		return true;
	}
}
