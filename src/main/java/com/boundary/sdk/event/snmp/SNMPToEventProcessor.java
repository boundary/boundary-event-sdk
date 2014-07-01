/**
 * 
 */
package com.boundary.sdk.event.snmp;

import java.io.File;
import java.util.Date;
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
    
    private final static String HOSTNAME_PROPERTY_NAME = "hostname";
    private final static String SENDER_REF = "Boundary Event SDK";
    private final static String SENDER_TYPE = "Boundary Event SDK";
	
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
	
	/**
	 * Converts an {@link SnmpMessage}/{@link PDUv1} into a {@link RawEvent}
	 * 
	 * @param message - Contains a {@link SnmpMessage}
	 * @param pdu - Contains a {@link PDUv1} with the actual trap contents
	 * @param event - {@link RawEvent} to be populated.
	 */
	public void processV1Trap(SnmpMessage message, PDU pdu, RawEvent event) {
        PDUv1 v1pdu = (PDUv1) pdu;
		String hostname = getPeerAddress(message);

		// CREATED_AT - Set from timestamp on the PDU
        event.setCreatedAt(new Date());
		
		// FINGERPRINT_FIELDS
		event.addFingerprintField(HOSTNAME_PROPERTY_NAME);
        event.addFingerprintField("enterprise");
		
		// MESSAGE - TBD: Set based on the content of the trap?
		
		// ORGANIZATION_ID - TBD: Override based on content of trap?
		
		// PROPERTIES
		event.addProperty(HOSTNAME_PROPERTY_NAME, hostname);
        event.addProperty("enterprise", v1pdu.getEnterprise().toString());
        event.addProperty("agent-addr", v1pdu.getAgentAddress().toString());
        event.addProperty("generic-trap", Integer.toString(v1pdu.getGenericTrap()));
        event.addProperty("trap",Integer.toString(v1pdu.getSpecificTrap()));
        event.addProperty("time-stamp", Long.toString(v1pdu.getTimestamp()));

		// RECEIVED_AT - Default to value set by Boundary
		
		// SENDER - Defaults to the Boundary Event SDK
		event.getSender().setRef(SENDER_REF).setType(SENDER_TYPE);
		
		// SEVERITY - TBD: Set severity based on content of trap?
		event.setSeverity(Severity.WARN);
		
		// SOURCE
		event.getSource().setRef(hostname).setType("host");
		
		//STATUS - TBD: Depends on trap fields?
		event.setStatus(Status.OPEN);
		
		// TAGS
		event.addTag(hostname);
        
        // TITLE
		event.setTitle(Integer.toString(v1pdu.getSpecificTrap()) + " trap received from " + hostname);
	}
	
	/**
	 * Converts an {@link SnmpMessage}/{@link PDU} into a {@link RawEvent}
	 * 
	 * @param message - Contains a {@link SnmpMessage}
	 * @param pdu - Contains a {@link PDU} with the actual trap contents
	 * @param event - {@link RawEvent} to be populated.
	 */
	public void processV2Trap(SnmpMessage message, PDU pdu, RawEvent event) {
		String specificTrap = "";
		String hostname = getPeerAddress(message);

		
		// CREATED_AT
		event.setCreatedAt(new Date());

		// FINGERPRINT_FIELDS
		event.addFingerprintField(HOSTNAME_PROPERTY_NAME);
		
		// ORGANIZATION_ID - TBD: Override based on content of the PDU?
		
		// PROPERTIES
		event.addProperty(HOSTNAME_PROPERTY_NAME, hostname);
		event.addProperty("error_status", pdu.getErrorStatusText());
		
		// Get the variable bindings from the trap and create properties in the event
		Vector<? extends VariableBinding> varBinds = pdu.getVariableBindings();
		for (VariableBinding var : varBinds) {
			OID oid = var.getOid();
			if (oid.startsWith(SnmpConstants.snmpTraps) ||
				oid.startsWith(SnmpConstants.snmpTrapOID)) {
				specificTrap = var.toValueString();
				event.addProperty("trap",specificTrap);
				// MESSAGE
				event.setMessage(var.toValueString());
			}
			else {
				event.addProperty(var.getOid().toString(),var.toValueString());
				event.addFingerprintField(var.getOid().toString());
			}
		}
		
		// RECEIVED_AT - Default to value set by Boundary
		
		// SENDER
		event.getSender().setRef(SENDER_REF).setType(SENDER_TYPE);

		// SEVERITY - TBD: set the severity based on content of the trap
		event.setSeverity(Severity.WARN);

		// SOURCE
		event.getSource().setRef(hostname).setType("host");
		
		//STATUS - TBD: Set status based on severity??
		event.setStatus(Status.OPEN);

		// TAGS
		event.addTag(hostname);
		
		// TITLE
		event.setTitle(specificTrap + " trap received from " + hostname);
	}
	
	/**
	 * Help function to get the peer address from an {@link SnmpMessage}.
	 * 
	 * @param message
	 * @return String
	 */
	private String getPeerAddress(SnmpMessage message) {
		String [] s = message.getHeader("peerAddress").toString().split("/");
		String address = s[0];
		return address;
	}
	
	/**
	 * Handles the translation of a {@link SnmpMessage} to {@link RawEvent}
	 * 
	 */
	@Override
	public void process(Exchange exchange) throws Exception {
		// Extract the SnmpMessage and PDU instances from the Camel Exchange
		Message message = exchange.getIn();
		SnmpMessage snmpMessage = message.getBody(SnmpMessage.class);
		PDU pdu = snmpMessage.getSnmpMessage();

		// Create our event so that we can populate with SNMP data.
		RawEvent event = new RawEvent();

		// Dispatch based on PDU type of the trap
		// TBD: Handling of v3 PDU traps
		if (pdu.getType() == PDU.V1TRAP) {
			processV1Trap(snmpMessage, pdu, event);
		}
		else {
			processV2Trap(snmpMessage, pdu, event);
		}

		// Dump out the contents of our event for debugging purposes
		LOG.debug("RawEvent: " + event);

		// Assign the RawEvent to the body
		message.setBody(event, RawEvent.class);
	}
}
