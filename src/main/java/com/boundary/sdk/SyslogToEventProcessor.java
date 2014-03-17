/**
 * 
 */
package com.boundary.sdk;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;

import com.boundary.sdk.Event;
import com.boundary.sdk.Event.Severity;


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

		SyslogMessage sm = (SyslogMessage) message.getBody(SyslogMessage.class);
		System.out.println("Received syslog message: " + sm);
		
		// Create our event so that we can populate with the Syslog data
		Event event = new Event();
		event = event.getDefaultEvent();
		event.setTitle(sm.getHostname()+":" + sm.getSeverity() + ":" + sm.getTimestamp()); 
		
		// Delegate to member method call to perform the translation
		//this.translateSyslogMessage(message, event);
		message.setBody(event, Event.class);
	}
	
	private void translateSyslogMessage(SyslogMessage message, Event event) {
		
		// Set severity
		setSeverity(message,event);
		
		
	}
	/**
	 * Set the Event from the severity in the Message
	 * @param m
	 * @param e
	 */
	private void setSeverity(SyslogMessage message, Event event) {
		
		switch(message.getSeverity()) {
		case EMERG:
			event.setSeverity(Severity.CRITICAL);
			break;
		case ALERT:
			event.setSeverity(Severity.CRITICAL);
			break;
		case CRIT:
			event.setSeverity(Severity.CRITICAL);
			break;
		case ERR:
			event.setSeverity(Severity.ERROR);
			break;
		case WARNING:
			event.setSeverity(Severity.WARN);
			break;
		case NOTICE:
			event.setSeverity(Severity.INFO);
			break;
		case INFO:
			event.setSeverity(Severity.INFO);
			break;
		case DEBUG:
			event.setSeverity(Severity.INFO);
			break;
		default:
			//TODO: How to bail in this case. It should not happen but should still be asserted.
		}
	
	}

}
