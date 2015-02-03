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
package com.boundary.sdk.event.script;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScriptEvent implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1860570764995937469L;
	private Date date;
	private String string;
	private int integer;
	private double decimal;
	private Map<String,Object>map;
	private List<String> list;
	private ScriptEnum enumeration;
	private ScriptObject object;
	
	public ScriptEvent() {
		this.date = new Date();
		this.string = "";
		this.map = new HashMap<String,Object>();
		this.list = new ArrayList<String>();
		this.object = new ScriptObject();
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getString() {
		return string;
	}

	public void setString(String string) {
		this.string = string;
	}

	public int getInteger() {
		return integer;
	}

	public void setInteger(int integer) {
		this.integer = integer;
	}

	public double getDecimal() {
		return decimal;
	}

	public void setDecimal(double decimal) {
		this.decimal = decimal;
	}

	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public ScriptEnum getEnumeration() {
		return enumeration;
	}

	public void setEnumeration(ScriptEnum enumeration) {
		this.enumeration = enumeration;
	}

	public ScriptObject getObject() {
		return object;
	}

	public void setObject(ScriptObject object) {
		this.object = object;
	}

	@Override
	public String toString() {
		return "ScriptEvent [date=" + date + ", string=" + string
				+ ", integer=" + integer + ", decimal=" + decimal + ", map="
				+ map + ", list=" + list + ", enumeration=" + enumeration
				+ ", object=" + object + "]";
	}


}
