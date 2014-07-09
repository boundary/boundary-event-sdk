package com.boundary.sdk.event.service;

import static org.junit.Assert.*;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.sdk.event.service.db.PingServiceModel;
import com.boundary.sdk.event.service.db.PortServiceModel;

public class ServiceCheckRouterTest extends CamelSpringTestSupport {

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
	
	@Ignore
	@Test
	public void test() {
		MockEndpoint mock = getMockEndpoint("mock:service-checks-router-out");
	}

	@Ignore
	@Test
	public void testGetEndPointsFromRequest() {
		ServiceCheckRouter router = new ServiceCheckRouter();
		ServiceCheckRequest request = new ServiceCheckRequest();
		PingConfiguration pingConfiguration = new PingConfiguration();
		PingServiceModel pingModel = new PingServiceModel();
		PortConfiguration portConfiguration = new PortConfiguration();
		PortServiceModel portModel = new PortServiceModel();
		
		ServiceTest<PingConfiguration,PingServiceModel> pingTest = new ServiceTest<PingConfiguration,PingServiceModel>(
				"ping","ping","localhost",request.getRequestId(),pingConfiguration,pingModel);
		ServiceTest<PortConfiguration,PortServiceModel> portTest = new ServiceTest<PortConfiguration,PortServiceModel>(
				"port","port","localhost",request.getRequestId(),portConfiguration,portModel);
		
		request.addServiceTest(pingTest);
		request.addServiceTest(portTest);
		
//		String endPoints = router.getEndPointsFromRequest(request);
//		
//		assertEquals("check test names","ping,port",endPoints);
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}
}
