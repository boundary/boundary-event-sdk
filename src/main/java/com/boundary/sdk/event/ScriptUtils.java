// Copyright 2014-2015 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.sdk.event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utilities used for java script execution
 * 
 * @author davidg
 *
 */
public class ScriptUtils {
	
	private static Logger LOG = LoggerFactory.getLogger(ScriptUtils.class);

	public ScriptUtils() {
	}
	
	/**
	 * Finds a script to be tested from 
	 * @param scriptPath File path
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
