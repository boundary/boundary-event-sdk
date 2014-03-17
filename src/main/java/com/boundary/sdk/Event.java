package com.boundary.sdk;

import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

/**
 * @author davidg
 *
 */
public class Event implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mTitle;
	private String mSource;
	private String mFingerprintFields;
	private Date mCreatedAt;
	private Date mReceivedAt;
	private Severity mSeverity;
	private Status mStatus;
	
	
	public enum Severity {
		
		INFO,
		WARN,
		ERROR,
		CRITICAL;
		
		Severity() {
			//this.value = value;
		}
	}
	
	public enum Status {
		OPEN,
		CLOSED,
		ACKNOWLEDGED,
		OK;
		
		Status() {
			
		}
	}
	
	public class Source {
		private String mRef;
		private String mType;
		private Properties mProperties;
		
		Source() {
			
		}
		
		void setRef(String ref) {
			this.mRef = ref;
		}
		
		String getRef() {
			return "";
		}
	}
	
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
	
	public Date getCreatedAt() {
		return mCreatedAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.mCreatedAt = createdAt;
	}
	
	public Severity getSeverity() {
		return this.mSeverity;
	}
	
	public void setSource(String ref, String type, Properties properties) {
	}
	
	public void setSeverity(Severity severity) {
		this.mSeverity = severity;
	}
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("title: " + mTitle);
		s.append(",fingerprintFields: " + mFingerprintFields);
		s.append(",source: " + mSource);
		
		return s.toString();
	}
}
