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
package com.boundary.sdk.event.service.ssh;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE;
import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_INSTANCE;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.camel.component.ssh.SshxResult;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;

public class SSHCheckToEventProcessorTest extends CamelSpringTestSupport  {
	
    @EndpointInject(uri = "mock:measure-out")
    private MockEndpoint endPoint;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	//TODO: Mock SSH Server
	//@Ignore("No Mock SSH Server")
	@Test
	public void testSSHCheckToEventProcessor() {
		
		// Our "real" output and expected output
		String output = "lerma";
		String expectedOutput = output;
		
		// Define our command which is just echo the output
		String command = "echo " + output;

		// Constants that define our service test and the host to run them against.
		String serviceName = "SDN";
		String testName = "SDN check";
		String hostName = "abc.def.com";

		// Create the standard out and standard error InputStreams and then assign
		// our result instance
		InputStream out = new ByteArrayInputStream(output.getBytes(Charset.forName("UTF-8")));
		InputStream err = new ByteArrayInputStream("".getBytes(Charset.forName("UTF-8")));
		SshxResult result = new SshxResult("echo " + output, 0, out, err);
		
		// Create instance of our service check to generate a unique request id
		ServiceCheckRequest request = new ServiceCheckRequest();
		
		// Create instance of ssh configuration to populate.
		SshxConfiguration config = new SshxConfiguration();
		SshxServiceModel model = new SshxServiceModel();
		
		config.setHost(hostName);
		config.setCommand(command);
		model.setExpectedOutput(expectedOutput);
		
		ServiceTest<SshxConfiguration,SshxServiceModel> serviceTest = new ServiceTest<SshxConfiguration,SshxServiceModel>(
				testName,"ssh",serviceName,request.getRequestId(),config,model);
		request.addServiceTest(serviceTest);
		
		Map<String,Object> properties = new HashMap<String,Object>();
		properties.put(SERVICE_CHECK_REQUEST_INSTANCE, request);
		properties.put(SERVICE_TEST_INSTANCE, serviceTest);
		
		template.sendBodyAndHeaders("direct:in",result,properties);
		
		List<Exchange> receivedExchanges = endPoint.getReceivedExchanges();
		
		for (Exchange e : receivedExchanges) {
			RawEvent event = e.getIn().getBody(RawEvent.class);

			assertNotNull(event);
			assertEquals("check source ref",hostName,event.getSource().getRef());
			assertEquals("check severity",Severity.INFO, event.getSeverity());
			assertEquals("check status",Status.CLOSED,event.getStatus());
		}
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/ssh-to-event-test.xml");
	}

}
