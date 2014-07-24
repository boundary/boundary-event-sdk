package com.boundary.sdk.event.snmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.mp.SnmpConstants;

public class SnmpPollerRouteBuilder extends SNMPRouteBuilder {
	
	
//	<from uri="snmp:192.168.178.100:161?protocol=udp&amp;type=POLL&amp;oids=1.3.6.1.2.1.1.5.0&amp;snmpVersion=1&amp;snmpCommunity=piston"/>
//	<log message="${body}" />
//	<to uri="mock:snmp-get-out"/>
//	<to uri="stream:out" />
	private static Logger LOG = LoggerFactory.getLogger(SnmpPollerRouteBuilder.class);
	
	SnmpPollerRouteBuilder() {
		communityRead="public";
	}
	
	
	private String communityRead;
	
	private String oids;
	
	public void setOids(String oids) {
		this.oids = oids;
	}
	public String getOids() {
		return this.oids;
	}

	public void setCommunityRead(String communityRead) {
		this.communityRead = communityRead;
	}
	public String getCommunityRead() {
		return this.communityRead;
	}
	

	private String getUri() {
		StringBuffer sb = new StringBuffer();
		sb.append("snmp:");
		sb.append(getBindAddress());
		sb.append(":");
		sb.append(getPort());
		sb.append("?protocol=udp");
		sb.append("&type=POLL");
		sb.append("&oids=" + getOids());
		sb.append("&snmpVersion=" + SnmpConstants.version2c);
		sb.append("&snmpCommunity=" + getCommunityRead());
		LOG.debug("from: " + sb.toString());
		return sb.toString();
	}
	/**
	 * Configuration for the SNMP route 
	 */
	@Override
	public void configure() {
		String uri = getUri();
		
		from(uri)
		.startupOrder(startUpOrder)
		.routeId(this.routeId)
		.log("body: ${body}")
		.to(this.getToUri())
		.to("mock:snmp-poller-out");
		;
	}
	
	public static void main(String [] args) {
		SnmpPollerRouteBuilder poller = new SnmpPollerRouteBuilder();
		
		System.out.println(poller.getUri());
	}

}
