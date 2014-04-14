package com.boundary.sdk;

import java.util.ArrayList;

public class TestEvent {
	
	private String name;
	private ArrayList<String> tags;

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
	
	public ArrayList<String> getTags() {
		return tags;
	}

	public TestEvent() {
		this.name = new String();
		this.tags = new ArrayList<String>();
	}
	
	public TestEvent(String name) {
		this.name = name;
		this.tags = new ArrayList<String>();
	}
	
	@Override
	public String toString() {
		return "TestEvent [name=" + name + "]";
	}
}
