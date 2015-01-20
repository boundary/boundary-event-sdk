/**
 * 
 */
package com.boundary.sdk.event.service.db;

import static org.junit.Assert.*;

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

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;


/**
 * @author davidg
 *
 */
public class ServiceDatabaseTest extends CamelSpringTestSupport  {

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
	
	@Test
	public void testDatabase() throws InterruptedException {
		MockEndpoint endPoint = getMockEndpoint("mock:service-db-out");
		endPoint.setExpectedMessageCount(1);

		endPoint.await(60, TimeUnit.SECONDS);
		endPoint.assertIsSatisfied();
	}
	

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/service-check.xml");
	}

}
