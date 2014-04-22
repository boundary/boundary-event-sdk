package com.boundary.sdk;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.component.syslog.Rfc3164SyslogDataFormat;

public class SysLogRouteBuilder extends RouteBuilder {
	
	private int port;
	private String toUri;
	private String routeId;
	
	private final String DEFAULT_SYSLOG_ROUTE_NAME="SYSLOG-ROUTE";
	private final int DEFAULT_SYSLOG_PORT = 1514;
	

	/**
	 * Constructor
	 */
	public SysLogRouteBuilder() {
		this.toUri = BoundaryRouteBuilder.DEFAULT_EVENT_TO_URI;
		this.routeId = DEFAULT_SYSLOG_ROUTE_NAME;
		this.port = DEFAULT_SYSLOG_PORT;
	}
	
	/**
	 * Sets the port to listen for forwarded syslog messages
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Sets the URI to send events to
	 * @param toUri
	 */
	public void setToUri(String toUri)  {
		this.toUri = toUri;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getToUri() {
		return this.toUri;
	}
	
	/**
	 * Sets the route ID to a defined value, make it easier to track
	 * the route in the logs.
	 * 
	 * @param routeId
	 */
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRouteId() {
		return this.routeId;
	}
	
	@Override
	public void configure() {
		String uri = "netty:udp://127.0.0.1:" + port + "?sync=false&allowDefaultCodec=false";

        DataFormat syslogDataFormat = new Rfc3164SyslogDataFormat();
		from(uri)
		.routeId(routeId)
		.unmarshal(syslogDataFormat)
		.process(new SyslogToEventProcessor())
		.marshal().serialization()
        .to(toUri)
        ;
	}
}
