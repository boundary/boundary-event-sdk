/**
 * 
 */
package com.boundary.sdk.event;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author davidg
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public enum TestStatus {
	
	SUCCEED("SUCCEED"),
	FAIL("FAIL");
	
	@JsonProperty
	private String name;
	
	TestStatus(String name) {
		this.name = name;
	}

}
