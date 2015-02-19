package rules;

import java.util.ArrayList;
import java.util.List;

import com.bigdata.rdf.rules.FullClosure;
import com.bigdata.rdf.store.AbstractTripleStore;
import com.bigdata.relation.rule.Rule;

public class ExampleClosure extends FullClosure {

	public ExampleClosure(AbstractTripleStore db) {
		super(db);
	}

	@Override
	protected List<Rule> getCustomRules(String database) {
		List<Rule> customRules = new ArrayList<Rule>();
		customRules.addAll(super.getCustomRules(database));
		customRules.add(new RuleEx03(database, vocab));
		customRules.add(new RuleEx09(database, vocab));
		return customRules;
	}
}
