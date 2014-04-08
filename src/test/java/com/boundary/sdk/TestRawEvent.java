package com.boundary.sdk;

import static org.junit.Assert.*;

import org.junit.Test;

import com.boundary.sdk.Event;
import com.boundary.sdk.Event.Severity;

import java.util.Calendar;
import java.util.Date;

public class TestEvent {
	
	private Event event;
	
	public void setup() {
	}
	
	@Test
	public void testNullEvent() {
		Event event = new Event();
		assertNotNull(event);
	}

	@Test
	public void testTitle() {
		String expectedTitle = "My Message";
		event = new Event();
		event.setTitle(expectedTitle);
		assertEquals(event.getTitle(),expectedTitle);
		
	}
	
	@Test
	public void testFingerprintFields() {
		String[] expectedFingerprintFields = new String[1];
		event = new Event();
		event.setFingerprintFields(expectedFingerprintFields);
		assertArrayEquals("Check fingerprintFields", expectedFingerprintFields,event.getFingerprintFields());
	}
	
	/**
	 * Test default values of Events
	 */
	@Test
	public void testDefaultConstructor() {
		Event event = new Event();
		
		assertNull(event.getTitle());
		assertNull(event.getFingerprintFields());
		assertNull(event.getSource());
	}
	
	@Test
	public void testConstructor() {
		String [] fields = new String[1];
		fields[0]  = "@message";
		Source source = new Source();
		
		Event event = new Event("foo",fields);
	}
	
	@Test
	public void testCreatedAt() {
		Calendar expectedDate = Calendar.getInstance();
		Event event = new Event();
		event.setCreatedAt(expectedDate.getTime());
		
		assertEquals("Check creatdAt",event.getCreatedAt(),expectedDate.getTime());
	}
	
	@Test
	public void testSeverity() {
		Severity expectedSeverity = Severity.CRITICAL;
		Event event = new Event();
		event.setSeverity(expectedSeverity);
		assertEquals("Check Severity", event.getSeverity(),expectedSeverity);
	}
}



