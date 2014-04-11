package com.boundary.sdk;

public class TestEvent {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TestEvent() {
		name = new String();
	}
	
	public TestEvent(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "TestEvent [name=" + name + "]";
	}
}
