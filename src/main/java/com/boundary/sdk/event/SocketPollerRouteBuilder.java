package com.boundary.sdk.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.net.ConnectException;
//import java.nio.channels.ClosedChannelException;

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

	public SocketPollerRouteBuilder() {
		// Default to poll every minute
		this.cron = "0/60+*+*+*+*+?";
	}
	
	/**
	 * 
	 * @param cron
	 */
	public void setCron(String cron) {
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void configure() throws Exception {		
		String socketUri = String.format("netty:tcp://" + this.host + ":" + this.port + "?sync=false");
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
}
