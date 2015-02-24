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

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Server implements Serializable {
	
	private static final long serialVersionUID = -7682762205137369401L;
	
	@JsonProperty("isSet")
	private boolean set;
	@JsonProperty
	private String aggregate;
	@JsonProperty
	private String metric;
	@JsonProperty
	private String hostname;
	@JsonProperty
	private Number value;
	@JsonProperty
	private Number threshold;
	@JsonProperty
	private Date time;
	@JsonProperty
	private String link;
	@JsonProperty
	private Text text;
	
	public boolean isSet() {
		return set;
	}
	public String getAggregate() {
		return aggregate;
	}
	public String getMetric() {
		return metric;
	}
	public String getHostname() {
		return hostname;
	}
	public Number getValue() {
		return value;
	}
	public Number getThreshold() {
		return threshold;
	}
	public Date getTime() {
		return time;
	}
	public String getLink() {
		return link;
	}
	public Text getText() {
		return text;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Server [set=");
		builder.append(set);
		builder.append("\n, aggregate=");
		builder.append(aggregate);
		builder.append("\n, metric=");
		builder.append(metric);
		builder.append("\n, hostname=");
		builder.append(hostname);
		builder.append("\n, value=");
		builder.append(value);
		builder.append("\n, threshold=");
		builder.append(threshold);
		builder.append("\n, time=");
		builder.append(time);
		builder.append("\n, link=");
		builder.append(link);
		builder.append("\n, text=");
		builder.append(text);
		builder.append("]");
		return builder.toString();
	}
}
