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
package com.boundary.sdk.event.exec;

import static com.boundary.sdk.event.notification.NotificationTestUtil.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

import com.boundary.sdk.event.notification.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.apache.camel.component.exec.ExecBinding.*;

public class ExecTest extends CamelSpringTestSupport {
		
    @Produce(uri = "direct:exec-in")
    private ProducerTemplate in;

    @EndpointInject(uri = "mock:exec-out")
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
	public void testProducerExec() throws InterruptedException, IOException {
		String echoString = "foobar";
		out.setExpectedMessageCount(1);
		Map<String,Object> headers = new HashMap<String,Object>();
		List<String> args = new ArrayList<String>();
		args.add("-n");
		args.add(echoString);
		
		headers.put(EXEC_COMMAND_EXECUTABLE,"echo");
		headers.put(EXEC_COMMAND_ARGS,args);
		
		in.sendBodyAndHeaders("",headers);

		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			String body = message.getBody(String.class);
			assertEquals("output is not equal",echoString,body);
		}
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/exec-test.xml");
	}
}
