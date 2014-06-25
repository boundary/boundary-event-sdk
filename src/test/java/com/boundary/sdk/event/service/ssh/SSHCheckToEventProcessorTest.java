package com.boundary.sdk.event.service.ssh;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;
import com.boundary.sdk.event.service.ssh.SSHCheckToEventProcessor;

import static com.boundary.sdk.event.service.ssh.SshHeaderNames.*;
import static com.boundary.sdk.event.service.QuartzHeaderNames.*;
import static com.boundary.sdk.event.util.BoundaryHeaderNames.*;

import com.boundary.sdk.event.util.BoundaryHeaderNames;
import com.boundary.camel.component.ssh.SshxConfiguration;

public class SSHCheckToEventProcessorTest extends CamelSpringTestSupport  {



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
	public void testSSHCheckToEventProcessor() {
		MockEndpoint endPoint = getMockEndpoint("mock:out");
		String output = "ren & stimpy";
		String expectedOutput = output;
		String triggerName = "bugs";
		String serviceName = "SDN";
		String testName = "SDN check";
		String hostName = "abc.def.com";
		String fireTime = new Date().toString();;
		ServiceCheckRequest request = new ServiceCheckRequest();
		SshxConfiguration config = new SshxConfiguration();
		config.setHost(hostName);
		ServiceTest<SshxConfiguration> serviceTest =
				new ServiceTest<SshxConfiguration>(testName,serviceName,request.getRequestId(),config);
		
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put(BoundaryHeaderNames.BOUNDARY_SERVICE_NAME,serviceName);
		properties.put(SSH_HEADER_EXPECTED_OUTPUT, expectedOutput);
		properties.put(QUARTZ_HEADER_TRIGGER_NAME,triggerName);
		properties.put(QUARTZ_HEADER_FIRE_TIME,fireTime);
		properties.put(BOUNDARY_SERVICE_TEST, serviceTest);
		
		template.sendBodyAndHeaders("direct:in",output,properties);
		
		List<Exchange> receivedExchanges = endPoint.getReceivedExchanges();
		
		for (Exchange e : receivedExchanges) {
			RawEvent event = e.getIn().getBody(RawEvent.class);

			assertNotNull(event);
			assertEquals("check source ref",hostName,event.getSource().getRef());
			assertEquals("check severity",Severity.INFO, event.getSeverity());
			assertEquals("check status",Status.CLOSED,event.getStatus());
		}
	}

	@Test
	public void testProcess() {
		//fail("Not yet implemented");
	}
	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/ssh-to-event-test.xml");
	}

}
