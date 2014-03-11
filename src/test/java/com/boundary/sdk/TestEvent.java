package com.boundary.sdk;

import static org.junit.Assert.*;

import org.junit.Test;
import com.boundary.sdk.Event;

public class TestEvent {
	
	private Event event;
	
	public void setup() {
		event = new Event();
	}
	
	@Test
	public void testNullEvent() {
		assertNotNull(this.event);
	}

	@Test
	public void testTitle() {
		String expectedTitle = "My Message";
		this.event.setTitle(expectedTitle);
		assertEquals(this.event.getTitle(),expectedTitle);
		
	}
	
	@Test
	public void testFingerprintFields() {
		String expectedFingerprintFields = "My Message";
		this.event.setFingerprintFields(expectedFingerprintFields);
		assertEquals(this.event.getFingerprintFields(),expectedFingerprintFields);
	}
	
	@Test
	public void testDefault() {
		String emptyString = new String();
		
		assertEquals(this.event.getTitle(), emptyString);
		assertEquals(this.event.getFingerprintFields(), emptyString);
		assertEquals(this.event.getSource(), emptyString);
	}
}
