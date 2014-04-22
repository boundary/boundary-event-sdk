package com.boundary.sdk;

import org.apache.camel.builder.RouteBuilder;

/**
 * Base classes for all Boundary created route builders
 * 
 * @author davidg
 *
 */
public abstract class BoundaryRouteBuilder extends RouteBuilder {
	
	protected String toUri;
	protected String fromUri;
	protected String routeId;
	// TBD, does this make sense adding
	protected String logFileName;
	protected int startUpOrder;
	
	protected final static String DEFAULT_EVENT_TO_URI = "direct:boundary-event";

	public BoundaryRouteBuilder() {
		this.toUri = "";
		this.routeId = "";
		this.logFileName = "";
		this.startUpOrder = 0;
	}
	
	/**
	 * Sets the URI to send events to
	 * @param toUri
	 */
	public void setToUri(String toUri)  {
		this.toUri = toUri;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getToUri() {
		return this.toUri;
	}
	
	/**
	 * URI used to receive {@link RawEvents} from.
	 * @param fromUri
	 */
	public void setFromUri(String fromUri) {
		this.fromUri = fromUri;
	}
	
	/**
	 * Get the current URI that is receiving {@link RawEvents}
	 * 
	 * @return
	 */
	public String getFromUri() {
		return this.fromUri;
	}

	
	/**
	 * Sets the route ID to a defined value, make it easier to track
	 * the route in the logs.
	 * 
	 * @param routeId
	 */
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRouteId() {
		return this.routeId;
	}
	
	/**
	 * Sets relative order of when this route starts
	 * @param startUpOrder
	 */
	public void setStartUpOrder(int startUpOrder) {
		this.startUpOrder = startUpOrder;
	}
	
	/**
	 * Returns the start up order of the route
	 * @return
	 */
	public int getStartUpOrder() {
		return this.startUpOrder;
	}


}
