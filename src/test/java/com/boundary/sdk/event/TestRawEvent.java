// Copyright 2014-2015 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.boundary.sdk.event;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestRawEvent {
	
	private RawEvent event;
	private String longStr;
	private String maxStr;
	
	private final int MAX_API_STRING_LENGTH = 255;
	private final int ONE_OVER_MAX_API_STRING_LENGTH = MAX_API_STRING_LENGTH + 1;

	
	@Before
	public void setUp() {
		event = new RawEvent();
		longStr = createString(ONE_OVER_MAX_API_STRING_LENGTH,'T');
		maxStr = createString(MAX_API_STRING_LENGTH,'T');

	}
	
	@After
	public void destroy() {
		event = null;
	}
	
	/**
	 * Helper method to create strings of repeating characters
	 * 
	 * @param length Length of the string to create
	 * @param ch Character to repeat
	 * @return String
	 */
	public String createString(int length, char ch) {
	    char[] chars = new char[length];
	    Arrays.fill(chars, ch);
	    return new String(chars);
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
	public void testCreatedAt() {
		Calendar expectedDate = Calendar.getInstance();
		event.setCreatedAt(expectedDate.getTime());
		
		assertEquals("Check createdAt",event.getCreatedAt(),expectedDate.getTime());
	}
	
	@Test
	public void testFingerprintFields() {
		ArrayList<String> expectedFingerprintFields = new ArrayList<String>();
		expectedFingerprintFields.add("foo");
		event.setFingerprintFields(expectedFingerprintFields);
		assertTrue("Check fingerprintFields", event.getFingerprintFields().containsAll(expectedFingerprintFields));
	}
	
	@Test
	public void testTruncatedFingerprintFields() {
		ArrayList<String> fingerprintFields = new ArrayList<String>();
		ArrayList<String> maxFingerprintFields = new ArrayList<String>();
		maxFingerprintFields.add(maxStr);
		fingerprintFields.add(longStr);
		event.setFingerprintFields(fingerprintFields);
		assertTrue("Check fingerprintFields", event.getFingerprintFields().containsAll(maxFingerprintFields));
	}
	
	@Test
	public void testMaxFingerprintFields() {
		ArrayList<String> fingerprintFields = new ArrayList<String>();
		ArrayList<String> maxFingerprintFields = new ArrayList<String>();
		maxFingerprintFields.add(maxStr);
		fingerprintFields.add(maxStr);
		event.setFingerprintFields(fingerprintFields);
		assertTrue("Check fingerprintFields", event.getFingerprintFields().containsAll(maxFingerprintFields));
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
	public void testAddTruncatedFingerprintFields() {
		ArrayList<String> expectedFingerprintFields = new ArrayList<String>();
		expectedFingerprintFields.add(maxStr);
		event.addFingerprintField(longStr);
		assertTrue("Check add truncated fingerprintFields", event.getFingerprintFields().containsAll(expectedFingerprintFields));
	}
	
	@Test
	public void testAddMaximumFingerprintFields() {
		ArrayList<String> expectedFingerprintFields = new ArrayList<String>();
		expectedFingerprintFields.add(maxStr);
		event.addFingerprintField(maxStr);
		assertTrue("Check add maximum fingerprintFields", event.getFingerprintFields().containsAll(expectedFingerprintFields));
	}
	
	@Test
	public void testMessage() {
		String expectedMessage ="Good Morning Vietnam!";
		event.setMessage(expectedMessage);
		assertEquals("Check message field",expectedMessage,event.getMessage());
	}
	
	@Test
	public void testTruncatedMessage() {
		event.setMessage(longStr);
		assertEquals("Check truncated message filed",maxStr,event.getMessage());
	}
	
	@Test
	public void testMaxMessage() {
		event.setMessage(maxStr);
		assertEquals("Check truncated message filed",maxStr,event.getMessage());
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
	public void testTruncatedProperties() {
		LinkedHashMap<String,Object> longProps = new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> maxProps = new LinkedHashMap<String,Object>();
		
		longProps.put("closer to the heart", longStr);
		maxProps.put("closer to the heart", maxStr);
		event.setProperties(longProps);
		
		assertEquals("Check properities field",maxProps,event.getProperties());
	}
	
	@Test
	public void testMaxProperties() {
		LinkedHashMap<String,Object> props = new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> maxProps = new LinkedHashMap<String,Object>();
		
		props.put("closer to the heart", maxStr);		
		maxProps.put("closer to the heart", maxStr);
		event.setProperties(props);
		
		assertEquals("Check properities field",maxProps,event.getProperties());
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
		expectedSender.setRef("A");
		expectedSender.setType("B");
		expectedSender.setName("C");
		LinkedHashMap<String,Object> aProp = new LinkedHashMap<String,Object>();
		aProp.put("D","E");
		
		event.setSender(expectedSender);
		assertEquals("Check Sender ref", expectedSender.getRef(),event.getSender().getRef());
		assertEquals("Check Sender type",expectedSender.getType(),event.getSender().getType());
		assertEquals("Check Sender name",expectedSender.getName(),event.getSender().getName());
		assertEquals("Check Sender properties",expectedSender.getProperties(),event.getSender().getProperties());

	}
	
	@Test
	public void setAndGetSender() {
		String aRef = "ren.nick.com";
		String aType = "host";
		String aName = "stimpy";
		
		Source aSender = new Source();
		Source bSender = new Source();
		LinkedHashMap<String,Object> aProp = new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> bProp = new LinkedHashMap<String,Object>();

		
		aSender.setRef(aRef);
		aSender.setType(aType);
		aSender.setName(aName);
		aProp.put("hello", "goodbye");
		aSender.setProperties(aProp);
		
		bSender.setRef(aRef);
		bSender.setType(aType);
		bSender.setName(aName);
		bProp.put("hello", "goodbye");
		bSender.setProperties(bProp);

		event.setSender(aSender);
		
		Source cSender = event.getSender();
		
		assertEquals("Check Get Sender ref", bSender.getRef(),cSender.getRef());
		assertEquals("Check Get Sender type",bSender.getType(),cSender.getType());
		assertEquals("Check Get Sender name",bSender.getName(),cSender.getName());
		assertEquals("Check Get Sender properties",bSender.getProperties(),cSender.getProperties());

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
	public void setAndGetSource() {
		String aRef = "ren.nick.com";
		String aType = "host";
		String aName = "stimpy";
		
		Source aSource = new Source();
		Source bSource = new Source();
		LinkedHashMap<String,Object> aProp = new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> bProp = new LinkedHashMap<String,Object>();

		
		aSource.setRef(aRef);
		aSource.setType(aType);
		aSource.setName(aName);
		aProp.put("hello", "goodbye");
		aSource.setProperties(aProp);
		
		bSource.setRef(aRef);
		bSource.setType(aType);
		bSource.setName(aName);
		bProp.put("hello", "goodbye");
		bSource.setProperties(bProp);

		event.setSource(aSource);
		
		Source cSource = event.getSource();
		
		assertEquals("Check Get Source ref", bSource.getRef(),cSource.getRef());
		assertEquals("Check Get Source type",bSource.getType(),cSource.getType());
		assertEquals("Check Get Source name",bSource.getName(),cSource.getName());
		assertEquals("Check Get Source properties",bSource.getProperties(),cSource.getProperties());

	}
	
	@Test
	public void testTruncatedSource() {
		Source source = new Source();
		String longRef = createString(ONE_OVER_MAX_API_STRING_LENGTH,'X');
		String longType = createString(ONE_OVER_MAX_API_STRING_LENGTH,'Y');
		String longName = createString(ONE_OVER_MAX_API_STRING_LENGTH,'Z');
		
		String maxRef = createString(MAX_API_STRING_LENGTH,'X');
		String maxType = createString(MAX_API_STRING_LENGTH,'Y');
		String maxName = createString(MAX_API_STRING_LENGTH,'Z');

		source.setRef(longRef);
		source.setType(longType);
		source.setName(longName);
		
		LinkedHashMap<String,Object> longProp = new LinkedHashMap<String,Object>();
		LinkedHashMap<String,Object> maxProp = new LinkedHashMap<String,Object>();
		
		longProp.put("hello",longStr);
		maxProp.put("hello", maxStr);

		source.setProperties(longProp);
		
		assertEquals("Check Source ref", maxRef,source.getRef());
		assertEquals("Check Source type",maxType,source.getType());
		assertEquals("Check Source name",maxName,source.getName());
		assertEquals("Check Source properties",maxProp,source.getProperties());
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
	public void testTruncateTags() {
		String a = createString(ONE_OVER_MAX_API_STRING_LENGTH,'A');
		String b = createString(ONE_OVER_MAX_API_STRING_LENGTH,'B');
		String c = createString(ONE_OVER_MAX_API_STRING_LENGTH,'C');
		String A = createString(MAX_API_STRING_LENGTH,'A');
		String B = createString(MAX_API_STRING_LENGTH,'B');
		String C = createString(MAX_API_STRING_LENGTH,'C');

		ArrayList<String> tags = new ArrayList<String>();
		tags.add(a);
		tags.add(b);
		tags.add(c);
		
		ArrayList<String> truncatedTags = new ArrayList<String>();
		truncatedTags.add(A);
		truncatedTags.add(B);
		truncatedTags.add(C);
		
		event.setTags(tags);
		assertEquals("Check tags",truncatedTags,event.getTags());
	}
	
	@Test
	public void testMaximumTags() {
		String a = createString(MAX_API_STRING_LENGTH,'A');
		String b = createString(MAX_API_STRING_LENGTH,'B');
		String c = createString(MAX_API_STRING_LENGTH,'C');
		String A = createString(MAX_API_STRING_LENGTH,'A');
		String B = createString(MAX_API_STRING_LENGTH,'B');
		String C = createString(MAX_API_STRING_LENGTH,'C');

		ArrayList<String> tags = new ArrayList<String>();
		tags.add(a);
		tags.add(b);
		tags.add(c);
		
		ArrayList<String> truncatedTags = new ArrayList<String>();
		truncatedTags.add(A);
		truncatedTags.add(B);
		truncatedTags.add(C);
		
		event.setTags(tags);
		assertEquals("Check tags",truncatedTags,event.getTags());
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
	public void testAddTruncateTag() {
		ArrayList<String> truncatedTags = new ArrayList<String>();
		truncatedTags.add(maxStr);
		event.addTag(longStr);
		assertTrue("Check add truncated tag", event.getTags().containsAll(truncatedTags));
	}
	
	@Test
	public void testAddMaximumTag() {
		ArrayList<String> maxTags = new ArrayList<String>();
		maxTags.add(maxStr);
		event.addTag(maxStr);
		assertTrue("Check add truncated tag", event.getTags().containsAll(maxTags));

	}

	@Test
	public void testTitle() {
		String expectedTitle = "My Message";
		event.setTitle(expectedTitle);
		assertEquals("Check title", event.getTitle(),expectedTitle);
	}
	
	@Test
	public void testTitleTruncation() {
		event.setTitle(longStr);
		assertEquals("Check truncate title",maxStr,event.getTitle());
	}
	
	@Test
	public void testTitleMaximum() {
		event.setTitle(maxStr);
		assertEquals("Check maximum title",maxStr,event.getTitle());
	}
	
	private RawEventBuilder rawEventBuilder() {
		RawEventBuilder builder = RawEventBuilder.builder();
		Date now = new Date();
		builder.setCreatedAt(new Date());
		List<String> fingerprintFields = new ArrayList<String>();
		fingerprintFields.add("red");
		fingerprintFields.add("blue");
		fingerprintFields.add("green");
		builder.setFingerprintFields(fingerprintFields);
		builder.setMessage("HELLO PEOPLE");
		builder.setOrganizationId("foobar");
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("one","1");
		properties.put("two","2");
		properties.put("three","3");
		builder.setProperties(properties);
		builder.setReceivedAt(now);
		Source sender = new Source("alex","geddy","neal",properties);
		builder.setSender(sender);
		builder.setSeverity(Severity.INFO);
		Source source = new Source("larry","curl","moe",properties);
		builder.setSource(source);
		builder.setStatus(Status.OPEN);
		List<String> tags = new ArrayList<String>();
		tags.add("cyan");
		tags.add("magenta");
		tags.add("yellow");
		builder.setTags(tags);
		builder.setTitle("foobar");
		return builder;
	}
	
	@Test
	public void testEventEquals() {
		RawEventBuilder builder = rawEventBuilder();
		
		RawEvent event1 = builder.build();
		RawEvent event2 = builder.build();
		
		assertTrue("check equals",event1.equals(event2));
	}
	
	@Test
	public void testNotEqual() {
		RawEventBuilder builder = RawEventBuilder.builder();
		
		builder.setTitle("foobar");
		RawEvent event1 = builder.build();
		builder.setTitle("barfoo");
		RawEvent event2 = builder.build();
		
		assertFalse("check title equals",event1.equals(event2));
	}
}



