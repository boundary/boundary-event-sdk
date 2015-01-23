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
package com.boundary.sdk.event.exec;

import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_ARGS;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_EXECUTABLE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ExecTest extends CamelSpringTestSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(ExecTest.class);
	
	private Map<String,Object> headers;
	private List<String> args;
		
    @Produce(uri = "direct:exec-in")
    private ProducerTemplate in;
    
    @Produce(uri = "seda:exec-bean-in?concurrentConsumers=10")
    private ProducerTemplate exec_bean_in;

    @EndpointInject(uri = "mock:exec-out")
    private MockEndpoint out;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		headers = new HashMap<String,Object>();
		args = new ArrayList<String>();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	private void sendExec(ProducerTemplate in) {
		headers.put(EXEC_COMMAND_ARGS,args);
		in.sendBodyAndHeaders("",headers);
	}
	@Ignore
	@Test
	public void testSimpleExec() throws InterruptedException, IOException {
		String echoString = "foobar";
		out.setExpectedMessageCount(1);
		args.add("-n");
		args.add(echoString);
		
		headers.put(EXEC_COMMAND_EXECUTABLE,"echo");
		sendExec(in);

		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			String body = message.getBody(String.class);
			assertEquals("output is not equal",echoString,body);
		}
	}
	@Ignore
	@Test
	public void testSimpleExecBean() throws InterruptedException, IOException {
		String echoString = "foobar";
		out.setExpectedMessageCount(1);
		args.add("-n");
		args.add(echoString);
		
		headers.put(EXEC_COMMAND_EXECUTABLE,"echo");
		sendExec(in);

		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			String body = message.getBody(String.class);
			assertEquals("output is not equal",echoString,body);
		}
	}
	
	@Ignore
	@Test
	public void testMetricExec() throws InterruptedException, IOException {
		out.setExpectedMessageCount(1);
		args.add("192.168.178.101");
		args.add("jdg_sample");
		args.add("1000");
		
		headers.put(EXEC_COMMAND_EXECUTABLE,"metric-add");

		sendExec(in);

		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			String body = message.getBody(String.class);
			//assertEquals("output is not equal",body);
			LOG.info(body);
		}
	}
	@Ignore
	@Test
	public void testMetricExecBean() throws InterruptedException, IOException {
		Random random = new Random(1024L);

		out.setExpectedMessageCount(100);
		headers.put(EXEC_COMMAND_EXECUTABLE,"metric-add");

		for (int i = 100 ; i != 0 ; i--) {
			args.clear();
			args.add("192.168.178.101");
			args.add("jdg_sample");
			args.add(Integer.toString(random.nextInt(1000)));
			sendExec(in);
			Thread.sleep(100);
		}

		out.assertIsSatisfied();
		List<Exchange> exchanges = out.getExchanges();
		
		for(Exchange exchange : exchanges) {
			Message message = exchange.getIn();
			String body = message.getBody(String.class);
			//assertEquals("output is not equal",body);
			LOG.info(body);
		}
	}


	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/exec-test.xml");
	}
}
