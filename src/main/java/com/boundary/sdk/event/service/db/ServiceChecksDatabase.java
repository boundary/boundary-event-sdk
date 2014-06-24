package com.boundary.sdk.event.service.db;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;

public class ServiceChecksDatabase implements Processor {

	public ServiceChecksDatabase() {
		// TODO Auto-generated constructor stub
	}
	
//	private Map<String,List<Object>> groupByCheckId(List<Map<String,Object>> list) {
//		Map<String,List<Object>> map = new HashMap<String,List<Object>>();
//		
//		for (Map<String,Object> m : list) {
//			map.put(m.getKey(), value)
//		}
//		return map;
//	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		//List<Map<String, Object>> list = message.getBody(List.class);
//		for (Map<String,Object> row : list) {
//			System.out.println(row);
//		}
//		
		//ListIterator <Map<String,Object>> i = list.listIterator();
		
		
//		{serviceId=1, serviceName=SDN Director, serviceCheckName=SDN Director Service Check, serviceTestId=1, serviceTestName=Check SDN Director host status, serviceTypeName=ping, serviceTypeTableName=t_ping_config, configId=1}
//		{serviceId=1, serviceName=SDN Director, serviceCheckName=SDN Director Service Check, serviceTestId=2, serviceTestName=Check plumgrid process status, serviceTypeName=ssh, serviceTypeTableName=t_port_config, configId=1}
//		{serviceId=1, serviceName=SDN Director, serviceCheckName=SDN Director Service Check, serviceTestId=3, serviceTestName=Check plumgrid-sal process status, serviceTypeName=ssh, serviceTypeTableName=t_port_config, configId=2}
//		{serviceId=1, serviceName=SDN Director, serviceCheckName=SDN Director Service Check, serviceTestId=4, serviceTestName=Check nginx process status, serviceTypeName=ssh, serviceTypeTableName=t_port_config, configId=3}

		
		ServiceCheckRequest request = new ServiceCheckRequest();
		PingConfiguration pingConfig1 = new PingConfiguration();
//		PingConfiguration pingConfig2 = new PingConfiguration();
		PortConfiguration portConfig = new PortConfiguration();
		
		pingConfig1.setHost("184.169.202.188");
//		pingConfig2.setHost("google.com");
		portConfig.setHost("184.169.202.188");
		portConfig.setPort(10010);
		
		ServiceTest<PingConfiguration> serviceTest = new ServiceTest<PingConfiguration>("ping","Ping 184.169.202.188",request.getRequestId(),pingConfig1);
		serviceTest.setServiceTestType("ping");
		request.addServiceTest(serviceTest);
		ServiceTest<PortConfiguration> portTest = new ServiceTest<PortConfiguration>("port","Check plumgrid port",request.getRequestId(),portConfig);
		portTest.setServiceTestType("port");
		request.addServiceTest(portTest);
//		request.addServiceTest(new ServiceTest<PingConfiguration>("ping","Google Web Search",request.getRequestId(),pingConfig2));
		
		message.setBody(request);
	}
}
