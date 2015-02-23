package tests;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import org.junit.Test;
import org.openrdf.model.Value;
import org.openrdf.query.BindingSet;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

public class Tests {

	private RepositoryConnection createRepository() throws Exception {
		Properties properties = new Properties();
		String resource = "/exampleclosure.properties";
		InputStream propertiesStream = getClass().getResourceAsStream(resource);
		properties.load(propertiesStream);

		File jnl = File.createTempFile("bigdata", ".jnl");
		properties.setProperty(BigdataSail.Options.FILE, jnl.getAbsolutePath());

		BigdataSail sail = new BigdataSail(properties);
		Repository repo = new BigdataSailRepository(sail);
		repo.initialize();

		return repo.getConnection();
	}

	private void run(Example example) throws Exception {
		RepositoryConnection cxn = createRepository();
		PeekingIterator<BindingSet> rows = Iterators.peekingIterator(example.run(cxn).iterator());
		cxn.close();
		example.check(rows);
	}

	@Test
	public void example1() throws Exception {
		run(new Example1());
	}

	@Test
	public void example2() throws Exception {
		run(new Example2());
	}

	@Test
	public void example3() throws Exception {
		run(new Example3());
	}
}
