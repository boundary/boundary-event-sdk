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

/**
 * @author davidg
 *
 */
public class SNMPRouteBuilderTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
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

        /**
         *
         */
        @Test
        public void testBindAddress() {
            String expectedBindAddress = "1.2.3.4";
	    SNMPRouteBuilder builder = new SNMPRouteBuilder();
            builder.setBindAddress(expectedBindAddress);
            assertEquals("check bind address",expectedBindAddress,builder.getBindAddress());
        }
	
	@Test
	public void testMibRepository() {
		String expectedPath = "foobar";
		SNMPRouteBuilder builder = new SNMPRouteBuilder();
		
		builder.setMibRepository(expectedPath);
		assertEquals("Check license",expectedPath,builder.getMibRepository());
	}

	@Test
	public void testLicense() {
		String expectedLicense = "foobar";
		SNMPRouteBuilder builder = new SNMPRouteBuilder();
		
		builder.setLicense(expectedLicense);
		assertEquals("Check license",expectedLicense,builder.getLicense());
	}

}
