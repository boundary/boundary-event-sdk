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
package com.boundary.sdk.metric;

import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MeasurementToJSONTest extends CamelSpringTestSupport {
	
	private final String DEFAULT_JSON="{\"source\":\"\",\"metric\":\"\",\"measure\":0}";
	
    @Produce(uri = "direct:measure-in")
    private ProducerTemplate producerTemplate;
    
    @EndpointInject(uri = "mock:json-out")
    private MockEndpoint mockOut;

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/measure-to-json-test.xml");
	}

	@Test
	public void testSetSource() throws Exception {
		mockOut.setExpectedMessageCount(1);
		Measurement measurement = new Measurement();
		
		measurement.setSource("1");	
		producerTemplate.sendBody(measurement);
		assertMockEndpointsSatisfied();
	}
	
	@Test
	public void testDefaultJSON() throws Exception {
		mockOut.setExpectedMessageCount(1);
		Measurement measurement = new Measurement();
		
		producerTemplate.sendBody(measurement);
		
		List<Exchange> exchanges = mockOut.getExchanges();
		
		for (Exchange exchange : exchanges) {
			assertEquals("check for default JSON",DEFAULT_JSON,exchange.getIn().getBody(String.class));
		}
	}
	
	@Test
	public void testPayload() {
		
	}
}
