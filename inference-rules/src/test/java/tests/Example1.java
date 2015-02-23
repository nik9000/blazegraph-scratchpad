package tests;

import static org.hamcrest.Matchers.hasToString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.util.LinkedList;
import java.util.List;

import org.openrdf.OpenRDFException;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.RepositoryConnection;

import com.google.common.collect.PeekingIterator;


public class Example1 implements Example {

	public Iterable<BindingSet> run(RepositoryConnection cxn)
			throws OpenRDFException {

		Resource s = new URIImpl("http://www.bigdata.com/rdf#Mike");
		URI p = new URIImpl("http://www.bigdata.com/rdf#loves");
		Value o = new URIImpl("http://www.bigdata.com/rdf#RDF");
		Statement stmt = new StatementImpl(s, p, o);
		cxn.add(stmt);

		String queryStr = "" //
				+ "PREFIX ex: <http://www.example.org/#>\n                    "
				+ "SELECT ?who ?whom WHERE {\n                                "
				+ "  ?who <http://www.bigdata.com/rdf#loves> ?whom .\n        "
				+ "}                                                          ";
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
		assertThat(rows.peek().getValue("who"), hasToString("http://www.bigdata.com/rdf#Mike"));
		assertThat(rows.peek().getValue("whom"), hasToString("http://www.bigdata.com/rdf#RDF"));
		rows.next();
		assertFalse(rows.hasNext());
	}
}
