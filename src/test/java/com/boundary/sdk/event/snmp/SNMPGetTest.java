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

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author davidg
 *
 */
public class SNMPGetTest extends CamelSpringTestSupport  {
	
    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;


	@Test
	public void testSnmpGet() throws InterruptedException {
//		out.await(8,TimeUnit.SECONDS);
		out.setMinimumExpectedMessageCount(1);
		
		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		for (Exchange exchange : exchanges) {
			assertNotNull("Body is null",exchange.getIn().getBody());
			System.out.println(exchange.getIn().getBody());
		}
		
	}
	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/snmp-collector.xml");
	}
}
