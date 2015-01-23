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

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.sdk.event.service.ping.PingServiceModel;
import com.boundary.sdk.event.service.port.PortServiceModel;

public class ServiceCheckRouterTest extends CamelSpringTestSupport {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Ignore
	@Test
	public void test() {
		MockEndpoint mock = getMockEndpoint("mock:service-checks-router-out");
	}

	@Ignore
	@Test
	public void testGetEndPointsFromRequest() {
		ServiceCheckRouter router = new ServiceCheckRouter();
		ServiceCheckRequest request = new ServiceCheckRequest();
		PingConfiguration pingConfiguration = new PingConfiguration();
		PingServiceModel pingModel = new PingServiceModel();
		PortConfiguration portConfiguration = new PortConfiguration();
		PortServiceModel portModel = new PortServiceModel();
		
		ServiceTest<PingConfiguration,PingServiceModel> pingTest = new ServiceTest<PingConfiguration,PingServiceModel>(
				"ping","ping","localhost",request.getRequestId(),pingConfiguration,pingModel);
		ServiceTest<PortConfiguration,PortServiceModel> portTest = new ServiceTest<PortConfiguration,PortServiceModel>(
				"port","port","localhost",request.getRequestId(),portConfiguration,portModel);
		
		request.addServiceTest(pingTest);
		request.addServiceTest(portTest);
		
//		String endPoints = router.getEndPointsFromRequest(request);
//		
//		assertEquals("check test names","ping,port",endPoints);
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		// TODO Auto-generated method stub
		return null;
	}
}
