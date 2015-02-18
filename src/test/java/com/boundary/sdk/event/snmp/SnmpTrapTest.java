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
package com.boundary.sdk.event.snmp;

import static org.junit.Assert.*;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringTestSupport;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.VariableBinding;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.List;

import com.boundary.sdk.event.snmp.SmiSupport;
import com.snmp4j.smi.SmiManager;
import com.snmp4j.smi.SmiObject;

public class SnmpTrapTest extends CamelSpringTestSupport {
	
    private static SmiManager smiManager;
	@EndpointInject(uri = "mock:out")
    private MockEndpoint out;
    
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
    	SmiSupport smi = new SmiSupport();
    	smi.setLicense(System.getenv("BOUNDARY_MIB_LICENSE"));
    	smi.setRepository(System.getenv("BOUNDARY_MIB_REPOSITORY"));
    	smi.initialize();
    	smi.loadModules();
    	smiManager = smi.getSmiManager();

	}
	
	@Before
    public void setUp() throws Exception {
		super.setUp();
    }

    
    private SendTrap SendTrapDefault() {
		SendTrap sendTrap = new SendTrap();
		sendTrap.setUpTime(1000000L);
		sendTrap.setHost("localhost");
		sendTrap.setPort(1162);
		sendTrap.setDescription("Sample Trap");
		return sendTrap;
    }
    
    private SnmpTrap getTrap() {
		List<Exchange> exchanges = out.getExchanges();
		SnmpTrap trap = exchanges.get(0).getIn().getBody(SnmpTrap.class);
    	return trap;
    }

	@Test
	public void testTrap() throws InterruptedException, IOException {
		out.expectedMessageCount(1);
		SendTrap sendTrap = SendTrapDefault();
		sendTrap.send();
		out.assertIsSatisfied();
		
		List<Exchange> exchanges = out.getExchanges();
		SnmpTrap trap = exchanges.get(0).getIn().getBody(SnmpTrap.class);
		assertNotNull(trap);
		assertEquals("check trap name","linkDown",trap.getTrapName());
	}

	@Ignore("Broken Test, lookup fails in RMON-MIB")
	@Test
	public void TestRisingAlarm() throws InterruptedException {
		out.expectedMessageCount(1);
		
		SmiObject notification = smiManager.findSmiObject("RMON-MIB","rmon.rmonEventsV2.risingAlarm");
		assertNotNull("check for null SmiObject",notification);
		OID oid = notification.getOID();
		SendTrap sendTrap = SendTrapDefault();
		sendTrap.addVariableBinding(new VariableBinding(oid,new OctetString("Rising Alarm")));
		
		out.assertIsSatisfied();
		
		SnmpTrap trap = getTrap();
		assertNotNull(trap);
		assertEquals("check trap name","risingAlarm",trap.getTrapName()); 
		
	}

	@Override
	protected AbstractApplicationContext createApplicationContext() {
		return new ClassPathXmlApplicationContext("META-INF/spring/test-snmp-trap.xml");
	}

}
