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
package com.boundary.sdk.event.service.port;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.Ignore;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;

/**
 * @version $Revision$
 */
public class PortCheckTest extends CamelSpringTestSupport {

        // TODO: Resolve issue with test referencing the ServiceTest
	@Ignore("TEST BROKEN WITH NULL POINTER")
	@Test
	public void testPort() throws Exception {
		MockEndpoint out = getMockEndpoint("mock:out");
		out.setExpectedMessageCount(1);

		assertMockEndpointsSatisfied();
	}
	
        // TODO: Resolve issue with test referencing the ServiceTest
	@Ignore("TEST BROKEN WITH NULL POINTER")
	@Test
	public void testConnectionRefused() throws InterruptedException {
		MockEndpoint mock = getMockEndpoint("mock:out");
		mock.setExpectedMessageCount(1);

		mock.assertIsSatisfied();
		
		List<Exchange> receivedExchanges = mock.getReceivedExchanges();
		for (Exchange e : receivedExchanges) {
			RawEvent event = e.getIn().getBody(RawEvent.class);

			assertNotNull(event);
			assertEquals("check severity",Severity.CRITICAL, event.getSeverity());
			assertEquals("check status",Status.OPEN,event.getStatus());
		}
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/port-test.xml");
	}
}
