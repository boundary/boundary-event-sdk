package com.boundary.sdk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * 
 * An <code>RawEvent</code> object is a Java implementation of the JSON representation used in the
 * Boundary Event REST API.
 * 
 * NOTE: This classes uses lazy initialization for some fields so that the
 *       serialization to JSON is skipped for those fields that have not been
 *       specified.
 *
 * @author davidg
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class RawEvent extends BaseEvent implements Serializable {
	
	/**
	 * Members
	 */
	private static final long serialVersionUID = 1L;
	@JsonProperty
	private Date createdAt;
	@JsonProperty
	private ArrayList<String> fingerprintFields;
	@JsonProperty
	private String message;
	@JsonProperty
	private String organizationId;
	@JsonProperty
	private LinkedHashMap<String,Object> properties;
	@JsonProperty
	private Date receivedAt;
	@JsonProperty
	private Source sender;
	@JsonProperty
	private Severity severity;
	@JsonProperty
	private Source source;
	@JsonProperty
	private Status status;
	@JsonProperty
	private ArrayList<String> tags;
	@JsonProperty
	private String title;

	/**
	 *  Default constructor for a RawEvent
	 */
	public RawEvent() {
//		this(new Source(),new ArrayList<String>(),"");
//		this.addFingerprintField("@title");
	}
	
	/**
	 * 
	 * @param source
	 * @param fingerprintFields
	 * @param title
	 */
	public RawEvent(Source source,ArrayList<String> fingerprintFields,String title) {	
//		this.createdAt = new Date().toString();
//		this.fingerprintFields = fingerprintFields;
//		this.message = "";
//		this.organizationId = "";
//		this.properties = new LinkedHashMap<String,Object>();
//		this.receivedAt = new Date().toString();
//		this.sender = new Source();
//		this.severity = Severity.INFO;
//		this.source = source;
//		this.status = Status.OK;
//		this.tags = new ArrayList<String>();
//		this.title = title;
	}
	
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
	 * Used for lazy initialization of the fingerprint fields.
	 */
	private void initFingerprintFields() {
		this.fingerprintFields = new ArrayList<String>();
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<String> getFingerprintFields() {
		if (fingerprintFields == null) {
			initFingerprintFields();
		}
		return fingerprintFields;
	}
	
	/**
	 * Adds a single fingerprint field to the existing fingerprint fields.
	 * 
	 * @param Field to use in the fingerprint field
	 * @return RawEvent
	 */
	public RawEvent putFingerprintField(String value) {
		if (fingerprintFields == null) {
			initFingerprintFields();
		}
		fingerprintFields.add(value);
		return this;
	}
	
	public RawEvent removeFingerprintField(String value) {
		if (fingerprintFields != null){
			fingerprintFields.remove(value);
		}
		return this;
	}
	
	/**
	 * Sets the fingerprint fields for the event
	 * 
	 * @param fingerprintFields Fingerprint fields to use for the event
	 * @return RawEvent
	 */
	public RawEvent setFingerprintFields(ArrayList<String> fingerprintFields) {
		if (fingerprintFields == null) {
			initFingerprintFields();
		}
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
	 * Internal method to initialize event properties
	 */
	private void initProperties() {
		this.properties = new LinkedHashMap<String,Object>();
	}
	
	/**
	 * Adds a property to an event. A property consists of
	 * a key/value pair.
	 * 
	 * @param key
	 * @param value
	 */
	public void putProperty(String key,Object value) {
		if (properties == null) {
			initProperties();
		}
		this.properties.put(key, value);
	}
	
	/**
	 * Removes a property with the key from the {@link RawEvent}.
	 * @param key
	 */
	public void removeProperty(String key) {
		if (properties != null) {
			this.properties.remove(key);
		}
	}
	
	/**
	 * Returns all of the properties associated with {@link RawEvent}
	 * 
	 * @return Map<String,Object> containing the properties
	 */
	public LinkedHashMap<String,Object> getProperties() {
		if (properties == null) {
			initProperties();
		}
		return this.properties;
	}
	
	/**
	 * Sets the properties of an event
	 * 
	 * @param properties Map<String,Object> containing the properties to add to the event.
	 * @return RawEvent
	 */
	public RawEvent setProperties(LinkedHashMap<String,Object> properties) {
		if (properties == null) {
			initProperties();
		}
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
	
	private void initializeSender() {
		this.sender = new Source();
	}
	
	/**
	 * Returns the current sender associated with the event
	 * 
	 * @return Source containing the sender
	 */
	public Source getSender() {
		if (sender == null) {
			initializeSender();
		}
		return this.sender;
	}
	
	/**
	 * Sets the sender associated with the event
	 * 
	 * @parm sender Source to assign as sender
	 * @return RawEvent
	 */
	public RawEvent setSender(Source sender) {
		if (sender == null) {
			initializeSender();
		}

		this.sender = sender;
		return this;
	}
	
	/**
	 * Internal method to initialize Status
	 */
	private void initializeSeverity() {
		this.severity = Severity.INFO;
	}

	/**
	 * 
	 * @return Severity
	 */
	public Severity getSeverity() {
		if (severity == null) {
			initializeSeverity();
		}
		return this.severity;
	}

	/**
	 * 
	 * @param severity - Severity Value
	 * @return RawEvent
	 */
	public RawEvent setSeverity(Severity severity) {
		if (severity == null) {
			initializeSeverity();
		}
		this.severity = severity;
		return this;
	}

	private void initializeSource() {
		source = new Source();
	}

	/**
	 * Gets the source of the event
	 * 
	 * @return Source associated with the event
	 */
	public Source getSource() {
		if (source == null) {
			initializeSource();
		}
		return source;
	}
	
	/**
	 * Sets the source of the event
	 * 
	 * @param source Source to associate with the event.
	 * @return RawEvent
	 */
	public RawEvent setSource(Source source) {
		if (source == null) {
			initializeSource();
		}
		this.source = source;
		return this;
	}
	
	private void initializeStatus() {
		this.status = Status.OK;
	}
	
	/**
	 * Returns the status of the event
	 * 
	 * @return Status
	 */
	public Status getStatus() {
		if (status == null) {
			initializeStatus();
		}
		return this.status;
	}
	
	/**
	 * Sets the status of the RawEvent
	 * 
	 * @param status
	 * @return RawStatus
	 */
	public RawEvent setStatus(Status status) {
		if (status == null) {
			initializeStatus();
		}
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
	 * Internal method to initialize the {@link RawEvent} tags attribute
	 */
	private void initializeTags() {
		this.tags = new ArrayList<String>();
	}
	
	/**
	 * Sets the entire list of tags in the event
	 * from the list of tags passed in.
	 * 
	 * @param tags List of tags to add to the event
	 */
	public RawEvent setTags(ArrayList<String> tags) {
		if (tags == null) {
			initializeTags();
		}
		this.tags = tags;
		return this;
	}
	
	/**
	 * Adds a single tag value to the {@link RawEvent}.
	 * 
	 * @param tag Value of tag.
	 * @return RawEvent
	 */
	public RawEvent putTag(String tag) {
		if (tags == null) {
			initializeTags();
		}
		this.tags.add(tag);
		return this;
	}
	
	public RawEvent removeTag(String tag) {
		if (tags != null) {
			tags.remove(tag);
		}
		return this;
	}
	
	/**
	 * Get the tags associated with the {@link RawEvent}
	 * 
	 * @return
	 */
	public ArrayList<String> getTags() {
		if (tags == null) {
			initializeTags();
		}

		return tags;
	}
	
	/**
	 * Converts a {@link RawEvent} into a string representation
	 */
	public String toString() {
		StringBuffer s = new StringBuffer();

		s.append(createdAt == null ? "" : "createdAt: " +  createdAt);
		s.append(fingerprintFields == null ? "" : ",fingerprintFields: " + fingerprintFields.toString());
		s.append(message == null ? "": ",message: " + message);
		s.append(organizationId == null ? "": ",organizationId: " + organizationId);
		s.append(properties == null ? "" : ",properties: " + properties);
		s.append(receivedAt == null ? "" : ",receivedAt: " + receivedAt);
		s.append(sender == null ? "" : ",sender: " + sender);
		s.append(severity == null ? "" : ",severity: " + severity);
		s.append(source == null ? "" : ",source: " + source);
		s.append(status == null ? "" : ",status: " + status);
		s.append(tags == null ? "" : ",tags: " + tags.toString());
		s.append(title == null ? "" : ",title: " + title);

		return s.toString();
	}
	/**
	 * 
	 * Creates an event with default values as follows:
	 * 
	 * title - "MyEvent"
	 * fingerprintFields - "@title"
	 * source - "localhost"
	 * status - OPEN
	 * 
	 * @return Default event
	 */
	public static RawEvent getDefaultEvent() {
		RawEvent event = new RawEvent();
		
		event.setTitle("MyEvent");
		event.setStatus(Status.OPEN);
		event.setSeverity(Severity.WARN);
		event.putFingerprintField("@title");
		event.getSource().setRef("localhost");
		event.getSource().setType("host");

		return event;
	}
}
