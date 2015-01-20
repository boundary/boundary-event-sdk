package com.boundary.sdk.api.meter;

public class ListMeter extends MeterAPI {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getPath() {
		StringBuffer sb = new StringBuffer();
		sb.append(MeterAPI.BASE_PATH);
		MeterData data = getData();
		if (data.getName() != null) {
			sb.append("/");
			sb.append(data.getName());
		}
		return sb.toString();
	}

	@Override
	public String getMethod() {
		return "GET";
	}
}
