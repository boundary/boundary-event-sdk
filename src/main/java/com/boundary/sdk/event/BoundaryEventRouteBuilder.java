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
package com.boundary.sdk.event;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.AuthMethod;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.http.HttpConfiguration;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.apache.camel.LoggingLevel.*;

/**
 * {@link RouteBuilder} for sending events to Boundary. Accepts serialized {@link RawEvent}
 * that is transformed to JSON.
 *  
 * @author davidg
 *
 */
public class BoundaryEventRouteBuilder extends BoundaryRouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(BoundaryEventRouteBuilder.class);

	private String apiHost;
	private int apiPort;
	private String orgId;
	private String apiKey;

	/**
	 * Default constructor
	 */
	public BoundaryEventRouteBuilder() {
		this.apiHost = "api.boundary.com";
		this.apiPort = 443;
		this.orgId = "";
		this.apiKey = "";
		this.fromUri = "direct:boundary-event";
	}
	
	/**
	 * Get the Boundary Organization ID to use by default 
	 * @return {@link String} organization id
	 */
	public String getOrgId() {
		return this.orgId;
	}
		
	/**
	 * Set the Boundary Organization ID to use by default
	 * 
	 * @param orgId Organization Id from the Boundary console.
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
	 * @return {@link String}
	 */
	public String getApiKey() {
		return this.apiKey;
	}
		
	/**
	 * Set the host to use for sending Boundary API requests
	 * 
	 * @param apiHost Host that is running the Boundary API
	 */
	public void setApiHost(String apiHost) {
		this.apiHost = apiHost;
	}
	
	/**
	 * Return the current Boundary API host
	 * 
	 * @return {@link String}
	 */
	public String getApiHost() {
		return this.apiHost;
	}

	/**
	 * Set the port to use for sending Boundary API requests
	 * 
	 * @param apiPort Port to send Boundary API requests
	 */
	public void setApiPort(int apiPort) {
		this.apiPort = apiPort;
	}
	
	/**
	 * Set the port to use for sending Boundary API requests
	 * 
	 * @return int Gets the port used to send Boundary API requests
	 */
	public int getApiPort() {
		return this.apiPort;
	}
	
	/**
	 * Configures the Camel route that receives {@link RawEvent}
	 * and then sends to the Boundary API
	 * 
	 */
	@Override
	public void configure() {
		
		// Create the URL used to send events
		String url = "https://" + apiHost + ":" + apiPort + "/" + orgId + "/" + "events";
		
		LOG.info("boundary event api url: " + url);
		
		// Configure our HTTP connection to use BASIC authentication
		HttpConfiguration config = new HttpConfiguration();
		config.setAuthMethod(AuthMethod.Basic);
		config.setAuthUsername(this.apiKey);
		config.setAuthPassword("");
		
		HttpComponent http = this.getContext().getComponent("https",HttpComponent.class);
		http.setHttpConfiguration(config);
		
		from(fromUri)
			.startupOrder(startUpOrder)
			.routeId(routeId)
			.unmarshal().serialization()
			.marshal().json(JsonLibrary.Jackson)
			.log(INFO,"RawEvent: ${body}")
			.setHeader(Exchange.ACCEPT_CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
			.setHeader(Exchange.HTTP_METHOD, constant("POST"))
			.to("log:com.boundary.sdk.event.BoundaryEventRouteBuilder?level=INFO&groupInterval=60000&groupDelay=60000&groupActiveOnly=false")
			.to(url.toString())
			.log(DEBUG,"HTTP Method: ${headers.CamelHttpMethod},AcceptContentType={headers.CamelAcceptContentType}")
			.log(INFO,"HTTP Response Code: ${headers.CamelHttpResponseCode},Location: ${headers.Location}")
			;
	}
}
