package tests;

import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.openrdf.OpenRDFException;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.Update;
import org.openrdf.repository.RepositoryConnection;

import com.google.common.collect.PeekingIterator;

public class Example3 implements Example {
	private final String testType;

	public Example3(String testType) {
		this.testType = testType;
	}
	public Iterable<BindingSet> run(RepositoryConnection cxn)
			throws OpenRDFException {

		String insert = "" //
				+ "PREFIX ex: <http://www.example.org/#testType->\n           ".replace("testType", testType)
				+ "INSERT {\n                                                 "
				+ "  ex:book1 rdf:type ex:Publication .\n                     "
				+ "  ex:book2 rdf:type ex:Article .\n                         "
				+ "  ex:Article ex:subClassOf ex:Publication .\n              "
				+ "  ex:publishes ex:range ex:Publication .\n                 "
				+ "  ex:MITPress ex:publishes ex:book3 .\n                    "
				+ "} WHERE {}                                                ";
		Update update = cxn.prepareUpdate(QueryLanguage.SPARQL, insert);
		update.execute();

		String queryStr = "" //
				+ "PREFIX ex: <http://www.example.org/#testType->\n           ".replace("testType", testType)
				+ "SELECT ?book WHERE {\n                                     "
				+ "  ?book rdf:type ex:Publication.\n                         "
				+ "  ?book rdf:type/ex:subClassOf* ex:Publication.\n          "
				+ "} ORDER BY ?book                                           ";
		TupleQuery tupleQuery = cxn.prepareTupleQuery(QueryLanguage.SPARQL,
				queryStr);
		TupleQueryResult result = tupleQuery.evaluate();
		List<BindingSet> rows = new LinkedList<BindingSet>();
		while (result.hasNext()) {
			rows.add(result.next());
		}
		return rows;
	}

	public void check(PeekingIterator<BindingSet> rows) {
		assertThat(rows.next().getValue("book"), hasToString("http://www.example.org/#testType-book1".replace("testType", testType)));
		assertThat(rows.next().getValue("book"), hasToString("http://www.example.org/#testType-book2".replace("testType", testType)));
		if (testType.equals("inference")) {
			assertThat(rows.next().getValue("book"), hasToString("http://www.example.org/#testType-book3".replace("testType", testType)));
			// TODO this isn't done yet
		}
		assertFalse(rows.hasNext());
	}
}
