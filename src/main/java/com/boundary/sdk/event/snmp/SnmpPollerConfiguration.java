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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

public class SnmpPollerConfiguration {
	
	private String host;
	private long port;
	private Set<String> oids;
	private String community;
	private long delay;
	
	public SnmpPollerConfiguration() {
		oids = new LinkedHashSet<String>();
	}
		
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public long getPort() {
		return port;
	}

	public void setPort(long port) {
		this.port = port;
	}

	public void addOid(String oid) {
		oids.add(oid);
	}
	
	public String getOidsAsString() {
		StringBuilder builder = new StringBuilder();
		
		boolean first = true;
		for (String oid: oids) {
			if (first == false) {
				builder.append(",");
			}
			builder.append(oid);
			first = false;
		}
		
		return builder.toString();
	}
	
	public Set<String> getOids() {
		return this.oids;
	}

	public String getCommunity() {
		return community;
	}

	public void setCommunity(String community) {
		this.community = community;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}
}
