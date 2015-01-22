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
