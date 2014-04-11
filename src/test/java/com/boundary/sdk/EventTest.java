package com.boundary.sdk;

import java.io.File;

import org.apache.camel.Endpoint;
import org.apache.camel.Exchange;
import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



@SuppressWarnings("deprecation")
public class EventTest extends CamelSpringTestSupport {
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
		assertEquals("TestEvent [name=hello]", content);
	}
}