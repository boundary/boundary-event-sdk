package com.boundary.sdk;

public class BoundaryBean {
	
	BoundaryBean() {
		
	}
	public void callMe() {
		System.out.println(this.getClass().getName() + ": callMe method has been called");
	}
}