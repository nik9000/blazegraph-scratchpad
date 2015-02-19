package demos;

import org.openrdf.OpenRDFException;
import org.openrdf.query.BindingSet;
import org.openrdf.repository.RepositoryConnection;

public interface Example {

	Iterable<BindingSet> query(RepositoryConnection cxn) throws OpenRDFException;

	void update(RepositoryConnection cxn) throws OpenRDFException;

}
