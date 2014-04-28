package com.boundary.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//import java.net.ConnectException;
//import java.nio.channels.ClosedChannelException;

/**
 * Creates a route that checks to see if a port is accessible on a host.
 * 
 * @author davidg
 *
 */
public class SocketPollerRouteBuilder extends UDPRouteBuilder {

	private int repeatInterval;
	
	@SuppressWarnings("unused")
	private static Logger LOG = LoggerFactory.getLogger(SocketPollerRouteBuilder.class);

	public SocketPollerRouteBuilder() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param repeatInterval
	 */
	public void setRepeatInterval(int repeatInterval) {
		this.repeatInterval = repeatInterval;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRepeatInterval() {
		return repeatInterval;
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
		//String socketUri = String.format("mina:tcp://" + this.host + ":" + this.port + "?disconnect=true&sync=false");
		String socketUri = String.format("netty:tcp://" + this.host + ":" + this.port + "?sync=false");

		from("quartz://socketPoller?cron=0+*+*+*+*+?")
		.routeId(this.routeId)
		.onException(java.net.ConnectException.class,java.nio.channels.ClosedChannelException.class)
		.handled(true)
		.bean(HostConnectionFailure.class)
		.to("log:com.boundary.sdk.SocketPollerRouteBuilder?level=INFO&showHeaders=true&showBody=true&multiline=true")
		.marshal().serialization()
		.to(this.toUri)
		.end()
		.transform(constant(""))
		.to(socketUri)
		.to("log:com.boundary.sdk.SocketPollerRouteBuilder?level=INFO&showHeaders=true&showBody=true&multiline=true")
		;
	}
}
