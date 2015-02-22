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
package com.boundary.sdk.event.service.db;

import java.util.concurrent.TimeUnit;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assume;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class ServiceDatabaseTest extends CamelSpringTestSupport  {

	/**
	 * @throws {@link Exception}
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	/**
	 * @throws {@link Exception}
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws {@link Exception}
	 */
	@Before
	public void setUp() throws Exception {
		super.setUp();
		Assume.assumeTrue(DatabaseIsConnected.databaseConnected());
	}

	/**
	 * @throws {@link Exception}
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
