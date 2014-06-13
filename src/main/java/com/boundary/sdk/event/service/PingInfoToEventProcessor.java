/**
 * 
 */
package com.boundary.sdk.event.service;

import java.io.IOException;
import java.util.Properties;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.common.ServiceStatus;
import com.boundary.camel.component.ping.PingInfo;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.Status;

/**
 * Responsible for translating a {@link SyslogMessage} to {@link RawEvent}.
 * 
 * @author davidg
 * 
 */
public class PingInfoToEventProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(PingInfoToEventProcessor.class);

	public PingInfoToEventProcessor() {
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();

		// Extract SyslogMessage from the message body.
		PingInfo info = message.getBody(PingInfo.class);

		// Create new event to translate the syslog message to
		RawEvent event = new RawEvent();

		// Delegate to member method call to perform the translation
		pingStatusToEvent(info, event);
		
		LOG.debug("RawEvent: " + event);

		// Set the message body to the RawEvent
		message.setBody(event, RawEvent.class);
	}

	/**
	 * Converts a {@link SyslogMessage} to {@link RawEvent}
	 * 
	 * @param sm
	 * @param e
	 */
	private void pingStatusToEvent(PingInfo info, RawEvent e) {
		
		// Add the hostname
		Source s = e.getSource();
		String host = info.getHost();
		assert host != null: "Host is null";
		s.setRef(info.getHost()).setType("host");

		e.getProperties().put("hostname", info.getHost());
		e.addTag(info.getHost());
		
		// Add the message
		e.setMessage(info.getMessage());
		
		// Map the syslog severity to Boundary event severity
		Severity severity = info.getStatus() == ServiceStatus.FAIL ? Severity.CRITICAL : Severity.INFO;
		e.setSeverity(severity);	

		if (e.getSeverity() != Severity.INFO) {
			e.setStatus(Status.OPEN);
		}
		else {
			e.setStatus(Status.CLOSED);
		}
		
		// Set the uniqueness of the event by hostname, facility, and message.
		// TBD: These fields need to be split out in a configuration file
		e.addFingerprintField("hostname");
		e.addFingerprintField("@title");
		
		// Set the time at which the Syslog record was created
		e.setCreatedAt(info.getTimestamp());

		// Set Title
		e.setTitle("PingStatus: " + info.getHost());
		
		// Set Sender
		e.getSender().setRef("Ping");
		e.getSender().setType("Boundary Event SDK");
	}
}
