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
import com.boundary.camel.component.ping.PingResult;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.port.PortResult;
import com.boundary.camel.component.port.PortStatus;
import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.camel.component.url.UrlConfiguration;
import com.boundary.sdk.event.service.ping.PingServiceModel;
import com.boundary.sdk.event.service.port.PortServiceModel;
import com.boundary.sdk.event.service.ssh.SshxServiceModel;
import com.boundary.sdk.event.service.url.UrlServiceModel;

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
		PortServiceModel model = new PortServiceModel();
		configuration.setHost(HOST);
		configuration.setPort(PORT);
		ServiceTest<PortConfiguration,PortServiceModel> serviceTest = new ServiceTest<PortConfiguration,PortServiceModel>(
				"port","port","localhost", request.getRequestId(),configuration,model);

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
			ServiceTest<PortConfiguration,PortServiceModel> test = message.getHeader(
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
		PingServiceModel model = new PingServiceModel();
		configuration.setHost(HOST);
		ServiceTest<PingConfiguration,PingServiceModel> serviceTest = new ServiceTest<PingConfiguration,PingServiceModel>(
				"ping", "ping","localhost", request.getRequestId(), configuration,model);

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
	
	@Test
	public void testGetSshConfiguration() throws InterruptedException {

		MockEndpoint endPoint = getMockEndpoint("mock:ssh-service-test-out");
		endPoint.expectedMessageCount(1);

		ServiceCheckRequest request = new ServiceCheckRequest();

		SshxConfiguration configuration = new SshxConfiguration();
		SshxServiceModel model = new SshxServiceModel();
		configuration.setHost(HOST);
		ServiceTest<SshxConfiguration,SshxServiceModel> serviceTest = new ServiceTest<SshxConfiguration,SshxServiceModel>(
				"ssh", "ssh","localhost", request.getRequestId(), configuration,model);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_ID,request.getRequestId());
		properties.put(ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_INSTANCE,request);
		properties.put(ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE,serviceTest);
		properties.put(ServiceCheckPropertyNames.SERVICE_TEST_NAME,"url");

		template.sendBodyAndHeaders("direct:ssh-service-test-in",serviceTest, properties);
		
		endPoint.assertIsSatisfied();
		
		List<Exchange> receivedExchanges = endPoint.getReceivedExchanges();
		for (Exchange e : receivedExchanges) {
			SshxConfiguration config = e.getIn().getBody(SshxConfiguration.class);

			assertEquals("check host",HOST, config.getHost());
		}
	}


	@Test
	public void testGetUrlConfiguration() throws InterruptedException {

		MockEndpoint endPoint = getMockEndpoint("mock:url-service-test-out");
		endPoint.expectedMessageCount(1);

		ServiceCheckRequest request = new ServiceCheckRequest();

		UrlConfiguration configuration = new UrlConfiguration();
		configuration.setHost("myhost");
		UrlServiceModel model = new UrlServiceModel();
		configuration.setHost(HOST);
		ServiceTest<UrlConfiguration,UrlServiceModel> serviceTest = new ServiceTest<UrlConfiguration,UrlServiceModel>(
				"url", "url","localhost", request.getRequestId(), configuration,model);

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_ID,request.getRequestId());
		properties.put(ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_INSTANCE,request);
		properties.put(ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE,serviceTest);
		properties.put(ServiceCheckPropertyNames.SERVICE_TEST_NAME,"url");

		template.sendBodyAndHeaders("direct:url-service-test-in",serviceTest, properties);
		
		endPoint.assertIsSatisfied();
		
		List<Exchange> receivedExchanges = endPoint.getReceivedExchanges();
		for (Exchange e : receivedExchanges) {
			UrlConfiguration config = e.getIn().getBody(UrlConfiguration.class);

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
				
				from("direct:ssh-service-test-in")
				.log("ServiceTest: ${body}")
				.bean(new ExtractServiceTestConfiguration(), "extractSshxConfiguration")
				.to("mock:ssh-service-test-out");
				
				from("direct:url-service-test-in")
				.log("ServiceTest: ${body}")
				.bean(new ExtractServiceTestConfiguration(), "extractUrlConfiguration")
				.to("mock:url-service-test-out");

			}
		};
	}

}
