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
import java.util.Calendar;
import java.util.Calendar.Builder;
import java.util.Date;
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

import com.boundary.sdk.event.snmp.SnmpPollerConfiguration;
import com.boundary.sdk.event.snmp.entry;
import com.boundary.sdk.metric.Measurement;
import com.boundary.sdk.snmp.metric.Oid;

import static com.boundary.sdk.event.script.ScriptTestUtils.*;

public class SnmpToMeasureTest extends CamelSpringTestSupport {

	@Produce(uri = "direct:in")
	private ProducerTemplate in;

	@EndpointInject(uri = "mock:out")
	private MockEndpoint out;

	@Test
	public void test() throws InterruptedException {
		String expectedOid = "1.3.6.1.2.1.1.3.0";
		Number expectedValue = 2929358;
		String expectedSource = "www.myweb.com";
		String expectedMetricId = "MY_FAVORITE_METRIC";
		
		SnmpPollerConfiguration config = new SnmpPollerConfiguration();
		config.setCommunityRead("foobar");
		config.setDelay(30);
		config.setPort(1161);
		config.setHost(expectedSource);
		Oid oid = new Oid();
		oid.setMetricId(expectedMetricId);
		oid.setOid(expectedOid);
		config.addOid(oid);

		// Build Date of 2014-12-31 13:13:13
		Builder builder = new Calendar.Builder();
		builder.setDate(2014, 11, 31);
		builder.setTimeOfDay(13,13,13);
		Date expectedTimestamp = builder.build().getTime();
		entry entry = new entry();
		entry.setOid(expectedOid);
		entry.setValue(expectedValue.toString());
		
		out.expectedMessageCount(1);
		in.sendBodyAndHeaders(entry,setScriptHeaders("classpath:META-INF/js/snmp-to-measure.js",config));
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		assertEquals("check exchange count",1,exchanges.size());
		Exchange exchange = exchanges.get(0);
		Message message = exchange.getIn();
		Measurement m = message.getBody(Measurement.class);
		assertNotNull("check event for null",m);
		assertEquals("check source",expectedSource,m.getSource());
		assertEquals("check metric",expectedMetricId,m.getMetric());
		assertEquals("check measure",entry.getValue(),Integer.toString(m.getMeasure().intValue()));
		assertEquals("check timestamp",expectedTimestamp,m.getTimestamp());
		System.out.println(m);
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(
				"META-INF/spring/test-snmp-to-measure.xml");
	}

}
