package com.boundary.sdk.api.meter;

import java.io.Serializable;

public abstract class MeterAPI implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String BASE_PATH = "meters";
	private MeterData data;

	public MeterData getData() {
		return data;
	}

	public void setData(MeterData data) {
		this.data = data;
	}

	public abstract String getPath();
	public abstract String getMethod();
}
