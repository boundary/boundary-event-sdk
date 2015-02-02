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

public class OidLists {
	
	@JsonProperty("oid-list")
	private List<OidListEntry> oidList;
	
	public List<OidListEntry> getOidList() {
		return oidList;
	}

	public void setOidList(List<OidListEntry> oidList) {
		this.oidList = oidList;
	}

	public static OidLists load(String resource) throws URISyntaxException {
		OidLists instance = new OidLists();

		ClassLoader classLoader = instance.getClass().getClassLoader();
		URL url = classLoader.getResource(resource);
		File file = new File(url.toURI());

		ObjectMapper mapper = new ObjectMapper();

		try {
			instance = mapper.readValue(file,OidLists.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}
	
	public List<Oid> getOids(List<Long> ids) {
		Set<Oid> oids = new LinkedHashSet<Oid>();
		
		for (OidListEntry entry : oidList) {
			if (ids.contains(entry.getId())) {
				for (Oid oid : entry.getOids()) {
					if (oid.isEnabled()) {
						oids.add(oid);
					}
				}
			}
		}
		
		List<Oid> list = new ArrayList<Oid>();
		list.addAll(oids);
		return list;
	}

	@Override
	public String toString() {
		return "OidLists [oidList=" + oidList + "]";
	}
}
