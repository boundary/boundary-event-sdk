package com.boundary.sdk.event.esper;

import java.io.Serializable;

public class EsperEvent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3003943985203190621L;
	String name;
	int value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "EsperEvent [name=" + name + ", value=" + value + "]";
	}
}
