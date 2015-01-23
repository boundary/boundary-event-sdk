// Copyright 2014 Boundary, Inc.
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

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author davidg
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)

public class Source implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private String ref;
	@JsonProperty
	private String type;
	@JsonProperty
	private String name;
	@JsonProperty
	private Map<String,Object> properties;

	/**
	 * Default constructor
	 */
	public Source() {
	}
	
	/**
	 * Constructor that uses ref and type
	 * 
	 * @param ref {@link String} Source reference
	 * @param type {@link String} Source type
	 */
	public Source(String ref, String type) {
		setRef(ref);
		setType(type);
	}
	
	/**
	 * Constructor
	 * 
	 * @param ref {@link String} Source reference
	 * @param type {@link String} Source type
	 * @param name {@link String} Source name
	 */
	public Source(String ref,String type,String name) {
		setRef(ref);
		setType(type);
		setName(name);
	}

	/**
	 * Constructor
	 * 
	 * @param ref {@link String} Source reference
	 * @param type {@link String} Source type
	 * @param name {@link String} Source name
	 * @param properties {@link String} Source properties
	 */
	public Source(String ref, String type, String name, Map<String,Object> properties) {
		setRef(ref);
		setType(type);
		setName(name);
		setProperties(properties);
	}
	
	/**
	 * Set the ref value
	 * 
	 * @param ref {@link String} Source reference
	 * @return {@link Source}
	 */
	public Source setRef(String ref) {
		this.ref = BaseEvent.truncateToMaximumLength(ref);
		return this;
	}
	
	/**
	 * 
	 * @return {@link String}
	 */
	public String getRef() {
		return this.ref;
	}
	
	/**
	 * Set the type of the source.
	 * @param type {@link String} Source type
	 * @return {@link Source}
	 */
	public Source setType(String type) {
		this.type = BaseEvent.truncateToMaximumLength(type);
		return this;
	}
	
	/**
	 * Get the type of this source.
	 * 
	 * @return {@link String}
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Sets the name of this source
	 * 
	 * @param name {@link String} Source name
	 * @return {@link Source}
	 */
	public Source setName(String name) {
		this.name = BaseEvent.truncateToMaximumLength(name);
		return this;
	}
	
	/**
	 * 
	 * @return {@link String}
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Internal method to initialize the source properties
	 */
	private void initializeProperties() {
		properties = new LinkedHashMap<String,Object>();
	}
	
	/**
	 * 
	 * @return {@link Map}
	 */
	public Map<String,Object> getProperties() {
		if (properties == null) {
			initializeProperties();
		}
		return properties;
	}
	
	/**
	 * Set the properties of the {@link RawEvent}
	 * @param properties {@link String} Source properties
	 * @return {@link Source}
	 */
	public Source setProperties(Map<String,Object> properties) {
		if (properties == null) {
			initializeProperties();
		}
		this.properties = BaseEvent.truncateToMaximumLength(properties);
		return this;
	}
	
	/**
	 * Add a property to a {@link RawEvent}
	 * 
	 * @param key {@link String} Name of the property
	 * @param value {@link Object} Value of the property
	 * @return {@link Source}
	 */
	public Source addProperty(String key,Object value) {
		if (properties == null) {
			initializeProperties();
		}
		this.properties.put(BaseEvent.truncateToMaximumLength(key), value);
		return this;
	}
	
	/**
	 * Converts to a string representation
	 * 
	 * @return {@link String}
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(ref == null ? "" : "ref: " + this.ref);
		s.append(type == null ? "" : ",type: " + this.type);
		s.append(name == null ? "" : ",name: " + this.name);
		s.append(properties == null ? "": ",properties: " + this.properties);
		return s.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((properties == null) ? 0 : properties.hashCode());
		result = prime * result + ((ref == null) ? 0 : ref.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Source other = (Source) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (properties == null) {
			if (other.properties != null)
				return false;
		} else if (!properties.equals(other.properties))
			return false;
		if (ref == null) {
			if (other.ref != null)
				return false;
		} else if (!ref.equals(other.ref))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}
}
