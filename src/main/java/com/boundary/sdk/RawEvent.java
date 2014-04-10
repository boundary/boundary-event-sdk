package com.boundary.sdk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * An <code>RawEvent</code> object is a Java implementation of the JSON representation used in the
 * Boundary Event REST API.
 *
 * @author davidg
 */
public class RawEvent extends BaseEvent implements Serializable {
	
	/**
	 * Members
	 */
	private static final long serialVersionUID = 1L;
	private Date createdAt;
	private ArrayList<String> fingerprintFields;
	private String message;
	private String organizationId;
	private Map<String,Object> properties;
	private Date receivedAt;
	private Source sender;
	private Severity severity;
	private Source source;
	private Status status;
	private ArrayList<String> tags;
	private String title;

	
	/**
	 * 
	 * @author davidg
	 *
	 */
	private enum RawEventFields {
		CREATED_AT,
		EVENT_ID,
		FINGERPRINT_FIELDS,
		ID,
		MESSAGE,
		ORGANIZATION_ID,
		PROPERTIES,
		RECEIVED_AT,
		SENDER,
		SEVERITY,
		SOURCE,
		STATUS,
		TAGS,
		TITLE
	}
	
	/**
	 * Boundary event severity enumeration
	 * 
	 * @author davidg
	 *
	 */
	public enum Severity {
		
		INFO,
		WARN,
		ERROR,
		CRITICAL;
		
		private Severity() {
			
		}
	}
	
	/**
	 * Boundary event status enumeration
	 * 
	 * @author davidg
	 *
	 */
	public enum Status {
		
		OPEN,
		CLOSED,
		ACKNOWLEDGED,
		OK;
		
		/**
		 * 
		 */
		private Status() {
			
		}
	}
	/**
	 * Definition for the Source type that mirrors
	 * the JSON implementation in the REST API
	 * 
	 * @author davidg
	 *
	 */
	public class Source {

		private String ref;
		private String type;
		private String name;
		private Map<String,Object> properties;
		
		/**
		 * 
		 */
		public Source() {
			this("","",null,new HashMap<String,Object>());
		}
		
		public Source(String ref,String type) {
			this(ref,type,"",new HashMap<String,Object>());
		}
		
		/**
		 * 
		 * @param ref Reference value
		 * @param type Type of the reference value
		 * @param name Name of the source
		 */
		public Source(String ref,String type,String name) {
			this(ref,type,name,new HashMap<String,Object>());
		}
		
		/**
		 * 
		 * @param ref Reference value
		 * @param type Type of the reference value
		 * @param name Name of the source
		 * @param properties List of objects.
		 */
		public Source(String ref,String type,String name,Map<String,Object> properties) {
			this.ref = ref;
			this.type = type;
			this.name = name;
			this.properties = properties;
		}
		
		/**
		 * 
		 * @param ref Reference value
		 */
		public void setRef(String ref) {
			this.ref = ref;
		}
		
		/**
		 * 
		 * @return Reference value
		 */
		public String getRef() {
			return this.ref;
		}
		
		/**
		 * 
		 * @param type Type of the source
		 */
		public void setType(String type) {
			this.type = type;
		}
		
		public String getType() {
			return this.type;
		}
		
		/**
		 * 
		 * @param name Name of the source
		 */
		public void setName(String name) {
			this.name = name;
		}
		
		/**
		 * 
		 * @return
		 */
		public String getName() {
			return this.name;
		}
		
		public Map<String,Object> getProperties() {
			return this.properties;
		}
	}
	
	/**
	 *  Default contructor for a RawEvent
	 */
	public RawEvent() {
		this(null,null,null);
	}
	
	/**
	 * 
	 * @param title
	 */
	public RawEvent(String title) {
		this(title,null,null);
	}
	
	/**
	 * 
	 * @param title
	 * @param fingerprintFields
	 */
	public RawEvent(String title,ArrayList<String> fingerprintFields) {
		this(title,fingerprintFields,null);
	}
	
	/**
	 * 
	 * @param title
	 * @param fingerprintFields
	 * @param source
	 */
	public RawEvent(String title,ArrayList<String> fingerprintFields,Source source) {
		this(null,
				fingerprintFields,
				"",
				"",
				new HashMap<String,Object>(),
				new Date(),
				null,
				Severity.INFO,
				null,
				Status.OK,
				new ArrayList<String>(),
				title);
	}
	
	/**
	 * 
	 * @param createdAt
	 * @param fingerprintFields
	 * @param message
	 * @param organizationId
	 * @param properties
	 * @param receivedAt
	 * @param sender
	 * @param severity
	 * @param source
	 * @param status
	 * @param tags
	 * @param title
	 */
	public RawEvent(
			Date createdAt,
			ArrayList<String> fingerprintFields,
			String message,
			String organizationId,
			HashMap<String,Object> properties,
			Date receivedAt,
			Source sender,
			Severity severity,
			Source source,
			Status status,
			ArrayList<String> tags,
			String title
			) {
		this.createdAt = createdAt;
		this.fingerprintFields = fingerprintFields;
		this.message = message;
		this.organizationId = organizationId;
		this.properties = properties;
		this.receivedAt = receivedAt;
		this.sender = sender;
		this.severity = severity;
		this.source = source;
		this.status = status;
		this.tags = tags;
		this.title = title;
	}
	
	/**
	 * Returns the creation event of the event
	 * @return
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getFingerprintFields() {
		return fingerprintFields;
	}
	
	/**
	 * Adds a single fingerprint field to the existing fingerprint fields.
	 * 
	 * @param Field to use in the fingerprint field
	 */
	public void addFingerprintField(String value) {
		this.fingerprintFields.add(value);
	}
	
	/**
	 * Sets the fingerprint fields for the event
	 * 
	 * @param fingerprintFields Fingerprint fields to use for the event
	 */
	public void setFingerprintFields(ArrayList<String> fingerprintFields) {
		this.fingerprintFields = fingerprintFields;
	}

	/**
	 * Returns the current value of the message describing the event.
	 * 
	 * @return Current value of event message
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Set the message describing the event.
	 * 
	 * @param message Value to set the event message to
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * Returns the current value of the Boundary organization id of the event
	 * @return Value of organization Id
	 */
	public String getOrganizationId() {
		return this.organizationId;
	}
	
	/**
	 * Set the Boundary organization id on the event
	 * 
	 * @param organizationId Boundary organizationId
	 */
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}
	
	/**
	 * Returns the properties of the event
	 * 
	 * @return Map<String,Object> containing the properties
	 */
	public Map<String,Object> getProperties() {
		return this.properties;
	}
	
	/**
	 * Sets the properties of an event
	 * 
	 * @param properties Map<String,Object> containing the properties to add to the event.
	 */
	public void setProperties(Map<String,Object> properties) {
		this.properties = properties;
	}
	
	/**
	 * Returns the date associated with the event.
	 * 
	 * @return Date received 
	 */
	public Date getReceivedAt() {
		return this.receivedAt;
	}
	
	/**
	 * Sets the received date/time of the event
	 * 
	 * @param receivedAt Date contain the even received tim
	 */
	public void setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
	}
	
	/**
	 * Returns the current sender associated with the event
	 * 
	 * @return Source containing the sender
	 */
	public Source getSender() {
		return this.sender;
	}

	/**
	 * Gets the source of the event
	 * 
	 * @return Source associated with the event
	 */
	public Source getSource() {
		return source;
	}
	
	/**
	 * Sets the source of the event
	 * 
	 * @param source Source to associate with the event.
	 */
	public void setSource(Source source) {
		this.source = source;
	}
	
	/**
	 * Returns the status of the event
	 * 
	 * @return Status
	 */
	public Status getStatus() {
		return this.status;
	}
	
	/**
	 * Sets the status of the RawEvent
	 * 
	 * @param status
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * Returns the current value of the RawEvent
	 * 
	 * @return
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	/**
	 * 
	 * @return Severity
	 */
	public Severity getSeverity() {
		return this.severity;
	}

	/**
	 * 
	 * @param severity - Severity Value
	 */
	public void setSeverity(Severity severity) {
		this.severity = severity;
	}
	
	/**
	 * Sets the entire list of tags in the event
	 * from the list of tags passed in.
	 * 
	 * @param tags List of tags to add to the event
	 */
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
	/**
	 * Adds a single tag value to the event.
	 * 
	 * @param tag Value of tag.
	 */
	public void addTag(String tag) {
		this.tags.add(tag);
	}
	
	public ArrayList<String> getTags() {
		return tags;
	}
	
	/**
	 * 
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();
		
		s.append("createdAt: " + createdAt);
		s.append("\nfingerprintFields: " + fingerprintFields.toString());
		s.append("\nmessage: " + message);
		s.append("\norganizationId: " + organizationId);
		s.append("\nproperties: " + properties);
		s.append("\nreceivedAt: " + receivedAt);
		s.append("\nsender: " + sender);
		s.append("\nsource: " + source);
		s.append("\ntags: " + tags.toString());
		s.append("\ntitle: " + title);

		return s.toString();
	}
	/**
	 * 
	 * Creates an event with default values as follows:
	 * 
	 * title - "MyEvent"
	 * fingerprintFields - "@title"
	 * source - 
	 * 
	 * @return Default event
	 */
	public static RawEvent getDefaultEvent() {
		RawEvent event = new RawEvent();

		event.setTitle("MyEvent");
		ArrayList<String> fields = new ArrayList<String>();
		fields.add("@title");
		event.setFingerprintFields(fields);
		Source s = new RawEvent.Source();
		
		try {
			String hostname = java.net.InetAddress.getLocalHost().getHostName();
			event.setSource(hostname, "host");
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		return event;
	}
}
