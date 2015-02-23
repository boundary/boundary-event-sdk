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
package com.boundary.sdk.event.snmp;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Status;

/**
 * Tests for SNMP Trap handling route builder
 *
 */
public class SnmpTrapRouteBuilderTest extends CamelSpringTestSupport {

    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;

	@Test
	public void testBindAddress() {
		String expectedBindAddress = "1.2.3.4";
		SnmpTrapRouteBuilder builder = new SnmpTrapRouteBuilder();
		builder.setBindAddress(expectedBindAddress);
		assertEquals("check bind address", expectedBindAddress,
				builder.getBindAddress());
	}

	@Test
	public void testMibRepository() {
		String expectedPath = "foobar";
		SnmpTrapRouteBuilder builder = new SnmpTrapRouteBuilder();

		builder.setMibRepository(expectedPath);
		assertEquals("Check license", expectedPath, builder.getMibRepository());
	}

	@Test
	public void testLicense() {
		String expectedLicense = "foobar";
		SnmpTrapRouteBuilder builder = new SnmpTrapRouteBuilder();

		builder.setLicense(expectedLicense);
		assertEquals("Check license", expectedLicense, builder.getLicense());
	}

	@Test
	public void testRun() throws Exception {
		out.expectedMessageCount(1);
		SendTrap trap = new SendTrap();
		
		trap.setPort(1162);
		trap.send();
		
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		assertEquals("check exchange size",1,exchanges.size());
		Exchange exchange = exchanges.get(0);
		Message in = exchange.getIn();
		RawEvent event = in.getBody(RawEvent.class);
		assertNotNull("check for null",event);
		
		Source source = new Source();
		source.setRef("127.0.0.1");
		source.setType("host");
		Source sender = new Source();
		sender.setRef("Boundary Event SDK");
		sender.setType("Boundary Event SDK");
		Date now = new Date();
		
		assertEquals("check title","linkDown trap received from 127.0.0.1",event.getTitle());
		assertEquals("check message","Received linkDown trap",event.getMessage());
		assertEquals("check source",source,event.getSource());
		assertEquals("check sender",sender,event.getSender());
		long diff = Math.abs(now.getTime() - event.getCreatedAt().getTime());
		assertTrue("check createdAt",diff < 100);
		assertEquals("check status",Status.OPEN,event.getStatus());
		assertEquals("check severity",Severity.WARN,event.getSeverity());
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put("linkDown", "Host has been restarted");
		properties.put("sysUpTimeInstance", "7:12:00.00");
		properties.put("sysDescrip.0","Test Trap!");
		assertTrue("check properties forward",event.getProperties().values().containsAll(properties.values()));
		assertTrue("check properties backward",properties.values().containsAll(event.getProperties().values()));
		List<String> tags = new ArrayList<String>();
		tags.add("127.0.0.1");
		tags.add("linkDown");
		tags.add("raw");
		assertTrue("check tags forward",event.getTags().containsAll(tags));
		assertTrue("check tags backward",properties.values().containsAll(event.getProperties().values()));
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-snmp-trap-route.xml");
	}
}
