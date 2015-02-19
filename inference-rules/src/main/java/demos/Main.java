package demos;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.repository.RepositoryException;

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;

public class Main {

	public static void main(String[] args) throws Exception {
		run(new Example1());
		run(new Example2());
		run(new Example3());
	}

	public static void run(Example example) throws Exception {
		RepositoryConnection cxn = createRepository();
		System.out.println("\nRunning example " + example.getClass().getName());
		example.update(cxn);
		example.query(cxn);
		cxn.commit();
		cxn.close();
	}

	public static RepositoryConnection createRepository() throws IOException,
			RepositoryException {
		File jnl = File.createTempFile("bigdata", ".jnl");
		Properties properties = new Properties();
		String resource = "/fullerclosure.properties";
		InputStream propertiesStream = Main.class.getResourceAsStream(resource);
		properties.load(propertiesStream);
		properties.setProperty(BigdataSail.Options.FILE, jnl.getAbsolutePath());
		BigdataSail sail = new BigdataSail(properties);
		Repository repo = new BigdataSailRepository(sail);
		repo.initialize();
		return repo.getConnection();
	}

}
