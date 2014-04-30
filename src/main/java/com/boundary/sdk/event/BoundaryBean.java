package com.boundary.sdk.event;

public class BoundaryBean {
	
	BoundaryBean() {
		
	}
	public void callMe() {
		System.out.println(this.getClass().getName() + ": callMe method has been called");
	}
}