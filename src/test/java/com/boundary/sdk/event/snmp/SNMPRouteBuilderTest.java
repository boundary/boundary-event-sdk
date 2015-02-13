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

import static com.boundary.sdk.util.TestUtil.readFile;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 */
public class SNMPRouteBuilderTest extends CamelSpringTestSupport {
	
    @Produce(uri = "direct:in")
    private ProducerTemplate in;
	
    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;


	@Test
	public void testBindAddress() {
		String expectedBindAddress = "1.2.3.4";
		SnmpTrapRouteBuilder builder = new SnmpTrapRouteBuilder();
		builder.setBindAddress(expectedBindAddress);
		assertEquals("check bind address", expectedBindAddress,builder.getBindAddress());
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
	public void testSnmpGet() throws InterruptedException {
		
		out.setMinimumExpectedMessageCount(0);
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
//		Exchange exchange = exchanges.get(0);
		assertEquals(0,exchanges.size());
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(
				"META-INF/spring/test-snmp-poller-route.xml");
	}

}
