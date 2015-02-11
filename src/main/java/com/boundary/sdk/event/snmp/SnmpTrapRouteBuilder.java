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
package com.boundary.sdk.event.snmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.RawEvent;
import org.apache.camel.component.snmp.SnmpMessage;
import com.boundary.sdk.event.UDPRouteBuilder;

/**
 * Route builder that receives SNMP Traps and 
 * extracts the variable bindings from them
 *
 */
public class SnmpTrapRouteBuilder extends UDPRouteBuilder {
	
	private static Logger LOG = LoggerFactory.getLogger(SnmpTrapRouteBuilder.class);
	
	private final int DEFAULT_SNMP_PORT=162;
	
	protected String mibRepositoryPath;
	protected String license;
	protected boolean convertToEvent;

	/**
	 * Default constructor
	 */
	public SnmpTrapRouteBuilder() {
		this.port = DEFAULT_SNMP_PORT;
		this.mibRepositoryPath="";
		this.license = "";
		this.convertToEvent = true;
	}
	
	/**
	 * Sets the path to the directory
	 * to the compiled MIBs.
	 * 
	 * @param path File path to directory
	 */
	public void setMibRepository(String path) {
		this.mibRepositoryPath = path;
	}
	
	/**
	 * Returns the currently configured path
	 * to the compiled MIBs
	 * 
	 * @return {@link String} with the path to the MIB repository
	 */
	public String getMibRepository() {
		return this.mibRepositoryPath;
	}
	
	/**
	 * Sets the SNMP4J-SMI license key
	 * 
	 * @param license {@link String} containing the license
	 */
	public void setLicense(String license) {
		this.license = license;
	}
	
	/**
	 * Returns the current value of the SNMP4J-SMI license key
	 * 
	 * @return {@link String} contain the configured license key
	 */
	public String getLicense() {
		return this.license;
	}
	
	/**
	 * Returns the whether the {@link SnmpMessage} is to be converted into {@link RawEvent}
	 * 
	 * @return boolean, true - convert event ; false - do not convert
	 */
	public boolean isConvertToEvent() {
		return convertToEvent;
	}

	/**
	 * Set whether the {@link SnmpMessage} is converted into a {@link RawEvent}
	 * 
	 * @param convertToEvent Flag indicating behavior
	 */
	public void setConvertToEvent(boolean convertToEvent) {
		this.convertToEvent = convertToEvent;
	}

	/**
	 * Configuration for the SNMP route 
	 */
	@Override
	public void configure() {
		String uri = String.format("snmp:%s:%d?protocol=udp&type=TRAP",getBindAddress(),getPort());
		from(uri)
		.startupOrder(startUpOrder)
		.routeId(this.routeId)
		.to("log:com.boundary.sdk.event.snmp.SnmpRouteBuilder?level=DEBUG&showBody=true&showHeaders=true")
		.process(new SnmpMessageToVarBinds(getMibRepository(),getLicense()))
		.marshal().serialization()
		.to(getToUri())
		;
	}
}
