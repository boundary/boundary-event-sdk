package com.boundary.sdk.event.service.ssh;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;

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
		SshxServiceModel model = new SshxServiceModel();
		config.setHost(hostName);
		config.setCommand("echo " + output);
		model.setExpectedOutput(output);
		ServiceTest<SshxConfiguration,SshxServiceModel> serviceTest = new ServiceTest<SshxConfiguration,SshxServiceModel>(
				testName,"ssh",serviceName,request.getRequestId(),config,model);
		
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put(SERVICE_TEST_INSTANCE, serviceTest);
		
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
