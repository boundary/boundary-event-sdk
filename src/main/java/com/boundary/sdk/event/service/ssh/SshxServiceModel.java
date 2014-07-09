package com.boundary.sdk.event.service.ssh;

import com.boundary.sdk.event.service.ServiceModel;

/**
 * Service Model for SSH Service Tests
 * 
 * @author davidg
 *
 */
public class SshxServiceModel extends ServiceModel {
	
	private String expectedOutput;
	
	public SshxServiceModel() {
		expectedOutput = "";
	}

	public String getExpectedOutput() {
		return expectedOutput;
	}

	public void setExpectedOutput(String expectedOutput) {
		this.expectedOutput = expectedOutput;
	}

}
