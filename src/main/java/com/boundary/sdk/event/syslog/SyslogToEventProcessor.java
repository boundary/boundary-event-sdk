/**
 * 
 */
package com.boundary.sdk.event.syslog;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;

/**
 * @author davidg
 * 
 */
public class SyslogToEventProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(SyslogToEventProcessor.class);

	public SyslogToEventProcessor() {
	}
	
	private void logSyslogMessage(SyslogMessage slm) {
		LOG.debug(slm.getTimestamp()
				+ "|" + slm.getFacility()
				+ "|" + slm.getSeverity()
				+ "|" + slm.getHostname()
				+ "|" + slm.getRemoteAddress()
				+ "|" + slm.getLogMessage());
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();

		// Extract SyslogMessage from the message body.
		SyslogMessage syslogMessage = (SyslogMessage) message.getBody(SyslogMessage.class);
		logSyslogMessage(syslogMessage);

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

		e.getProperties().put("facility", sm.getFacility());
		e.addTag(sm.getFacility().toString());
		
		// Add the hostname
		e.getSource().setRef(sm.getHostname()).setType("host");
		e.getProperties().put("hostname", sm.getHostname());
		e.addTag(sm.getHostname());
		
		// Add the message
		e.setMessage(sm.getLogMessage());
		e.getProperties().put("message", sm.getLogMessage());
		
		// Add the remote address
		e.getProperties().put("remote_address", sm.getRemoteAddress());
		e.addTag(sm.getRemoteAddress());
		
		// Map the syslog severity to Boundary event severity
		Severity severity = getEventSeverity(sm.getSeverity());
		e.setSeverity(severity);
		e.addProperty("severity", sm.getSeverity().toString());
		e.addTag(sm.getSeverity().toString());
		
		Status status = getEventStatus(sm.getSeverity());
		e.setStatus(status);
		
		// Set the uniqueness of the event by hostname and facility
		// TBD These fields need to be split out in a configuration file
		e.addFingerprintField("hostname");
		e.addFingerprintField("facility");
		
		// Set the time at which the syslog record was created
		// TBD: Ensure time is in UTC
		e.setCreatedAt(sm.getTimestamp());
		e.addProperty("timestamp", sm.getTimestamp());

		// Set Title
		// TBD External configuration
		e.setTitle("Syslog Message from  " + sm.getHostname());
	}

	/**
	 * Set the Event from the severity in the Message
	 * 
	 * @param m
	 * @param e
	 */
	private Severity getEventSeverity(SyslogSeverity severity) {
		getProperties(severityMap,"syslog.severity.properties");
		String strSeverity = severityMap.getProperty(severity.toString());
		return Severity.valueOf(strSeverity);
	}
	
	/**
	 * 
	 * @param severity
	 * @return {@link Status}
	 */
	private Status getEventStatus(SyslogSeverity severity) {
		getProperties(statusMap,"syslog.status.properties");
		String strStatus = statusMap.getProperty(severity.toString());
		return Status.valueOf(strStatus);
	}


	private static Properties severityMap = new Properties();
	private static Properties statusMap = new Properties();

	public static void getProperties(Properties properties, String propertyFileName) {
		if (properties.isEmpty()) {
			try {
				properties.load(Thread.currentThread()
						.getContextClassLoader()
						.getResourceAsStream(propertyFileName));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				LOG.error(e.getStackTrace().toString());
			}
		}
	}
}
