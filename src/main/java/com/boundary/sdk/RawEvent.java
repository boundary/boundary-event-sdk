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
	 *  Default contructor for a RawEvent
	 */
	public RawEvent() {
		this(new Source(),new ArrayList<String>(),"");
		this.addFingerprintField("@title");
	}
	
	/**
	 * 
	 * @param source
	 * @param fingerprintFields
	 * @param title
	 */
	public RawEvent(Source source,ArrayList<String> fingerprintFields,String title) {
		
		this.createdAt = new Date();
		this.fingerprintFields = fingerprintFields;
		this.message = "";
		this.organizationId = "";
		this.properties = new HashMap<String,Object>();
		this.receivedAt = new Date();
		this.sender = new Source();
		this.severity = Severity.INFO;
		this.source = source;
		this.status = Status.OK;
		this.tags = new ArrayList<String>();
		this.title = title;
	}
	
//	/**
//	 * 
//	 * @param title
//	 * @param fingerprintFields
//	 */
//	public RawEvent(String title,ArrayList<String> fingerprintFields) {
//		this(title,fingerprintFields,null);
//	}
//	
//	/**
//	 * 
//	 * @param title
//	 * @param fingerprintFields
//	 * @param source
//	 */
//	public RawEvent(String title,ArrayList<String> fingerprintFields,Source source) {
//		this(null,
//				fingerprintFields,
//				"",
//				"",
//				new HashMap<String,Object>(),
//				new Date(),
//				null,
//				Severity.INFO,
//				null,
//				Status.OK,
//				new ArrayList<String>(),
//				title);
//	}
//	
//	/**
//	 * 
//	 * @param createdAt
//	 * @param fingerprintFields
//	 * @param message
//	 * @param organizationId
//	 * @param properties
//	 * @param receivedAt
//	 * @param sender
//	 * @param severity
//	 * @param source
//	 * @param status
//	 * @param tags
//	 * @param title
//	 */
//	public RawEvent(
//			Date createdAt,
//			ArrayList<String> fingerprintFields,
//			String message,
//			String organizationId,
//			HashMap<String,Object> properties,
//			Date receivedAt,
//			Source sender,
//			Severity severity,
//			Source source,
//			Status status,
//			ArrayList<String> tags,
//			String title
//			) {
//		this.createdAt = createdAt;
//		this.fingerprintFields = fingerprintFields;
//		this.message = message;
//		this.organizationId = organizationId;
//		this.properties = properties;
//		this.receivedAt = receivedAt;
//		this.sender = sender;
//		this.severity = severity;
//		this.source = source;
//		this.status = status;
//		this.tags = tags;
//		this.title = title;
//	}
//	
	/**
	 * Returns the creation event of the event
	 * @return
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	
	/**
	 * 
	 * @param createdAt
	 * @return RawEvent
	 */
	public RawEvent setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
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
	 * @return RawEvent
	 */
	public RawEvent addFingerprintField(String value) {
		this.fingerprintFields.add(value);
		return this;
	}
	
	/**
	 * Sets the fingerprint fields for the event
	 * 
	 * @param fingerprintFields Fingerprint fields to use for the event
	 * @return RawEvent
	 */
	public RawEvent setFingerprintFields(ArrayList<String> fingerprintFields) {
		this.fingerprintFields = fingerprintFields;
		return this;
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
	 * @return RawEvent
	 */
	public RawEvent setMessage(String message) {
		this.message = message;
		return this;
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
	 * @return RawEvent
	 */
	public RawEvent setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
		return this;
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
	 * @return RawEvent
	 */
	public RawEvent setProperties(Map<String,Object> properties) {
		this.properties = properties;
		return this;
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
	 * @param receivedAt Date containing the event received time
	 * @return
	 */
	public RawEvent setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
		return this;
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
	 * Sets the sender associated with the event
	 * 
	 * @parm sender Source to assign as sender
	 * @return RawEvent
	 */
	public RawEvent setSender(Source sender) {
		this.sender = sender;
		return this;
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
	 * @return RawEvent
	 */
	public RawEvent setSeverity(Severity severity) {
		this.severity = severity;
		return this;
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
	 * @return RawEvent
	 */
	public RawEvent setSource(Source source) {
		this.source = source;
		return this;
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
	 * @return RawStatus
	 */
	public RawEvent setStatus(Status status) {
		this.status = status;
		return this;
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
	 * @return RawEvent
	 */
	public RawEvent setTitle(String title) {
		this.title = title;
		return this;
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
	 * Adds a single tag value to the {@link RawEvent}.
	 * 
	 * @param tag Value of tag.
	 * @return RawEvent
	 */
	public RawEvent addTag(String tag) {
		this.tags.add(tag);
		return this;
	}
	
	/**
	 * Get the tags associated with the {@link RawEvent}
	 * 
	 * @return
	 */
	public ArrayList<String> getTags() {
		return tags;
	}
	
	/**
	 * Converts a {@link RawEvent} into a string representation
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
		
		Source s = new Source();
		
		try {
			String hostname = java.net.InetAddress.getLocalHost().getHostName();
			event.setSource(s);
		}
		catch(Exception e) {
			System.out.println(e.getStackTrace());
		}
		return event;
	}
}
