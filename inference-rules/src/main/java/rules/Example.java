package rules;

import org.openrdf.OpenRDFException;
import org.openrdf.repository.RepositoryConnection;

public interface Example {

	void query(RepositoryConnection cxn) throws OpenRDFException;

	void update(RepositoryConnection cxn) throws OpenRDFException;

}
