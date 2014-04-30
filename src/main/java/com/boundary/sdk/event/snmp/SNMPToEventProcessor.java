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
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Message message = exchange.getIn();
		SnmpMessage snmpMessage = message.getBody(SnmpMessage.class);
		PDU pdu = snmpMessage.getSnmpMessage();
		
		LOG.debug("PDU: {}",pdu);
		
		// Create our event so that we can populate with the Syslog data
		RawEvent event = new RawEvent();
		
		// Get the type of trap received either: TRAP (v2) or TRAPV1 (v1)
		String trapType = PDU.getTypeString(pdu.getType());
		
		// Get the variable bindings from the trap and create properties in the event
		Vector<? extends VariableBinding> varBinds = pdu.getVariableBindings();
		for (VariableBinding var : varBinds) {
			event.addProperty(var.getOid().toString(),var.toValueString());
			event.addFingerprintField(var.getOid().toString());
		}
		event.addProperty("error_status", pdu.getErrorStatusText());
		
		// Address is in the form of X.X.X.X/port if IPV4
		String hostname = snmpMessage.getHeader("peerAddress").toString();

		//TBD, Set title using the trap type and hostname
		event.setTitle(trapType + " from " + hostname);
		
		event.addProperty("type", trapType == "TRAP" ? "v2c" : "v1");
		
		//TBD, What should the message field be set to by default??
		event.setMessage(pdu.getErrorStatusText());
		
		//TBD, set the severity based on content of the trap
		event.setSeverity(Severity.WARN);
		
		//TBD Set status based on severity??
		event.setStatus(Status.OPEN);

		event.getSource().setRef(hostname);
		event.getSource().setType("host");
		
		LOG.debug("RawEvent: {}",event);
		
		// Assign the RawEvent to the body
		message.setBody(event, RawEvent.class);
	}
}
