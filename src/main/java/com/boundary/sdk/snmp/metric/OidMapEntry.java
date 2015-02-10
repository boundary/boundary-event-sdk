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
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OidMapEntry {
	@Min(1)
	@JsonProperty
	private long id;
	@NotNull
	@JsonProperty
	private String name;
	@JsonProperty
	private boolean enabled;
	@NotNull
	@JsonProperty
	private List<OidMap> oids;
	
	public OidMapEntry() {
		this.enabled = true;
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
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public List<OidMap> getOids() {
		return oids;
	}
	public void setOids(List<OidMap> oids) {
		this.oids = oids;
	}
	
	@Override
	public String toString() {
		return "OidListEntry [id=" + id + ", name=" + name + ", enabled="
				+ enabled + ", oids=" + oids + "]";
	}
	
}
