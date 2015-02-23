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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.sdk.event.BoundaryEventRouteBuilder;

public class BoundaryEventRouteBuilderTest {
	
	private BoundaryEventRouteBuilder route;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		route = new BoundaryEventRouteBuilder();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEmptyPassword() {
		assertEquals("Check empty password","",route.getPassword());
	}

	@Test
	public void testSetPassword() {
		String expectedPassword = "XXXXXXXXXX";
		route.setPassword(expectedPassword);
		assertEquals("Check apiKey",expectedPassword,route.getPassword());
	}
	
	@Test
	public void testHost() {
		String expectedApiHost = "some.where.but.no.where.com";
		route.setHost(expectedApiHost);
		assertEquals("Check api host",expectedApiHost,route.getHost());
	}
	
	@Test
	public void testRouteId() {
		String expectedRouteId = "Toucan Sam";
		route.setRouteId(expectedRouteId);
		assertEquals("Check route Id",expectedRouteId,route.getRouteId());
	}
	
	@Test
	public void testFromUri() {
		String expectedFromUri = "Toucan Sam";
		route.setFromUri(expectedFromUri);
		assertEquals("Check fromUri",expectedFromUri,route.getFromUri());
	}

	@Test
	public void testApiPort() {
		int expectedPort = 8888;
		route.setPort(expectedPort);
		assertEquals("Check api port",expectedPort,route.getPort());
	}

}
