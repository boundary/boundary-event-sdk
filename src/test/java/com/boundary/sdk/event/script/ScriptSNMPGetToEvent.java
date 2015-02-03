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
package com.boundary.sdk.event.script;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.Test;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.snmp.entry;
import com.boundary.sdk.event.snmp.snmp;

import static com.boundary.sdk.event.script.ScriptTestUtils.*;

public class ScriptSNMPGetToEvent extends CamelSpringTestSupport {

	@Produce(uri = "direct:in")
	private ProducerTemplate in;

	@EndpointInject(uri = "mock:out")
	private MockEndpoint out;

	@Test
	public void test() throws InterruptedException {
		entry entry = new entry();
		entry.setOid("1.3.6.1.2.1.1.3.0");
		entry.setValue("2929358");
		
		out.expectedMessageCount(1);
		in.sendBodyAndHeaders(entry,setScriptHeader("classpath:META-INF/js/snmp-to-measure.js"));
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		assertEquals("check exchange count",1,exchanges.size());
		Exchange exchange = exchanges.get(0);
		Message message = exchange.getIn();
		RawEvent e = message.getBody(RawEvent.class);
		assertNotNull("check event for null",e);
		System.out.println(e);
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(
				"META-INF/spring/test-snmp-to-measure.xml");
	}

}
