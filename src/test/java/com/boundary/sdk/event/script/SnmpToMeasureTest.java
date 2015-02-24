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
import org.snmp4j.smi.OID;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.UnsignedInteger32;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.boundary.sdk.event.snmp.SnmpPollerConfiguration;
import com.boundary.sdk.event.snmp.entry;
import com.boundary.sdk.metric.Measurement;
import com.boundary.sdk.snmp.metric.OidMap;

import static com.boundary.sdk.event.script.ScriptTestUtils.*;

public class SnmpToMeasureTest extends CamelSpringTestSupport {
	
	private final static String SNMP_TO_MEASURE_SCRIPT = "classpath:META-INF/js/lib/snmp-to-measure.js";

	@Produce(uri = "direct:in")
	private ProducerTemplate in;

	@EndpointInject(uri = "mock:out")
	private MockEndpoint out;
	
	private SnmpPollerConfiguration getSnmpPollerConfiguration(String expectedSource,
			String expectedMetricId,String expectedOid) {
		SnmpPollerConfiguration config = new SnmpPollerConfiguration();
		config.setCommunityRead("foobar");
		config.setDelay(30);
		config.setPort(1161);
		config.setHost(expectedSource);
		OidMap oid = new OidMap();
		oid.setMetricId(expectedMetricId);
		oid.setOid(expectedOid);
		config.addOid(oid);
		
		return config;
	}
	
	private Measurement getMeasurement(MockEndpoint out) {
		List<Exchange> exchanges = out.getExchanges();
		assertEquals("check exchange count",1,exchanges.size());
		Exchange exchange = exchanges.get(0);
		Message message = exchange.getIn();
		Measurement m = message.getBody(Measurement.class);
		return m;
	}

	@Test
	public void testTimeTickVariable() throws InterruptedException {
		String expectedOid = "1.3.6.1.2.1.1.3.0";
		Number expectedValue = 2929358;
		String expectedSource = "www.myweb.com";
		String expectedMetricId = "MY_FAVORITE_METRIC";
		
		VariableBinding vb = new VariableBinding();
		TimeTicks v = new TimeTicks();
		v.setValue(expectedValue.longValue());
		
		vb.setOid(new OID(expectedOid));
		vb.setVariable(v);

		SnmpPollerConfiguration config = getSnmpPollerConfiguration(
				expectedSource,expectedMetricId,expectedOid);
		out.expectedMessageCount(1);
		in.sendBodyAndHeaders(vb,setScriptHeaders(SNMP_TO_MEASURE_SCRIPT,config));
		out.assertIsSatisfied();
		
		Measurement m = getMeasurement(out);
		assertNotNull("check event for null",m);
		assertEquals("check source",expectedSource,m.getSource());
		assertEquals("check metric",expectedMetricId,m.getMetric());
		assertEquals("check measure",v.getValue(),m.getMeasure().intValue());
	}
	
	@Test
	public void testCounter32Variable() throws InterruptedException {
		String expectedOid = "1.3.6.1.2.1.4.3.0";
		Number expectedValue = 1588007;
		String expectedSource = "www.myweb.com";
		String expectedMetricId = "MY_FAVORITE_METRIC";
		
		VariableBinding vb = new VariableBinding();
		Counter32 v = new Counter32();
		v.setValue(expectedValue.longValue());
		
		vb.setOid(new OID(expectedOid));
		vb.setVariable(v);

		SnmpPollerConfiguration config = getSnmpPollerConfiguration(
				expectedSource,expectedMetricId,expectedOid);
		out.expectedMessageCount(1);
		in.sendBodyAndHeaders(vb,setScriptHeaders(SNMP_TO_MEASURE_SCRIPT,config));
		out.assertIsSatisfied();
		
		Measurement m = getMeasurement(out);
		assertNotNull("check event for null",m);
		assertEquals("check source",expectedSource,m.getSource());
		assertEquals("check metric",expectedMetricId,m.getMetric());
		assertEquals("check measure",v.getValue(),m.getMeasure().intValue());
	}
	
	@Test
	public void testGauge() {
		
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext(
				"META-INF/spring/test-snmp-to-measure.xml");
	}
}
