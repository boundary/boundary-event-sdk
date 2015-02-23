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

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.boundary.sdk.event.snmp.SendTrap.TrapVersion;

public class SendTrapTest {

	@Test
	public void testSendTrap() throws IOException {
		SendTrap trap = new SendTrap();
		trap.send();
	}

	@Test
	public void testSetUpTime() {
		long expectedUpTime = 938L;
		SendTrap trap = new SendTrap();
		trap.setUpTime(expectedUpTime);
		assertEquals("check setUpTime()",expectedUpTime,trap.upTime);
	}

	@Test
	public void testSetDescription() {
		String expectedDescription = "hello";
		SendTrap trap = new SendTrap();
		trap.setDescription(expectedDescription);
		assertEquals("check setDescription()",expectedDescription,trap.description);
	}

	@Test
	public void testSetCommunity() {
		String expectedCommunity = "hello";
		SendTrap trap = new SendTrap();
		trap.setCommunity(expectedCommunity);
		assertEquals("check setCommunity()",expectedCommunity,trap.community);
	}

	@Test
	public void testSetHost() {
		String expectedHost = "myhost";
		SendTrap trap = new SendTrap();
		trap.setHost(expectedHost);
		assertEquals("check setCommunity()",expectedHost,trap.host);
	}

	@Test
	public void testSetPort() {
		int expectedPort = 333;
		SendTrap trap = new SendTrap();
		trap.setPort(expectedPort);
		assertEquals("check setPort()",expectedPort,trap.port);
	}
	
	@Test
	public void testSetVersion() {
		TrapVersion expectedVersion = TrapVersion.V3;
		SendTrap trap = new SendTrap();
		trap.setVersion(expectedVersion);
		assertEquals("check setVersion()",expectedVersion,trap.version);
	}
}
