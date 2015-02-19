package rules;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;

public class RepoMan {

	public static Properties properties() throws IOException {
		InputStream propertiesStream = RepoMan.class
				.getResourceAsStream("/fullclosure.properties");
		Properties properties = new Properties();
		properties.load(propertiesStream);
		return properties;
	}

	public static Repository repo() throws IOException, RepositoryException {
		File journal = File.createTempFile("bigdata", ".jnl");
		Properties properties = properties();
		properties.setProperty(BigdataSail.Options.FILE,
				journal.getAbsolutePath());
		BigdataSail sail = new BigdataSail(properties);
		Repository repo = new BigdataSailRepository(sail);
		repo.initialize();
		return repo;
	}

}
