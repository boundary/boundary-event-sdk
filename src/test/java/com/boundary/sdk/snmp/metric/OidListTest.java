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

public class OidListTest {

	private final static String OID_LISTS_FILE = "META-INF/json/test-oid-lists.json";

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
		OidMapList oidLists = OidMapList.load(OID_LISTS_FILE);
		assertNotNull("check oids list for null", oidLists);
		assertEquals("check oids list size", 3, oidLists.getOidList().size());

	}

	@Test
	public void testOidListOne() throws URISyntaxException {
		OidMapList oidLists = OidMapList.load(OID_LISTS_FILE);

		List<OidMapEntry> oidList = oidLists.getOidList();
		assertNotNull("check oidlist for null", oidList);
		
		OidMapEntry entry1 = oidList.get(0);
		
		assertEquals("check entry id",1,entry1.getId());
		assertEquals("check entry name","Default",entry1.getName());
		assertEquals("check entry enabled",true,entry1.isEnabled());
		assertEquals("check entry oids size",9,entry1.getOids().size());
		
		List<OidMap> oids = entry1.getOids();
		assertNotNull("check oids for null",oids);
		
		OidMap oid1 = oids.get(0);
		assertEquals("check oid 1","1.3.6.1.2.1.25.1.5.0",oid1.getOid());
		assertEquals("check metric id 1","METRIC_RED",oid1.getMetricId());
		assertEquals("check description 1","Red",oid1.getDescription());
		
		OidMap oid2 = oids.get(1);
		assertEquals("check oid 2","1.3.6.1.2.1.25.1.6.0",oid2.getOid());
		assertEquals("check metric id 2","METRIC_GREEN",oid2.getMetricId());
		assertEquals("check description 2","Green",oid2.getDescription());
		
		OidMap oid3 = oids.get(2);
		assertEquals("check oid 3","1.3.6.1.2.1.6.9.0",oid3.getOid());
		assertEquals("check metric id 3","METRIC_BLUE",oid3.getMetricId());
		assertEquals("check description 3","Blue",oid3.getDescription());

		OidMap oid4 = oids.get(3);
		assertEquals("check oid 4","1.3.6.1.2.1.7.1.0",oid4.getOid());
		assertEquals("check metric id 4","METRIC_CYAN",oid4.getMetricId());
		assertEquals("check description 4","Cyan",oid4.getDescription());

		OidMap oid5 = oids.get(4);
		assertEquals("check oid 5","1.3.6.1.2.1.7.4.0",oid5.getOid());
		assertEquals("check metric id 5","METRIC_PINK",oid5.getMetricId());
		assertEquals("check description 5","Pink",oid5.getDescription());

		OidMap oid6 = oids.get(5);
		assertEquals("check oid 6","1.3.6.1.2.1.6.10.0",oid6.getOid());
		assertEquals("check metric id 6","METRIC_MAGENTA",oid6.getMetricId());
		assertEquals("check description 6","Magenta",oid6.getDescription());

		OidMap oid7 = oids.get(6);
		assertEquals("check oid 7","1.3.6.1.2.1.6.11.0",oid7.getOid());
		assertEquals("check metric id 7","METRIC_YELLOW",oid7.getMetricId());
		assertEquals("check description 7","Yellow",oid7.getDescription());

		OidMap oid8 = oids.get(7);
		assertEquals("check oid 8","1.3.6.1.2.1.4.3.0",oid8.getOid());
		assertEquals("check metric id 8","METRIC_PURPLE",oid8.getMetricId());
		assertEquals("check description 8","Purple",oid8.getDescription());

		OidMap oid9 = oids.get(8);
		assertEquals("check oid 9","1.3.6.1.2.1.4.10.0",oid9.getOid());
		assertEquals("check metric id 9","METRIC_ORANGE",oid9.getMetricId());
		assertEquals("check description 9","Orange",oid9.getDescription());

	}
	
	@Test
	public void testOidListTwo() throws URISyntaxException {
		OidMapList oidLists = OidMapList.load(OID_LISTS_FILE);

		List<OidMapEntry> oidList = oidLists.getOidList();
		assertNotNull("check oidlist for null", oidList);
		
		OidMapEntry entry1 = oidList.get(1);
		
		assertEquals("check entry id",2,entry1.getId());
		assertEquals("check entry name","Extra",entry1.getName());
		assertEquals("check entry enabled",true,entry1.isEnabled());
		assertEquals("check entry oids size",2,entry1.getOids().size());
		
		List<OidMap> oids = entry1.getOids();
		assertNotNull("check oids for null",oids);
		
		OidMap oid1 = oids.get(0);
		assertEquals("check oid 1","1.3.6.1.2.1.25.1.5.0",oid1.getOid());
		assertEquals("check metric id 1","METRIC_SKY",oid1.getMetricId());
		assertEquals("check description 1","Sky",oid1.getDescription());
		
		OidMap oid2 = oids.get(1);
		assertEquals("check oid 2","1.3.6.1.2.1.25.1.6.0",oid2.getOid());
		assertEquals("check metric id 2","METRIC_CLOUDS",oid2.getMetricId());
		assertEquals("check description 2","Clouds",oid2.getDescription());

	}

	@Test
	public void testOidListThree() throws URISyntaxException {
		OidMapList oidLists = OidMapList.load(OID_LISTS_FILE);

		List<OidMapEntry> oidList = oidLists.getOidList();
		assertNotNull("check oidlist for null", oidList);
		
		OidMapEntry entry1 = oidList.get(2);
		
		assertEquals("check entry id",3,entry1.getId());
		assertEquals("check entry name","DNS",entry1.getName());
		assertEquals("check entry enabled",true,entry1.isEnabled());
		assertEquals("check entry oids size",2,entry1.getOids().size());
		
		List<OidMap> oids = entry1.getOids();
		assertNotNull("check oids for null",oids);
		
		OidMap oid1 = oids.get(0);
		assertEquals("check oid 1","1.3.6.1.2.1.25.1.5.0",oid1.getOid());
		assertEquals("check metric id 1","METRIC_DELAY",oid1.getMetricId());
		assertEquals("check description 1","Delay",oid1.getDescription());
		
		OidMap oid2 = oids.get(1);
		assertEquals("check oid 2","1.3.6.1.2.1.25.1.6.0",oid2.getOid());
		assertEquals("check metric id 2","METRIC_LOOKUP",oid2.getMetricId());
		assertEquals("check description 2","Lookup",oid2.getDescription());

	}
}
