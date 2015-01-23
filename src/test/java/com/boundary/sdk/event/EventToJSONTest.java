// Copyright 2014-2015 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.sdk.event;

import java.io.File;
import java.util.ArrayList;

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
		event.setStatus(TestStatus.SUCCEED);
		
		
		template.sendBody("direct:event-to-json", event);
		
		Thread.sleep(1000);

		File target = new File("target/event-to-json.log");
		assertTrue("Log file exists: ", target.exists());
		String content = context.getTypeConverter().convertTo(String.class,target);
		String expectedContent = "{\"name\":\"hello\",\"tags\":[\"red\",\"green\",\"blue\"],\"properties\":{\"hello\":\"world\",\"mylist\":[\"yellow\",\"magenta\",\"cyan\"]},\"status\":\"SUCCEED\",\"source\":{\"ref\":\"localhost\",\"type\":\"host\",\"properties\":{\"song_list\":[\"Red Barchetta\",\"Freewill\",\"La Villa Strangiato\"]}}}";
		assertEquals(expectedContent,content);
	}
	
	@Test
	public void testJSONNullHandling() throws Exception {
		TestEvent event = new TestEvent();
		
		template.sendBody("direct:event-to-json", event);
		
		Thread.sleep(1000);

		File target = new File("target/event-to-json.log");
		assertTrue("Log file exists: ", target.exists());
		String content = context.getTypeConverter().convertTo(String.class,target);
		String expectedContent = "{}";
		assertEquals(expectedContent,content);

	}
	
	@Test
	public void testJSONAllFields() throws Exception {
		TestEvent event = new TestEvent();
		
		event.setName("red");
		event.setStatus(TestStatus.FAIL);
		event.addProperty("Hello", "World!");
		event.addTag("foobar");
		
		
		template.sendBody("direct:event-to-json", event);
		
		File target = new File("target/event-to-json.log");
		assertTrue("Log file exists: ", target.exists());
		String content = context.getTypeConverter().convertTo(String.class,target);
		String expectedContent = "{\"name\":\"red\",\"tags\":[\"foobar\"],\"properties\":{\"Hello\":\"World!\"},\"status\":\"FAIL\"}";
		assertEquals(expectedContent,content);
	}
}