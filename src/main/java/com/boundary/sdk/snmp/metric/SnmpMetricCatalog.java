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
package com.boundary.sdk.snmp.metric;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Master catalog of meta for SNMP metric collection
 */
public class SnmpMetricCatalog {
	
	@JsonProperty("oid-list")
	private List<OidList> oidList;
	@JsonProperty("host-lists")
	private List<HostList> hostList;
	@JsonProperty
	private List<Pollers> pollers;
	
	public SnmpMetricCatalog() {
		this.oidList = new ArrayList<OidList>();
		this.hostList = new ArrayList<HostList>();
		this.pollers = new ArrayList<Pollers>();
	}

	public List<OidList> getOidList() {
		return oidList;
	}

	public void setOidList(List<OidList> oidList) {
		this.oidList = oidList;
	}

	public List<HostList> getHostList() {
		return hostList;
	}

	public void setHostList(List<HostList> hostList) {
		this.hostList = hostList;
	}

	public List<Pollers> getPollers() {
		return pollers;
	}

	public void setPollers(List<Pollers> pollers) {
		this.pollers = pollers;
	}
	
	
}
