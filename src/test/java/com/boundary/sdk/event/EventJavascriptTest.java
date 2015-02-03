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

import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//@SuppressWarnings("deprecation")
public class EventJavascriptTest extends CamelSpringTestSupport {
	private static Logger LOG = LoggerFactory.getLogger(EventJavascriptTest.class);

	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/event-javascript-test.xml");
	}

	@Ignore("Feature not enabled yet")
	@Test
	public void testEventToLog() throws Exception {
		TestEvent event = new TestEvent("hello");
		
		template.sendBody("direct:event-test", event);
		
		Thread.sleep(2000);

		File target = new File("target/event-javascript-test.log");
		assertTrue("Log file exists: ", target.exists());
		String content = context.getTypeConverter().convertTo(String.class,target);
		LOG.info(content);
		String expectedContent = "{\"name\":\"hello\",\"tags\":[\"red\",\"green\",\"blue\"],\"properties\":{\"hello\":\"world\",\"mylist\":[\"yellow\",\"magenta\",\"cyan\"]},\"source\":{\"ref\":\"localhost\",\"properties\":{\"song_list\":[\"Red Barchetta\",\"Freewill\",\"La Villa Strangiato\"]}}}";
		assertEquals(expectedContent, content);
	}
}