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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RawEventBuilderTest {
	
	private RawEventBuilder builder;

	@Before
	public void setUp() throws Exception {
		builder = new RawEventBuilder();
	}

	@After
	public void tearDown() throws Exception {
		builder = null;
	}

	@Test
	public void testRawEventBuilder() {
		RawEvent event1 = new RawEvent();
		RawEvent event2 = builder.build();
		assertEquals("testRawEventBuilder",event1,event2);
	}

	@Test
	public void testSetCreatedAt() {
		Date now = new Date();
		RawEvent event1 = new RawEvent();
		event1.setCreatedAt(now);
		builder.setCreatedAt(now);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetCreatedAt",event1,event2);
	}

	@Test
	public void testSetFingerprintFields() {
		List<String> fingerprintFields = new ArrayList<String>();
		RawEvent event1 = new RawEvent();
		event1.setFingerprintFields(fingerprintFields);
		builder.setFingerprintFields(fingerprintFields);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetFingerprintFields",event1,event2);
	}

	@Test
	public void testSetMessage() {
		String message = "Hello World!";
		RawEvent event1 = new RawEvent();
		event1.setMessage(message);
		builder.setMessage(message);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetMessage",event1,event2);
	}

	@Test
	public void testSetOrganizationId() {
		String organizationID = "DEADBEEF";
		RawEvent event1 = new RawEvent();
		event1.setOrganizationId(organizationID);
		builder.setOrganizationId(organizationID);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetOrganizationId",event1,event2);
	}

	@Test
	public void testSetProperties() {
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("Begin","the");
		properties.put("day","with");
		properties.put("a","friendly");
		RawEvent event1 = new RawEvent();
		event1.setProperties(properties);
		builder.setProperties(properties);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetProperties",event1,event2);
	}

	@Test
	public void testSetReceivedAt() {
		Date receivedAt = new Date();
		RawEvent event1 = new RawEvent();
		event1.setReceivedAt(receivedAt);
		builder.setReceivedAt(receivedAt);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetReceivedAt",event1,event2);
	}

	@Test
	public void testSetSender() {
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("I","will");
		properties.put("choose","a");
		properties.put("path","thats");
		Source sender = new Source("a","b","c",properties);
		RawEvent event1 = new RawEvent();
		event1.setSender(sender);
		builder.setSender(sender);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetSender",event1,event2);
	}

	@Test
	public void testSetSeverity() {
		Severity severity = Severity.ERROR;
		RawEvent event1 = new RawEvent();
		event1.setSeverity(severity);
		builder.setSeverity(severity);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetSeverity",event1,event2);
	}

	@Test
	public void testSetSource() {
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("Todays","Tom");
		properties.put("Sawyer","gets");
		properties.put("high","on");
		Source sender = new Source("x","y","z",properties);
		RawEvent event1 = new RawEvent();
		event1.setSender(sender);
		builder.setSender(sender);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetSource",event1,event2);
	}

	@Test
	public void testSetStatus() {
		Status status = Status.CLOSED;
		RawEvent event1 = new RawEvent();
		event1.setStatus(status);
		builder.setStatus(status);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetStatus",event1,event2);
	}

	@Test
	public void testSetTags() {
		List<String> tags = new ArrayList<String>();
		tags.add("uno");
		tags.add("dos");
		tags.add("tres");
		RawEvent event1 = new RawEvent();
		event1.setTags(tags);
		builder.setTags(tags);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetStatus",event1,event2);
	}

	@Test
	public void testSetTitle() {
		String title = "John Barley Corn Must Die";
		RawEvent event1 = new RawEvent();
		event1.setTitle(title);
		builder.setTitle(title);
		RawEvent event2 = builder.build();
		
		assertEquals("testSetTitle",event1,event2);
	}

}
