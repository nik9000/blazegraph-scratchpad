package rules;

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

public class Example1 implements Example {

	public void query(RepositoryConnection cxn) throws OpenRDFException {
		String queryStr = "" //
				+ "PREFIX ex: <http://www.example.org/#>\n                    "
				+ "SELECT ?who ?whom WHERE {\n                                "
				+ "  ?who <http://www.bigdata.com/rdf#loves> ?whom .\n        "
				+ "}                                                          ";
		TupleQuery tupleQuery = cxn.prepareTupleQuery(QueryLanguage.SPARQL,
				queryStr);
		TupleQueryResult result = tupleQuery.evaluate();
		while (result.hasNext()) {
			BindingSet row = result.next();
			System.out.println(row.toString());
		}
	}

	public void update(RepositoryConnection cxn) throws OpenRDFException {
		Resource s = new URIImpl("http://www.bigdata.com/rdf#Mike");
		URI p = new URIImpl("http://www.bigdata.com/rdf#loves");
		Value o = new URIImpl("http://www.bigdata.com/rdf#RDF");
		Statement stmt = new StatementImpl(s, p, o);
		cxn.add(stmt);
	}

}
