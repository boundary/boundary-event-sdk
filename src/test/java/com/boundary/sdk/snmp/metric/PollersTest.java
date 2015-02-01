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
package com.boundary.sdk.snmp.metric;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PollersTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testLoad() throws URISyntaxException {
		Pollers pollers = Pollers.load("META-INF/json/test-snmp-pollers.json");
		assertNotNull("check for not null",pollers);
	}
	
	@Test
	public void testPollerEntry1() throws URISyntaxException {
		Pollers pollers = Pollers.load("META-INF/json/test-snmp-pollers.json");
		PollerEntry entry = pollers.getPollers().get(0);
		assertNotNull("check poller entry for null",entry);
		
		assertEquals("Check poller id",1L,entry.getId());
		assertEquals("check poller name","Web Server Poller",entry.getName());
		assertEquals("check poller description",
				"Polls metrics from SNMP agents from infrastructure web servers",
				entry.getDescription());
		
		List<HostListRef> hostList = entry.getHostLists();
		assertEquals("check host list count",1,hostList.size());
		HostListRef hostListRef = hostList.get(0);
		assertNotNull("check host list ref for null",hostListRef);
		assertEquals("check host list ref id",1,hostListRef.getId());
		assertEquals("check host list ref enabled",true,hostListRef.isEnabled());
	}
	
	@Test
	public void testPollerEntry2() throws URISyntaxException {
		Pollers pollers = Pollers.load("META-INF/json/test-snmp-pollers.json");
		PollerEntry entry = pollers.getPollers().get(1);
		assertNotNull("check poller entry for null",entry);
		
		assertEquals("Check poller id",2L,entry.getId());
		assertEquals("check poller name","Database poller",entry.getName());
		assertEquals("check poller description",
				"Polls metrics from SNMP agents from infrastructure databases",
				entry.getDescription());
		
		List<HostListRef> hostList = entry.getHostLists();
		assertEquals("check host list count",2,hostList.size());
		HostListRef hostListRef1 = hostList.get(0);
		assertNotNull("check host list ref for null",hostListRef1);
		assertEquals("check host list ref id",1,hostListRef1.getId());
		assertEquals("check host list ref enabled",true,hostListRef1.isEnabled());

		HostListRef hostListRef2 = hostList.get(1);
		assertNotNull("check host list ref for null",hostListRef2);
		assertEquals("check host list ref id",2,hostListRef2.getId());
		assertEquals("check host list ref enabled",false,hostListRef2.isEnabled());

	}
	
	@Test
	public void testPollerEntry3() throws URISyntaxException {
		Pollers pollers = Pollers.load("META-INF/json/test-snmp-pollers.json");
		PollerEntry entry = pollers.getPollers().get(2);
		assertNotNull("check poller entry for null",entry);
		
		assertEquals("Check poller id",3L,entry.getId());
		assertEquals("check poller name","DNS Poller",entry.getName());
		assertEquals("check poller description",
				"Polls metrics from SNMP agents from infrastructure DNS services",
				entry.getDescription());
		
		List<HostListRef> hostList = entry.getHostLists();
		assertEquals("check host list count",1,hostList.size());
		HostListRef hostListRef1 = hostList.get(0);
		assertNotNull("check host list ref for null",hostListRef1);
		assertEquals("check host list ref id",3,hostListRef1.getId());
		assertEquals("check host list ref enabled",true,hostListRef1.isEnabled());
	}

}
