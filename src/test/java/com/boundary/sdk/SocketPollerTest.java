package com.boundary.sdk;

import java.io.File;
import java.util.ArrayList;
import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SocketPollerTest extends CamelSpringTestSupport {

    @EndpointInject(uri = "mock:result")
    private MockEndpoint mockOut;

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/socket-poller.xml");
	}

	
	@Test
	public void testCron() throws Exception {
		mockOut.setExpectedMessageCount(2);

		mockOut.assertIsSatisfied();
	}
}