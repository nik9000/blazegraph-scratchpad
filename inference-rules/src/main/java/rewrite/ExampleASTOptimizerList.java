package rewrite;

import com.bigdata.rdf.sparql.ast.optimizers.DefaultOptimizerList;

public class ExampleASTOptimizerList extends DefaultOptimizerList {
	private static final long serialVersionUID = -7025970079952924736L;

	public ExampleASTOptimizerList() {
		push(new ExampleRewrite());
	}
}
