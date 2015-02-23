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

import org.apache.camel.component.snmp.SnmpMessage;

import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.UDPRouteBuilder;

public abstract class SnmpBaseRouteBuilder extends UDPRouteBuilder{


	protected String mibRepositoryPath;
	protected String license;
	protected boolean convertToEvent;

	public SnmpBaseRouteBuilder() {
		super();
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

}
