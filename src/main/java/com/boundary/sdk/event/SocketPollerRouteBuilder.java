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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates a route that checks periodically checks a port on a host
 * to see if it available.
 * 
 * @author davidg
 *
 */
public class SocketPollerRouteBuilder extends UDPRouteBuilder {

	
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(SocketPollerRouteBuilder.class);

	private String cron;
	private String host;

	public SocketPollerRouteBuilder() {
		// Default to poll every minute
		this.cron = "0/60+*+*+*+*+?";
	}
	
	/**
	 * 
	 * @param cron Quartz cron string
	 */
	public void setCron(String cron) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void configure() throws Exception {		
		String socketUri = String.format("netty:tcp://" + this.getHost() + ":" + this.getPort() + "?sync=false");
		String timerUri = String.format("quartz://" + this.routeId + "?cron=" + this.cron);
		LOG.info("Polling host:  " + this.host + " on port: " + this.port + " with this schedule: " + this.cron);

		from(timerUri)
		.routeId(this.routeId)
		// These are the exception classes that indicate the connection failed.
		.onException(java.net.ConnectException.class,java.nio.channels.ClosedChannelException.class)
		.handled(true) // Mark these exceptions as handled
		.bean(HostConnectionFailure.class)
		.to("log:com.boundary.sdk.SocketPollerRouteBuilder?level=INFO&showHeaders=true&showBody=true&multiline=true")
		.marshal().serialization() // Marshal the RawEvent before sending
		.to(this.toUri)
		.end()
		.transform(constant("")) // Netty requires a body to initiate the connection. Set body to an empty string.
		.to(socketUri)
		.to("log:com.boundary.sdk.SocketPollerRouteBuilder?level=INFO&showHeaders=true&showBody=true&multiline=true")
		// Connection succeeds
		;
	}

	private String getHost() {
		return host;
	}
}
