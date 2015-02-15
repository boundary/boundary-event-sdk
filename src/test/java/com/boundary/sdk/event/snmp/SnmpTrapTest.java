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

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

public class SnmpTrapTest extends CamelSpringTestSupport {
	
    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;


	@Test
	public void test() throws InterruptedException, IOException {
		out.expectedMessageCount(1);
		SendTrap sendTrap = new SendTrap();
		sendTrap.setUpTime(1000000L);
		sendTrap.setHost("localhost");
		sendTrap.setPort(1162);
		sendTrap.setDescription("Sample Trap");
		sendTrap.send();
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		SnmpTrap trap = exchanges.get(0).getIn().getBody(SnmpTrap.class);
		assertNotNull(trap);
		assertEquals("check trap name","linkDown",trap.getTrapName());
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-snmp-trap.xml");
	}

}
