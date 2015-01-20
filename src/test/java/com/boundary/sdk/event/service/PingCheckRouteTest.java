/**
 * 
 */
package com.boundary.sdk.event.service;

import static org.junit.Assert.*;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author davidg
 *
 */
public class PingCheckRouteTest extends CamelSpringTestSupport{

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
	
	@Ignore("Not Implemented")
	@Test
	public void testPing() throws InterruptedException {

		MockEndpoint endPoint = getMockEndpoint("mock:out");
		endPoint.setExpectedMessageCount(1);

		endPoint.assertIsSatisfied();
	}
	
	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/ping-check-test.xml");
	}

}
