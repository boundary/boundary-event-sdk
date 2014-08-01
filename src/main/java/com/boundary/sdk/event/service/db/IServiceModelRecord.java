package com.boundary.sdk.event.service.db;

import java.util.Map;

import com.boundary.sdk.event.service.ServiceCheckRequest;

public interface IServiceModelRecord {
	
	public void populate(ServiceCheckRequest request, Map<String,Object> row);

}
