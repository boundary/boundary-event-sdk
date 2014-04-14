package com.boundary.sdk;

import java.io.File;
import java.util.ArrayList;

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
		
		event.addTag("red");
		event.addTag("green");
		event.addTag("blue");
		
		event.addProperty("hello", "world");
		ArrayList<String> aList = new ArrayList<String>();
		aList.add("yellow");
		aList.add("magenta");
		aList.add("cyan");
		
		event.addProperty("mylist",aList);
		
		ArrayList<String> aSongList = new ArrayList<String>();
		aSongList.add("Red Barchetta");
		aSongList.add("Freewill");
		aSongList.add("La Villa Strangiato");
		
		event.getSource().setRef("localhost").setType("host");
		event.getSource().addProperty("song_list", aSongList);
		
		
		template.sendBody("direct:event-to-json", event);
		
		Thread.sleep(1000);

		File target = new File("target/event-to-json.log");
		assertTrue("Log file exists: ", target.exists());
		String content = context.getTypeConverter().convertTo(String.class,target);
		String expectedContent = "{\"name\":\"hello\",\"tags\":[\"red\",\"green\",\"blue\"],\"properties\":{\"hello\":\"world\",\"mylist\":[\"yellow\",\"magenta\",\"cyan\"]},\"status\":\"SUCCEED\",\"source\":{\"ref\":\"localhost\",\"properties\":{\"song_list\":[\"Red Barchetta\",\"Freewill\",\"La Villa Strangiato\"]}}}";
		assertEquals(expectedContent,content);
	}
}