package com.boundary.sdk.event.snmp;

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
	
	private PDU pdu;
	
	private long timeTicks;
	private String description;

	public SendTrap() {
		this.pdu = new PDU();
		pdu.setType(PDU.TRAP);

	}
	
	void setUpTime(long timeTicks) {
		this.timeTicks = timeTicks;
	}
	
	void setDescription(String description) {
		this.description = description;
	}
	
	public static void main(String[] args) throws Exception {
	      // Create PDU           
	      PDU trap = new PDU();
	      trap.setType(PDU.TRAP);

	      trap.add(new VariableBinding(SnmpConstants.linkDown, new OctetString("Host has been restarted")));
	      trap.add(new VariableBinding(SnmpConstants.sysUpTime, new TimeTicks(5000))); // put your uptime here, hundredths of a second
	      trap.add(new VariableBinding(SnmpConstants.sysDescr, new OctetString("Test TRAP")));         

	      // TBD Make this configurable
	      Address targetaddress = new UdpAddress("localhost/1162");
	      CommunityTarget target = new CommunityTarget();
	      // TBD Make this configurable
	      target.setCommunity(new OctetString("public"));
	      // TBD make configurable
	      target.setVersion(SnmpConstants.version2c);
	      target.setAddress(targetaddress);

	      // Send
	      Snmp snmp = new Snmp(new DefaultUdpTransportMapping());
	      snmp.send(trap, target, null, null);                      
	}
}
