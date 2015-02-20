package tests;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.Update;
import org.openrdf.repository.RepositoryConnection;

import client.LocalConnection;
import client.LocalDump;

public class Tests {

	final String WDQ_CAPITAL = "<http://www.wikidata.org/entity/P36s>";

	private void expect(String expected, Value value) {
		assertEquals(expected, value.stringValue());
	}

	@BeforeClass
	public static void seedDatabase() throws Exception {
		RepositoryConnection con = LocalConnection.get();

		// This blows up with large files
		// FileInputStream fis = new FileInputStream(LocalDump.file());
		// GZIPInputStream gis = new GZIPInputStream(fis);
		// con.add(gis, null, RDFFormat.N3);

		String query = "LOAD <file://" + LocalDump.path() + ">";
		Update update = con.prepareUpdate(QueryLanguage.SPARQL, query);
		update.execute();
	}

	@Test
	public void queryTest() throws Exception {
		String queryS = "SELECT ?place ?capital WHERE { ?place " + WDQ_CAPITAL
				+ " ?capital. }";

		RepositoryConnection con = LocalConnection.get();
		TupleQuery query = con.prepareTupleQuery(QueryLanguage.SPARQL, queryS);
		TupleQueryResult rows = query.evaluate();

		BindingSet row = rows.next();

		expect("http://www.wikidata.org/entity/Q3680", row.getValue("place"));
		expect("http://www.wikidata.org/entity/Q3680S42A257E1-04A6-4D15-A57C-E3F6B0788AF0",
				row.getValue("capital"));
	}
}
