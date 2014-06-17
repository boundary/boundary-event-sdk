package com.boundary.sdk.event.service;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.boundary.camel.component.ping.PingConfiguration;
import com.boundary.camel.component.port.PortConfiguration;
import com.boundary.sdk.event.RawEvent;

import static com.boundary.sdk.event.service.ServiceCheckPropertyNames.*;

public class ServiceCheckToRawEvent {

	/**
	 * 
	 * @param exchange {@link Exchange} from a Camel route
	 */
	public void toEvent(Exchange exchange) {
		Message message = exchange.getIn();
		ServiceCheckRequest request = message.getHeader(SERVICE_CHECK_REQUEST_INSTANCE,ServiceCheckRequest.class);
		ServiceCheckResults results = message.getBody(ServiceCheckResults.class);
		
		RawEvent event = new RawEvent();
		
		message.setBody(event);
	}
}

