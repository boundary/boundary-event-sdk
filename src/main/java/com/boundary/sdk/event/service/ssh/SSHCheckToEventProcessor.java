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

import com.boundary.camel.component.ssh.SshxConfiguration;
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
		String expectedOutput = message.getHeader(SshHeaderNames.SSH_HEADER_EXPECTED_OUTPUT,String.class);
		
//		ByteArrayInputStream inputStream = message.getBody(ByteArrayInputStream.class);
//		String output = getOutputString(inputStream);
		
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
		event.addFingerprintField("host");
		
		// Service Tests are always have a status of OK
		event.setStatus(Status.OK);
		
		// Add the required properties
		event.addProperty("command",serviceTestConfig.getCommand());
		event.addProperty("expected-output",expectedOutput);
		String output = message.getBody(String.class);
		event.addProperty("output",output);
		event.addProperty("service-name",serviceTest.getServiceName());
		event.addProperty("host",serviceTestConfig.getHost());
		
		event.addTag(serviceTest.getServiceName());
		
		LOG.info(event.toString());
		LOG.info(expectedOutput);
		// Set the severity base on the expected output 
		if (output.matches(expectedOutput)) {
			event.setSeverity(Severity.INFO);
			event.setMessage("Received expected output");
		}
		else {
			event.setSeverity(Severity.WARN);
			event.setMessage("Received unexpected output");
		}
		
		message.setBody(event);
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
