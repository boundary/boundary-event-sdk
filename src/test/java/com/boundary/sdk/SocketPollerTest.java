package com.boundary.sdk;


import org.apache.camel.EndpointInject;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
//import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SocketPollerTest extends CamelSpringTestSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(SocketPollerTest.class);

    @EndpointInject(uri = "mock:exception")
    private MockEndpoint mockOut;

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/socket-poller.xml");
	}

	@Ignore("Requires credentials to communicate with Boundary")
	@Test
	public void testCron() throws Exception {
		mockOut.setExpectedMessageCount(2);
		
		Thread.sleep(300000);
		LOG.info("testCron()");
		mockOut.assertIsSatisfied();
	}
}
