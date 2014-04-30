package com.boundary.sdk.event;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.Status;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestRawEvent {
	
	private RawEvent event;

	
	@Before
	public void setUp() {
		event = new RawEvent();
	}
	
	@After
	public void destroy() {
		event = null;
	}

	/**
	 * Test default constructor
	 */
	@Test
	public void testNullEvent() {
		assertNotNull(event);
	}

	/**
	 * Test default values of Events
	 */
	@Test
	public void testDefaultConstructor() {
	
		assertNull("Check createdAt",event.getCreatedAt());
		assertNotNull("check for null fingerprint fields",event.getFingerprintFields());
		assertNull("Check message",event.getMessage());
		assertNull("Check organization id",event.getOrganizationId());
		assertNotNull("check fingerprint fields",event.getProperties());
		assertNull("Check receivedAt",event.getReceivedAt());
		assertNotNull("Check sender",event.getSender());
		assertNotNull("Check severity",event.getSeverity());
		assertNotNull("Check source",event.getSource());
		assertNotNull("Check for null source",event.getSource());
		assertNotNull("Check status", event.getStatus());
		assertNotNull("Check tags",event.getTags());
		assertNull("check for null title",event.getTitle());
	}
	
	@Test
	public void testAllArgConstructor() {
		
	}

	@Test
	public void testCreatedAt() {
		Calendar expectedDate = Calendar.getInstance();
		event.setCreatedAt(expectedDate.getTime());
		
		assertEquals("Check createdAt",event.getCreatedAt(),expectedDate.getTime());
	}
	
	@Test
	public void testFingerprintFields() {
		ArrayList<String> expectedFingerprintFields = new ArrayList<String>();
		event.setFingerprintFields(expectedFingerprintFields);
		assertTrue("Check fingerprintFields", event.getFingerprintFields().containsAll(expectedFingerprintFields));
	}
	
	@Test
	public void testAddFingerprintFields() {
		String expectedField = "DEADBEEFDEADBEEFDEADBEEF";
		ArrayList<String> expectedFingerprintFields = new ArrayList<String>();
		expectedFingerprintFields.add(expectedField);
		event.addFingerprintField(expectedField);
		assertTrue("Check add fingerprintFields", event.getFingerprintFields().containsAll(expectedFingerprintFields));

	}
	
	@Test
	public void testMessage() {
		String expectedMessage ="Good Morning Vietnam!";
		event.setMessage(expectedMessage);
		assertEquals("Check message field",expectedMessage,event.getMessage());
	}
	
	@Test
	public void testOrganizationId() {
		String expectedOrganizationId = "DEADBEEFDEADBEEFDEADBEEF";
		event.setOrganizationId(expectedOrganizationId);
		assertEquals("Check organization field",expectedOrganizationId,event.getOrganizationId());
	}
	
	@Test
	public void testProperties() {
		LinkedHashMap<String,Object> expectedProperties = new LinkedHashMap<String,Object>();
		
		expectedProperties.put("host","some.really.long.host.name");
		event.setProperties(expectedProperties);
		
		assertEquals("Check properities field",expectedProperties,event.getProperties());
		
	}
	
	@Test
	public void testReceivedAt() {
		Date expectedReceivedAt = new Date();
		event.setReceivedAt(expectedReceivedAt);
		assertEquals("Check received at", expectedReceivedAt,event.getReceivedAt());
	}
	
	@Test
	public void testSender() {
		Source expectedSender = new Source();
		event.setSender(expectedSender);
		assertEquals("Check sender",expectedSender,event.getSender());
	}
	
	@Test
	public void testSeverity() {
		Severity expectedSeverity = Severity.CRITICAL;
		RawEvent event = new RawEvent();
		event.setSeverity(expectedSeverity);
	
		assertEquals("Check Severity", event.getSeverity(),expectedSeverity);
	}
	
	@Test
	public void testSource() {
		Source source = new Source();
		String aRef = "stimpy.nick.com";
		String aType = "host";
		String aName = "ren";
		LinkedHashMap<String,Object> aProp = new LinkedHashMap<String,Object>();
		String aValue = "Hello World!";
		aProp.put("hello",aValue);
		source.setRef(aRef);
		source.setType(aType);
		source.setName(aName);
		
		source.setProperties(aProp);
		
		assertEquals("Check Source ref", aRef,source.getRef());
		assertEquals("Check Source type",aType,source.getType());
		assertEquals("Check Source name",aName,source.getName());
		assertEquals("Check Source properties",aProp,source.getProperties());
	}
	
	@Test
	public void testStatus() {
		Status expectedStatus = Status.CLOSED;
		event.setStatus(expectedStatus);
		assertEquals("Check expectedStatus",expectedStatus,event.getStatus());
	}
	
	@Test
	public void testTags() {
		ArrayList<String> tags = new ArrayList<String>();
		
		tags.add("red");
		tags.add("blue");
		tags.add("green");
		
		event.setTags(tags);
		assertEquals("Check tags",tags,event.getTags());
	}
	
	@Test
	public void testAddTag() {
		String expectedTag = "DEADBEEFDEADBEEFDEADBEEF";
		ArrayList<String> expectedTags = new ArrayList<String>();
		expectedTags.add(expectedTag);
		event.addTag(expectedTag);
		assertTrue("Check add tag", event.getTags().containsAll(expectedTags));
	}

	@Test
	public void testTitle() {
		String expectedTitle = "My Message";
		event.setTitle(expectedTitle);
		assertEquals(event.getTitle(),expectedTitle);
	}
}



