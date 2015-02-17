package client;

import java.io.FileInputStream;
import java.util.zip.GZIPInputStream;

import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryConnection;
import org.openrdf.rio.RDFFormat;

import com.bigdata.rdf.sail.BigdataSailFactory;

public class UpdateViaStream {

	public static void main(String[] args) throws Exception {
		Repository repo = BigdataSailFactory.connect("localhost", 9999);
		RepositoryConnection con = repo.getConnection();
		FileInputStream fileInputStream = new FileInputStream(LocalDump.get());
		GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream);
		con.add(gzipInputStream, null, RDFFormat.N3);
	}

}