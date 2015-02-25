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

import com.fasterxml.jackson.annotation.JsonProperty;

public class Text implements Serializable {

	private static final long serialVersionUID = 8570365157652149921L;
	
	@JsonProperty("isSet")
	private boolean set;
	@JsonProperty
	private String serverName;
	@JsonProperty
	private String link;
	@JsonProperty
	private String labelHTML;
	@JsonProperty
	private String labelText;
	public boolean isSet() {
		return set;
	}
	public String getServerName() {
		return serverName;
	}
	public String getLink() {
		return link;
	}
	public String getLabelHTML() {
		return labelHTML;
	}
	public String getLabelText() {
		return labelText;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Text [set=");
		builder.append(set);
		builder.append(", serverName=");
		builder.append(serverName);
		builder.append(", link=");
		builder.append(link);
		builder.append(", labelHTML=");
		builder.append(labelHTML);
		builder.append(", labelText=");
		builder.append(labelText);
		builder.append("]");
		return builder.toString();
	}
	
	
}
