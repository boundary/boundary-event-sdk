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
package com.boundary.sdk.event.notification;

import static com.boundary.sdk.event.notification.NotificationTestUtil.NOTIFICATION_JSON;
import static com.boundary.sdk.event.notification.NotificationTestUtil.readFile;
import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.Route;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.model.FromDefinition;
import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.Registry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebhookNotificationTest extends CamelSpringTestSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(WebhookNotificationTest.class);

    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	// TODO: TEST IS BROKEN
	@Test
	public void testNotification() throws InterruptedException, IOException {
		String body = readFile(NOTIFICATION_JSON,Charset.defaultCharset());
		out.setExpectedMessageCount(1);
		
		CamelContext context = context();
		RouteDefinition routeDefinition = context.getRouteDefinition("WEBHOOK-TEST");

		assertNotNull("RouteDefinition is null",routeDefinition);
		List<FromDefinition> inputs = routeDefinition.getInputs();
		FromDefinition from = inputs.get(0);
		String uri = from.getEndpointUri();
		uri = uri.replaceFirst("jetty:","");
		LOG.debug("uri: {}", uri);

		// Send HTTP notification
        HttpClient httpclient = new HttpClient();
        PostMethod httppost = new PostMethod(uri);
        Header contentHeader = new Header("Content-Type", "application/json");
        httppost.setRequestHeader(contentHeader);
        StringRequestEntity reqEntity = new StringRequestEntity(body,null,null);
        httppost.setRequestEntity(reqEntity);
        int status = httpclient.executeMethod(httppost);

        assertEquals("Received wrong response status", 200, status);

		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		LOG.debug("EXCHANGE COUNT: {}",exchanges.size());
		for (Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			String messageBody = message.getBody(String.class);
			Object o = message.getBody();
			LOG.debug("class: " + o.getClass().toString());
			LOG.debug("messageBody: " + messageBody);
			LOG.debug("id: " + exchange.getExchangeId());
			//assertEquals("Body not equal",body,messageBody);
		}
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/webhook-notification-test.xml");
	}
}
