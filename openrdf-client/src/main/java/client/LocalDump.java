package client;

import java.io.File;
import java.net.URL;

public class LocalDump {

	public static File get() {
		URL url = LocalDump.class.getClassLoader().getResource(
				"wikidata-statements_head-100k.nt.gz");
		return new File(url.getFile());
	}

}
