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
package com.boundary.sdk.event.snmp;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.smi.OID;

import com.snmp4j.smi.SmiManager;
import com.snmp4j.smi.SmiObject;

public class OidLookupTest {
	
    private static final Logger LOG = LoggerFactory.getLogger(OidLookupTest.class);

	private static SmiSupport smi;
	private static SmiManager smiManager;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		String ber ="TCP-MIB::tcpCurrEstab.0";
		LOG.info("length: " + ber.length());
		
		smi = new SmiSupport();
		smi.setLicense(System.getenv("BOUNDARY_MIB_LICENSE"));
		smi.setRepository(System.getenv("BOUNDARY_MIB_REPOSITORY"));
		smi.initialize();
		smi.loadModules();
		smiManager = smi.getSmiManager();

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		OID oid = new OID(".1.3.6.1.2.1.6.9.0");
		LOG.info("oid.getBERLength(): {}",oid.getBERLength());
	}
	
	@Test
	public void testSysDescription() {
		OID oid = new OID("1.3.6.1.2.1.1.1");
		SmiObject object = smiManager.findSmiObject(oid);
		assertEquals("check for","sysDescr",object.getObjectName());
	}
	
	@Test
	public void testSysObjectId() {
		OID oid = new OID("1.3.6.1.2.1.1.2");
		SmiObject object = smiManager.findSmiObject(oid);
		assertEquals("check for","sysObjectID",object.getObjectName());
	}
}
