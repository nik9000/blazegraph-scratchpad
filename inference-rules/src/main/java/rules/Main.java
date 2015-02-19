package rules;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

public class Main {

	public static void main(String[] args) throws Exception {
		run(new Example1());
		run(new Example2());
		run(new Example3());
	}

	public static void run(Example example) throws Exception {
		Repository repo = RepoMan.repo();
		RepositoryConnection cxn = repo.getConnection();
		System.out.println("\nRunning example " + example.getClass().getName());
		example.update(cxn);
		example.query(cxn);
		cxn.commit();
		cxn.close();
	}

}
