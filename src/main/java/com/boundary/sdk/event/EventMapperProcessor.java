/**
 * @author davidg
 */
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
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
//	public void eval(String script) {
//		//engin.evel
//	}

	/**
	 * 
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
	 * @param scriptName
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
	 * @param scriptDirectory
	 */
	public void setScriptDirectory(String scriptDirectory) {
		this.scriptDirectory = scriptDirectory;
	}
}
