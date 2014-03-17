package com.boundary.sdk;

import static org.junit.Assert.*;

import org.junit.Test;

import com.boundary.sdk.Event;

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
		String expectedFingerprintFields = "My Message";
		event = new Event();
		event.setFingerprintFields(expectedFingerprintFields);
		assertEquals(event.getFingerprintFields(),expectedFingerprintFields);
	}
	
	@Test
	public void testDefault() {
		String emptyString = new String();
		Event event = new Event();
		
		assertEquals(event.getTitle(), emptyString);
		assertEquals(event.getFingerprintFields(), emptyString);
		assertEquals(event.getSource(), emptyString);
	}
	
	@Test
	public void testCreatedAt() {
		Calendar expectedDate = Calendar.getInstance();
		Event event = new Event();
		event.setCreatedAt(expectedDate.getTime());
		
		assertEquals(event.getCreatedAt(),expectedDate.getTime());
	}
}



