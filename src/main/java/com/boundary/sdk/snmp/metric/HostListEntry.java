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

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;


public class HostListEntry {
	
	private final static String DEFAULT_COMMUNITY_READ="public";
	private final static long DEFAULT_PORT=161;

	@JsonProperty
	private Long id;
	@JsonProperty
	private String name;
	@JsonProperty
	String description;
	@JsonProperty
	long port;
	@NotNull
	@JsonProperty("community-read")
	String communityRead;
	@JsonProperty
	private List<Host> hosts;
	
	public HostListEntry() {
		this.port = DEFAULT_PORT;
		this.communityRead = DEFAULT_COMMUNITY_READ;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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

	public String getCommunityRead() {
		return communityRead;
	}
	public void setCommunityRead(String communityRead) {
		this.communityRead = communityRead;
	}
	public List<Host> getHosts() {
		return hosts;
	}
	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}
	
	@Override
	public String toString() {
		return "HostListEntry [id=" + id + ", name=" + name + ", description="
				+ description + ", port=" + port + ", communityRead="
				+ communityRead + ", hosts=" + hosts + "]";
	}

}
