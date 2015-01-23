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

public class EsperRawEvent{

	private static final long serialVersionUID = 1300660569674106078L;
	
	private RawEvent event;

	public EsperRawEvent(RawEvent event) {
		this.event = event;
	}
	
	public String [] getTags() {
		return (String []) event.getTags().toArray();
	}
	
	public String getTag(int index) {
		return event.getFingerprintFields().get(index);
	}
	
	public void setTag(int index,String value) {
		
	}
	
	public Object getProperty(String key) {
		return event.getProperties().get(key);
	}
	
	public void setProperty(String key, Object value) {
		event.getProperties().put(key, value);
	}
	
	public Source getSource() {
		return event.getSource();
	}
	
	public Source getSender() {
		return event.getSender();
	}
	
	public Date getTimestamp() {
		return event.getCreatedAt();
	}


}
