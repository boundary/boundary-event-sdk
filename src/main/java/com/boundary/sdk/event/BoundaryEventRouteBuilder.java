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
 */
public class BoundaryEventRouteBuilder extends BoundaryRouteBuilder {
	
	private final String DEFAULT_HOST="premimum-api.boundary.com";
	private final int DEFAULT_PORT = 443;
	private final String DEFAULT_FROM_URI = "direct:boundary-event";
	
	private static Logger LOG = LoggerFactory.getLogger(BoundaryEventRouteBuilder.class);

	private String host;
	private int port;
	private String user;
	private String password;

	/**
	 * Default constructor
	 */
	public BoundaryEventRouteBuilder() {
		this.host = DEFAULT_HOST;
		this.port = DEFAULT_PORT;
		this.fromUri = DEFAULT_FROM_URI;
		this.password = "";
	}
			
	/**
	 * Set the host to use for sending Boundary API requests
	 * 
	 * @param host Host that is running the Boundary API
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Return the current Boundary API host
	 * 
	 * @return {@link String}
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * Set the port to use for sending Boundary API requests
	 * 
	 * @param apiPort Port to send Boundary API requests
	 */
	public void setPort(int apiPort) {
		this.port = apiPort;
	}
	
	/**
	 * Get the port to use for sending Boundary API requests
	 * 
	 * @return int Gets the port used to send Boundary API requests
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Gets the e-mail used for sending Boundary API requests
	 * @return {@link String} e-mail
	 */
	public String getUser() {
		return user;
	}

	/**
	 * Sets the e-mail use for sending Boundary API requests
	 * @param email E-mail associated with the Boundary instance
	 */
	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String apiToken) {
		this.password = apiToken;
	}

	/**
	 * Configures the Camel route that receives {@link RawEvent}
	 * and then sends to the Boundary API
	 * 
	 */
	@Override
	public void configure() {
		// Create the URL used to send events
		String url = String.format("https://%s:%d/events",host,port);
		LOG.debug("boundary event api url: " + url);
		
		// Configure our HTTP connection to use BASIC authentication
		HttpConfiguration config = new HttpConfiguration();
		config.setAuthMethod(AuthMethod.Basic);
		config.setAuthUsername(this.getUser());
		config.setAuthPassword(this.getPassword());
		
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
