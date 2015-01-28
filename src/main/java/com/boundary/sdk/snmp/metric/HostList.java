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

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * List of hosts to be polled for SNMP metrics
 *
 */
public class HostList {
//	{
//		"id": 1,
//		"name": "Web Servers",
//		"description": "Web Servers to monitor via SNMP",
//		"port": 161,
//		"community-read": "public",
//		"hosts": [
//			"web-001",
//			"web-002",
//			"web-003",
//			"web-004",
//			"web-005"
//		]
//	},
	@JsonProperty
	private long id;
	@JsonProperty
	private String name;
	@JsonProperty
	String description;
	@JsonProperty
	long port;
	@JsonProperty("community-read")
	String defaultCommunityRead;
	@JsonProperty
	private List<String> hosts;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getPort() {
		return port;
	}
	public void setPort(long port) {
		this.port = port;
	}
	public String getDefaultCommunityRead() {
		return defaultCommunityRead;
	}
	public void setDefaultCommunityRead(String defaultCommunityRead) {
		this.defaultCommunityRead = defaultCommunityRead;
	}
	public List<String> getHosts() {
		return hosts;
	}
	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
	}
	
	@Override
	public String toString() {
		return "HostList [id=" + id + ", name=" + name + ", description="
				+ description + ", port=" + port + ", defaultCommunityRead="
				+ defaultCommunityRead + ", hosts=" + hosts + "]";
	}
}
