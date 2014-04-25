package com.boundary.sdk.event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ScriptUtils {
	
	private static Logger LOG = LoggerFactory.getLogger(ScriptUtils.class);

	public ScriptUtils() {
	}
	
	/**
	 * Finds a script to be tested from 
	 * @param scriptName
	 * @return {@link File}
	 */
	static public FileReader getFile(String scriptPath) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		URL url = loader.getResource(scriptPath);
		assert(url == null);
		File f = new File(url.getFile());
		FileReader reader = null;
		try {
			reader = new FileReader(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			LOG.error(e.getMessage());
		}
		return reader;
	}
}
