/**
 * 
 */
package com.boundary.sdk;

import java.io.IOException;
import java.util.Properties;

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

	@Override
	public void process(Exchange exchange) throws Exception {

		CamelContext context = exchange.getContext();
		Message message = exchange.getIn();

		// Extract SyslogMessage from the message body.
		SyslogMessage syslogMessage = (SyslogMessage) message
				.getBody(SyslogMessage.class);
		System.out.println("Received syslog message: " + syslogMessage);

		// Create new event to translate the syslog message to
		RawEvent event = new RawEvent();

		// Delegate to member method call to perform the translation
		syslogMessageToEvent(syslogMessage, event);

		// Set the message body to the RawEvent
		message.setBody(event, RawEvent.class);
	}

	/**
	 * Converts a {@link SyslogMessage} to {@link RawEvent}
	 * 
	 * @param sm
	 * @param e
	 */
	private void syslogMessageToEvent(SyslogMessage sm, RawEvent e) {

		//
		// Facility
		// Hostname
		// Log Message
		// Remote Address
		// Severity
		// Timestamp
		// Get the CamelContext and Message

		setEventSeverity(sm, e);
		e.setTitle(sm.getHostname() + ":" + sm.getTimestamp());
	}

	/**
	 * Set the Event from the severity in the Message
	 * 
	 * @param m
	 * @param e
	 */
	private void setEventSeverity(SyslogMessage sm, RawEvent e) {
		Properties severityMap = getSeverityProperties("syslog.properties");
//		System.out.println("SEVERITY MAP" + severityMap);
//		System.out.println(severityMap.getProperty(sm.getSeverity().toString()));
		String strSeverity = severityMap.getProperty(sm.getSeverity().toString());
		Severity severity = Severity.valueOf(strSeverity);
		e.setSeverity(Severity.INFO);

		// switch(message.getSeverity()) {
		// case EMERG:
		// event.setSeverity(Severity.CRITICAL);
		// break;
		// case ALERT:
		// event.setSeverity(Severity.CRITICAL);
		// break;
		// case CRIT:
		// event.setSeverity(Severity.CRITICAL);
		// break;
		// case ERR:
		// event.setSeverity(Severity.ERROR);
		// break;
		// case WARNING:
		// event.setSeverity(Severity.WARN);
		// break;
		// case NOTICE:
		// event.setSeverity(Severity.INFO);
		// break;
		// case INFO:
		// event.setSeverity(Severity.INFO);
		// break;
		// case DEBUG:
		// event.setSeverity(Severity.INFO);
		// break;
		// default:
		// //TODO: How to bail in this case. It should not happen but should
		// still be asserted.
		// }

	}

	private static Properties severityProperties = null;

	public static Properties getSeverityProperties(String propertyFileName) {
		if (severityProperties == null) {
			try {
				severityProperties = new Properties();
				severityProperties.load(Thread.currentThread()
						.getContextClassLoader()
						.getResourceAsStream(propertyFileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return severityProperties;
	}

}
