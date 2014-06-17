package com.boundary.sdk.event;

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

	/**
	 * Default constructor
	 * 
	 */
	public BoundaryRouteBuilder() {
		this.toUri = "";
		this.routeId = "";
		this.logFileName = "";
		this.startUpOrder = 0;
	}
	
	/**
	 * Sets the URI to send events to
	 * @param toUri URI to send messages to
	 */
	public void setToUri(String toUri)  {
		this.toUri = toUri;
	}
	
	/**
	 * Returns the current value of the to URI
	 * @return {@link String}
	 */
	public String getToUri() {
		return this.toUri;
	}
	
	/**
	 * URI used to receive {@link RawEvent} from.
	 * @param fromUri URI to receive messages from`
	 */
	public void setFromUri(String fromUri) {
		this.fromUri = fromUri;
	}
	
	/**
	 * Get the current URI that is receiving {@link RawEvent}
	 * 
	 * @return {@link String}
	 */
	public String getFromUri() {
		return this.fromUri;
	}

	
	/**
	 * Sets the route ID to a defined value, make it easier to track
	 * the route in the logs.
	 * 
	 * @param routeId Name of the route
	 */
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	
	/**
	 * Returns the current value of the route id.
	 * @return {@link String} Name of the route
	 */
	public String getRouteId() {
		return this.routeId;
	}
	
	/**
	 * Sets relative order of when this route starts.
	 * 
	 * @param startUpOrder Global order in which to start this route
	 */
	public void setStartUpOrder(int startUpOrder) {
		this.startUpOrder = startUpOrder;
	}
	
	/**
	 * Returns the start up order of the route
	 * 
	 * @return int Global order in which to start this route
	 */
	public int getStartUpOrder() {
		return this.startUpOrder;
	}
}
