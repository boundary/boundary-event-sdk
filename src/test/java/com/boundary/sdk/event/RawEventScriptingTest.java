package com.boundary.sdk.event;
import com.boundary.sdk.RawEvent;
import com.boundary.sdk.SyslogToEventProcessor;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawEventScriptingTest {
	
	private static Logger LOG = LoggerFactory.getLogger(RawEventScriptingTest.class);
	
    ScriptEngineManager manager;
    ScriptEngine engine;
    RawEvent event;
    String scriptDirectory="js/rawevent";
    String scriptEngineName = "JavaScript";
    

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
        manager = new ScriptEngineManager();
        engine = manager.getEngineByName(scriptEngineName);
        event = new RawEvent();
        engine.put("event", event);
	}

	@After
	public void tearDown() throws Exception {
        manager = null;
        engine = null;
        event = null;
        
	}
	
	/**
	 * Finds a script to be tested from 
	 * @param scriptName
	 * @return {@link File}
	 */
	private FileReader getScript(String scriptName) {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();

		URL url = loader.getResource(scriptDirectory + "/" + scriptName);
		assertNotNull(url);
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
	
	private Object runScript(String scriptName) throws ScriptException {
		Object o = null;
		
        // evaluate a script string. The script accesses "file"
        // variable and calls method on it
        try {
            o = engine.eval(getScript(scriptName));
		} catch (ScriptException e) {
			e.printStackTrace();
			LOG.error("file: " + e.getFileName());
			LOG.error("line: " + e.getLineNumber());
			LOG.error("column: " + e.getColumnNumber());
			LOG.error("message" + e.getMessage());
			throw e;
		}
        return o;
	}

	@Test
	public void testScriptEngine() throws ScriptException{
		event.setTitle("foobar");
		runScript("smoke.js");
	}
	
	@Test(expected=ScriptException.class)
	public void testException() throws ScriptException {
		runScript("exception.js");
	}
	
	@Test
	public void testSetTitle() throws ScriptException {
		runScript("set_title.js");
		assertEquals("Test setTitle()", event.getTitle(),"foobar");
	}
	
	@Test
	public void testChangeTitle() throws ScriptException {
		String title = "2112";
		event.setTitle(title);
		runScript("set_title.js");
		assertFalse(title == event.getTitle());
	}
	
	@Test
	public void testReturnsTrue() throws ScriptException {
		Boolean o = (Boolean)runScript("return_true.js");

		assertTrue(o.booleanValue());
	}
	
	@Test
	public void testSetSourceRef() throws ScriptException {
		runScript("set_source.js");
		
		assertEquals("Check setRef()","Geddy Lee",event.getSource().getRef());
		assertEquals("Check setType()","Musician",event.getSource().getType());
	}
}
