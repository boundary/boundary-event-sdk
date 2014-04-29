/**
 * 
 */
package com.boundary.sdk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import com.boundary.sdk.event.RawEvent;
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
	private LinkedHashMap<String,Object> properties;

	/**
	 * Default constructor
	 */
	public Source() {
		this("","");
	}
	
	/**
	 * Constructor that uses ref and type
	 * 
	 * @param ref
	 * @param type
	 */
	public Source(String ref, String type) {
		this(ref,type,"");
	}
	
	/**
	 * Constructor
	 * 
	 * @param ref
	 * @param type
	 * @param name
	 */
	public Source(String ref,String type,String name) {
		this(ref,type,name,new HashMap<String,Object>());
	}

	/**
	 * Constructor
	 * 
	 * @param ref
	 * @param type
	 * @param name
	 * @param properties
	 */
	public Source(String ref, String type, String name, Map<String,Object> properties) {
//		this.ref = ref;
//		this.type = type;
//		this.name = name;
//		this.properties = properties;
	}
	
	/**
	 * Set the ref value
	 * 
	 * @param ref
	 * @return {@link Source}
	 */
	public Source setRef(String ref) {
		this.ref = ref;
		return this;
	}
	
	/**
	 * 
	 * @return {@link Source}
	 */
	public String getRef() {
		return this.ref;
	}
	
	/**
	 * Set the type of the source.
	 * @param type
	 * @return {@link Source}
	 */
	public Source setType(String type) {
		this.type = type;
		return this;
	}
	
	/**
	 * Get the type of this source.
	 * 
	 * @return {@link Source}
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * Sets the name of this source
	 * 
	 * @param name
	 * @return {@link Source}
	 */
	public Source setName(String name) {
		this.name = name;
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
	 * @param properties
	 * @return {@link Source}
	 */
	public Source setProperties(LinkedHashMap<String,Object> properties) {
		if (properties == null) {
			initializeProperties();
		}
		this.properties = properties;
		return this;
	}
	
	/**
	 * Add a property to a {@link RawEvent}
	 * 
	 * @param key
	 * @param value
	 * @return {@link Source}
	 */
	public Source addProperty(String key,Object value) {
		if (properties == null) {
			initializeProperties();
		}
		this.properties.put(key, value);
		return this;
	}
	
	/**
	 * Converts to a string representation
	 * 
	 * @return {@link String}
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append(ref == null ? "": ",ref: " + this.ref);
		s.append(type == null ? "" : ",type: " + this.type);
		s.append(name == null ? "" : "name: " + this.name);
		s.append(properties == null ? "": ",properties: " + this.properties);
		return s.toString();
	}
}
