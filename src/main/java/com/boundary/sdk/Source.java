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
	 * 
	 */
	public Source() {
		this("","");
	}
	
	/**
	 * 
	 * @param ref
	 * @param type
	 */
	public Source(String ref, String type) {
		this(ref,type,"");
	}
	
	/**
	 * Constructor with the required parameters for an event
	 * 
	 * @param ref
	 * @param type
	 * @param name
	 */
	public Source(String ref,String type,String name) {
		this(ref,type,name,new HashMap<String,Object>());
	}

	/**
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
	
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	public String getRef() {
		return this.ref;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Map<String,Object> getProperties() {
		return properties;
	}
	
	public void setProperties(Map<String,Object> properties) {
		this.properties = properties;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("ref: " + this.ref);
		s.append("type: " + this.type);
		s.append("name: " + this.name);
		s.append("properties: " + this.properties);
		return s.toString();
	}


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
