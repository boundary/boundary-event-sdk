package com.boundary.sdk;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestSource {
	
	@Override
	public String toString() {
		return "TestSource [ref=" + ref + ", type=" + type + ", props=" + properties + "]";
	}

	String ref;
	String type;
	Map<String,Object> properties;
	
	
	public TestSource() {
		this.ref = "";
		this.type = "";
		this.properties = new LinkedHashMap<String,Object>();
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
	
	public TestSource addProperty(String key,Object value) {
		this.properties.put(key, value);
		return this;
	}
	
	public Map<String,Object> getProperties() {
		return this.properties;
	}

}
