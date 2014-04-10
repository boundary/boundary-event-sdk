/**
 * 
 */
package com.boundary.sdk;

import java.util.Date;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;

import com.boundary.sdk.RawEvent;
import com.boundary.sdk.Severity;


/**
 * @author davidg
 * 
 */
public class SNMPToEventProcessor implements Processor {

	private boolean debug = false;
	/**
	 * 
	 */
	
	public SNMPToEventProcessor() {
		this(false);
	}
	public SNMPToEventProcessor(boolean debug) {
		// TODO Auto-generated constructor stub
	}
	
	private final String CAMEL_SYSLOG_HOSTNAME = "CamelSyslogHostname";
	private final String CAMEL_SYSLOG_SEVERITY = "CamelSyslogSeverity";

	@Override
	public void process(Exchange exchange) throws Exception {
		// Get the CamelContext and Message
		CamelContext context = exchange.getContext();
		Message message = exchange.getIn();
		
		// Create our event so that we can populate with the Syslog data
		RawEvent event = new RawEvent();
		event = event.getDefaultEvent();
		Date dt = new java.util.Date();
		event.setTitle("SNMP TRAP " + dt);
		event.setSeverity(Severity.CRITICAL);
		
		// Delegate to member method call to perform the translation
		//this.translateSyslogMessage(message, event);
		message.setBody(event, RawEvent.class);
	}
}
