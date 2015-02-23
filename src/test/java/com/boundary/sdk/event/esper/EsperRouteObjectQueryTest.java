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
package com.boundary.sdk.event.esper;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class EsperRouteObjectQueryTest extends CamelSpringTestSupport {
	
	
    @Produce(uri = "direct:in")
    private ProducerTemplate in;

    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;

	@Override
	protected AbstractXmlApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-esper-object-queries.xml");
	}
	
	private void sendEvents() {
		Employee employee = null;
		Address address = null;
		NewEmployeeEvent event = null;
		
		employee = new Employee("Bob");
		event = new NewEmployeeEvent(employee);
		in.sendBody(event);
		
		address = new Address(740,"Park Avenue");
		employee = new Employee("Ted");
		employee.addAddress("home", address);
		event = new NewEmployeeEvent(employee);
		in.sendBody(event);
		
		address = new Address(32,"Wall Street");
		employee = new Employee("Alice");
		employee.addAddress("work", address);
		event = new NewEmployeeEvent(employee);
		in.sendBody(event);
		
	}
	
	private NewEmployeeEvent getEvent() {
		List<Exchange> exchanges = out.getExchanges();
		NewEmployeeEvent event = null;

		if (!exchanges.isEmpty()) {
			Exchange exchange = exchanges.get(0);
			Message message = exchange.getIn();
			event = message.getBody(NewEmployeeEvent.class);
		}
		return event;
	}
	
	private int getEventCount() {
		List<Exchange> exchanges = out.getExchanges();
		return exchanges.size();
	}
	
	@Test
	public void testStringField() throws InterruptedException {
		out.expectedMessageCount(1);
		sendEvents();
		out.assertIsSatisfied();
	}
	
	@Test
	public void testArrayField() throws InterruptedException {
		out.expectedMessageCount(1);
		sendEvents();
		out.await(5,TimeUnit.SECONDS);
		NewEmployeeEvent event = getEvent();
		assertEquals("check count",1,getEventCount());
		assertEquals("check name","Ted",event.getName());
	}
	
	@Test
	public void testLongField() throws InterruptedException {
		out.expectedMessageCount(1);
		sendEvents();
		out.assertIsSatisfied();
	}

}