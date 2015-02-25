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
package com.boundary.sdk.event.notification;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Top level class of Notification JSON to Java Objects
 */
public class AlarmNotification implements Serializable {
	
	private static final long serialVersionUID = 141334700285462329L;
	@JsonProperty
	private String alarmName;
	@JsonProperty
	private NotificationStatus status;
	@JsonProperty
	private Metric metric;
	@JsonProperty
	private Map<String,Server> affectedServers;
	@JsonProperty
	private Map<String,Server> resolvedServers;
	public String getAlarmName() {
		return alarmName;
	}
	public void setAlarmName(String alarmName) {
		this.alarmName = alarmName;
	}
	public NotificationStatus getStatus() {
		return status;
	}
	public void setStatus(NotificationStatus status) {
		this.status = status;
	}
	public Metric getMetric() {
		return metric;
	}
	public void setMetric(Metric metric) {
		this.metric = metric;
	}
	public Map<String, Server> getAffectedServers() {
		return affectedServers;
	}
	public void setAffectedServers(Map<String, Server> affectedServers) {
		this.affectedServers = affectedServers;
	}
	public Map<String, Server> getResolvedServers() {
		return resolvedServers;
	}
	public void setResolvedServers(Map<String, Server> resolvedServers) {
		this.resolvedServers = resolvedServers;
	}
	
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AlarmNotification [alarmName=");
		builder.append(alarmName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", metric=");
		builder.append(metric);
		builder.append(", affectedServers=");
		builder.append(affectedServers);
		builder.append(", resolvedServers=");
		builder.append(resolvedServers);
		builder.append("]");
		return builder.toString();
	}
	
	public static AlarmNotification load(String resource) throws URISyntaxException {
		AlarmNotification instance = new AlarmNotification();

		ClassLoader classLoader = instance.getClass().getClassLoader();
		URL url = classLoader.getResource(resource);
		File file = new File(url.toURI());

		ObjectMapper mapper = new ObjectMapper();

		try {
			instance = mapper.readValue(file,AlarmNotification.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return instance;
	}
}
