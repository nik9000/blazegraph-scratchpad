package tests;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.openrdf.query.BindingSet;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

import com.bigdata.rdf.sail.BigdataSail;
import com.bigdata.rdf.sail.BigdataSailRepository;
import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;

@RunWith(value = Parameterized.class)
public class Tests {
	@Parameters(name="{0}")
	public static List<String[]> implementations() {
		List<String[]> implementations = new ArrayList<String[]>();
		implementations.add(new String[] {"astrewrite"});
		implementations.add(new String[] {"inference"});
		return implementations;
	}

	@Parameter(0)
	public String testType;

	private RepositoryConnection createRepository() throws Exception {
		Properties properties = new Properties();
		InputStream propertiesStream = getClass().getResourceAsStream("/exampleclosure.properties");
		properties.load(propertiesStream);
		System.setProperty("ASTOptimizerClass", "rewrite.ExampleASTOptimizerList");

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
		run(new Example3(testType));
	}
}
