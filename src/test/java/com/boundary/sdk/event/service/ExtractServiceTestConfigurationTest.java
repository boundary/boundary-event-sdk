package com.boundary.sdk.event.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.ping.PingInfo;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.port.PortInfo;
import com.boundary.camel.component.port.PortStatus;

public class ExtractServiceTestConfigurationTest extends CamelTestSupport {
	
	private final static String HOST = "google.com";
	private final static int PORT = 80;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testGetPortConfiguration() throws InterruptedException {

		MockEndpoint endPoint = getMockEndpoint("mock:port-service-test-out");
		endPoint.expectedMessageCount(1);

		ServiceCheckRequest request = new ServiceCheckRequest();

		PortConfiguration configuration = new PortConfiguration();
		configuration.setHost(HOST);
		configuration.setPort(PORT);
		ServiceTest<PortConfiguration> serviceTest = new ServiceTest<PortConfiguration>(
				"port","port","localhost", request.getRequestId(), configuration);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_ID,request.getRequestId());
		properties.put(ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_INSTANCE,request);
		properties.put(ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE,serviceTest);
		properties.put(ServiceCheckPropertyNames.SERVICE_TEST_NAME,"port");

		template.sendBodyAndHeaders("direct:port-service-test-in",serviceTest, properties);
		
		endPoint.assertIsSatisfied();
		
		List<Exchange> receivedExchanges = endPoint.getReceivedExchanges();
		for (Exchange e : receivedExchanges) {
			Message message = e.getIn();
			PortConfiguration config = message.getBody(PortConfiguration.class);
			ServiceCheckRequest req = message.getHeader(
					ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_INSTANCE,ServiceCheckRequest.class);
			ServiceTest<PortConfiguration> test = message.getHeader(
					ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE,ServiceTest.class);

			assertEquals("check host",HOST, config.getHost());
			assertEquals("check port",PORT,config.getPort());
			
			assertEquals("check service request id",request.getRequestId(),req.getRequestId());
			assertEquals("check service request id on service test",
					serviceTest.getRequestId(),test.getRequestId());
		}
	}
	
	@Test
	public void testGetPingConfiguration() throws InterruptedException {

		MockEndpoint endPoint = getMockEndpoint("mock:ping-service-test-out");
		endPoint.expectedMessageCount(1);

		ServiceCheckRequest request = new ServiceCheckRequest();

		PingConfiguration configuration = new PingConfiguration();
		configuration.setHost(HOST);
		ServiceTest<PingConfiguration> serviceTest = new ServiceTest<PingConfiguration>(
				"ping", "ping","localhost", request.getRequestId(), configuration);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_ID,request.getRequestId());
		properties.put(ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_INSTANCE,request);
		properties.put(ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE,serviceTest);
		properties.put(ServiceCheckPropertyNames.SERVICE_TEST_NAME,"ping");

		template.sendBodyAndHeaders("direct:ping-service-test-in",serviceTest, properties);
		
		endPoint.assertIsSatisfied();
		
		List<Exchange> receivedExchanges = endPoint.getReceivedExchanges();
		for (Exchange e : receivedExchanges) {
			PingConfiguration config = e.getIn().getBody(PingConfiguration.class);

			assertEquals("check host",HOST, config.getHost());
		}
	}


	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		return new RouteBuilder() {
			@Override
			public void configure() throws Exception {
				from("direct:ping-service-test-in")
				.log("ServiceTest: ${body}")
				.bean(new ExtractServiceTestConfiguration(), "extractPingConfiguration")
				.to("mock:ping-service-test-out");
				
				from("direct:port-service-test-in")
				.log("ServiceTest: ${body}")
				.bean(new ExtractServiceTestConfiguration(), "extractPortConfiguration")
				.to("mock:port-service-test-out");

			}
		};
	}

}
