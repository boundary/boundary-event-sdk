package com.boundary.sdk.api;

public class SampleAPIRouteBuilder extends BoundaryAPIRouteBuilder {

	@Override
	public String getPath() {
		return "sample";
	}

	@Override
	public void configure() {
		from("direct:sample-in")
		.to("mock:sample-out");
	}
}
