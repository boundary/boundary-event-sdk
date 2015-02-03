// Copyright 2014-2015 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.sdk.event.syslog;

import java.io.IOException;
import java.util.Properties;
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
 * Responsible for translating a {@link SyslogMessage} to {@link RawEvent}.
 * 
 * @author davidg
 * 
 */
public class SyslogToEventProcessor implements Processor {

	private static Logger LOG = LoggerFactory.getLogger(SyslogToEventProcessor.class);
	
	// Flag to indicated if the event should be translated into a {@link RawEvent} or not
	private boolean convertToEvent;

	public SyslogToEventProcessor() {
		this(true);
	}
	public SyslogToEventProcessor(boolean convertToEvent) {
		this.convertToEvent = convertToEvent;
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
		
		LOG.debug("Converting SyslogMessage to {}",this.convertToEvent ? "RawEvent" : "SyslogMessageEvent");
		
		// Conditionally translate {@link SyslogMessage} to {@link RawEvent}
		if (this.convertToEvent) {
			Message message = exchange.getIn();
	
			// Extract SyslogMessage from the message body.
			SyslogMessage syslogMessage = (SyslogMessage) message.getBody(SyslogMessage.class);
			logSyslogMessage(syslogMessage);
	
			// Create new event to translate the syslog message to
			RawEvent event = new RawEvent();
	
			// Delegate to member method call to perform the translation
			syslogMessageToEvent(syslogMessage, event);
			
			LOG.trace("{} {}",event.getClass().getName(),event);
	
			// Set the message body to the RawEvent
			message.setBody(event, RawEvent.class);
		}
		else {
			Message message = exchange.getIn();
			SyslogMessage syslogMessage = message.getBody(SyslogMessage.class);
			SyslogMessageEvent syslogEvent = new SyslogMessageEvent(syslogMessage);
			LOG.trace("{} {}",syslogEvent.getClass().getName(),syslogEvent);
			message.setBody(syslogEvent);
		}
	}

	/**
	 * Converts a {@link SyslogMessage} to {@link RawEvent}
	 * 
	 * @param sm
	 * @param e
	 */
	private void syslogMessageToEvent(SyslogMessage sm, RawEvent e) {

		// Add facility
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
		// e.getProperties().put("remote_address", sm.getRemoteAddress());
		// e.addTag(sm.getRemoteAddress());
		
		// Map the syslog severity to Boundary event severity
		Severity severity = getEventSeverity(sm.getSeverity());
		e.setSeverity(severity);
		
		// Set event status based on Severity
		Status status = getEventStatus(sm.getSeverity());
		e.setStatus(status);
		
		// Set the uniqueness of the event by hostname, facility, and message.
		// TBD: These fields need to be split out in a configuration file
		e.addFingerprintField("hostname");
		e.addFingerprintField("facility");
		e.addFingerprintField("message");
		
		// Set the time at which the Syslog record was created
		e.setCreatedAt(sm.getTimestamp().getTime());

		// Set Title
		e.setTitle("Syslog message from: " + sm.getHostname());
		
		// Set Sender
		e.getSender().setRef("Syslog");
		e.getSender().setType("Boundary Event SDK");
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
				e.printStackTrace();
				LOG.error(e.getStackTrace().toString());
			}
		}
	}
	public boolean isConvertToEvent() {
		return this.convertToEvent;
	}
	public void setConvertToEvent(boolean convertToEvent) {
		this.convertToEvent = convertToEvent;
	}
}
