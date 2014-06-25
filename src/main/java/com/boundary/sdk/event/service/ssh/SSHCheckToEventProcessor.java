package com.boundary.sdk.event.service.ssh;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.ping.PingInfo;
import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.camel.component.ssh.SshxInfo;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Status;

import static com.boundary.sdk.event.service.QuartzHeaderNames.*;
import static com.boundary.sdk.event.util.BoundaryHeaderNames.*;

import com.boundary.sdk.event.service.ServiceTest;
import com.boundary.sdk.event.snmp.MIBCompilerLogger;

public class SSHCheckToEventProcessor implements Processor {
	
	private static Logger LOG = LoggerFactory.getLogger(SSHCheckToEventProcessor.class);

	public SSHCheckToEventProcessor() {

	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		
		String output = message.getBody(String.class);
		String expectedOutput = message.getHeader(SshHeaderNames.SSH_HEADER_EXPECTED_OUTPUT,String.class);

		String testName = message.getHeader(BOUNDARY_SERVICE_NAME,String.class);
		ServiceTest<SshxConfiguration> serviceTest = message.getHeader(BOUNDARY_SERVICE_TEST,ServiceTest.class);
		SshxConfiguration serviceTestConfig = serviceTest.getConfiguration();
		
		//
		// Create the RawEvent and populate with values
		//
		
		RawEvent event = new RawEvent();
		
		// Set the creation to now!
		event.setCreatedAt(new Date());
		
		// Host gets set from the host we ran the SSH against.
		event.getSource().setRef(serviceTestConfig.getHost());
		event.getSource().setType("host");
		

		// Define the uniqueness of the event based on the service and the host that was tested.
		event.addFingerprintField("service");
		event.addFingerprintField("hostname");
		
		// Add the required properties
		String hostname = serviceTestConfig.getHost();
		String serviceName = serviceTest.getServiceName();
		event.addProperty("command",serviceTestConfig.getCommand());
		event.addProperty("expected-output",expectedOutput);
		event.addProperty("output",output);
		event.addProperty("service-test",serviceTest.getName());
		event.addProperty("service",serviceName);
		event.addProperty("hostname",hostname);
		event.addProperty("time-out",serviceTestConfig.getTimeout());
		
		// Tag the service that was tested
		event.addTag(serviceTest.getServiceName());
		event.addTag(hostname);
		
		// Generate our title of the event
		// TODO: Service test provides a template from the available data??
		event.setTitle(serviceName + " - " + serviceTest.getName());

		
		// Set Severity, Status, and Message of the event based on the matching of expected output
		if (output.matches(expectedOutput)) {
			event.setSeverity(Severity.INFO);
			event.setStatus(Status.CLOSED);
			event.setMessage("Received expected output: " + expectedOutput);
			
		}
		else {
			event.setSeverity(Severity.WARN);
			event.setStatus(Status.CLOSED);
			event.setMessage("Received unexpected output");
		}
		
		// Set Sender
		event.getSender().setRef("Service Health Check");
		event.getSender().setType("Boundary Event SDK");
		
		LOG.debug("RawEvent: " + event);
		
		message.setBody(event);
	}
	
	private void sshStatusToEvent(ServiceTest<SshxInfo> serviceTest,SshxInfo info,RawEvent event) {

	}

	
	private String getOutputString(ByteArrayInputStream inputStream) {
		StringBuffer sb = new StringBuffer();
		List<String> lines = getOutputToList(inputStream);
		
		for (String line : lines) {
			sb.append(line);
		}
		
		return sb.toString();
	}
	
	private List<String> getOutputToList(ByteArrayInputStream inputStream) {
		List<String> lines = new ArrayList<String>();
	
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		return lines;
	}
}
