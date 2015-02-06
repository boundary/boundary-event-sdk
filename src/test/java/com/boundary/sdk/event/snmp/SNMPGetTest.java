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
import org.apache.camel.Header;
import org.apache.camel.Message;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.metric.MeasureRouteBuilder;
import com.boundary.sdk.metric.Measurement;


/**
 * @author davidg
 *
 */
public class SNMPGetTest extends CamelSpringTestSupport  {
	
	private static Logger LOG = LoggerFactory.getLogger(SNMPGetTest.class);
	
    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;

	@Test
	public void testSnmpGet() throws InterruptedException {
		out.await(10,TimeUnit.SECONDS);
		out.setMinimumExpectedMessageCount(1);
		
		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		for (Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			Object o = message.getBody();
			assertNotNull("Check for null body",o);
			LOG.info("Body class: {}",o.getClass().toString());
			LOG.info("Body content: {}",o.toString());

			Number httpCode = message.getHeader(Exchange.HTTP_RESPONSE_CODE,Number.class);
			assertNotNull("check for null result code",httpCode);
			assertEquals("check HTTP result code",200,httpCode);
		}
		
	}
	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-snmp-collector.xml");
	}
}
