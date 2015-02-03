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
package com.boundary.sdk.event.esper;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.List;
import java.util.TimeZone;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Service;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.Resource;


public class EsperRouteTest extends CamelSpringTestSupport {
	
	
	
	
//    @Produce(uri = "direct:in")
//    private ProducerTemplate producerTemplate;
//
//    @EndpointInject(uri = "mock:out")
//    private MockEndpoint mockOut;

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-esper-route.xml");
	}
	
	@Test
	public void testReadURIClasspath() throws IOException, URISyntaxException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL url = classLoader.getResource("test-esper-query-load.json");
		File file = new File(url.toURI());
		assertTrue("check if file",file.isFile());
		assertTrue("check file exists",file.exists());
	}
	
	@Test
	public void testConfigurationProperty() {
		String expectedPath = "foo.xml";
		EsperRouteBuilder builder = new EsperRouteBuilder();
		builder.setConfiguration(expectedPath);
		assertEquals("testQueriesProperty",expectedPath,builder.getConfiguration());
	}
	
	@Test
	public void testInstanceProperty() {
		String expectedInstance = "event";
		EsperRouteBuilder builder = new EsperRouteBuilder();
		builder.setInstance(expectedInstance);
		assertEquals("testInstanceProperty",expectedInstance,builder.getInstance());
	}

	@Ignore
	@Test
	public void testQueryListLoad() throws Exception {
		EsperRouteBuilder builder = new EsperRouteBuilder();
		builder.setConfiguration("test-esper-route-query-load.json");
		builder.loadConfiguration();
		QueryList queryList = builder.getQueryList();
		List<Query> qlist = queryList.getQueries();
		assertEquals("test query list count",3,queryList.getQueries().size());
		
		Query query1 = qlist.get(0);
		Builder build = new Calendar.Builder();
		build.setTimeZone(TimeZone.getTimeZone("GMT"));
		build.setDate(2015,0,27);
		build.setTimeOfDay(0, 0, 0, 0);
		Calendar cal = build.build();
		assertEquals("check query 1 name","red",query1.getName());
		assertEquals("check query 1 create",cal.getTime(),query1.getCreated());
		assertEquals("check query 1 enabled",true,query1.isEnabled());
		assertEquals("check query 1 description","Red description",query1.getDescription());
		assertEquals("check query 1 query",
				"select * from Red where name = 'ONE' and property = 2",query1.getQuery());
	}
	
	@Test
	public void testEmptyJSON() throws Exception {
//		mockOut.setExpectedMessageCount(1);
//		RawEvent event = new RawEvent();
//		
//		producerTemplate.sendBody(event);
//
//		mockOut.equals("{}");
	}
}