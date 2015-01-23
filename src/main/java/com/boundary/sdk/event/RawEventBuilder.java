// Copyright 2014-2015 Boundary, Inc.
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

package com.boundary.sdk.event;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class RawEventBuilder {
	
	private Date createdAt;
	private List<String> fingerprintFields;
	private String message;
	private String organizationId;
	private Map<String,Object> properties;
	private Date receivedAt;
	private Source sender;
	private Severity severity;
	private Source source;
	private Status status;
	private List<String> tags;
	private String title;

	RawEventBuilder() {
	}
	
	public RawEventBuilder setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}
	
	public RawEventBuilder setFingerprintFields(List<String> fingerprintFields) {
		this.fingerprintFields = fingerprintFields;
		return this;
	}
	
	public RawEventBuilder setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public RawEventBuilder setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
		return this;
	}
	
	public RawEventBuilder setProperties(Map<String,Object> properties) {
		this.properties = properties;
		return this;
	}
	
	public RawEventBuilder setReceivedAt(Date receivedAt) {
		this.receivedAt = receivedAt;
		return this;
	}

	public RawEventBuilder setSender(Source sender) {
		this.sender = sender;
		return this;
	}
	
	public RawEventBuilder setSeverity(Severity severity) {
		this.severity = severity;
		return this;
	}
	
	public RawEventBuilder setSource(Source source) {
		this.source = source;
		return this;
	}
	
	public RawEventBuilder setStatus(Status status) {
		this.status = status;
		return this;
	}

	public RawEventBuilder setTags(List<String> tags) {
		this.tags = tags;
		return this;
	}
	
	public RawEventBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public RawEvent build() {
		RawEvent newEvent = new RawEvent();
		
		if (this.createdAt != null) {
			newEvent.setCreatedAt(this.createdAt);
		}
		
		if (this.fingerprintFields != null) {
			newEvent.setFingerprintFields(this.fingerprintFields);
		}
		
		if (this.message != null) {
			newEvent.setMessage(this.message);
		}

		if (this.organizationId != null) {
			newEvent.setOrganizationId(this.organizationId);
		}
		
		if (this.properties != null) {
			newEvent.setProperties(this.properties);
		}
		
		if (this.receivedAt != null) {
			newEvent.setReceivedAt(this.receivedAt);
		}
		
		if (this.sender != null) {
			newEvent.setSender(this.sender);
		}

		if (this.severity != null) {
			newEvent.setSeverity(this.severity);
		}
		
		if (this.source != null) {
			newEvent.setSource(this.source);
		}
		
		if (this.status != null) {
			newEvent.setStatus(this.status);
		}
		
		if (this.tags != null) {
			newEvent.setTags(this.tags);
		}
		
		if (this.title != null) {
			newEvent.setTitle(this.title);
		}
		
		return newEvent;
	}
	
	public static RawEventBuilder builder() {
		return new RawEventBuilder();
	}
}
