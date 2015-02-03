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
package com.boundary.sdk.event.notification;

import static com.boundary.sdk.event.notification.NotificationTestUtil.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 *
 */
public class JsonToNotificationTest extends CamelSpringTestSupport {
		
    @Produce(uri = "direct:json-in")
    private ProducerTemplate in;

    @EndpointInject(uri = "mock:notification-out")
    private MockEndpoint out;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Test
	public void testReadFile() throws IOException {
		String resource = readFile(NOTIFICATION_BASIC_JSON,Charset.defaultCharset());
		assertNotNull("Output is null",resource);
	}

	@Test
	public void testSimpleJsonToNotification() throws IOException {
		String expectedContents="Notification [event=null, filterId=e3c045ec-8028-48ce-9373-93e5b01c690c, filterName=Pester Michael about Critical events, notificationId=4ba705f6-690c-4877-b041-791b84e1e032]";
		String s = readFile(NOTIFICATION_BASIC_JSON,Charset.defaultCharset());
		ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
		Notification notification = mapper.readValue(s, Notification.class);
		System.out.println("notification: " + notification);
		assertEquals("Notification output incorrect",expectedContents,notification.toString());
	}

	@Test
	public void testUnMarshallingJsonSimple() throws InterruptedException, IOException {
		out.setExpectedMessageCount(1);
		String s = readFile(NOTIFICATION_BASIC_JSON,Charset.defaultCharset());
		in.sendBody(s);
		out.await(5,TimeUnit.SECONDS);
		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			Notification notif = message.getBody(Notification.class);
			assertEquals("filterId does not match","e3c045ec-8028-48ce-9373-93e5b01c690c",notif.getFilterId());
			assertEquals("filterName does not match","Pester Michael about Critical events",notif.getFilterName());
		    assertEquals("notificationId does not match","4ba705f6-690c-4877-b041-791b84e1e032",notif.getNotificationId());
		}
	}
	@Test
	public void testUnMarshallingJsonComplete() throws InterruptedException, IOException {
		out.setExpectedMessageCount(1);
		String s = readFile(NOTIFICATION_JSON,Charset.defaultCharset());
		in.sendBody(s);
		out.await(5,TimeUnit.SECONDS);
		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			Notification notif = message.getBody(Notification.class);
			validateNotification(notif);
		}
	}
	
	@Test
	public void testUnMarshallingJsonFull() throws InterruptedException, IOException {
		out.setExpectedMessageCount(1);
		String s = readFile(NOTIFICATION_FULL_JSON,Charset.defaultCharset());
		in.sendBody(s);
		out.await(5,TimeUnit.SECONDS);
		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			Notification notif = message.getBody(Notification.class);
			validateNotification(notif);
		}
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/json-to-notification-test.xml");
	}
}
