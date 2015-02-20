package client;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.bigdata.rdf.sail.BigdataSailFactory;

public class LocalConnection {

	public static RepositoryConnection get() throws RepositoryException {
		// Repository repo =
		// BigdataSailFactory.connect("http://localhost:9999/bigdata/sparql");
		Repository repo = BigdataSailFactory.connect("localhost", 9999);
		return repo.getConnection();
	}

}
