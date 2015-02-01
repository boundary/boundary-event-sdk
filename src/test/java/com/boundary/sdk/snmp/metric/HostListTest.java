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
		
		assertEquals("check host list entry id 1",1,hostListEntry.getId());
		assertEquals("check host list entry name 1","Web Servers",hostListEntry.getName());
		assertEquals("check host list entry name 1","Web Servers",hostListEntry.getDescription());
		assertEquals("check host list entry name 1","Web Servers",hostListEntry.getDefaultCommunityRead());
	}


}
