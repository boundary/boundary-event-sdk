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
	private Source mSource;
	private String[] mFingerprintFields;
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
		this(null,null,null);
	}
	
	public Event(String title) {
		this(title,null,null);
	}
	
	public Event(String title,String[] fingerprintFields) {
		this(title,fingerprintFields,null);
	}
	
	public Event(String title,String[] fingerprintFields,Source source) {
		this.mTitle = title;
		this.mFingerprintFields = fingerprintFields;
		this.mSource = source;
	}
	
	public String getTitle() {
		return this.mTitle;
	}
	
	public void setTitle(String title) {
		this.mTitle = title;
	}

	public String[] getFingerprintFields() {
		return mFingerprintFields;
	}
	
	public void setFingerprintFields(String[] fingerprintFields) {
		this.mFingerprintFields = fingerprintFields;
	}

	
	public Date getCreatedAt() {
		return mCreatedAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.mCreatedAt = createdAt;
	}
		
	public Source getSource() {
		return mSource;
	}

	public void setSource(String ref, String type, Properties properties) {
		this.mSource.setRef(ref);
		//this.mSource.setType(type);
	}
	/**
	 * 
	 * @return Severity
	 */
	public Severity getSeverity() {
		return this.mSeverity;
	}

	/**
	 * 
	 * @param severity - Severity Value
	 */
	public void setSeverity(Severity severity) {
		this.mSeverity = severity;
	}
	
	
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("title: " + mTitle);
		s.append(",fingerprintFields: " + mFingerprintFields.toString());
		s.append(",source: " + mSource);
		
		return s.toString();
	}
	
	public Event getDefaultEvent() {
		Event event = new Event();

		event.setTitle("MyEvent");
		String[] fields = new String[1];
		fields[0] = "@title";
		event.setFingerprintFields(fields);
		Source s = new Source();
		
		try {
			String hostname = java.net.InetAddress.getLocalHost().getHostName();
			event.setSource(hostname, "host",null);
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}

		return event;
	}
}
