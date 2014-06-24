package com.boundary.sdk.event.service.ssh;

import static com.boundary.sdk.event.service.ssh.SshHeaderNames.SSH_HEADER_EXPECTED_OUTPUT;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;
import static com.boundary.sdk.event.util.BoundaryHeaderNames.*;

public class SendSSHServiceTests implements Processor {
	private SshxConfiguration configuration;
	private String serviceTestName;
	private String serviceName;
	private String requestId;
	private String expectedOutput;
	private String serviceTestType;
	
	public String getServiceTestType() {
		return serviceTestType;
	}

	public void setServiceTestType(String serviceTestType) {
		this.serviceTestType = serviceTestType;
	}

	public SendSSHServiceTests() {
		configuration = new SshxConfiguration();
		
		//TODO: Remove this completely. Justing for creating request Ids
		ServiceCheckRequest request = new ServiceCheckRequest();
		requestId = request.getRequestId();
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();

		ServiceTest<SshxConfiguration> serviceTest =
				new ServiceTest<SshxConfiguration>(getServiceTestName(),getServiceName(),getRequestId(),configuration);
		
		// Set the body to the command
		message.setBody(getCommand());
		message.setHeader(SSH_HEADER_EXPECTED_OUTPUT,getExpectedOutput());
		message.setHeader(BOUNDARY_SERVICE_NAME,getServiceName());
		message.setHeader(BOUNDARY_SERVICE_TEST,serviceTest);
	}

	public String getServiceTestName() {
		return serviceTestName;
	}

	public void setServiceTestName(String testName) {
		this.serviceTestName = testName;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getHost() {
		return configuration.getHost();
	}

	public void setHost(String host) {
		this.configuration.setHost(host);
	}

	public String getUser() {
		return configuration.getUser();
	}

	public void setUser(String user) {
		this.configuration.setUser(user);
	}

	public String getPassword() {
		return this.configuration.getPassword();
	}

	public void setPassword(String password) {
		this.configuration.setPassword(password);
	}

	public String getCommand() {
		return this.configuration.getCommand();
	}

	public void setCommand(String command) {
		this.configuration.setCommand(command);
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}
	
}
