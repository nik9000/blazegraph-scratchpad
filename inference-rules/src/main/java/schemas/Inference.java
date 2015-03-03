package schemas;

import org.openrdf.model.URI;
import org.openrdf.model.impl.URIImpl;

public class Inference {
	public static final String NAMESPACE = "http://www.example.org/#inference-";
	public static final URI SUBCLASSOF = new URIImpl(NAMESPACE + "subClassOf");
	public static final URI RANGE = new URIImpl(NAMESPACE + "range");
}
