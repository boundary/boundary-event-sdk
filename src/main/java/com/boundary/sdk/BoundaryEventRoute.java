package com.boundary.sdk;

import java.util.Map;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
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
 * {@link RouteBuilder} for sending events to Boundary.
 * @author davidg
 *
 */
public class BoundaryEventRoute extends RouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(BoundaryEventRoute.class);

	private String apiHost;
	private String orgId;
	private String apiKey;
	private String fromUri;
	private String routeId;
	
	public BoundaryEventRoute() {
		this.apiHost = "api.boundary.com";
		this.orgId = "";
		this.apiKey = "";
		this.fromUri = "direct:boundary-event";
	}
		
	/**
	 * Set the Boundary Organization ID to use by default
	 * 
	 * @param orgId Organizationa Id from the Boundary console.
	 */
	
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
	/**
	 * Set the Boundary authentication key
	 * 
	 * @param apiKey API key from the Boundary Console
	 */
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
	/**
	 * Current value of the API key
	 * 
	 * @return String
	 */
	public String getApiKey() {
		return this.apiKey;
	}
		
	/**
	 * Set the host to use for sending Boundary API requests
	 * 
	 * @param apiHost
	 */
	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}
	
	/**
	 * Return the current Boundary API host
	 * 
	 * @return
	 */
	public String getApiHost() {
		return this.apiHost;
	}
		
	/**
	 * URI used to receive {@link RawEvents} from.
	 * @param fromUri
	 */
	public void setFromUri(String fromUri) {
		this.fromUri = fromUri;
	}
	
	/**
	 * Get the current URI that is receiving {@link RawEvents}
	 * 
	 * @return
	 */
	public String getFromUri() {
		return this.fromUri;
	}

	/**
	 * Set the route Id which provides a well-known name in JMX
	 * and Camel logs.
	 * 
	 * @param routeId
	 */
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	
	/**
	 * Return current value of the route Id
	 * 
	 * @return String
	 */
	public String getRouteId() {
		return this.routeId;
	}
	
	/**
	 * Configures the Camel route that receives {@link RawEvents}
	 * and then sends to the Boundary API
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
		
		from(fromUri)
			.routeId(routeId)
			.unmarshal().serialization()
			.marshal().json(JsonLibrary.Jackson)
			.setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))			.to("log:com.boundary.sdk.BoundaryEventRoute?level=DEBUG&groupInterval=10000&groupDelay=60000&groupActiveOnly=false")
			.to(url.toString())
			.log(LoggingLevel.DEBUG, "created event")
			;
	}
}
