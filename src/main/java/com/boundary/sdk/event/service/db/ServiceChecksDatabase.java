package com.boundary.sdk.event.service.db;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.camel.component.ssh.SshxConfiguration;
import com.boundary.sdk.event.BoundaryEventRouteBuilder;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;
import com.boundary.sdk.event.service.ssh.SshxServiceModel;

public class ServiceChecksDatabase implements Processor {
	
	private static Logger LOG = LoggerFactory.getLogger(ServiceChecksDatabase.class);


	public ServiceChecksDatabase() {
		// TODO Auto-generated constructor stub
	}
	

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		List<Map<String, Object>> list = message.getBody(List.class);
		for (Map<String,Object> row : list) {
			LOG.info(row.toString());
		}		
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
		
		SshxServiceModel plumgridProcessModel = new SshxServiceModel();
		plumgridProcessModel.setExpectedOutput("^plumgrid start/running, process\\s\\d+\n");
		
		
		
		SshxConfiguration plumgridSalProcessTest = new SshxConfiguration();
		plumgridSalProcessTest.setHost(sdnDirectorHost);
		plumgridSalProcessTest.setCommand("status plumgrid-sal");
		plumgridSalProcessTest.setTimeout(10000);
		
		SshxServiceModel plumgridSalProcessTestModel = new SshxServiceModel();
		plumgridSalProcessTestModel.setExpectedOutput("^plumgrid-sal start/running, process\\s\\d+\n");
		
		

		SshxConfiguration nginxProcessTest = new SshxConfiguration();
		nginxProcessTest.setHost(sdnDirectorHost);
		nginxProcessTest.setCommand("status nginx");
		plumgridSalProcessTest.setTimeout(10000);
		
		SshxServiceModel nginxProcessModel = new SshxServiceModel();
		nginxProcessModel.setExpectedOutput("^nginx start/running, process\\s\\d+\n");


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
}
