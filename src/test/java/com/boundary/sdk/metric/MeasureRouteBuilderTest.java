package com.boundary.sdk.metric;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class MeasureRouteBuilderTest extends CamelTestSupport {
	
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
	@Ignore
	@Test
	public void testGetUrl() {
		
	}
	
	@Test
	public void testSendMetric() throws InterruptedException {
		Measurement measurement = new Measurement();
		measurement.setSource("26");
		measurement.setMetric("jdg_sample");
		measurement.setMeasure(100);
		out.setExpectedMessageCount(1);
		in.sendBody(measurement);
		
		out.await(20, TimeUnit.SECONDS);
		
		out.assertIsSatisfied();
	}
	
	@Ignore
	@Test
	public void testRouteURI() {
		MeasureRouteBuilder route = new MeasureRouteBuilder();
		assertEquals("get URL",DEFAULT_URI,route.getUrl());	
	}

	@Override
	protected RouteBuilder createRouteBuilder() throws Exception {
		MeasureRouteBuilder route = new MeasureRouteBuilder();
		
		route.setFromUri("direct:measure-in");
		route.setToUri("mock:measure-out");
		route.setUser(System.getenv("BOUNDARY_METRICS_EMAIL"));
		route.setPassword(System.getenv("BOUNDARY_METRICS_TOKEN"));

		return route;
	}
}
