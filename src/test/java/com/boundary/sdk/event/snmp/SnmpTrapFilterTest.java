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

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SnmpTrapFilterTest extends CamelSpringTestSupport {

	@Produce(uri = "direct:in")
	private ProducerTemplate in;

	@EndpointInject(uri = "mock:out")
	private MockEndpoint out;
	
	private SendTrap trap;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		trap = new SendTrap();
		trap.setPort(1162);
	}

	@Ignore
	@Test
	public void test() throws InterruptedException, IOException {
		out.expectedMessageCount(1);
		trap.send();
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		assertEquals("check size",1,exchanges.size());
		
	}
	
	@Test
	public void testWarmStart() throws InterruptedException, IOException {
		out.expectedMessageCount(1);
		out.await(5,TimeUnit.SECONDS);
		trap.send();
		SendTrap trap1 = new SendTrap();
		trap1.setPort(1162);
		trap1.addVariableBinding(new VariableBinding(SnmpConstants.warmStart, new OctetString("Host has been restarted")));
		trap1.send();
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		assertEquals("check size",1,exchanges.size());
		for (Exchange exchange : exchanges) {
			SnmpTrap trap = exchange.getIn().getBody(SnmpTrap.class);
		}
		
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-snmp-trap-filter.xml");
	}
}
