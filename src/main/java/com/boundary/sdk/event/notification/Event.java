// Copyright 2014 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.boundary.sdk.event.notification;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.boundary.sdk.event.Severity;
import com.boundary.sdk.event.Source;
import com.boundary.sdk.event.Status;

public class Event {
	private String fingerprint;
	private List<String> fingerprintFields;
	private List<String> tags;
    private Date firstSeenAt;
    private String id;
    private Date lastSeenAt;
    private Date lastUpdatedAt;
    private String message;
    private String organizationId;
    private Map<String,Object> properties;
    private Severity severity;
    private Source source;
    private Status status;
    private Number timesSeen;
    private String title;
    private List<Source> relatedSources;
    
    public Event() {
    }

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public List<String> getFingerprintFields() {
		return fingerprintFields;
	}

	public void setFingerprintFields(List<String> fingerprintFields) {
		this.fingerprintFields = fingerprintFields;
	}

	public Date getFirstSeenAt() {
		return firstSeenAt;
	}

	public void setFirstSeenAt(Date firstSeenAt) {
		this.firstSeenAt = firstSeenAt;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getLastSeenAt() {
		return lastSeenAt;
	}

	public void setLastSeenAt(Date lastSeenAt) {
		this.lastSeenAt = lastSeenAt;
	}

	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}

	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public Severity getSeverity() {
		return severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Number getTimesSeen() {
		return timesSeen;
	}

	public void setTimesSeen(Number timesSeen) {
		this.timesSeen = timesSeen;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Source> getRelatedSources() {
		return relatedSources;
	}

	public void setRelatedSources(List<Source> relatedSources) {
		this.relatedSources = relatedSources;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "Event [fingerprint=" + fingerprint + ", fingerprintFields="
				+ fingerprintFields + ", firstSeenAt=" + firstSeenAt + ", id="
				+ id + ", lastSeenAt=" + lastSeenAt + ", lastUpdatedAt="
				+ lastUpdatedAt + ", message=" + message + ", organizationId="
				+ organizationId + ", properties=" + properties + ", severity="
				+ severity + ", source=" + source + ", status=" + status
				+ ", timesSeen=" + timesSeen + ", title=" + title
				+ ", relatedSources=" + relatedSources + "]";
	}
}
