package client;

import org.openrdf.query.BindingSet;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

import com.bigdata.rdf.sail.BigdataSailFactory;

public class Query {

	public static void main(String[] args) throws Exception {
		String queryStr = "SELECT ?place ?capital WHERE {"
				+ "?place <http://www.wikidata.org/entity/P36s> ?capital. "
				+ "}";

		Repository repo = BigdataSailFactory
				.connect("http://localhost:9999/bigdata/sparql");

		RepositoryConnection con = repo.getConnection();
		TupleQuery tupleQuery = con.prepareTupleQuery(QueryLanguage.SPARQL,
				queryStr);
		TupleQueryResult result = tupleQuery.evaluate();

		while (result.hasNext()) {
			BindingSet row = result.next();
			System.out.println(row.toString());
		}
	}
}
