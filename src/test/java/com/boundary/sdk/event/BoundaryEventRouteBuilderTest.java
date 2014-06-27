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
	public void testEmptyKey() {
		assertEquals("Check empty key","",route.getApiKey());
	}

	@Test
	public void testSetApiKey() {
		String expectedApiKey = "XXXXXXXXXX";
		route.setApiKey(expectedApiKey);
		assertEquals("Check apiKey",expectedApiKey,route.getApiKey());
	}
	
	@Test
	public void testApiHost() {
		String expectedApiHost = "some.where.but.no.where.com";
		route.setApiHost(expectedApiHost);
		assertEquals("Check api host",expectedApiHost,route.getApiHost());
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
		int expectedApiPort = 8888;
		route.setApiPort(expectedApiPort);
		assertEquals("Check api port",expectedApiPort,route.getApiPort());
	}

}
