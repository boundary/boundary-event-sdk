package com.boundary.sdk;

import static org.junit.Assert.*;

import org.junit.Test;

import com.boundary.sdk.RawEvent;
import com.boundary.sdk.RawEvent.Severity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TestRawEvent {
	
	private RawEvent event;
	
	public void setup() {
		
	}
	
	

	/**
	 * Test default constructor
	 */
	@Test
	public void testNullEvent() {
		RawEvent event = new RawEvent();
		assertNotNull(event);
	}

	@Test
	public void testTitle() {
		String expectedTitle = "My Message";
		event = new RawEvent();
		event.setTitle(expectedTitle);
		assertEquals(event.getTitle(),expectedTitle);
		
	}
	
	@Test
	public void testSource() {
		Source source = new Source();
		String aRef = "stimpy.nick.com";
		String aType = "host";
		String aName = "ren";
		source.setRef(aRef);
		source.setType(aType);
		source.setName(aName);
		
		assertEquals(aRef,source.getRef());
	}
	
	/**
	 * Test default values of Events
	 */
	@Test
	public void testDefaultConstructor() {
		RawEvent event = new RawEvent();
		
		assertNull(event.getTitle());
		assertNull(event.getFingerprintFields());
		assertNull(event.getSource());
	}
	
	@Test
	public void testNoArgConstructor() {
		RawEvent event = new RawEvent();
		assertNull(event.getTitle());
	}
	
	@Test
	public void testAllArgConstructor() {
		
	}
//	CREATED_AT,
//	EVENT_ID,
//	FINGERPRINT_FIELDS,
//	ID,
//	MESSAGE,
//	ORGANIZATION_ID,
//	PROPERTIES,
//	RECEIVED_AT,
//	SENDER,
//	SEVERITY,
//	SOURCE,
//	STATUS,
//	TAGS,
//	TITLE
	@Test
	public void testCreatedAt() {
		Calendar expectedDate = Calendar.getInstance();
		RawEvent event = new RawEvent();
		event.setCreatedAt(expectedDate.getTime());
		
		assertEquals("Check createdAt",event.getCreatedAt(),expectedDate.getTime());
	}
	
	@Test
	public void testFingerprintFields() {
		ArrayList<String> expectedFingerprintFields = new ArrayList<String>();
		event = new RawEvent();
		event.setFingerprintFields(expectedFingerprintFields);
		assertTrue("Check fingerprintFields", event.getFingerprintFields().containsAll(expectedFingerprintFields));
	}
	
	@Test
	public void testMessage() {
		String expectedMessage ="Good Morning Vietname!";
		event = new RawEvent();
		event.setMessage(expectedMessage);
		assertEquals("Check message field",expectedMessage,event.getMessage());
	}

	
	@Test
	public void testSeverity() {
		Severity expectedSeverity = Severity.CRITICAL;
		RawEvent event = new RawEvent();
		event.setSeverity(expectedSeverity);
		assertEquals("Check Severity", event.getSeverity(),expectedSeverity);
	}
}



