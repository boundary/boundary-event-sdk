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
import java.io.FileReader;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * @author davidg
 *
 */
public class EventMapperProcessor implements Processor  {
	
	private static Logger LOG = LoggerFactory.getLogger(EventMapperProcessor.class);
	
    ScriptEngineManager manager;
    ScriptEngine engine;
    String scriptEngineName = "JavaScript";
    
    String scriptDirectory;
    String scriptName;
    
	/**
	 * Default constructor
	 */
	public EventMapperProcessor() {
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName(scriptEngineName);
        scriptDirectory = System.getProperty("user.home") + "/scripts";
        LOG.debug(scriptDirectory);
        LOG.debug(scriptName);
	}


	/**
	 * @param args Command line arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
//	public void eval(String script) {
//		//engin.evel
//	}

	/**
	 * 
	 * @param exchange {@link Exchange} from the camel route
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		String scriptPath = scriptDirectory + "/" + scriptName;
		//scriptPath = "/Users/davidg/scripts/myscript.js";
		LOG.debug("SCRIPT: " + scriptPath);

//      engine.put("event", event);
		FileReader reader = new FileReader(new File(scriptPath));
		
		engine.eval(reader);
		
	}


	/**
	 * Sets the script to be used for the transformation of the event
	 * @param scriptName Name of the script to run
	 */
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * Returns the script associated with this {@link EventMapperProcessor}
	 * @return {@link String} with the script name
	 */
	public String getScriptName() {

		return this.scriptName;
	}

	/**
	 * Returns the directory of where the scripts are locatede for this {@link EventMapperProcessor}
	 * @return {@link String}
	 */
	public String getScriptDirectory() {
		return this.scriptDirectory;
	}

	/**
	 * Set the location to look for scripts to associate with the {@link EventMapperProcessor}
	 * @param scriptDirectory Path to the directory containing scripts
	 */
	public void setScriptDirectory(String scriptDirectory) {
		this.scriptDirectory = scriptDirectory;
	}
}
