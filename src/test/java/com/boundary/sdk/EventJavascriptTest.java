package com.boundary.sdk;

import java.io.File;

import org.apache.camel.test.junit4.CamelSpringTestSupport;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SuppressWarnings("deprecation")
public class EventJavascriptTest extends CamelSpringTestSupport {
	private static Logger LOG = LoggerFactory.getLogger(EventJavascriptTest.class);

	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/event-javascript-test.xml");
	}

	@Test
	public void testEventToLog() throws Exception {
		TestEvent event = new TestEvent("hello");
		
		template.sendBody("direct:event-test", event);
		
		Thread.sleep(2000);

		File target = new File("target/event-javascript-test.log");
		assertTrue("Log file exists: ", target.exists());
		String content = context.getTypeConverter().convertTo(String.class,target);
		LOG.info(content);
		String expectedContent = "{\"name\":\"hello\",\"tags\":[\"red\",\"green\",\"blue\"],\"properties\":{\"hello\":\"world\",\"mylist\":[\"yellow\",\"magenta\",\"cyan\"]},\"status\":\"SUCCEED\",\"source\":{\"ref\":\"localhost\",\"properties\":{\"song_list\":[\"Red Barchetta\",\"Freewill\",\"La Villa Strangiato\"]}}}";
		assertEquals(expectedContent, content);
	}
}