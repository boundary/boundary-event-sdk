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
package com.boundary.sdk.event.service.ssh;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * @author davidg
 *
 */
public class SSHTest extends CamelSpringTestSupport  {

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
	
	// TODO: Create a mock SSH to test these independent of the host we are running on
	@Ignore("UNABLE TO RUN WITHOUT STANDARD SSH MOCK OBJECT")
	@Test
	public void testSSH() throws InterruptedException, IOException {
		MockEndpoint endPoint = getMockEndpoint("mock:ssh-out");
		endPoint.setExpectedMessageCount(1);
		
		String cmd = "ls -l";
		template.sendBody("direct:ssh-in",cmd);
		
		endPoint.assertIsSatisfied();
		
		List<Exchange> list = endPoint.getExchanges();
		
		for (Exchange x: list) {
			Message m = x.getIn();
			InputStream out = m.getBody(InputStream.class);
			BufferedReader bufferedReader = null;

			bufferedReader = new BufferedReader(new InputStreamReader(out));
			List<String> lines = new ArrayList<String>();
			
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				lines.add(line);
			}
		}
	}
	
	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/ssh-test.xml");
	}

}
