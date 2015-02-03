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
import org.junit.Ignore;
import org.junit.Test;

import com.boundary.sdk.snmp.metric.SnmpMetricCatalog;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SnmpMetricCatalogTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	private SnmpMetricCatalog snmpMetricCatalog;


	@Before
	public void setUp() throws Exception {
		snmpMetricCatalog = load("META-INF/json/test-snmp-poller-catalog.json");
	}

	@After
	public void tearDown() throws Exception {
		snmpMetricCatalog = null;
	}
	
	private SnmpMetricCatalog load(String resource) throws URISyntaxException {
		SnmpMetricCatalog snmpMetricCatalog = null;

		ClassLoader classLoader = this.getClass().getClassLoader();
		URL url = classLoader.getResource(resource);
		File file = new File(url.toURI());

		ObjectMapper mapper = new ObjectMapper();

		try {
			snmpMetricCatalog = mapper.readValue(file,SnmpMetricCatalog.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return snmpMetricCatalog;
	}

	@Test
	public void testSnmpMetricCatalog() throws URISyntaxException {
		
		assertEquals("check poller count",3,snmpMetricCatalog.getPollers().size());
		assertEquals("check host list count",3,snmpMetricCatalog.getHostList().size());
	}

	@Test
	public void testGetOidList() {
		List<OidListEntry> oidList = snmpMetricCatalog.getOidList();
		assertNotNull("check for null oidList",oidList);
		assertEquals("check oids list count",3,oidList.size());

		
		OidListEntry list1 = oidList.get(0);
		assertEquals("check id",1,list1.getId());
		assertEquals("check name","Default",list1.getName());
		assertEquals("check oids count",9,list1.getOids().size());
		
		Oid entry1 = list1.getOids().get(0);
		assertEquals("check oid 1","1.3.6.1.2.1.25.1.5.0",entry1.getOid());
		assertEquals("check metric-id 1","",entry1.getMetricId());
		assertEquals("check description 1","Red",entry1.getDescription());
		assertEquals("check enabled 1",true,entry1.isEnabled());
		
		Oid entry2 = list1.getOids().get(1);
		assertEquals("check oid 2","1.3.6.1.2.1.25.1.6.0",entry2.getOid());
		assertEquals("check metric-id 2","",entry2.getMetricId());
		assertEquals("check description 2","Green",entry2.getDescription());
		assertEquals("check enabled 2",false,entry2.isEnabled());

		
//		{
//			"oid":"1.3.6.1.2.1.25.1.5.0",
//			"metric-id":"",
//			"description":"Red"
//		},
//		{
//			"oid":"1.3.6.1.2.1.25.1.6.0",
//			"metric-id":"",
//			"description":"Green"
//		},
	}

	@Test
	public void testGetHostList() {

	}

	@Ignore
	@Test
	public void testGetPollers() {
		fail("Not yet implemented");
	}

}
