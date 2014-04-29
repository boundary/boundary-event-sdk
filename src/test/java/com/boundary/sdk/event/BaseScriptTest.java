package com.boundary.sdk.event;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URL;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseScriptTest extends CamelTestSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(BaseScriptTest.class);
	
    protected ScriptEngineManager manager;
    protected ScriptEngine engine;
    protected String scriptEngineName = "JavaScript";

	public BaseScriptTest() {
	}
	
	/**
	 * Sets up the {@ScriptEngineManager} and {@link ScriptEngine}
	 */
	public void setUp() throws Exception {
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName(scriptEngineName);
	}
}
