package com.boundary.sdk;

/**
 * Boundary event status enumeration
 * 
 * @author davidg
 *
 */
public enum Status {
	
	OPEN("OPEN"),
	CLOSED("CLOSED"),
	ACKNOWLEDGED("ACKNOWLEDGED"),
	OK("OK");
	
	private String text;
	
	/**
	 * Constructor
	 */
	Status(String text) {
		this.text = text;
	}
}

