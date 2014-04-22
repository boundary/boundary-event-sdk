/**
 * 
 */
package com.boundary.sdk;

import java.io.File;
import java.util.Date;
import java.util.Vector;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.snmp.SnmpMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import com.boundary.sdk.RawEvent;
import com.boundary.sdk.Severity;
import com.boundary.sdk.event.snmp.SmiSupport;


/**
 * @author davidg
 * 
 */
public class SNMPToEventProcessor implements Processor {
	
    private static final Logger LOG = LoggerFactory.getLogger(SNMPToEventProcessor.class);
    
    SmiSupport smi;
    
    String mibRepositoryPath;
	
	public SNMPToEventProcessor(String mibRepositoryPath) {
		this.mibRepositoryPath = mibRepositoryPath;
		smi = new SmiSupport();
		smi.setLicense("a8 29 19 b4 66 e5 4c 1f / LlSFSvNS");
		File mibDirectory = new File("/Users/davidg/git_working/boundary-camel-example/snmp4j-smi-1.1.3/mibrepository");
		smi.setRepositoryPath(mibDirectory.getAbsolutePath());
		smi.initialize();
		smi.loadModules();
	}

	/**
	 * Returns the current path to the MIB repository
	 * 
	 * @return
	 */
	public String getMibRepositoryPath() {
		return this.mibRepositoryPath;
	}

	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		Message message = exchange.getIn();
		SnmpMessage snmpMessage = message.getBody(SnmpMessage.class);
		PDU pdu = snmpMessage.getSnmpMessage();
		
		// Create our event so that we can populate with the Syslog data
		RawEvent event = new RawEvent();
		
		// Get the type of trap received either: TRAP (v2) or TRAPV1 (v1)
		String trapType = PDU.getTypeString(pdu.getType());
		
		// Get the variable bindings from the trap and create properties in the event
		Vector<? extends VariableBinding> varBinds = pdu.getVariableBindings();
		for (VariableBinding var : varBinds) {
			event.putProperty(var.getOid().toString(),var.toValueString());
			event.putFingerprintField(var.getOid().toString());
		}
		event.putProperty("error_status", pdu.getErrorStatusText());
		
		// Address is in the form of X.X.X.X/port if IPV4
		String hostname = snmpMessage.getHeader("peerAddress").toString();

		//TBD, Set title using the trap type and hostname
		event.setTitle(trapType + " from " + hostname);
		
		event.putProperty("type", trapType == "TRAP" ? "v2c" : "v1");
		
		//TBD, What should the message field be set to by default??
		event.setMessage(pdu.getErrorStatusText());
		
		//TBD, set the severity based on content of the trap
		event.setSeverity(Severity.WARN);
		
		//TBD Set status based on severity??
		event.setStatus(Status.OPEN);

		event.getSource().setRef(hostname);
		event.getSource().setType("host");
//		event.setMessage(message.getBody().toString());
//		event.putProperty("message",message.getBody().toString());
//		event.putFingerprintField("@title");
		
		// Assign the RawEvent to the body
		message.setBody(event, RawEvent.class);
	}
}
