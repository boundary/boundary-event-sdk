package com.boundary.sdk;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BoundaryEventRouteTest {
	
	private BoundaryEventRoute route;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		route = new BoundaryEventRoute();
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testEmptyKey() {
		assertEquals("Check empty key","",route.getApiKey());
	}

	@Test
	public void testSetApiKey() {
		String expectedApiKey = "XXXXXXXXXX";
		route.setApiKey(expectedApiKey);
		assertEquals("Check apiKey",expectedApiKey,route.getApiKey());
	}

}
