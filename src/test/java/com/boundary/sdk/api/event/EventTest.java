package com.boundary.sdk.api.event;

import static org.junit.Assert.*;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.sdk.event.BoundaryEventRouteBuilder;

public class EventTest {

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

	@Test
	public void test() {
		// fail("Not yet implemented");
	}

	public static void main(String args[]) throws InterruptedException {
		Event event = new Event();
		event.start();
		Thread.sleep(30000);
		event.setTitle("Standalone Event");
		event.getSource().setRef("localhost").setType("host");
		event.addFingerprintField("@title");
		event.send();
	}

}
