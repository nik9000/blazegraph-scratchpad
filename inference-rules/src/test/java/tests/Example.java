package tests;

import org.openrdf.OpenRDFException;
import org.openrdf.query.BindingSet;
import org.openrdf.repository.RepositoryConnection;

public interface Example {
	Iterable<BindingSet> run(RepositoryConnection cxn) throws OpenRDFException;
}
