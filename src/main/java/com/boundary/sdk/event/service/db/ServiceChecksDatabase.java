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
package com.boundary.sdk.event.service.db;

import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;
import com.boundary.sdk.event.service.ping.PingServiceModel;
import com.boundary.sdk.event.service.port.PortServiceModel;
import com.boundary.sdk.event.service.ssh.SshxServiceModel;
import com.boundary.sdk.event.service.url.UrlServiceDatabase;

public class ServiceChecksDatabase implements Processor {
	
	//TODO: Separate class for handling constants
	private static final String PING = "ping";
	private static final String PORT = "port";
	private static final String SSH = "ssh";
	private static final String URL = "url";
	private static Logger LOG = LoggerFactory.getLogger(ServiceChecksDatabase.class);


	public ServiceChecksDatabase() {
		// TODO Auto-generated constructor stub
	}
	
	
	private void sendTestData(Exchange exchange) {
		Message message = exchange.getIn();
		String sdnDirectorHost = "192.168.137.11";
		String sdnDirectorName = "SDN Director";
		ServiceCheckRequest request = new ServiceCheckRequest();
		
		PingConfiguration sdnDirectorPingTest = new PingConfiguration();
		sdnDirectorPingTest.setHost(sdnDirectorHost);
		
		PingServiceModel sdnDirectorPingModel = new PingServiceModel();
	
		
		PortConfiguration sdnDirectorPortTest8080 = new PortConfiguration();
		sdnDirectorPortTest8080.setHost(sdnDirectorHost);
		sdnDirectorPortTest8080.setPort(8080);
		
		PortServiceModel sdnDirectorPortModel8080 = new PortServiceModel();
		
		
		
		SshxConfiguration plumgridProcessTest = new SshxConfiguration();
		plumgridProcessTest.setHost(sdnDirectorHost);
		plumgridProcessTest.setCommand("status plumgrid");
		plumgridProcessTest.setTimeout(10000);
		plumgridProcessTest.setUsername("plumgrid");
		plumgridProcessTest.setPassword("plumgrid");
		
		SshxServiceModel plumgridProcessModel = new SshxServiceModel();
		plumgridProcessModel.setExpectedOutput("^plumgrid start/running, process\\s+\\d+");
		
		
		
		SshxConfiguration plumgridSalProcessTest = new SshxConfiguration();
		plumgridSalProcessTest.setHost(sdnDirectorHost);
		plumgridSalProcessTest.setCommand("status plumgrid-sal");
		plumgridSalProcessTest.setTimeout(10000);
		plumgridSalProcessTest.setUsername("plumgrid");
		plumgridSalProcessTest.setPassword("plumgrid");

		
		SshxServiceModel plumgridSalProcessTestModel = new SshxServiceModel();
		plumgridSalProcessTestModel.setExpectedOutput("^plumgrid-sal start/running, process\\s\\d+");
		
		

		SshxConfiguration nginxProcessTest = new SshxConfiguration();
		nginxProcessTest.setHost(sdnDirectorHost);
		nginxProcessTest.setCommand("status nginx");
		nginxProcessTest.setTimeout(10000);
		nginxProcessTest.setUsername("plumgrid");
		nginxProcessTest.setPassword("plumgrid");

		
		SshxServiceModel nginxProcessModel = new SshxServiceModel();
		nginxProcessModel.setExpectedOutput("^nginx start/running, process\\s\\d+");


		ServiceTest<PingConfiguration,PingServiceModel> pingSDNDirector= new ServiceTest<PingConfiguration,PingServiceModel>(
				"host status","ping",sdnDirectorName,request.getRequestId(),sdnDirectorPingTest,sdnDirectorPingModel);
		request.addServiceTest(pingSDNDirector);
		
		ServiceTest<PortConfiguration,PortServiceModel> portSDNDirector8080
			= new ServiceTest<PortConfiguration,PortServiceModel>(
					"8080 port status","port",sdnDirectorName,request.getRequestId(),sdnDirectorPortTest8080,sdnDirectorPortModel8080);
		request.addServiceTest(portSDNDirector8080);
		
		ServiceTest<SshxConfiguration,SshxServiceModel> sshPlumgridProcess = new ServiceTest<SshxConfiguration,SshxServiceModel>(
				"plumgrid process status","ssh",sdnDirectorName,request.getRequestId(),plumgridProcessTest,plumgridProcessModel);
		request.addServiceTest(sshPlumgridProcess);
		
		ServiceTest<SshxConfiguration,SshxServiceModel> sshPlumgridSalProcess = new ServiceTest<SshxConfiguration,SshxServiceModel>(
				"plumgrid-sal process status","ssh",sdnDirectorName,request.getRequestId(),plumgridProcessTest,plumgridProcessModel);
		request.addServiceTest(sshPlumgridSalProcess);
		
		ServiceTest<SshxConfiguration,SshxServiceModel> sshNginxProcess = new ServiceTest<SshxConfiguration,SshxServiceModel>(
				"nginx process status","ssh",sdnDirectorName,request.getRequestId(),plumgridProcessTest,plumgridProcessModel);
		request.addServiceTest(sshNginxProcess);

		message.setBody(request);
	}
	
	private void createPingServiceTest(ServiceCheckRequest request, Map<String,Object> row) {
		String pingHost = row.get("pingHost").toString();
		int pingTimeout = Integer.parseInt(row.get("pingTimeout").toString());
		
		PingConfiguration pingConfiguration = new PingConfiguration();
		pingConfiguration.setHost(pingHost);
		pingConfiguration.setTimeout(pingTimeout);
		
		String serviceName = row.get("serviceName").toString();
		String serviceTestName = row.get("serviceTestName").toString();
		String serviceTypeName = row.get("serviceTypeName").toString();

		
		PingServiceModel pingServiceModel = new PingServiceModel();
		
		ServiceTest<PingConfiguration,PingServiceModel> pingServiceTest =
				new ServiceTest<PingConfiguration,PingServiceModel>(serviceTestName,serviceTypeName,serviceName,
				request.getRequestId(),pingConfiguration,pingServiceModel);
		request.addServiceTest(pingServiceTest);
	}
	
	private void createPortServiceTest(ServiceCheckRequest request, Map<String,Object> row) {
		String portHost = row.get("portHost").toString();
		int port = Integer.parseInt(row.get("portPort").toString());
		int portTimeout = Integer.parseInt(row.get("portTimeout").toString());
		
		PortConfiguration portConfiguration = new PortConfiguration();
		portConfiguration.setHost(portHost);
		portConfiguration.setPort(port);
		portConfiguration.setTimeout(portTimeout);
		
		String serviceName = row.get("serviceName").toString();
		String serviceTestName = row.get("serviceTestName").toString();
		String serviceTypeName = row.get("serviceTypeName").toString();

		PortServiceModel portServiceModel = new PortServiceModel();
		
		ServiceTest<PortConfiguration,PortServiceModel> portServicetest =
				new ServiceTest<PortConfiguration,PortServiceModel>(serviceTestName,serviceTypeName,serviceName,
				request.getRequestId(),portConfiguration,portServiceModel);
		request.addServiceTest(portServicetest);
	}
	
	private void createSshServiceTest(ServiceCheckRequest request, Map<String,Object> row) {
		String sshHost = row.get("sshHost").toString();
		int sshPort = Integer.parseInt(row.get("sshPort").toString());
		int sshTimeout = Integer.parseInt(row.get("sshTimeout").toString());
		String sshUserName = row.get("sshUserName").toString();
		String sshPassword = row.get("sshPassword").toString();
		String sshCommand = row.get("sshCommand").toString();
		
		SshxConfiguration sshConfiguration = new SshxConfiguration();
		sshConfiguration.setHost(sshHost);
		sshConfiguration.setPort(sshPort);
		sshConfiguration.setTimeout(sshTimeout);
		sshConfiguration.setUsername(sshUserName);
		sshConfiguration.setPassword(sshPassword);
		sshConfiguration.setCommand(sshCommand);
		
		String serviceName = row.get("serviceName").toString();
		String serviceTestName = row.get("serviceTestName").toString();
		String serviceTypeName = row.get("serviceTypeName").toString();

		SshxServiceModel sshServiceModel = new SshxServiceModel();
		String expectedOutput = row.get("sshExpectedOutput").toString();
		sshServiceModel.setExpectedOutput(expectedOutput);
		
		ServiceTest<SshxConfiguration,SshxServiceModel> sshServicetest =
				new ServiceTest<SshxConfiguration,SshxServiceModel>(serviceTestName,serviceTypeName,serviceName,
				request.getRequestId(),sshConfiguration,sshServiceModel);
		request.addServiceTest(sshServicetest);
	}
	
	private void createUrlServiceTest(ServiceCheckRequest request, Map<String,Object> row) {
		UrlServiceDatabase serviceUrl = new UrlServiceDatabase();
		
		serviceUrl.populate(request,row);

	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		ServiceCheckRequest request = new ServiceCheckRequest();

		List<Map<String, Object>> list = message.getBody(List.class);
		for (Map<String,Object> row : list) {
			LOG.debug("Service Test Data: " + row.toString());
			String serviceTestType = row.get("serviceTypeName").toString();
			
			switch (serviceTestType) {
			case PING:
				createPingServiceTest(request,row);
				break;
			case PORT:
				createPortServiceTest(request,row);
				break;
			case SSH:
				createSshServiceTest(request,row);
				break;
			case URL:
				createUrlServiceTest(request,row);
				break;
			}
		}
		//TODO: How to handle if there are no service tests
		message.setBody(request);
	}
}
