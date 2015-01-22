package com.boundary.sdk.event.syslog;

import org.apache.camel.model.RouteDefinition;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.component.syslog.SyslogDataFormat;

import com.boundary.sdk.event.BoundaryRouteBuilder;
import com.boundary.sdk.event.UDPRouteBuilder;
import com.boundary.sdk.event.RawEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SysLogRouteBuilder extends UDPRouteBuilder {

	private static Logger LOG = LoggerFactory.getLogger(SysLogRouteBuilder.class);
	
	private final String DEFAULT_SYSLOG_ROUTE_NAME="SYSLOG-ROUTE";
	private final int DEFAULT_SYSLOG_PORT = 1514;
	private SyslogToEventProcessor eventProcessor;

	/**
	 * Default Constructor
	 */
	public SysLogRouteBuilder() {
		this.toUri = BoundaryRouteBuilder.DEFAULT_EVENT_TO_URI;
		this.routeId = DEFAULT_SYSLOG_ROUTE_NAME;
		this.port = DEFAULT_SYSLOG_PORT;
		this.eventProcessor = new SyslogToEventProcessor();
	}
	
	/**
	 * 
	 * @param convertToEvent flag to indicate conversion to {@link RawEvent}
	 */
	public SysLogRouteBuilder(boolean convertToEvent) {
		this();
		this.eventProcessor.setConvertToEvent(convertToEvent);
	}
	
	/**
	 * Builds route for handling message forwarded from syslog.
	 */
	@Override
	public void configure() {
		String uri = "netty:udp://"+ getBindAddress() + ":" + getPort() + "?sync=false&allowDefaultCodec=false";
        DataFormat syslogDataFormat = new SyslogDataFormat();
		RouteDefinition routeDefinition = from(uri);
		
		routeDefinition
		.startupOrder(startUpOrder)
		.routeId(routeId)
		.unmarshal(syslogDataFormat)
		.process(this.eventProcessor)
		.marshal().serialization()
        .to(toUri)
        ;
	}

	public boolean isConvertToEvent() {
		return this.eventProcessor.isConvertToEvent();
	}

	public void setConvertToEvent(boolean convertToEvent) {
		this.eventProcessor.setConvertToEvent(convertToEvent);
	}
}
