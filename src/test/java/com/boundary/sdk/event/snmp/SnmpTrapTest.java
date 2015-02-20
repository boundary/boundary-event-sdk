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

import java.util.Vector;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.VariableBinding;

import com.boundary.sdk.event.snmp.SnmpTrap.SnmpVersion;

import java.util.Vector;

public class SnmpTrapTest {
	
	private static SnmpScript snmpScript = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		snmpScript = new SnmpScript();
		snmpScript.load();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testConstructor() {
		SnmpTrap trap = new SnmpTrap();
		assertEquals("check toString()",
				"SnmpTrap [variableBindings=[], trapName=, host=localhost, version=V2C, raw=true]",
				trap.toString());
	}

	@Test
	public void testGetVariableBindings() {
		SnmpTrap trap = new SnmpTrap();
		Vector<VariableBinding> varBinds = new Vector<VariableBinding>();
		varBinds.add(new VariableBinding(SnmpConstants.linkUp,new OctetString("Network link is now up")));
		varBinds.add(new VariableBinding(SnmpConstants.sysUpTime,new TimeTicks(100000L)));
		trap.setVariableBindings(varBinds);
		assertTrue("check getVariableBindings()",varBinds.containsAll(trap.getVariableBindings()));
	}

	@Test
	public void testGetVarBindInt() {
		SnmpTrap trap = new SnmpTrap();
		Vector<VariableBinding> varBinds = new Vector<VariableBinding>();
		varBinds.add(new VariableBinding(SnmpConstants.linkUp,new OctetString("Network link is now up")));
		varBinds.add(new VariableBinding(SnmpConstants.sysUpTime,new TimeTicks(100000L)));
		trap.setVariableBindings(varBinds);
		VariableBinding variableBinding = trap.getVarBind(0);
		assertEquals("check variableBinding","linkUp = Network link is now up",variableBinding.toString());
	}

	@Test
	public void testGetVarBindOID() {
		SnmpTrap trap = new SnmpTrap();
		Vector<VariableBinding> varBinds = new Vector<VariableBinding>();
		varBinds.add(new VariableBinding(SnmpConstants.linkUp,new OctetString("Network link is now up")));
		varBinds.add(new VariableBinding(SnmpConstants.sysUpTime,new TimeTicks(100000L)));
		trap.setVariableBindings(varBinds);
		VariableBinding variableBinding = trap.getVarBind(SnmpConstants.linkUp);
		assertEquals("check variableBinding","linkUp = Network link is now up",variableBinding.toString());

	}

	@Test
	public void testGetTrapName() {
		String expectedTrapName = "linkDown";
		SnmpTrap trap = new SnmpTrap();
		trap.setTrapName(expectedTrapName);
		assertEquals("check Trap name",expectedTrapName,trap.getTrapName());
	}

	@Test
	public void testGetHost() {
		String expectedHostNqme = "myHost";
		SnmpTrap trap = new SnmpTrap();
		trap.setHost(expectedHostNqme);
		assertEquals("check Host",expectedHostNqme,trap.getHost());
	}

	@Test
	public void testGetVersion() {
		SnmpTrap trap = new SnmpTrap();
		trap.setVersion(SnmpVersion.V3);
		assertEquals("check getVersion()",SnmpVersion.V3,trap.getVersion());
		trap.setVersion(SnmpVersion.V2C);
		assertEquals("check getVersion()",SnmpVersion.V2C,trap.getVersion());
		trap.setVersion(SnmpVersion.V1);
		assertEquals("check getVersion()",SnmpVersion.V1,trap.getVersion());
	}

	@Test
	public void testIsRaw() {
		SnmpTrap trap = new SnmpTrap();
		trap.setRaw(false);
		assertEquals("check isRaw()",false,trap.isRaw());
	}

	@Test
	public void testToString() {
		SnmpTrap trap = new SnmpTrap();
		String expectedString = "SnmpTrap [variableBindings=[coldStart = Hello], trapName=warmStart, host=10.10.10.10, version=V3, raw=false]";
		trap.setRaw(true);
		trap.setVersion(SnmpVersion.V3);
		Vector<VariableBinding> varBinds = new Vector<VariableBinding>();
		varBinds.add(new VariableBinding(SnmpConstants.coldStart, new OctetString("Hello")));
		trap.setVariableBindings(varBinds);
		trap.setTrapName("warmStart");
		trap.setHost("10.10.10.10");
		trap.setRaw(false);
		assertEquals("check toString()",expectedString,trap.toString());
	}

}
