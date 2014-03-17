package com.boundary.sdk;

import java.util.Map;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.AuthMethod;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.http.HttpConfiguration;

/**
 * 
 * @author davidg
 *
 */
public class EventRoute extends BoundaryEventRoute {

	private String apiHost = "api.boundary.com";
	private String orgID;
	private String apiKey;
	private String authorization;
	private StringBuffer url = new StringBuffer();
	private String contentType = "application/json";

	public EventRoute(String orgID, String apiKey) {
		super(orgID, apiKey,"EVENT-ROUTE","event");
	}
}
