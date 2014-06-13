package com.boundary.sdk.event.service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;

public class ServiceCheckRouterTest {

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
	public void testGetEndPointsFromRequest() {
		ServiceCheckRouter router = new ServiceCheckRouter();
		ServiceCheckRequest request = new ServiceCheckRequest();
		PingConfiguration pingConfiguration = new PingConfiguration();
		PortConfiguration portConfiguration = new PortConfiguration();
		ServiceTest<PingConfiguration> pingTest = new ServiceTest<PingConfiguration>("ping",request.getRequestId(),pingConfiguration);
		ServiceTest<PortConfiguration> portTest = new ServiceTest<PortConfiguration>("port",request.getRequestId(),portConfiguration);
		
		request.addServiceTest(pingTest);
		request.addServiceTest(portTest);
		
		String endPoints = router.getEndPointsFromRequest(request);
		
		assertEquals("check test names","ping,port",endPoints);
	}
}
