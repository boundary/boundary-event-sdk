package com.boundary.sdk.event.service.ssh;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;

import static com.boundary.sdk.event.service.QuartzHeaderNames.*;
import static com.boundary.sdk.event.util.BoundaryHeaderNames.*;

import com.boundary.sdk.event.service.ServiceTest;

public class SSHCheckToEventProcessor implements Processor {

	private static final String QUARTZ_HEADER_FIRE_TIME = null;

	public SSHCheckToEventProcessor() {

	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		String output = message.getBody(String.class);
		String expectedOutput = message.getHeader(SshHeaderNames.SSH_HEADER_EXPECTED_OUTPUT,String.class);
		String triggerName = message.getHeader(QUARTZ_HEADER_TRIGGER_NAME,String.class);
		//String createdAtDate = message.getHeader(QUARTZ_HEADER_FIRE_TIME,String.class);
		String testName = message.getHeader(BOUNDARY_SERVICE_NAME,String.class);
		ServiceTest<SshxConfiguration> serviceTest = message.getHeader(BOUNDARY_SERVICE_TEST,ServiceTest.class);
		SshxConfiguration serviceTestConfig = serviceTest.getConfiguration();
		//message.setHeader("created-at-date", createdAtDate);
		
		RawEvent event = new RawEvent();
		
		event.getSource().setRef(serviceTestConfig.getHost());
		event.getSource().setType("host");
		
		event.setTitle(serviceTest.getServiceName() + " - " + serviceTest.getName());
		event.addFingerprintField("service-name");
		
		// Service Tests are always have a status of OK
		event.setStatus(Status.OK);
		
		//
		event.addProperty("command",serviceTestConfig.getCommand());
		event.addProperty("expected-output",expectedOutput);
		event.addProperty("output", message.getBody());
		event.addProperty("service-name",serviceTest.getServiceName());
		
		// Set the severity base on the expected output 
		if (output.matches(expectedOutput)) {
			event.setSeverity(Severity.INFO);
		}
		else {
			event.setSeverity(Severity.WARN);
		}
		
		message.setBody(event);
	}
	
	public void process() {
		
	}

}
