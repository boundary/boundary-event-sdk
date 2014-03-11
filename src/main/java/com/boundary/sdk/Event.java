/**
 * 
 */
package com.boundary.sdk;

/**
 * @author davidg
 *
 */
public class Event {
	
	private String mTitle;
	private String mSource;
	private String mFingerprintFields;
	
	public Event() {
		this.mTitle = "";
		this.mFingerprintFields = "";
		this.mSource = "";
	}
	
	public String getTitle() {
		return this.mTitle;
	}
	
	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String getFingerprintFields() {
		return mFingerprintFields;
	}
	
	public void setFingerprintFields(String fingerprintFields) {
		this.mFingerprintFields = fingerprintFields;
	}

	public String getSource() {
		return mSource;
	}
}
