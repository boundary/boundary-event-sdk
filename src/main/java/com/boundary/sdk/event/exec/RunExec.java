package com.boundary.sdk.event.exec;

import com.boundary.sdk.event.BoundaryRouteBuilder;

public class RunExec extends BoundaryRouteBuilder {

	@Override
	public void configure() throws Exception {
		from(getFromUri())
		.to(getToUri());
	}
}
