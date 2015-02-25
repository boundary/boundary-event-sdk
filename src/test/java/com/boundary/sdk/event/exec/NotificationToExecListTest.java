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
package com.boundary.sdk.event.exec;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.notification.AlarmNotification;

public class NotificationToExecListTest extends CamelSpringTestSupport {
	
	private final static String NOTIFICATION_FILE = "META-INF/json/multi-triggered-notification.json";
	
    @Produce(uri = "direct:in")
    private ProducerTemplate in;
    
    @EndpointInject(uri = "mock:out")
    private MockEndpoint out;


	@Test
	public void testLoad() throws URISyntaxException {
		AlarmNotification notification = AlarmNotification.load(NOTIFICATION_FILE);
		assertNotNull("check notification",notification);

	}
	
	@Test
	public void test() throws URISyntaxException, InterruptedException {
		AlarmNotification notification = AlarmNotification.load(NOTIFICATION_FILE);
		String currentDirectory = new File("").getAbsolutePath();

		out.expectedMessageCount(1);
		
		in.sendBody(notification);
		
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		Exchange exchange = exchanges.get(0);
		Message message = exchange.getIn();
		ExecList execList = message.getBody(ExecList.class);
		assertNotNull("check for null",execList);
		List<Exec> execs = execList.getExecList();
		assertEquals("Check size",3,execs.size());
		assertNotNull(execs);
		Exec exec1 = execs.get(0);
		assertEquals("Check executable","echo",exec1.getExecutable());
		assertEquals("Check arguments","Alarm for server: boundary-snmp-001",exec1.getArgs());
		assertEquals("Check working directory",currentDirectory,exec1.getWorkingDirectory());
	}


	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-notification-to-exec-list.xml");
	}

}
