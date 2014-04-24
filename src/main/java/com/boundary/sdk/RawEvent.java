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
 * An {@link RawEvent} object is a Java class that mirrors the JSON representation used in the
 * Boundary Event REST API. For additional details see the
 * <a href="https://app.boundary.com/docs/events_api">Boundary Event API</a>
 * documentation.
 * 
 * NOTE: This classes uses lazy initialization for some fields so that the
 *       serialization to JSON (via Jackson) is skipped for those fields that have not been
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
	 *  Default constructor for a raw event
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
	 * Returns the creation date of a raw event.
	 * 
	 * @return {@link Date} that has the date and time of when the event occurred
	 */
	public Date getCreatedAt() {
		return createdAt;
	}
	
	/**
	 * Sets the creation date of a raw event.
	 * 
	 * @param createdAt Date and time ({@link Date})  of when the event was created.
	 * 
	 * @return Returns the {@link RawEvent}
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
	 * Get the finger print fields associated with the raw event
	 * 
	 * @return Returns a {@link ArrayList} with finger print fields
	 */
	public ArrayList<String> getFingerprintFields() {
		if (fingerprintFields == null) {
			initFingerprintFields();
		}
		return fingerprintFields;
	}
	
	/**
	 * Adds a single finger print field a raw event.
	 * 
	 * @param fingerPrintFieldName {@link String} containing the finger print field to the {@link RawEvent}
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent addFingerprintField(String fingerPrintFieldName) {
		if (fingerprintFields == null) {
			initFingerprintFields();
		}
		fingerprintFields.add(fingerPrintFieldName);
		return this;
	}
	
	/**
	 * Removes a finger print field from a raw event.
	 * 
	 * @param fieldName
	 * 
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent removeFingerprintField(String fieldName) {
		if (fingerprintFields != null){
			fingerprintFields.remove(fieldName);
		}
		return this;
	}
	
	/**
	 * Sets the finger print fields for a raw event.
	 * 
	 * @param fingerprintFields Finger print fields to use for the {@link RawEvent}
	 * 
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent setFingerprintFields(ArrayList<String> fingerprintFields) {
		if (fingerprintFields == null) {
			initFingerprintFields();
		}
		this.fingerprintFields = fingerprintFields;
		return this;
	}

	/**
	 * Returns the current value of the message describing the raw event.
	 * 
	 * @return {@link String} containing the message associated with the {@link RawEvent}
	 */
	public String getMessage() {
		return this.message;
	}
	
	/**
	 * Set the message describing the raw event.
	 * 
	 * @param message {@link String} to set the {@link RawEvent} to
	 * @return Returns the {@link RawEvent}
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
	 * Set the Boundary organization id on the raw event.
	 * 
	 * @param organizationId Boundary organizationId
	 * @return Returns the {@link RawEvent}
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
	 * Adds a property to a raw event. A property consists of
	 * a name/value pair.
	 * 
	 * @param propertyName
	 * @param propertValue
	 */
	public void addProperty(String propertyName,Object propertValue) {
		if (properties == null) {
			initProperties();
		}
		this.properties.put(propertyName, propertValue);
	}
	
	/**
	 * Removes a property with the given name from the raw event.
	 * 
	 * @param propertyName {@link String} containing the name of the property to remove from the {@link RawEvent}.
	 */
	public void removeProperty(String propertyName) {
		if (properties != null) {
			this.properties.remove(propertyName);
		}
	}
	
	/**
	 * Returns all of the properties associated with the raw event
	 * 
	 * @return {@link LinkedHashMap} containing the properties of the {@link RawEvent}
	 */
	public LinkedHashMap<String,Object> getProperties() {
		if (properties == null) {
			initProperties();
		}
		return this.properties;
	}
	
	/**
	 * Sets the properties of a raw event.
	 * 
	 * @param properties ({@link LinkedHashMap}) to be associated with the {@link RawEvent}
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent setProperties(LinkedHashMap<String,Object> properties) {
		if (properties == null) {
			initProperties();
		}
		this.properties = properties;
		return this;
	}
	
	/**
	 * Returns received date and time associated with the raw event.
	 * 
	 * @return {@link Date} Date and time when the {@link RawEvent} 
	 */
	public Date getReceivedAt() {
		return this.receivedAt;
	}
	
	/**
	 * Sets the received date and time of a raw event.
	 * 
	 * @param receivedAt {@link Date} containing the {@link RawEvent} received date and time
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
		return this;
	}
	
	/**
	 * Internal method of initializing the sender ({@link Source}).
	 */
	private void initializeSender() {
		this.sender = new Source();
	}
	
	/**
	 * Returns the current sender associated with the raw event
	 * 
	 * @return {@link Source} containing the sender of the {@link RawEvent}
	 */
	public Source getSender() {
		if (sender == null) {
			initializeSender();
		}
		return this.sender;
	}
	
	/**
	 * Sets the sender associated with the raw event.
	 * 
	 * @param sender Sender ({@link Source}) to associate with the {@link RawEvent}
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent setSender(Source sender) {
		if (sender == null) {
			initializeSender();
		}
		this.sender = sender;
		return this;
	}
	
	/**
	 * Internal method to initialize this {@link RawEvent} severity({@link Severity}).
	 */
	private void initializeSeverity() {
		this.severity = Severity.INFO;
	}

	/**
	 * Gets the current severity from a raw event
	 * 
	 * @return Returns {@link Severity} of this {@link RawEvent}
	 */
	public Severity getSeverity() {
		if (severity == null) {
			initializeSeverity();
		}
		return this.severity;
	}

	/**
	 * Set the severity of a raw event
	 * 
	 * @param severity - Severity {@link Severity} to be associated with the {@link RawEvent}
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent setSeverity(Severity severity) {
		if (severity == null) {
			initializeSeverity();
		}
		this.severity = severity;
		return this;
	}

	/**
	 * Internal method for lazy initialization.
	 */
	private void initializeSource() {
		source = new Source();
	}

	/**
	 * Gets the source of the raw event.
	 * 
	 * @return Source({@link Source}) associated with the {@link RawEvent}
	 */
	public Source getSource() {
		if (source == null) {
			initializeSource();
		}
		return source;
	}
	
	/**
	 * Sets the source of a raw event
	 * 
	 * @param source {@link Source} to associate with the event.
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent setSource(Source source) {
		if (source == null) {
			initializeSource();
		}
		this.source = source;
		return this;
	}
	
	/**
	 * Internal method of lazy initialization of {@link Status}
	 */
	private void initializeStatus() {
		this.status = Status.OK;
	}
	
	/**
	 * Returns the status of the raw event
	 * 
	 * @return {@link Status} of the {@link RawEvent}
	 */
	public Status getStatus() {
		if (status == null) {
			initializeStatus();
		}
		return this.status;
	}
	
	/**
	 * Sets the status of the raw event.
	 * 
	 * @param status {@Status} associated with the {@link RawEvent}
	 * 
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent setStatus(Status status) {
		if (status == null) {
			initializeStatus();
		}
		this.status = status;
		return this;
	}

	/**
	 * Returns the current value of the {@link RawEvent}
	 * 
	 * @return {@link String} with title name of the {@link RawEvent}
	 */
	public String getTitle() {
		return this.title;
	}
	
	/**
	 * Sets the title of the raw event.
	 * 
	 * @param title {@link String} containing the title to associate with the {@link RawEvent}
	 * @return Returns the {@link RawEvent}
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
	 * Associates an array of tags with a raw event.
	 * 
	 * @param tags {@link ArrayList} of tags to associate with the {@link RawEvent}
	 */
	public RawEvent setTags(ArrayList<String> tags) {
		if (tags == null) {
			initializeTags();
		}
		this.tags = tags;
		return this;
	}
	
	/**
	 * Adds a single tag to a {@link RawEvent}.
	 * 
	 * @param tag Value of tag.
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent addTag(String tag) {
		if (tags == null) {
			initializeTags();
		}
		this.tags.add(tag);
		return this;
	}
	
	/**
	 * Removes a tag from the raw event
	 * 
	 * @param tag {@link String} containing the tag to be removed from a {@link RawEvent}
	 * @return Returns the {@link RawEvent}
	 */
	public RawEvent removeTag(String tag) {
		if (tags != null) {
			tags.remove(tag);
		}
		return this;
	}
	
	/**
	 * Get the tags associated with the raw event
	 * 
	 * @return {@link ArrayList} containing all of the tags associated with the {@link RawEvent}
	 */
	public ArrayList<String> getTags() {
		if (tags == null) {
			initializeTags();
		}

		return tags;
	}
	
	/**
	 * Converts a raw event into a string representation.
	 * 
	 * @return {@link String} representation of a {@link RawEvent}
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
	 * <p>
	 * <em>title</em> - MyEvent
	 * </p>
	 * <p>
	 * <em>fingerprintFields</em> - <code>@title</code>
	 * </p>
	 * <p>
	 * <em>source</em> - localhost
	 * </p>
	 * <p>
	 * <em>status</em> - <code>OPEN</code>
	 * </p>
	 * 
	 * @return Default population of the fields in a {@link RawEvent}
	 */
	public static RawEvent getDefaultEvent() {
		RawEvent event = new RawEvent();
		
		event.setTitle("MyEvent");
		event.setStatus(Status.OPEN);
		event.setSeverity(Severity.WARN);
		event.addFingerprintField("@title");
		event.getSource().setRef("localhost");
		event.getSource().setType("host");

		return event;
	}
}
