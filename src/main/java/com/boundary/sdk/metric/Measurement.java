// Copyright 2014-2015 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//    http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.boundary.sdk.metric;

import java.io.Serializable;
import java.util.Date;

import com.boundary.sdk.event.util.UnixTimeSerializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Data structure to add measurement readings to the data store.
 * Upon submitting measurements the data is instantly available to the Dashboard for graphing.
 * In order to submit data you must first have created a metric with a unique metric name.
 * 
 * <ul>
 *     <li>source    - The source of the metric</li>
 *     <li>metric    - The name of the metric that you've set up in your account</li>
 *     <li>measure   - Numeric measure to report</li>
 *     <li>timestamp - Unix timestamp the measurement was taken. If omitted, uses the time at which the measure is received.</li>
 * </ul>
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
public class Measurement implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty
	@JsonInclude
	private String source;
	@JsonProperty
	@JsonInclude
	private String metric;
	@JsonProperty
	@JsonInclude
	private Number measure;
    @JsonProperty
	@JsonSerialize(using = UnixTimeSerializer.class)
	private Date timestamp;
	
	public Measurement() {
		source = "";
		metric = "";
		measure = 0;
	}

	/**
	 * Sets the source of the metric
	 * 
	 * @return {@link String} source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * 	Sets the source of the metric
	 * 
	 * @param source {@link String} Source in form of observation domain id
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * Returns the name of the metric
	 * 
	 * @return {@link String} metric name
	 */
	public String getMetric() {
		return metric;
	}

	/**
	 * Sets the name of the metric
	 * 
	 * @param metric {@link String}
	 */
	public void setMetric(String metric) {
		this.metric = metric;
	}

	/**
	 * Sets the numeric measure
	 * 
	 * @return {@link Number}
	 */
	public Number getMeasure() {
		return measure;
	}
	/**
	 * Sets the numeric measure
	 * @param measure {@link Number} value of measurement
	 */
	public void setMeasure(Number measure) {
		this.measure = measure;
	}

	/**
	 * Sets the timestamp the measurement was taken.
	 * @return {@link Date} time of the measurement
	 */
	public Date getTimestamp() {
		return timestamp;
	}
	/**
	 * Gets the timestamp the measurement was taken.
	 * 
	 * @param timestamp {@link Date} time of the measurement
	 */
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"source\": ");
		sb.append("\"" + source + "\"");
		sb.append(",");
		sb.append("\"metric\": ");
		sb.append("\"" + metric + "\"");
		sb.append(",");
		sb.append("\"measure\": ");
		sb.append(measure);
		sb.append("}");
		return sb.toString();
	}
	
}
