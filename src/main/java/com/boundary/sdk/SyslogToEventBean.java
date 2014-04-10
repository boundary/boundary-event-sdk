/**
 * 
 */
package com.boundary.sdk;


import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;

import com.boundary.sdk.RawEvent;
import com.boundary.sdk.Severity;


/**
 * @author davidg
 * 
 */
public class SyslogToEventBean {
	
	public static RawEvent process(SyslogMessage message) {
		System.out.println("Received syslog message: " + message);
		
		// Create our event so that we can populate with the Syslog data
		RawEvent event = new RawEvent();
		event = event.getDefaultEvent();
		setSeverity(message,event);
		event.setTitle(message.getHostname() + ":" + message.getTimestamp()); 
		
		return event;
	}
	
	/**
	 * Set the Event from the severity in the Message
	 * @param m
	 * @param e
	 */
	private static void setSeverity(SyslogMessage message, RawEvent event) {
		
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
