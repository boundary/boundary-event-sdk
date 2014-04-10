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

	public static Status fromString(String text) {
		if (text != null) {
			for (Status b : Status.values()) {
				if (text.equalsIgnoreCase(b.text)) {
					return b;
				}
			}
		}
		return null;
	}

}
