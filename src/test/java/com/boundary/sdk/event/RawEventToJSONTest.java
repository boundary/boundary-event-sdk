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
package com.boundary.sdk.event;

import java.io.File;
import java.util.ArrayList;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Test;
import org.slf4j.Logger;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.RawEvent;

public class RawEventToJSONTest extends CamelSpringTestSupport {
	
    @Produce(uri = "direct:in")
    private ProducerTemplate producerTemplate;

    @EndpointInject(uri = "mock:out")
    private MockEndpoint mockOut;

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/boundary-event-to-json.xml");
	}

	@Test
	public void testSetTitle() throws Exception {
		mockOut.setExpectedMessageCount(1);
		RawEvent event = new RawEvent();
		
		event.setTitle("Sample Event");		
		producerTemplate.sendBody(event);
		assertMockEndpointsSatisfied();
	}
	
	@Test
	public void testEmptyJSON() throws Exception {
		mockOut.setExpectedMessageCount(1);
		RawEvent event = new RawEvent();
		
		producerTemplate.sendBody(event);

		mockOut.equals("{}");
	}
}