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

import java.net.URISyntaxException;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HostListTest {
	
	private static String HOST_LIST_FILE="META-INF/json/test-host-lists.json";

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
	public void testHostListLoad() throws URISyntaxException {
		HostLists hostLists = HostLists.load(HOST_LIST_FILE);
		assertNotNull("check for null host lists",hostLists);
	}
	
	@Test
	public void testHostListCount() throws URISyntaxException {
		HostLists hostLists = HostLists.load(HOST_LIST_FILE);
		assertEquals("check host lists count",3,hostLists.getHostLists().size());
	}
	
	@Test
	public void testHostListOne() throws URISyntaxException {
		HostLists hostLists = HostLists.load(HOST_LIST_FILE);
		
		HostListEntry hostListEntry = hostLists.getHostLists().get(0);
		assertNotNull("check for host list entry null",hostListEntry);
		assertEquals("check for host list size",5,hostListEntry.getHosts().size());
		
		assertEquals("check host list entry id 1",1,hostListEntry.getId());
		assertEquals("check host list entry name 1","Web Servers",hostListEntry.getName());
		assertEquals("check host list entry name 1","Web Servers to monitor via SNMP",hostListEntry.getDescription());
		assertEquals("check host list entry name 1","public",hostListEntry.getCommunityRead());
		assertEquals("check host list entry name 1",161,hostListEntry.getPort());
		
		List<Host> hosts = hostListEntry.getHosts();
		assertNotNull("check host list entry hosts not null 1",hosts);
		Host host1 = hosts.get(0);
		assertEquals("check host 1","web-001",host1.getHost());
		assertEquals("check community-read 1","foobar",host1.getCommunityRead());
		
		Host host2 = hosts.get(1);
		assertEquals("check host 2","web-002",host2.getHost());
		assertEquals("check community-read 2",null,host2.getCommunityRead());

		Host host3 = hosts.get(2);
		assertEquals("check host 3","web-003",host3.getHost());
		assertEquals("check community-read 3",null,host3.getCommunityRead());
	
		Host host4 = hosts.get(3);
		assertEquals("check host 4","web-004",host4.getHost());
		assertEquals("check community-read 4","public",host4.getCommunityRead());

	}
	
	@Test
	public void testHostListTwo() throws URISyntaxException {
		HostLists hostLists = HostLists.load(HOST_LIST_FILE);
		
		HostListEntry hostListEntry = hostLists.getHostLists().get(1);
		assertNotNull("check for host list entry null",hostListEntry);
		assertEquals("check for host list size",3,hostListEntry.getHosts().size());
		
		assertEquals("check host list entry id 2",2,hostListEntry.getId());
		assertEquals("check host list entry name 2","Database Servers",hostListEntry.getName());
		assertEquals("check host list entry name 2","",hostListEntry.getDescription());
		assertEquals("check host list entry name 2","YXz1297",hostListEntry.getCommunityRead());
		assertEquals("check host list entry name 2",1161,hostListEntry.getPort());
		
		List<Host> hosts = hostListEntry.getHosts();
		assertNotNull("check host list entry hosts not null 2",hosts);
		Host host1 = hosts.get(0);
		assertEquals("check host 1","database-001",host1.getHost());
		assertEquals("check community-read 1",null,host1.getCommunityRead());
		
		Host host2 = hosts.get(1);
		assertEquals("check host 2","database-002",host2.getHost());
		assertEquals("check community-read 2",null,host2.getCommunityRead());

		Host host3 = hosts.get(2);
		assertEquals("check host 3","database-003",host3.getHost());
		assertEquals("check community-read 3",null,host3.getCommunityRead());
	}

	@Test
	public void testHostListThree() throws URISyntaxException {
		HostLists hostLists = HostLists.load(HOST_LIST_FILE);
		
		HostListEntry hostListEntry = hostLists.getHostLists().get(2);
		assertNotNull("check for host list entry null",hostListEntry);
		assertEquals("check for host list size",2,hostListEntry.getHosts().size());

		
		assertEquals("check host list entry id 3",3,hostListEntry.getId());
		assertEquals("check host list entry name 3","DNS Servers",hostListEntry.getName());
		assertEquals("check host list entry name 3","SNMP monitoring of infrastructure DNS services",hostListEntry.getDescription());
		assertEquals("check host list entry name 3","public",hostListEntry.getCommunityRead());
		assertEquals("check host list entry name 3",1161,hostListEntry.getPort());
		
		List<Host> hosts = hostListEntry.getHosts();
		assertNotNull("check host list entry hosts not null 3",hosts);
		Host host1 = hosts.get(0);
		assertEquals("check host 1","ns-001",host1.getHost());
		assertEquals("check community-read 1","flubber",host1.getCommunityRead());
		
		Host host2 = hosts.get(1);
		assertEquals("check host 2","ns-002",host2.getHost());
		assertEquals("check community-read 2",null,host2.getCommunityRead());
	}
}
