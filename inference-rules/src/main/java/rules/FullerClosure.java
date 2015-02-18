package rules;

import java.util.ArrayList;
import java.util.List;

import com.bigdata.rdf.rules.FullClosure;
import com.bigdata.rdf.store.AbstractTripleStore;
import com.bigdata.relation.rule.Rule;

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