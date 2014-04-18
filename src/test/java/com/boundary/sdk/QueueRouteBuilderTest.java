package com.boundary.sdk;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueueRouteBuilderTest {

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
	public void testQueueName() {
		String expectedQueueName = "foobar";
		QueueRouterBuilder q = new QueueRouterBuilder();
		
		q.setQueueName(expectedQueueName);
		
		assertEquals("Check queue name",expectedQueueName,q.getQueueName());
	}

}
