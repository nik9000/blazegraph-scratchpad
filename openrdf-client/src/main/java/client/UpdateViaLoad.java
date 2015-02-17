package client;

import org.openrdf.query.QueryLanguage;
import org.openrdf.query.Update;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;

import com.bigdata.rdf.sail.BigdataSailFactory;

public class UpdateViaLoad {

	public static void main(String[] args) throws Exception {
		Repository repo = BigdataSailFactory
				.connect("http://localhost:9999/bigdata/sparql");

		String dumpPath = LocalDump.get().getAbsolutePath();

		RepositoryConnection con = repo.getConnection();
		Update update = con.prepareUpdate(QueryLanguage.SPARQL, "LOAD <file://"
				+ dumpPath + ">");
		update.execute();
	}

}