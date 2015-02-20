package client;

import java.io.File;
import java.net.URL;

public class LocalDump {

	public static String path() {
		return file().getAbsolutePath();
	}

	public static File file() {
		String filename = "wikidata-statements_head-10k.nt.gz";
		URL url = LocalDump.class.getClassLoader().getResource(filename);
		return new File(url.getFile());
	}

}
