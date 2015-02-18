package rules;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.model.URI;
import org.openrdf.model.Value;
import org.openrdf.model.impl.StatementImpl;
import org.openrdf.model.impl.URIImpl;
import org.openrdf.query.BindingSet;
import org.openrdf.query.MalformedQueryException;
import org.openrdf.query.QueryEvaluationException;
import org.openrdf.query.QueryLanguage;
import org.openrdf.query.TupleQuery;
import org.openrdf.query.TupleQueryResult;
import org.openrdf.query.Update;
import org.openrdf.query.UpdateExecutionException;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;

public class Repo {

	public static void main(String[] args) throws Exception {
		Properties properties = properties();
		Repository repo = repo(properties);
		doSomething(repo);
	}

	public static Properties properties() throws IOException {
		InputStream propertiesStream = Repo.class
				.getResourceAsStream("/fullclosure.properties");
		Properties properties = new Properties();
		properties.load(propertiesStream);
		return properties;
	}

	public static Repository repo(Properties properties) throws IOException,
			RepositoryException {
		File journal = File.createTempFile("bigdata", ".jnl");
		properties.setProperty(BigdataSail.Options.FILE,
				journal.getAbsolutePath());
		BigdataSail sail = new BigdataSail(properties);
		Repository repo = new BigdataSailRepository(sail);
		repo.initialize();
		return repo;
	}

	public static void doSomething(Repository repo) throws Exception {
		RepositoryConnection cxn = repo.getConnection();
		cxn.setAutoCommit(false);
		try {
			// singleStatement(cxn);
			update(cxn);
			query(cxn);
			cxn.commit();
		} catch (Exception ex) {
			cxn.rollback();
			throw ex;
		} finally {
			cxn.close();
		}
	}

	public static void update(RepositoryConnection cxn)
			throws RepositoryException, MalformedQueryException,
			UpdateExecutionException {
		String insert = "" //
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" //
				+ "PREFIX ex: <http://www.example.org/#>\n" //
				+ "INSERT {\n" //
				+ "  ex:book1 rdf:Type ex:Publication .\n" //
				+ "  ex:book2 rdf:type ex:Article .\n" //
				+ "  ex:Article rdfs:subClassOf ex:Publication .\n" //
				+ "  ex:publishes rdfs:range ex:Publication .\n" //
				+ "  ex:MITPress ex:publishes ex:book3 .\n" //
				+ "} WHERE {}";
		Update update = cxn.prepareUpdate(QueryLanguage.SPARQL, insert);
		update.execute();
	}

	public static void query(RepositoryConnection cxn)
			throws RepositoryException, MalformedQueryException,
			QueryEvaluationException {
		String queryStr = "" //
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n" //
				+ "PREFIX ex: <http://www.example.org/#>\n" //
				+ "SELECT ?book WHERE {\n" //
				+ "  ?book rdf:Type ex:Publication.\n" //
				+ "}";
		TupleQuery tupleQuery = cxn.prepareTupleQuery(QueryLanguage.SPARQL,
				queryStr);
		TupleQueryResult result = tupleQuery.evaluate();
		while (result.hasNext()) {
			BindingSet row = result.next();
			System.out.println(row.toString());
		}
	}

	public static void singleStatement(RepositoryConnection cxn)
			throws RepositoryException {
		Resource s = new URIImpl("http://www.bigdata.com/rdf#Mike");
		URI p = new URIImpl("http://www.bigdata.com/rdf#loves");
		Value o = new URIImpl("http://www.bigdata.com/rdf#RDF");
		Statement stmt = new StatementImpl(s, p, o);
		cxn.add(stmt);
	}

}
