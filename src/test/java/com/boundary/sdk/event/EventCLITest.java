/**
 * 
 */
package com.boundary.sdk.event;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import org.apache.commons.cli.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author davidg
 *
 */
public class EventCLITest {
	
	private ArrayList<String> args;
	private EventCLI cli;
	private RawEvent event;
	private GregorianCalendar cal;

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
		cal = new GregorianCalendar();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	
	private void process() {
		try {
			if (cli.configure(toArgs()) == false) {
				event = cli.getEvent();
			}
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
	
	private void addRequiredArgs(ArrayList<String> args) {
		args.add("-f");
		args.add("@title");
		args.add("-u");
		args.add("localhost");
		args.add("-n");
		args.add("HELLO");
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
		args.add("-u");
		args.add("localhost");
		args.add("-n");
		args.add("Hello");

		process();
		
		ArrayList<String> extractedFields = event.getFingerprintFields();		
		assertEquals("check fingerprint fields",fields,extractedFields);
	}

	@Test
	public void testHelp() throws ParseException {
		args.add("-h");

		process();
	}
	
//	public static final char OPTION_HELP = 'h';
//	public static final char OPTION_API_KEY = 'a';
//	public static final char OPTION_API_HOST = 'b';
//	public static final char OPTION_ORG_ID = 'o';
//	public static final char OPTION_CREATED_AT = 'z';
//	public static final char OPTION_FINGERPRINT_FIELDS = 'f';
//	public static final char OPTION_MESSAGE = 'm';
//	public static final char OPTION_PROPERTIES = 'p';
//	public static final char OPTION_RECEIVED_AT = 'r';
//	public static final char OPTION_SENDER = 'x';
//	public static final char OPTION_SEVERITY = 'y';
//	public static final char OPTION_SOURCE = 'u';
//	public static final char OPTION_STATUS = 'w';
//	public static final char OPTION_TAGS = 't';
//	public static final char OPTION_TITLE = 'n';

	@Test
	public void testApiKey() {
		String expectedApiKey = "DEADBEEF";
		addRequiredArgs(args);
		args.add("-a");
		args.add(expectedApiKey);
		
		process();
		
		//assertEquals("check api key",expectedApiKey);
	}
	@Test
	public void testOrganizationId() throws ParseException {
		String expectedOrgId = "9999";
		addRequiredArgs(args);
		args.add("-o");
		args.add(expectedOrgId);
		
		process();
		
		assertEquals("check organization",expectedOrgId,event.getOrganizationId());
	}
	
	@Test
	public void testParseDateTime_2() {
		Calendar c = javax.xml.bind.DatatypeConverter.parseDateTime("2010-01-01T12:00:00Z");
//will give you a Calendar object and you can simply use getTime() on it, if you need a Date object.
		Date dt = c.getTime();
		System.out.println(dt);
	}
	
	@Ignore
	@Test
	public void testParseDateTime_1() throws java.text.ParseException {
		String s = "2006-04-06 14:22:22";
		String iso = "2010-01-05T14:22:22Z";
		Date parsedDate = cli.parseDateTime(iso);

		cal.set(2010,04,05,14,22,22);
		cal.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = cal.getTime();
		
		assertEquals("check parse Date Time",date,parsedDate);
	}
	
	@Ignore
	@Test
	public void testCreatedAt_1() throws java.text.ParseException {
		
		cal.set(2014, 6, 1, 9, 30, 45);
		Date dt = cal.getTime();

		addRequiredArgs(args);
		args.add("-o");
		args.add("12-Apr-06 2:22:22PM");
		
		process();
		
		assertEquals("check created At",dt,event.getCreatedAt());
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
	
		args.add("-f");
		args.add("@title");
		args.add("-n");
		args.add("hello");
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
