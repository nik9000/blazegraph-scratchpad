package tests;

import org.openrdf.OpenRDFException;
import org.openrdf.query.BindingSet;
import org.openrdf.repository.RepositoryConnection;

import com.google.common.collect.PeekingIterator;

public interface Example {
	Iterable<BindingSet> run(RepositoryConnection cxn) throws OpenRDFException;
	void check(PeekingIterator<BindingSet> rows);
}
