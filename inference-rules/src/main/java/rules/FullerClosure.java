package rules;

import java.util.ArrayList;
import java.util.List;

import org.openrdf.model.impl.URIImpl;

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
		customRules.add(new RuleEx09(database, vocab));
		return customRules;
	}
}
