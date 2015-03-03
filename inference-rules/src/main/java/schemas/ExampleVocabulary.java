package schemas;

import com.bigdata.rdf.vocab.BaseVocabularyDecl;
import com.bigdata.rdf.vocab.DefaultBigdataVocabulary;

public class ExampleVocabulary extends DefaultBigdataVocabulary {

	public ExampleVocabulary() {
		super();
	}

	public ExampleVocabulary(final String namespace) {
		super(namespace);
	}

	@Override
	protected void addValues() {
		super.addValues();
		addDecl(new BaseVocabularyDecl(
				Inference.SUBCLASSOF,
				Inference.RANGE,
				Rewrite.SUBCLASSOF,
				Rewrite.RANGE
		));
	}
}