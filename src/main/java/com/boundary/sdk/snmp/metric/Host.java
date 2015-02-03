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
	@JsonProperty
	private boolean enabled;
	
	public Host() {
		this.port = UKNOWN_PORT;
		this.enabled = true;
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
	
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((communityRead == null) ? 0 : communityRead.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + (int) (port ^ (port >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Host other = (Host) obj;
		if (communityRead == null) {
			if (other.communityRead != null)
				return false;
		} else if (!communityRead.equals(other.communityRead))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Host [host=" + host + ", communityRead=" + communityRead
				+ ", port=" + port + ", enabled=" + enabled + "]";
	}

}
