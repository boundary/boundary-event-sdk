package com.boundary.sdk.event.service;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;

public class ExtractServiceTestConfiguration {

	public PingConfiguration getPingConfiguration(ServiceTest<PingConfiguration> serviceTest) {
		return serviceTest.getConfiguration();
	}
	
	/**
	 * Extract the {@link PortConfiguration} from the {@link ServiceTest} and
	 * set as header in {@link Message}
	 * @param exchange
	 */
	public void extractPortConfiguration(Exchange exchange) {
		Message message = exchange.getIn();
		ServiceTest<PortConfiguration> serviceTest = message.getBody(ServiceTest.class);
		PortConfiguration configuration = serviceTest.getConfiguration();
		
		message.setHeader("serviceTest", serviceTest);
		message.setBody(configuration);
	}
	
	/**
	 * Extract the {@link PingConfiguration} from the {@link ServiceTest} and
	 * set as header in {@link Message}
	 * @param exchange
	 */
	public void extractPingConfiguration(Exchange exchange) {
		Message message = exchange.getIn();
		ServiceTest<PingConfiguration> serviceTest = message.getBody(ServiceTest.class);
		PingConfiguration configuration = serviceTest.getConfiguration();
		
		message.setHeader("serviceTest", serviceTest);
		message.setBody(configuration);
	}
}
