package rules;

import org.openrdf.model.impl.URIImpl;

import com.bigdata.rdf.vocab.BaseVocabularyDecl;
import com.bigdata.rdf.vocab.DefaultBigdataVocabulary;

public class MyVocabularyVersion0 extends DefaultBigdataVocabulary {

	public MyVocabularyVersion0() {
		super();
	}

	public MyVocabularyVersion0(final String namespace) {
		super(namespace);
	}

	@Override
	protected void addValues() {

		super.addValues();

		addDecl(new BaseVocabularyDecl( //
				new URIImpl("http://www.example.org/#"), //
				new URIImpl("http://www.example.org/#subClassOf") //
		));

	}
}
