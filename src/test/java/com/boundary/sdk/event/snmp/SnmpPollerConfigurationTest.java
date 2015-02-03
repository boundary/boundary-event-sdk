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

import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.sdk.snmp.metric.Oid;

public class SnmpPollerConfigurationTest {

	@Test
	public void testSnmpPollerConfiguration() {
		SnmpPollerConfigurationTest config = new SnmpPollerConfigurationTest();
	}

	@Test
	public void testAddOid() {
		SnmpPollerConfiguration config = new SnmpPollerConfiguration();
		Oid oid = new Oid();
		oid.setOid("1.3.6.1.2.1.25.1.5.0");
		config.addOid(oid);
	}

	@Test
	public void testGetOidsSize() {
		SnmpPollerConfiguration config = new SnmpPollerConfiguration();
		Oid oid1 = new Oid();
		oid1.setOid("1.3.6.1.2.1.25.1.5.0");
		config.addOid(oid1);
		Oid oid2 = new Oid();
		oid2.setOid("1.3.6.1.2.1.25.1.5.0");
		config.addOid(oid2);
		
		List<Oid> set = config.getOids();
		assertEquals("check set count",1,set.size());
		assertEquals("check getOidsAsString","1.3.6.1.2.1.25.1.5.0",config.getOidsAsString());
	}
	
	@Test
	public void testDuplicateOids() {
		String expectedOidString = "1.3.6.1.2.1.25.1.5.0,1.3.6.1.2.1.25.1.6.0,1.3.6.1.2.1.6.9.0,1.3.6.1.2.1.7.1.0,1.3.6.1.2.1.7.4.0,1.3.6.1.2.1.6.10.0,1.3.6.1.2.1.6.11.0,1.3.6.1.2.1.4.3.0,1.3.6.1.2.1.4.10.0";
		SnmpPollerConfiguration config = new SnmpPollerConfiguration();
		
		Oid oid1 = new Oid();
		oid1.setOid("1.3.6.1.2.1.25.1.5.0");
		config.addOid(oid1);
		Oid oid2 = new Oid();
		oid2.setOid("1.3.6.1.2.1.25.1.6.0");
		config.addOid(oid2);
		Oid oid3 = new Oid();
		oid3.setOid("1.3.6.1.2.1.6.9.0");
		config.addOid(oid3);
		Oid oid4 = new Oid();
		oid4.setOid("1.3.6.1.2.1.7.1.0");
		config.addOid(oid4);
		Oid oid5 = new Oid();
		oid5.setOid("1.3.6.1.2.1.7.4.0");
		config.addOid(oid5);
		Oid oid6 = new Oid();
		oid6.setOid("1.3.6.1.2.1.6.10.0");
		config.addOid(oid6);
		Oid oid7 = new Oid();
		oid7.setOid("1.3.6.1.2.1.6.11.0");
		config.addOid(oid7);
		Oid oid8 = new Oid();
		oid8.setOid("1.3.6.1.2.1.4.3.0");
		config.addOid(oid8);
		Oid oid9 = new Oid();
		oid9.setOid("1.3.6.1.2.1.4.10.0");
		config.addOid(oid9);

		List<Oid> set = config.getOids();
		assertEquals("check set count",9,set.size());
		assertEquals("check getOidsAsString",expectedOidString,config.getOidsAsString());
	}

	@Test
	public void testCommunity() {
		SnmpPollerConfiguration config = new SnmpPollerConfiguration();
		config.setCommunity("foobar");
		assertEquals("check community","foobar",config.getCommunityRead());
	}
	
	@Test
	public void testDelay() {
		SnmpPollerConfiguration config = new SnmpPollerConfiguration();
		config.setDelay(30);
		assertEquals("check delay",30,config.getDelay());
	}
}
