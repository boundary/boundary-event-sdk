package com.boundary.sdk;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;

import com.boundary.sdk.event.syslog.SyslogMessageGenerator;

/**
 * @version $Revision$
 */
public class SyslogMessageTest extends CamelSpringTestSupport {

	private final String SYSLOG_OUT_URI = "mock:syslog-test";
	private final String QUEUE_OUT_URI = "mock:queue-test";
	private final int SYSLOG_ONLY_PORT = 1514;
	private final int SYSLOG_AND_QUEUE_PORT = 10514;
	private final int DELAY = 100;
	private final int DEFAULT_MESSAGE_COUNT = 100;

	@Test
	public void syslogOnlyMessageTest() throws Exception {
		MockEndpoint syslogOut = getMockEndpoint(SYSLOG_OUT_URI);
		int expectedMessageCount = DEFAULT_MESSAGE_COUNT;
		syslogOut.setExpectedMessageCount(expectedMessageCount);
		SyslogMessageGenerator generator = new SyslogMessageGenerator();
		
		generator.setPort(SYSLOG_ONLY_PORT);
		generator.sendMessages(expectedMessageCount, DELAY);

		assertMockEndpointsSatisfied();
	}
	
	@Test
	public void syslogAndQueueMessageTest() throws Exception {
		MockEndpoint queueOut = getMockEndpoint(QUEUE_OUT_URI);
		//queueOut.setMinimumResultWaitTime(10000);
 
		int expectedMessageCount = DEFAULT_MESSAGE_COUNT;
		queueOut.setExpectedMessageCount(expectedMessageCount);
		//queueOut.expectedMinimumMessageCount(expectedMessageCount);
		SyslogMessageGenerator generator = new SyslogMessageGenerator();
		
		generator.setPort(SYSLOG_AND_QUEUE_PORT);
		generator.sendMessages(expectedMessageCount, DELAY);
		
		queueOut.await(60000, TimeUnit.SECONDS);

		assertMockEndpointsSatisfied();
	}


	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(
				"META-INF/syslog-message-test.xml");
	}

}
