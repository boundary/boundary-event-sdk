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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PollerEntry {
	
	@JsonProperty
	private long id;
	@JsonProperty
	private String name;
	@JsonProperty
	private String description;
	@JsonProperty
	private long delay;
	@JsonProperty("host-lists")
	private List<HostListRef> hostLists;
	@JsonProperty("oid-lists")
	private List<OidListRef> oidLists;
	
	public PollerEntry() {
		this.hostLists = new ArrayList<HostListRef>();
		this.oidLists = new ArrayList<OidListRef>();
	}
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
	public long getDelay() {
		return delay;
	}
	public void setDelay(long delay) {
		this.delay = delay;
	}
	public List<HostListRef> getHostLists() {
		return hostLists;
	}
	public void setHostLists(List<HostListRef> hostLists) {
		this.hostLists = hostLists;
	}
	public List<OidListRef> getOidList() {
		return oidLists;
	}
	public void setOidList(List<OidListRef> oidLists) {
		this.oidLists = oidLists;
	}
	
	public List<Long> getHostListIds() {
		Set<Long> ids = new LinkedHashSet<Long>();
		for (HostListRef ref : hostLists) {
			if (ref.isEnabled()) {
				ids.add(ref.getId());
			}
		}
		
		ArrayList<Long> list = new ArrayList<Long>();
		list.addAll(ids);
		return list;
	}
	
	public List<Long> getOidListIds() {
		Set<Long> ids = new LinkedHashSet<Long>();
		for (OidListRef ref : oidLists) {
			if (ref.isEnabled()) {
				ids.add(ref.getId());
			}
		}
		
		ArrayList<Long> list = new ArrayList<Long>();
		list.addAll(ids);
		return list;
	}
	
	@Override
	public String toString() {
		return "Pollers [id=" + id + ", name=" + name + ", description="
				+ description + ", delay=" + delay + ", hostLists=" + hostLists
				+ ", oidList=" + oidLists + "]";
	}
}
