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

import com.fasterxml.jackson.annotation.JsonProperty;

public class Host {
	
	public final static long UKNOWN_PORT=-1;
	
	@JsonProperty
	private String host;
	@JsonProperty("community-read")
	private String communityRead;
	@JsonProperty
	private long port;
	
	public Host() {
		this.port = UKNOWN_PORT;
	}
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getCommunityRead() {
		return communityRead;
	}
	public void setCommunityRead(String communityRead) {
		this.communityRead = communityRead;
	}
	public long getPort() {
		return port;
	}
	public void setPort(long port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "Host [host=" + host + ", communityRead=" + communityRead
				+ ", port=" + port + "]";
	}
}
