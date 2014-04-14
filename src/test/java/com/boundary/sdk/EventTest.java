package com.boundary.sdk;

import java.io.File;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



@SuppressWarnings("deprecation")
public class EventTest extends CamelSpringTestSupport {
	private static Logger LOG = LoggerFactory.getLogger(EventTest.class);

	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/event-test.xml");
	}

	@Test
	public void testEventToLog() throws Exception {
		TestEvent event = new TestEvent("hello");
		
		template.sendBody("direct:event-test", event);
		
		Thread.sleep(5000);

		File target = new File("target/event-test.log");
		assertTrue("Log file exists: ", target.exists());
		String content = context.getTypeConverter().convertTo(String.class,target);
		LOG.info(content);
		String expectedContent = "TestEvent,name = hello,tags = [],props = {},status = SUCCEED,source = TestSource [ref=, type=, props={}]";
		assertEquals(expectedContent, content);
	}
}