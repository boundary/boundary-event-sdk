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

import java.io.IOException;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.RawEvent;

/**
 * Tests for SNMP Trap handling route builder
 *
 */
public class SnmpTrapRouteBuilderTest extends CamelSpringTestSupport {

    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;

	@Ignore
	@Test
	public void testBindAddress() {
		String expectedBindAddress = "1.2.3.4";
		SnmpTrapRouteBuilder builder = new SnmpTrapRouteBuilder();
		builder.setBindAddress(expectedBindAddress);
		assertEquals("check bind address", expectedBindAddress,
				builder.getBindAddress());
	}
	@Ignore
	@Test
	public void testMibRepository() {
		String expectedPath = "foobar";
		SnmpTrapRouteBuilder builder = new SnmpTrapRouteBuilder();

		builder.setMibRepository(expectedPath);
		assertEquals("Check license", expectedPath, builder.getMibRepository());
	}

	@Ignore
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
		Exchange exchange = exchanges.get(0);
		Message in = exchange.getIn();
		RawEvent event = in.getBody(RawEvent.class);
		assertNotNull("check for null",event);
		
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-snmp-trap-route.xml");
	}
}
