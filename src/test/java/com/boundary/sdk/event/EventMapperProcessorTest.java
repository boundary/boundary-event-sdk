/**
 * 
 */
package com.boundary.sdk.event;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author davidg
 *
 */
public class EventMapperProcessorTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private EventMapperProcessor eventMapper;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		eventMapper = new EventMapperProcessor();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testScriptName() {
		String expectedScriptName = "myscript.js";
		eventMapper.setScriptName(expectedScriptName);
		assertEquals("Check setScript()",expectedScriptName,eventMapper.getScriptName());
	}
	
	@Test
	public void testDirectoryName() {
		String directoryName = "myscript.js";
		eventMapper.setScriptDirectory(directoryName);
		assertEquals("Check setScript()",directoryName,eventMapper.getScriptDirectory());
	}
}
