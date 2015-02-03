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
package com.boundary.sdk.event.service.ssh;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.SERVICE_TEST_INSTANCE;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;

public class SendSSHServiceTests implements Processor {
	private SshxConfiguration configuration;
	private SshxServiceModel model;
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
		model = new SshxServiceModel();
		
		//TODO: Remove this completely. Justing for creating request Ids
		ServiceCheckRequest request = new ServiceCheckRequest();
		requestId = request.getRequestId();
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();

		ServiceTest<SshxConfiguration,SshxServiceModel> serviceTest = new ServiceTest<SshxConfiguration,SshxServiceModel>(
				getServiceTestName(),"ssh",getServiceName(),getRequestId(),configuration,model);
		
		// Set the body to the command
		message.setBody(getCommand());
		message.setHeader(SERVICE_TEST_INSTANCE,serviceTest);
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
		return configuration.getUsername();
	}

	public void setUser(String user) {
		this.configuration.setUsername(user);
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
