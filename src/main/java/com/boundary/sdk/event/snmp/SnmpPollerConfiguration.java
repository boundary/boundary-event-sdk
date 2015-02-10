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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.boundary.sdk.snmp.metric.OidMap;

public class SnmpPollerConfiguration {
	
	private static final long DEFAULT_SNMP_PORT = 161;
	private static final String DEFAULT_HOST="localhost";
	private static final String DEFAULT_COMMUNITY_READ="public";
	private static final long DEFAULT_DELAY = 5;
	private String host;
	private long port;
	private List<OidMap> oids;
	private String communityRead;
	private long delay;
	
	public SnmpPollerConfiguration() {
		this.oids = new ArrayList<OidMap>();
		this.host = DEFAULT_HOST;
		this.port = DEFAULT_SNMP_PORT;
		this.communityRead = DEFAULT_COMMUNITY_READ;
		this.delay = DEFAULT_DELAY;
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

	public void addOid(OidMap oid) {
		oids.add(oid);
	}
	
	public Map<String,OidMap> getOidMap() {
		Map<String,OidMap> map = new HashMap<String,OidMap>();
		
		for (OidMap oid: oids) {
			map.put(oid.getOid(), oid);
		}
		
		return map;
	}
	
	public String getOidsAsString() {
		StringBuilder builder = new StringBuilder();
		
		boolean first = true;
		for (OidMap oid: oids) {
			if (first == false) {
				builder.append(",");
			}
			builder.append(oid.getOid());
			first = false;
		}
		
		return builder.toString();
	}
	
	public void setOids(List<OidMap> oids) {
		this.oids = oids;
	}
	
	public List<OidMap> getOids() {
		return this.oids;
	}

	public String getCommunityRead() {
		return communityRead;
	}

	public void setCommunityRead(String communityRead) {
		this.communityRead = communityRead;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	@Override
	public String toString() {
		return "SnmpPollerConfiguration [host=" + host + ", port=" + port
				+ ", oids=" + oids + ", communityRead=" + communityRead
				+ ", delay=" + delay + "]";
	}

}
