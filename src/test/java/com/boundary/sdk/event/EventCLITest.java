/**
 * 
 */
package com.boundary.sdk.event;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.commons.cli.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author davidg
 *
 */
public class EventCLITest {
	
	private ArrayList<String> args;
	private EventCLI cli;
	private RawEvent event;

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

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		args = new ArrayList<String>();
		cli = new EventCLI();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	private void process() throws ParseException {
		cli.handleCommandlandArguments(toArgs());
		cli.populateEvent();
		event = cli.getEvent();
	}
	
	private String [] toArgs() {
		String [] lArgs = new String[args.size()];
		int i = 0;
		
		for (String s : args) {
			lArgs[i++] = s;
		}
		return lArgs;
	}
	
	@Test
	public void testArgs() {
		args.add("foo");
		args.add("bar");
		
		String [] lArgs = toArgs();
	}
	
	@Test
	public void testFingerprintFields() throws ParseException {
		ArrayList<String> fields = new ArrayList<String>();
		args.add("@message");
		args.add("foo");

		args.add("@message");
		args.add("foo");
		
		process();
		
		//assertEquals()
	}

	@Test
	public void testHelp() throws ParseException {
		args.add("-h");

		process();
		
	}
	
	@Test
	public void testOrganizationId() throws ParseException {
		String expectedOrgId = "9999";
		args.add("-o");
		args.add(expectedOrgId);
		
		process();
		
		assertEquals("check title",expectedOrgId,event.getOrganizationId());
	}
	
	@Test
	public void testTitle() throws ParseException {
		String expectedTitle = "Limelight";
		args.add("-n");
		args.add(expectedTitle);
		
		process();
		
		assertEquals("check title",expectedTitle,event.getTitle());
	}

}
