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
package com.boundary.sdk.event;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class TestSource {
	
	@Override
	public String toString() {
		return "TestSource [ref=" + ref + ", type=" + type + ", props=" + properties + "]";
	}
	@JsonProperty
	String ref;
	@JsonProperty
	String type;
	@JsonProperty
	Map<String,Object> properties;
	
	
	public TestSource() {
//		this.ref = "";
//		this.type = "";
//		this.properties = new LinkedHashMap<String,Object>();
	}
	
	public TestSource(String ref,String type) {
		this();
		this.ref = ref;
		this.type = type;
	}
	
	public TestSource setRef(String ref) {
		this.ref = ref;
		return this;
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public TestSource setType(String type) {
		this.type = type;
		return this;
	}
	
	public String getType(String type) {
		return this.type;
	}
	
	private void initProperties() {
		this.properties = new LinkedHashMap<String,Object>();
	}
	public TestSource addProperty(String key,Object value) {
		if (properties == null) {
			initProperties();
		}
		this.properties.put(key, value);
		return this;
	}
	
	public Map<String,Object> getProperties() {
		if (properties == null) {
			initProperties();
		}
		return this.properties;
	}
}
