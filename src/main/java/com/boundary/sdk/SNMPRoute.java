package com.boundary.sdk;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Route builder that receives SNMP messages into {@link RawEvent}
 * and then sends to Boundary event route.
 * 
 * @author davidg
 *
 */
public class SNMPRoute extends UDPRouterBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(SNMPRoute.class);
	
	private final int DEFAULT_SNMP_PORT=162;
	
	private String mibRepositoryPath;

	public SNMPRoute() {
		this.port = DEFAULT_SNMP_PORT;
		this.mibRepositoryPath="";
	}
	
	/**
	 * Sets the path to the directory
	 * to the compiled MIBs.
	 * 
	 * @param path
	 */
	public void setMibRepository(String path) {
		this.mibRepositoryPath = path;
	}
	
	/**
	 * Returns the currently configured path
	 * to the compiled MIBs
	 * @return
	 */
	public String getMibRepository() {
		return this.mibRepositoryPath;
	}
	
	/**
	 * Configuration for the SNMP route 
	 */
	@Override
	public void configure() {
		// TBD, verify if I have to provide host property
		// TBD, Should the trap listener be bound to port 0.0.0.0??
		// TBD, Support of TCP??
		String uri = "snmp:127.0.0.1:" + this.port + "?protocol=udp&type=TRAP";
		from(uri)
		.routeId(this.routeId)
		.to("log:" + this.getClass().toString() + "?level=INFO&showBody=true&showHeaders=true")
		.process(new SNMPToEventProcessor())
		.marshal().serialization()
		.to(toUri)
		;
	}
}
