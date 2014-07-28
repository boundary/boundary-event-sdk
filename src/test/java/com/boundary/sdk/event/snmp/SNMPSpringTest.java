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

import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Route;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.snmp.SnmpMessage;
import org.apache.camel.spi.RouteContext;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.smi.VariableBinding;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.notification.WebHookApplication;


/**
 * @author davidg
 *
 */
public class SNMPSpringTest extends CamelSpringTestSupport  {
	
	private static Logger LOG = LoggerFactory.getLogger(SNMPGetTest.class);

    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;

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
	
	//TODO: Configure Mock SSH Server for testing
	//@Ignore ("Need Mock SSH Server")
	@Test
	public void testSnmpGet() throws InterruptedException {
		out.setMinimumExpectedMessageCount(1);
		out.await(10,TimeUnit.SECONDS);
		out.assertIsSatisfied();
		
		CamelContext context = context();
		
		List<Exchange> exchanges = out.getExchanges();
		for (Exchange exchange : exchanges) {
			Message message = exchange.getOut();
			SnmpMessage snmpMessage = message.getBody(SnmpMessage.class);
			LOG.info("body: ",snmpMessage);
			assertNotNull("Body is null",snmpMessage);
			PDU pdu = snmpMessage.getSnmpMessage();
			for (Object o :  pdu.getVariableBindings()) {
				VariableBinding b = (VariableBinding)o;
				LOG.info("oid: {}, value: {}",b.getOid().toString(),b.getVariable().toString());
			}
		}
	}
	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/snmp-spring-test.xml");
	}
}
