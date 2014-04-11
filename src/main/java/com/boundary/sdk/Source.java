/**
 * 
 */
package com.boundary.sdk;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author davidg
 *
 */
public class Source implements Serializable {
	
	private String ref;
	private String type;
	private String name;
	private Map<String,Object> properties;

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
		this.ref = ref;
		this.type = type;
		this.name = name;
		this.properties = properties;
	}
	
	/**
	 * Set the ref value
	 * 
	 * @param ref
	 * @return
	 */
	public Source setRef(String ref) {
		this.ref = ref;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRef() {
		return this.ref;
	}
	
	/**
	 * Set the type of the source.
	 * @param type
	 * @return
	 */
	public Source setType(String type) {
		this.type = type;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getType() {
		return this.type;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Source setName(String name) {
		this.name = name;
		return this;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getName() {
		return this.name;
	}
	
	public Map<String,Object> getProperties() {
		return properties;
	}
	
	/**
	 * 
	 * @param properties
	 * @return
	 */
	public Source setProperties(Map<String,Object> properties) {
		this.properties = properties;
		return this;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("ref: " + this.ref);
		s.append("type: " + this.type);
		s.append("name: " + this.name);
		s.append("properties: " + this.properties);
		return s.toString();
	}
}
