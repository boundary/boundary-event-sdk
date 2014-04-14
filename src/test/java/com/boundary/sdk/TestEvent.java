package com.boundary.sdk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class TestEvent {
	
	private String name;
	private ArrayList<String> tags;
	private Map<String,Object> props;
	private TestStatus status;
	private TestSource source;

	public String getName() {
		return name;
	}

	public TestEvent setName(String name) {
		this.name = name;
		return this;
	}
	
	public TestEvent addTag(String tag) {
		this.tags.add(tag);
		return this;
	}
	
	public TestEvent addProp(String key, Object value) {
		props.put(key, value);
		return this;
	}
	
	public TestStatus getStatus() {
		return this.status;
	}
	
	public Map<String,Object> getProps() {
		return this.props;
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}
	
	public TestSource getSource() {
		return this.source;
	}

	public TestEvent() {
		this.name = new String();
		this.tags = new ArrayList<String>();
		this.props = new LinkedHashMap<String,Object>();
		this.status = TestStatus.SUCCEED;
		this.source = new TestSource();
	}
	
	public TestEvent(String name) {
		this();
		this.name = name;
	}
	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("TestEvent");
		s.append("\nname = " + name);
		s.append("\ntags = " + tags);
		return s.toString();	
	}
}
