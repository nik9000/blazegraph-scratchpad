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
		// use one of our pre-propertiesured option-sets or "modes"
		Properties properties = new Properties();
		properties.load(propertiesStream);
		return properties;
	}

	public static Repository repo(Properties properties) throws IOException,
			RepositoryException {
		// create a backing file for the database
		File journal = File.createTempFile("bigdata", ".jnl");
		properties.setProperty(BigdataSail.Options.FILE,
				journal.getAbsolutePath());

		// instantiate a sail and a Sesame repository
		BigdataSail sail = new BigdataSail(properties);
		Repository repo = new BigdataSailRepository(sail);
		repo.initialize();
		return repo;
	}

	public static void doSomething(Repository repo) throws Exception {
		RepositoryConnection cxn = repo.getConnection();
		cxn.setAutoCommit(false);
		try {
			singleStatement(cxn);
			cxn.commit();
		} catch (Exception ex) {
			cxn.rollback();
			throw ex;
		} finally {
			// close the repository connection
			cxn.close();
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
