package com.boundary.sdk;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestSource {
	
	String ref;
	String type;
	Map<String,Object> props;
	
	
	public TestSource() {
		this.ref = "";
		this.type = "";
		this.props = new LinkedHashMap<String,Object>();
	}
	
	public TestSource(String ref,String type) {
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
	
	public TestSource addProp(String key,Object value) {
		this.props.put(key, value);
		return this;
	}
	
	public Map<String,Object> getProps() {
		return this.props;
	}

}
