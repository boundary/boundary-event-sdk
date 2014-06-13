/**
 * 
 */
package com.boundary.sdk.event.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

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

/**
 * @author davidg
 *
 */
public class ServiceCheckTest extends CamelSpringTestSupport  {

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
	public void test() throws InterruptedException {
		MockEndpoint endPoint = getMockEndpoint("mock:out");

		endPoint.setExpectedMessageCount(3);
		Map<String,Object> properties = new HashMap<String,Object>();
		PingConfiguration configuration = new PingConfiguration();;
		
		properties.put("request", "foo");
		ServiceTest<PingConfiguration> test1 = new ServiceTest<PingConfiguration>("test1","999",configuration);
		ServiceTest<PingConfiguration> test2 = new ServiceTest<PingConfiguration>("test2","999",configuration);
		ServiceTest<PingConfiguration> test3 = new ServiceTest<PingConfiguration>("test3","999",configuration);

		
		template.sendBodyAndHeaders("direct:in",test1, properties);
		template.sendBodyAndHeaders("direct:in",test2, properties);
		template.sendBodyAndHeaders("direct:in",test3, properties);
		
		endPoint.assertIsSatisfied();
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/service-check.xml");
	}

}
