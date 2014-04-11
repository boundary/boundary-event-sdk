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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.RawEvent;
import com.boundary.sdk.Severity;

/**
 * @author davidg
 * 
 */
public class SyslogToEventProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(SyslogToEventProcessor.class);

	public SyslogToEventProcessor() {
	}

	@Override
	public void process(Exchange exchange) throws Exception {

		CamelContext context = exchange.getContext();
		Message message = exchange.getIn();

		// Extract SyslogMessage from the message body.
		SyslogMessage syslogMessage = (SyslogMessage) message
				.getBody(SyslogMessage.class);
		LOG.debug("Received syslog message: " + syslogMessage);

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

		// Add the facility to 
		// Set the event severity based on our default mapping
		// defined in a properties file
		//
		e.getProperties().put("facility", sm.getFacility());
		
		// Add the hostname
		e.getSource().setRef(sm.getHostname()).setType("host");
		e.getProperties().put("hostname", sm.getHostname());
		
		// Add the message
		e.setMessage(sm.getLogMessage());
		e.getProperties().put("message", sm.getLogMessage());
		
		// Add the remote address
		e.getProperties().put("remote_address", sm.getRemoteAddress());
		
		// Map the syslog severity to Boundary event severity
		Severity severity = getEventSeverity(sm.getSeverity());
		e.setSeverity(severity);
		
		// Set the time at which the syslog record was created
		e.setCreatedAt(sm.getTimestamp());
		
		e.setTitle(sm.getHostname() + ":" + sm.getTimestamp());

	}

	/**
	 * Set the Event from the severity in the Message
	 * 
	 * @param m
	 * @param e
	 */
	private Severity getEventSeverity(SyslogSeverity severity) {
		Properties severityMap = getSeverityProperties("syslog.severity.properties");
		String strSeverity = severityMap.getProperty(severity.toString());
		return Severity.valueOf(strSeverity);
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
				LOG.error(e.getStackTrace().toString());
			}
		}
		return severityProperties;
	}
}
