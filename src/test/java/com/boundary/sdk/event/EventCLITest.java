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
	
	private void process() {
		try {
			cli.handleCommandlandArguments(toArgs());
			cli.configureEvent();
			event = cli.getEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		fields.add("@message");
		fields.add("foo");
		fields.add("bar");
		args.add("-f");
		args.add("@message:foo:bar");

		process();
		
		ArrayList<String> extractedFields = event.getFingerprintFields();		
		assertEquals("check fingerprint fields",fields,extractedFields);
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
	public void testSource() throws ParseException {
		String expectedRef = "red";
		String expectedType = "green";
		String expectedName = "blue";
		Source expectedSource = new Source();
		expectedSource.setRef(expectedRef);
		expectedSource.setType(expectedType);
		expectedSource.setName(expectedName);
		args.add("-u");
		args.add(expectedRef + ":" + expectedType + ":" + expectedName);
		
		process();
		
		
		assertEquals("check source ref",expectedSource.getRef(),event.getSource().getRef());
		assertEquals("check source type",expectedSource.getType(),event.getSource().getType());
		assertEquals("check source name",expectedSource.getName(),event.getSource().getName());
	}
	
	@Test
	public void testTitle() throws ParseException {
		String expectedTitle = "Limelight";
		args.add("-n");
		args.add(expectedTitle);
		args.add("-f");
		args.add("@title");
		
		process();
		
		assertEquals("check title",expectedTitle,event.getTitle());
	}
	
	@Test
	public void testSendAnEvent() {
		
	}
}
