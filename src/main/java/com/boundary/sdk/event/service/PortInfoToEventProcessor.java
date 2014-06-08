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
import com.boundary.camel.component.port.PortInfo;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;


/**
 * Responsible for translating a {@link SyslogMessage} to {@link RawEvent}.
 * 
 * @author davidg
 * 
 */
public class PortInfoToEventProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(PortInfoToEventProcessor.class);

	public PortInfoToEventProcessor() {
	}
	
	@Override
	public void process(Exchange exchange) throws Exception {
		
		LOG.debug("PROCESS");
		Message message = exchange.getIn();

		// Extract SyslogMessage from the message body.
		PortInfo status = message.getBody(PortInfo.class);

		// Create new event to translate the syslog message to
		RawEvent event = new RawEvent();

		// Delegate to member method call to perform the translation
		portInfoToEvent(status, event);
		
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
	private void portInfoToEvent(PortInfo info, RawEvent e) {
		
		// Add the hostname
		e.getSource().setRef(info.getHost()).setType("host");
		e.getProperties().put("hostname", info.getHost());
		e.getProperties().put("port",info.getPort());
		e.getProperties().put("port-status",info.getPortStatus());
		e.getProperties().put("time-out", info.getTimeout());
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
			e.setStatus(Status.OK);
		}
		
		// Set the uniqueness of the event by hostname, facility, and message.
		// TBD: These fields need to be split out in a configuration file
		e.addFingerprintField("hostname");
		e.addFingerprintField("port");
		
		// Set the time at which the Syslog record was created
		e.setCreatedAt(info.getTimestamp());

		// Set Title
		e.setTitle("Checking host: " + info.getHost() + " on port: " + info.getPort());
		
		// Set Sender
		e.getSender().setRef("Port");
		e.getSender().setType("Boundary Event SDK");
	}
}
