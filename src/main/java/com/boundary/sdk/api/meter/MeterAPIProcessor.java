package com.boundary.sdk.api.meter;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

/**
 * Handles the process of the different API calls
 *
 */
public class MeterAPIProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		MeterAPI api = message.getBody(MeterAPI.class);
		
		message.setBody(api.getData());
		message.setHeader(Exchange.HTTP_PATH, api.getPath());
		message.setHeader(Exchange.HTTP_METHOD,api.getMethod());
	}

}
