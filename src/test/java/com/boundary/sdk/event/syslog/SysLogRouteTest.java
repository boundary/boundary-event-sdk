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
package com.boundary.sdk.event.syslog;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.sdk.event.syslog.SysLogRouteBuilder;

public class SysLogRouteTest {
	
	SysLogRouteBuilder route;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		route = new SysLogRouteBuilder();
	}

	@After
	public void tearDown() throws Exception {
		route = null;
	}

	@Test
	public void testRouteId() {
		String expectedRouteId = "foobar";
		route.setRouteId(expectedRouteId);
		assertEquals("Check route id",expectedRouteId,route.getRouteId());
	}
	
	@Test
	public void testPort() {
		int expectedPort = 514;
		route.setPort(expectedPort);
		assertEquals("Check port",expectedPort,route.getPort());
	}
	
	@Test
	public void testToUri() {
		String expectedToUri = "direct:i-am-the-egg-man";
		route.setToUri(expectedToUri);
		assertEquals("Check toUri",expectedToUri,route.getToUri());
	}
	
	@Test
	public void testTranslateFlagConstructor() {
		SysLogRouteBuilder route = new SysLogRouteBuilder(false);
		assertFalse("Check convertToEvent constructor",route.isConvertToEvent());
	}
	
	@Test
	public void testTranslateFlagMethod() {
		route.setConvertToEvent(false);
		assertFalse("Check convertToEvent method",route.isConvertToEvent());
	}

}
