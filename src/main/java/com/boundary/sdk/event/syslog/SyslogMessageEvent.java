package com.boundary.sdk.event.syslog;

import java.io.Serializable;
import java.util.Date;

import org.apache.camel.component.syslog.SyslogMessage;

public class SyslogMessageEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2281352560536208277L;
	
	private String facility;
	private String hostname;
	private String localAddress;
	private String logMessage;
	private String remoteAddress;
	private String severity;
	private Date timestamp;

	SyslogMessageEvent(SyslogMessage message) {
		this.facility = message.getFacility().toString();
		this.hostname = message.getHostname();
		this.localAddress = message.getLocalAddress();
		this.logMessage = message.getLogMessage();
		this.remoteAddress = message.getRemoteAddress();
		this.severity = message.getSeverity().toString();
		this.timestamp = message.getTimestamp().getTime();
	}

	public String getFacility() {
		return facility;
	}

	public void setFacility(String facility) {
		this.facility = facility;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getLocalAddress() {
		return localAddress;
	}

	public void setLocalAddress(String localAddress) {
		this.localAddress = localAddress;
	}

	public String getLogMessage() {
		return logMessage;
	}

	public void setLogMessage(String logMessage) {
		this.logMessage = logMessage;
	}

	public String getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	@Override
	public String toString() {
		return "SyslogMessageEvent [facility=" + facility + ", hostname="
				+ hostname + ", localAddress=" + localAddress + ", logMessage="
				+ logMessage + ", remoteAddress=" + remoteAddress
				+ ", severity=" + severity + ", timestamp=" + timestamp + "]";
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	
}
