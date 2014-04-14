/**
 * 
 */
package com.boundary.sdk;

/**
 * @author davidg
 *
 */
public enum TestStatus {
	
	SUCCEED("SUCCEED"),
	FAIL("FAIL");
	
	private String name;
	
	TestStatus(String name) {
		this.name = name;
	}

}
