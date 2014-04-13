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
public class EventToJSONTest extends CamelSpringTestSupport {
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/event-to-json.xml");
	}

	@Test
	public void testEventToLog() throws Exception {
		TestEvent event = new TestEvent("hello");
		
		template.sendBody("direct:event-to-json", event);
		
		Thread.sleep(1000);

		File target = new File("target/event-to-json.log");
		assertTrue("Log file exists: ", target.exists());
		String content = context.getTypeConverter().convertTo(String.class,target);
		assertEquals("{\"name\":\"hello\"}", content);
	}
}