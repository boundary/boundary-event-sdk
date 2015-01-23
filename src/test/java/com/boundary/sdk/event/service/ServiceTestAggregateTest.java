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
package com.boundary.sdk.event.service;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.SERVICE_CHECK_REQUEST_ID;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.sdk.event.service.ping.PingServiceModel;
import com.boundary.sdk.event.service.port.PortServiceModel;


/**
 *
 */
public class ServiceTestAggregateTest extends CamelSpringTestSupport  {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}
	
	@Ignore
	@Test
	public void testSplitRequest() throws InterruptedException {
		MockEndpoint endPoint = getMockEndpoint("mock:service-check-request-split");
		endPoint.setExpectedMessageCount(3);
		
		Map<String,Object> properties = new HashMap<String,Object>();
		ServiceCheckRequest request = new ServiceCheckRequest();
		properties.put(SERVICE_CHECK_REQUEST_ID, request.getRequestId());
		PingConfiguration configuration = new PingConfiguration();
		PingServiceModel model = new PingServiceModel();
		configuration.setHost("localhost");
		request.addServiceTest(new ServiceTest<PingConfiguration,PingServiceModel>(
				"ping","ping","localhost",request.getRequestId(),configuration,model));
		request.addServiceTest(new ServiceTest<PingConfiguration,PingServiceModel>(
				"ping","ping","localhost",request.getRequestId(),configuration,model));
		request.addServiceTest(new ServiceTest<PingConfiguration,PingServiceModel>(
				"ping","ping","localhost",request.getRequestId(),configuration,model));
		
		template.sendBodyAndHeaders("direct:service-check-request-in",request, properties);
		
		endPoint.assertIsSatisfied();
	}
	
	private void sendServiceRequest() {
		
		ServiceCheckRequest request = new ServiceCheckRequest();
		PingConfiguration pingConfig1 = new PingConfiguration();
		PingConfiguration pingConfig2 = new PingConfiguration();
		PortConfiguration portConfig = new PortConfiguration();
		PingServiceModel pingModel1 = new PingServiceModel();
		PingServiceModel pingModel2 = new PingServiceModel();
		PortServiceModel portModel = new PortServiceModel();
		
		pingConfig1.setHost("localhost");
		pingConfig2.setHost("google.com");
		portConfig.setHost("google.com");
		portConfig.setPort(81);
		
		request.addServiceTest(new ServiceTest<PingConfiguration,PingServiceModel>(
				"ping","ping","localhost",request.getRequestId(),pingConfig1,pingModel1));
		request.addServiceTest(new ServiceTest<PortConfiguration,PortServiceModel>(
				"port","port","Google Web Search",request.getRequestId(),portConfig,portModel));
		request.addServiceTest(new ServiceTest<PingConfiguration,PingServiceModel>(
				"ping","ping","Google Web Search",request.getRequestId(),pingConfig2,pingModel2));
		
		template.sendBody("direct:service-check-request-in",request);
	}

	@Test
	public void testAggregate() throws InterruptedException {
//		MockEndpoint endPoint = getMockEndpoint("mock:service-test-correlation-out");
		MockEndpoint pingEndPoint = getMockEndpoint("mock:ping-event-out");
		MockEndpoint portEndPoint = getMockEndpoint("mock:port-event-out");
		pingEndPoint.await(20, TimeUnit.SECONDS);
		portEndPoint.await(20, TimeUnit.SECONDS);

//		endPoint.setExpectedMessageCount(1);
		portEndPoint.setExpectedMessageCount(1);
		pingEndPoint.setExpectedMessageCount(2);
		
		sendServiceRequest();
		
//		endPoint.assertIsSatisfied();
		pingEndPoint.assertIsSatisfied();
		portEndPoint.assertIsSatisfied();
	}
	
	@Ignore
	@Test
	public void testPingCheck() throws InterruptedException {
		MockEndpoint endPoint = getMockEndpoint("mock:ping-out");
		endPoint.setExpectedMessageCount(1);
		
		PingConfiguration configuration = new PingConfiguration();
		
		configuration.setHost("localhost");
		
		template.sendBody("seda:ping-check", configuration);
		
		endPoint.assertIsSatisfied();
	}
	
	@Ignore
	@Test
	public void testServiceCheckRouter() throws InterruptedException {
		MockEndpoint endPoint = getMockEndpoint("mock:service-checks-router-out");
		endPoint.setExpectedMessageCount(3);
		
		sendServiceRequest();
		
		//endPoint.assertIsSatisfied();
	}
	
	@Ignore
	@Test
	public void testServiceCheckRoutePing() throws InterruptedException {
		MockEndpoint endPoint = getMockEndpoint("mock:ping-out");
		endPoint.setExpectedMessageCount(3);
		endPoint.await(20, TimeUnit.SECONDS);
		
		sendServiceRequest();
		
		endPoint.assertIsSatisfied();
	}


	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/service-check-aggregate-test.xml");
	}

}
