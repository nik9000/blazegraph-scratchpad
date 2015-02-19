package schemas;

import org.openrdf.model.impl.URIImpl;

import com.bigdata.rdf.vocab.BaseVocabularyDecl;
import com.bigdata.rdf.vocab.DefaultBigdataVocabulary;

public class EnhancedVocabulary extends DefaultBigdataVocabulary {

	public EnhancedVocabulary() {
		super();
	}

	public EnhancedVocabulary(final String namespace) {
		super(namespace);
	}

	@Override
	protected void addValues() {

		super.addValues();

		addDecl(new BaseVocabularyDecl( //
				new URIImpl(EXAMPLE.NAMESPACE),
				EXAMPLE.SUBCLASSOF
		));

	}
}
