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

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class TestEvent {
	
	@JsonProperty
	private String name;
	@JsonProperty
	private ArrayList<String> tags;
	@JsonProperty
	private Map<String,Object> properties;
	@JsonProperty
	private TestStatus status;
	@JsonProperty
	private TestSource source;

	public String getName() {
		return name;
	}

	public TestEvent setName(String name) {
		this.name = name;
		return this;
	}
	
	private void initStatus() {
		this.status = TestStatus.SUCCEED;
	}
	public TestStatus getStatus() {
		if (status == null) {
			initStatus();
		}
		return this.status;
	}

	private void initProperties() {
		this.properties = new LinkedHashMap<String,Object>();
	}
	public TestEvent addProperty(String key, Object value) {
		if (this.properties == null) {
			initProperties();
		}
		properties.put(key, value);
		return this;
	}
	
	
	public Map<String,Object> getProperties() {
		if (this.properties == null) {
			initProperties();
		}
		return this.properties;
	}
	
	private void initTags() {
		this.tags = new ArrayList<String>();
	}
	
	public TestEvent addTag(String tag) {
		if (tags == null) {
			initTags();
		}
		this.tags.add(tag);
		return this;
	}
	
	public ArrayList<String> getTags() {
		if (tags == null) {
			initTags();
		}
		return tags;
	}
	
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
	private void initSource() {
		this.source = new TestSource();
	}
	public TestSource getSource() {
		if (source == null) {
			initSource();
		}
		return this.source;
	}

	public void setStatus(TestStatus status) {
		this.status = status;
	}
	public TestEvent() {

	}
	
	public TestEvent(String name) {
		this();
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("TestEvent");
		s.append(",name = " + name);
		s.append(",tags = " + tags);
		s.append(",properties = " + properties);
		s.append(",status = " + status);
		s.append(",source = " + source);
		return s.toString();	
	}
}
