package com.boundary.sdk;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;


public class SyslogToEventProcessorTest {
	
	@Test
	public void testSeverityProperties() {
		Properties props = new Properties();
		SyslogToEventProcessor.getProperties(props,"syslog.severity.properties");
		SyslogMessage m = new SyslogMessage();
		Severity e;
		m.setSeverity(SyslogSeverity.NOTICE);
	}

}
