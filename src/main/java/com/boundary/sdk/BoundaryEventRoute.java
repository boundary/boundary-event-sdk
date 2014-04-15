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
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author davidg
 *
 */
public class BoundaryEventRoute extends RouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(BoundaryEventRoute.class);

	protected String apiHost;
	protected String orgId;
	protected String apiKey;
	protected String endPoint;
	protected String routeId;
	
	public BoundaryEventRoute() {
		this.apiHost = "api.boundary.com";
		this.orgId = "";
		this.apiKey = "";
		this.endPoint = "direct:boundary-event";
	}
	
	protected String getBody(Exchange exchange) {
		String s1 = "{ \"title\": \"example\", \"message\": \"test\",\"tags\": [\"example\", \"test\", \"stuff\"], \"fingerprintFields\": [\"@title\"], \"source\": { \"ref\": \"myhost\",\"type\": \"host\"}}";
		String s2 = "{ \"title\": \"%s\", \"severity\": \"%s\",\"status\":\"OPEN\",\"message\": \"test\",\"fingerprintFields\": [\"@title\"], \"source\": { \"ref\": \"myhost\",\"type\": \"host\"}}";
		Message m = exchange.getIn();
		RawEvent event = (RawEvent) m.getBody(RawEvent.class);
		LOG.debug("EVENT: " + event);
		
		String result = String.format(s2,event.getTitle(),event.getSeverity().toString());
		
		return result;
	}
	
	/**
	 * 
	 * @param orgId
	 */
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/**
	 * 
	 * @param apiKey
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	public String getApiKey() {
		return this.apiKey;
	}
		
	/**
	 * 
	 * @param apiHost
	 */
	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}
	
	/**
	 * 
	 * @param endPoint
	 */
	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}
	
	/**
	 * 
	 * @param routeId
	 */
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	
	/**
	 * Configuration for sending events to Boundary.
	 * 
	 */
	@Override
	public void configure() {
		
		// Create the URL used to send events
		String url = "https://" + apiHost + "/" + orgId + "/" + "events";
		
		// Configure our HTTP connection to use BASIC authentication
		HttpConfiguration config = new HttpConfiguration();
		config.setAuthMethod(AuthMethod.Basic);
		config.setAuthUsername(this.apiKey);
		config.setAuthPassword("");
		

		HttpComponent http = this.getContext().getComponent("https",HttpComponent.class);
		http.setHttpConfiguration(config);
		
		// TODO: Add route Id
		from(endPoint)
				.unmarshal().serialization()
				.marshal().json(JsonLibrary.Jackson)
				.setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/json"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.to(url.toString());
		;
	}
}
