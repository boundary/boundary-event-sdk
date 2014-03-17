/**
 * 
 */
package com.boundary.sdk;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.syslog.SyslogMessage;

import com.boundary.sdk.Event;


/**
 * @author davidg
 * 
 */
public class SyslogToEventProcessor implements Processor {

	private boolean debug = false;
	/**
	 * 
	 */
	
	public SyslogToEventProcessor() {
		this(false);
	}
	public SyslogToEventProcessor(boolean debug) {
		// TODO Auto-generated constructor stub
	}
	
	private final String CAMEL_SYSLOG_HOSTNAME = "CamelSyslogHostname";
	private final String CAMEL_SYSLOG_SEVERITY = "CamelSyslogSeverity";

	@Override
	public void process(Exchange exchange) throws Exception {
		// Get the CamelContext and Message
		CamelContext context = exchange.getContext();
		Message message = exchange.getIn();

		//SyslogMessage sm = (SyslogMessage) message.getBody(SyslogMessage.class);
		//System.out.println("Received syslog message: " + sm);
		
		// Create our event so that we can populate with the Syslog data
		Event event = new Event();
		
		// Delegate to member method call to perform the translation
		//this.translateSyslogMessage(message, event);
		message.setBody(event, Event.class);
	}
	
	private void translateSyslogMessage(SyslogMessage message, Event event) {
		
		
	}
	/**
	 * Set the Event from the severity in the Message
	 * @param m
	 * @param d
	 */
	private void setSeverity(SyslogMessage m, Event d) {
	
	}

}
