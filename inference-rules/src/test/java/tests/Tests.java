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

public class Tests {

	private RepositoryConnection createRepository() throws Exception {
		File jnl = File.createTempFile("bigdata", ".jnl");
		Properties properties = new Properties();
		String resource = "/exampleclosure.properties";
		InputStream propertiesStream = getClass().getResourceAsStream(resource);
		properties.load(propertiesStream);
		properties.setProperty(BigdataSail.Options.FILE, jnl.getAbsolutePath());
		BigdataSail sail = new BigdataSail(properties);
		Repository repo = new BigdataSailRepository(sail);
		repo.initialize();
		return repo.getConnection();
	}

	private Iterator<BindingSet> run(Example example) throws Exception {
		RepositoryConnection cxn = createRepository();
		Iterator<BindingSet> rows = example.run(cxn).iterator();
		cxn.close();
		return rows;
	}

	private void expect(String expected, Value value) {
		assertEquals(expected, value.stringValue());
	}

	@Test
	public void example1() throws Exception {
		Iterator<BindingSet> rows = run(new Example1());
		BindingSet row;

		row = rows.next();
		expect("http://www.bigdata.com/rdf#Mike", row.getValue("who"));
		expect("http://www.bigdata.com/rdf#RDF", row.getValue("whom"));

		assertFalse(rows.hasNext());
	}

	@Test
	public void example2() throws Exception {
		Iterator<BindingSet> rows = run(new Example2());

		expect("http://www.example.org/#book1", rows.next().getValue("book"));
		expect("http://www.example.org/#book2", rows.next().getValue("book"));
		expect("http://www.example.org/#book3", rows.next().getValue("book"));

		assertFalse(rows.hasNext());
	}

	@Test
	public void example3() throws Exception {
		Iterator<BindingSet> rows = run(new Example3());

		expect("http://www.example.org/#book1", rows.next().getValue("book"));
		expect("http://www.example.org/#book2", rows.next().getValue("book"));
		expect("http://www.example.org/#book3", rows.next().getValue("book"));

		assertFalse(rows.hasNext());
	}
}
