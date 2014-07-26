// Copyright 2014 Boundary, Inc.
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

package com.boundary.sdk.api;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.AuthMethod;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.http.HttpConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.BoundaryRouteBuilder;

/**
 * {@link RouteBuilder} for sending events to Boundary. Accepts serialized requests
 * to invoked the Boundary API
 *  
 * @author davidg
 *
 */
public abstract class BoundaryAPIRouteBuilder extends BoundaryRouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(BoundaryAPIRouteBuilder.class);
	
	private final String HTTP="http";
	private final String HTTPS="https";
	
	private final String DEFAULT_SCHEME="https";
	private static final int DEFAULT_PORT = -1;

	private String scheme;
	private String user;
	private String password;
	private String host;
	private int port;
	private String orgId;
	private String path;

	/**
	 * Default constructor
	 */
	public BoundaryAPIRouteBuilder() {
		scheme = DEFAULT_SCHEME;
		port = DEFAULT_PORT;
	}
	
	/**
	 * Set the scheme of the URL, e.g. http,https,ftp,etc.
	 * @return {@link String} scheme used by this API
	 */
	public String getScheme() {
		return scheme;
	}

	/**
	 * Get the scheme of the URL
	 * @param scheme {@link String} scheme
	 */
	public void setScheme(String scheme) {
		this.scheme = scheme;
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
	 * Set the user id
	 * 
	 * @param user {@link String} user id
	 */
	public void setUser(String user) {
		this.user = user;
	}
	
	/**
	 * Get the user id
	 * 
	 * @return {@link String}
	 */
	public String getUser() {
		return this.user;
	}
		
	/**
	 * Set the host to use for sending API requests
	 * 
	 * @param host hostname
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Set the password
	 * @return {@link String} password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Get the password
	 * @param password {@link String} password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the port
	 * @return {@link int}
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Set the port
	 * @param port {@link int} port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * Get the host to use for sending API requests
	 * 
	 * @return {@link String}
	 */
	public String getHost() {
		return this.host;
	}
	
	/**
	 * Returns the URL for the API call
	 * @return {@link String}
	 */
	public String getUrl() {
		StringBuffer sb = new StringBuffer();
		sb.append(HTTPS);
		sb.append("://");
		// User and password are optional
//		if (getUser() != null || getPassword() != null) {
//			sb.append(getUser() == null ? "" : getUser());
//			sb.append(":");
//			sb.append(getPassword());
//			sb.append("@");
//		}
		sb.append(getHost());
		sb.append(getPort() == DEFAULT_PORT ? "" : ":" + getPort());
		sb.append("/");
		sb.append(getPath() == null ? "" : getPath());

		return sb.toString();
	}
	/**
	 * Configure the API end point
	 * 
	 */
	protected void setConfiguration() {
		
		LOG.info("SCHEME: {}",getScheme());

		
		switch(getScheme()) {
		case "https":
			// Configure our HTTP connection to use BASIC authentication
			HttpConfiguration config = new HttpConfiguration();
			config.setAuthMethod(AuthMethod.Basic);
			LOG.info("USER: {}, PASSWORD: {}",getUser(),getPassword());
			config.setAuthUsername(getUser());
			config.setAuthPassword(getPassword());
			HttpComponent http = this.getContext().getComponent("https",HttpComponent.class);
			http.setHttpConfiguration(config);
			break;
		case "http":
			assert false;
			break;
		default:
			assert false;
		}

	}
	
	/**
	 * Get path
	 * @return path {@link String}
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Set path
	 * @param path {@link String}
	 */
	public void setPath(String path) {
		this.path  = path;
	}

	
	/**
	 * Child classes are required to override
	 */
	@Override
	public abstract void configure();
}
