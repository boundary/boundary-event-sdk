// Copyright 2014 Boundary, Inc.
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

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.boundary.sdk.util.TestUtil.*;

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
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SNMPEntryTest extends CamelSpringTestSupport  {
	
    @Produce(uri = "direct:split-in")
    private ProducerTemplate in;
	
    @EndpointInject(uri = "mock:split-out")
    private MockEndpoint out;

    @Produce(uri = "direct:snmp-in")
    private ProducerTemplate snmp_in;
	
    @EndpointInject(uri = "mock:snmp-out")
    private MockEndpoint snmp_out;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

//	@Ignore
//	@Test
//	public void testSNMPEntry() throws InterruptedException, IOException {
//		out.setMinimumExpectedMessageCount(1);
//		
//		String s = readFile("src/test/resources/snmp/snmp-entry.xml",Charset.defaultCharset());
//		in.sendBody(s);
//		out.assertIsSatisfied();
//		
//		List<Exchange> exchanges = out.getExchanges();
//		for (Exchange exchange : exchanges) {
//			assertNotNull("Body is null",exchange.getIn().getBody());
//		}
//	}

	@Test
	public void testSNMP() throws InterruptedException, IOException {
		snmp_out.setMinimumExpectedMessageCount(1);
		
		String s = readFile("src/test/resources/snmp/snmp-entry.xml",Charset.defaultCharset());
		snmp_in.sendBody(s);
		snmp_out.assertIsSatisfied();
		
		List<Exchange> exchanges = snmp_out.getExchanges();
		for (Exchange exchange : exchanges) {
			assertNotNull("Body is null",exchange.getIn().getBody());
		}
	}
	
	@Test
	public void testSplit() throws InterruptedException, IOException {
		out.setMinimumExpectedMessageCount(2);
		
		String s = readFile("src/test/resources/snmp/snmp-entry.xml",Charset.defaultCharset());
		in.sendBody(s);
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		for (Exchange exchange : exchanges) {
			assertNotNull("Body is null",exchange.getIn().getBody());
		}
	}


	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/snmp-entry-test.xml");
	}
}
