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
package com.boundary.sdk.metric;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.AuthMethod;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.http.HttpConfiguration;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MeasureRouteBuilderTest extends CamelSpringTestSupport {
	
	private static Logger LOG = LoggerFactory.getLogger(MeasureRouteBuilderTest.class);
	
	private final static String DEFAULT_URI="https://metrics-api.boundary.com/v1/post/measurements";

    @Produce(uri = "direct:measure-in")
    private ProducerTemplate in;

    @EndpointInject(uri = "mock:measure-out")
    private MockEndpoint out;


	public void setUp() throws Exception {
		super.setUp();
	}

	public void tearDown() throws Exception {
		super.tearDown();
	}

//	@Test
//	public void testGetScheme() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetOrgId() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetUser() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetPassword() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetPort() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testGetHost() {
//		fail("Not yet implemented");
//	}
//
	//@Ignore("BROKEN: Does not send BASIC authentication")
	@Test
	public void testSendMetric() throws InterruptedException {
		Measurement measurement = new Measurement();
		measurement.setSource("26");
		measurement.setMetric("API_RESPONSE_TIME");
		measurement.setMeasure(100);
		out.setExpectedMessageCount(1);
		in.sendBody(measurement);
		
		out.await(20, TimeUnit.SECONDS);
		
		out.assertIsSatisfied();
	}
	

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/measure-route-test.xml");
	}
}
