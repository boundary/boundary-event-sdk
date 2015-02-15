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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SendTrap {
	
	private static Logger LOG = LoggerFactory.getLogger(SendTrap.class);	
	
	public enum TrapVersion {
		
		V1(SnmpConstants.version1),
		V2C(SnmpConstants.version2c),
		V3(SnmpConstants.version3);
		
		private int version;

		TrapVersion(int version) {
			this.version = version;
		}
		
	};
	
	protected final String DEFAULT_HOST = "localhost";
	protected final int DEFAULT_PORT = 162;
	protected final long DEFAULT_UP_TIME = 30 * 24 * 60 * 60; // 30 days
	protected final String DEFAULT_DESCRIPTION = "Test Trap!";
	protected final TrapVersion DEFAULT_TRAP_VERSION = TrapVersion.V2C;
	protected final String DEFAULT_COMMUNITY = "public";

	protected long upTime;
	protected String description;
	protected String community;
	protected String host;
	protected int port;
	protected TrapVersion version;
	protected Vector<VariableBinding> varBinds;

	public SendTrap() {
		this.host = DEFAULT_HOST;
		this.port = DEFAULT_PORT;
		this.upTime = DEFAULT_UP_TIME;
		this.description = DEFAULT_DESCRIPTION;
		this.version = DEFAULT_TRAP_VERSION;
		this.community = DEFAULT_COMMUNITY;
		this.varBinds = new Vector<VariableBinding>();
	}

	public void setUpTime(long timeTicks) {
		this.upTime = timeTicks;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setCommunity(String community) {
		this.community = community;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setVersion(TrapVersion version) {
		this.version = version;
	}
	
	private String getTargetAddress() {
		return String.format("%s/%s",this.host,this.port);
	}
	
	public void addVariableBinding(VariableBinding varBind) {
		this.varBinds.add(varBind);
	}
	
	public void addDefaultTrap() {
		this.varBinds.add(new VariableBinding(SnmpConstants.linkDown, new OctetString("Host has been restarted")));
		// put your uptime here, hundredths of a second
		this.varBinds.add(new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(this.upTime))); 
		this.varBinds.add(new VariableBinding(SnmpConstants.sysDescr, new OctetString(this.description)));
	}

	public void send() throws IOException {
		// Create PDU
		PDU trap = new PDU();
		trap.setType(PDU.TRAP);
		
		if (this.varBinds.size() == 0) {
			addDefaultTrap();
		}
		
		// Add the varbinds to the trap
		for (VariableBinding vb : this.varBinds) {
			trap.add(vb);
		}
		// Set our target
		Address targetaddress = new UdpAddress(getTargetAddress());
		CommunityTarget target = new CommunityTarget();
		// Set the community read string
		target.setCommunity(new OctetString(this.community));
		// Set the version of the trap
		target.setVersion(version.version);
		target.setAddress(targetaddress);
		LOG.info("trap: {}",trap);

		// Send the trap
		Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
		snmp.send(trap, target, null, null);
	}

	public static void main(String[] args) {
		try {
			SendTrap trap = new SendTrap();
			trap.send();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
