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

package com.boundary.sdk.api;

import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.BoundaryRouteBuilder;

/**
 * {@link RouteBuilder} for sending events to Boundary. Accepts serialized requests
 * to invoked the Boundary API
 */
public abstract class BoundaryAPIRouteBuilder extends BoundaryRouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(BoundaryAPIRouteBuilder.class);
	
	private final String HTTP="http";
	private final String HTTPS="https";
	protected final String HTTP_AUTHORIZATION="Authorization";
	
	private final String DEFAULT_SCHEME="https";
	private static final int DEFAULT_PORT = -1;

	private String scheme;
	private String user;
	private String password;
	private String host;
	private int port;
	private String path;
	private String authentication;

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
	 * Get the host to use for sending API requests
	 * 
	 * @return {@link String}
	 */
	public String getHost() {
		return this.host;
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
	 * Get the target port of the end point
	 * @return Port number of the listening port of the API
	 */
	public int getPort() {
		return port;
	}

	/**
	 * Set the target port of the end point.
	 * @param port Port number of the listening port of the API
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * Returns the URL for the API call
	 * @return {@link String}
	 */
	public String getUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(HTTPS);
		sb.append("://");
		sb.append(getHost());
		sb.append(getPort() == DEFAULT_PORT ? "" : ":" + getPort());
		sb.append("/");
		sb.append(getPath() == null ? "" : getPath());

		return sb.toString();
	}
	/**
	 * Configure the API end point
	 */
	protected void setConfiguration() {
		
		LOG.info("SCHEME: {}",getScheme());

		switch(getScheme()) {
		case "https":
		case "http":
			if (getUser() != null || getPassword() != null) {
				StringBuilder sb = new StringBuilder();
				if (getUser() != null) {
					sb.append(getUser());
				}
				sb.append(":");
				if (getPassword() != null) {
					sb.append(getPassword());
				}
				setAuthentication(Base64.encodeBase64String(sb.toString().getBytes()));
			}
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

	
	public String getAuthentication() {
		return authentication;
	}

	public void setAuthentication(String authentication) {
		this.authentication = authentication;
	}

	/**
	 * Child classes are required to override
	 */
	@Override
	public abstract void configure();
}
