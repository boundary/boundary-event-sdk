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

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * List of hosts to be polled for SNMP metrics
 *
 */
public class HostLists {
	
	@JsonProperty("host-lists")
	private List<HostListEntry> hostLists;
	
	public HostLists() {
		this.hostLists = new ArrayList<HostListEntry>();
	}

	public List<HostListEntry> getHostLists() {
		return hostLists;
	}

	public void setHostList(List<HostListEntry> hostLists) {
		this.hostLists = hostLists;
	}
	
	public List<Host> getHosts(List<Long> ids) {
		Set<Host> hosts = new LinkedHashSet<Host>();
		
		for (HostListEntry entry : hostLists) {
			if (ids.contains(entry.getId())) {
				for (Host host : entry.getHosts()) {
					if (host.isEnabled()) {
						if (host.getPort() == Host.UKNOWN_PORT) {
							host.setPort(entry.getPort());
						}
						if (host.getCommunityRead() == null) {
							host.setCommunityRead(entry.getCommunityRead());
						}
						hosts.add(host);
					}
				}
			}
		}
		
		List<Host> list = new ArrayList<Host>();
		list.addAll(hosts);
		return list;
	}

	public static HostLists load(String resource) throws URISyntaxException {
		HostLists instance = new HostLists();

		ClassLoader classLoader = instance.getClass().getClassLoader();
		URL url = classLoader.getResource(resource);
		File file = new File(url.toURI());

		ObjectMapper mapper = new ObjectMapper();

		try {
			instance = mapper.readValue(file,HostLists.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}

	@Override
	public String toString() {
		return "HostLists [hostLists=" + hostLists + "]";
	}
	
}
