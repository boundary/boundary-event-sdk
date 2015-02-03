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

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spi.Registry;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class WebhookRouteBuilderTest extends CamelSpringTestSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(WebhookRouteBuilderTest.class);

    @EndpointInject(uri = "mock:webhook-out")
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
	public void testNotification() throws InterruptedException, IOException {
		String body = readFile(NOTIFICATION_JSON,Charset.defaultCharset());

		out.setExpectedMessageCount(1);
		
		// Get the url from the sprint DSL file by
		// 1) Getting the context
		// 2) Getting the registry
		// 3) Lookup the bean
		// 4) Call method to get url
		// 5) Strip out component name, "jetty:"
		CamelContext context = out.getCamelContext();
		Registry registry = context.getRegistry();
		WebHookRouteBuilder builder = (WebHookRouteBuilder)registry.lookupByName("webhook-route-builder");
		assertNotNull("builder is null",builder);
		String url = null;
		url = builder.getFromUri();
		url = url.replaceFirst("jetty:","");
		LOG.debug("url {}",url);
		
		// Send an HTTP request with the JSON paylod that is sent
		// from a Boundary Event Notification
        HttpClient httpclient = new HttpClient();
        PostMethod httppost = new PostMethod(url);
        Header contentHeader = new Header("Content-Type", "application/json");
        httppost.setRequestHeader(contentHeader);
        StringRequestEntity reqEntity = new StringRequestEntity(body,null,null);
        httppost.setRequestEntity(reqEntity);
        
        // Check our reponse status
        int status = httpclient.executeMethod(httppost);
        assertEquals("Received wrong response status", 200, status);
		
        // Assert the mock end point
		out.assertIsSatisfied();
		
		// Get
		List<Exchange> exchanges = out.getExchanges();
		LOG.debug("EXCHANGE COUNT: " + exchanges.size());
		for (Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			Object o = message.getBody();
			LOG.debug("class: " + o.getClass().toString());
			Notification notif = message.getBody(Notification.class);
			validateNotification(notif);
		}
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/webhook-route-builder-test.xml");
	}
}
