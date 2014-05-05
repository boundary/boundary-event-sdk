/**
 * 
 */
package com.boundary.sdk.event.snmp;

import java.io.File;
import java.util.Vector;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.snmp.SnmpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.PDUv1;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.VariableBinding;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;


/**
 * Implements {@link Processor} interface and is responsible for converting
 * a {@link PDU} to a {@link RawEvent}.
 * 
 * @author davidg
 * 
 */
public class SNMPToEventProcessor implements Processor {
	
    private static final Logger LOG = LoggerFactory.getLogger(SNMPToEventProcessor.class);
    
    SmiSupport smi;
	
    /**
     * 
     * @param repositoryPath Path to compiled MIBs
     * @param license SNMP4J-SMI license from <a href="http://www.snmp4j.org">http://www.snmp4j.org</a>
     */
	public SNMPToEventProcessor(String repositoryPath,String license) {
		smi = new SmiSupport();
		
		smi.setLicense(license);
		File mibDirectory = new File(repositoryPath);
		smi.setRepository(mibDirectory.getAbsolutePath());
		smi.initialize();
		smi.loadModules();
	}

	/**
	 * Returns the current path to the MIB repository
	 * 
	 * @return {@link String}
	 */
	public String getMibRepository() {
		return smi.getRepository();
	}
	
	/*
	 * Returns the current SNMP4J-SMI license
	 */
	public String getLicense() {
		return smi.getLicense();
	}
	
	public void processV2Traps(PDU pdu, RawEvent event) {
		
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		String specificTrap="";
		
		Message message = exchange.getIn();
		SnmpMessage snmpMessage = message.getBody(SnmpMessage.class);
		PDU pdu = snmpMessage.getSnmpMessage();
		
		LOG.debug("PDU: " + pdu);
		
		// Create our event so that we can populate with the Syslog data
		RawEvent event = new RawEvent();
		
		// Get the type of trap received either: TRAP (v2) or TRAPV1 (v1)
		String trapVersion = PDU.getTypeString(pdu.getType()) == "TRAP" ? "v2c" : "v1";
		event.addProperty("trapVersion", trapVersion);
		
        // Extract SNMPv1 specific variables
        if (pdu.getType() == PDU.V1TRAP) {
            PDUv1 v1pdu = (PDUv1) pdu;
            event.addProperty("enterprise", v1pdu.getEnterprise().toString());
            event.addFingerprintField("enterprise");
            event.addProperty("agent-addr", v1pdu.getAgentAddress().toString());
            event.addProperty("generic-trap", Integer.toString(v1pdu.getGenericTrap()));
            specificTrap = Integer.toString(v1pdu.getSpecificTrap());
            event.addProperty("trap",specificTrap);
            event.addProperty("time-stamp", Long.toString(v1pdu.getTimestamp()));
        }

		
		// Get the variable bindings from the trap and create properties in the event
		Vector<? extends VariableBinding> varBinds = pdu.getVariableBindings();
		for (VariableBinding var : varBinds) {
			OID oid = var.getOid();
			if (oid.startsWith(SnmpConstants.snmpTraps) ||
				oid.startsWith(SnmpConstants.snmpTrapOID)) {
				specificTrap = var.toValueString();
				event.addProperty("trap",specificTrap);
				event.setMessage(var.toValueString());
			}
			else {
				event.addProperty(var.getOid().toString(),var.toValueString());
				event.addFingerprintField(var.getOid().toString());
			}
		}
		
		event.addFingerprintField("trap");
		
		event.addProperty("error_status", pdu.getErrorStatusText());
		
		// Address is in the form of X.X.X.X/port if IPV4
		String [] s = snmpMessage.getHeader("peerAddress").toString().split("/");
		String hostname = s[0];
		event.addProperty("hostname",hostname);
		event.addFingerprintField("hostname");

		event.setTitle(specificTrap + " trap received from " + hostname);
				
		//TBD, set the severity based on content of the trap
		event.setSeverity(Severity.WARN);
		
		//TBD Set status based on severity??
		event.setStatus(Status.OPEN);

		event.getSource().setRef(hostname);
		event.getSource().setType("host");
		
		LOG.debug("RawEvent: " + event);
		
		// Assign the RawEvent to the body
		message.setBody(event, RawEvent.class);
	}
}
