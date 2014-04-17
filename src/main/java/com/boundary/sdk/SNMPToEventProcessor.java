/**
 * 
 */
package com.boundary.sdk;

import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.RawEvent;
import com.boundary.sdk.Severity;


/**
 * @author davidg
 * 
 */
public class SNMPToEventProcessor implements Processor {
	
    private static final Logger LOG = LoggerFactory.getLogger(SNMPToEventProcessor.class);
	
	public SNMPToEventProcessor() {
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		LOG.debug("class: {}",message.getBody().getClass().toString());
		
		// Create our event so that we can populate with the Syslog data
		RawEvent event = new RawEvent();
		Date dt = new java.util.Date();
		
		event.setTitle("SNMP TRAP " + dt);
		event.setStatus(Status.OPEN);
		event.setSeverity(Severity.WARN);
		event.getSource().setRef("localhost");
		event.getSource().setType("host");
//		event.setMessage(message.getBody().toString());
//		event.putProperty("message",message.getBody().toString());
		event.putFingerprintField("@title");
		
		message.setBody(event, RawEvent.class);
	}
}
