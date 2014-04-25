package com.boundary.sdk;

/**
 * Abstract class for common properties for UDP route builders.
 * 
 * @author davidg
 *
 */
public abstract class UDPRouteBuilder extends BoundaryRouteBuilder {
	
	protected int port;

	public UDPRouteBuilder() {
	}
	
	/**
	 * Sets the port to listen for forwarded syslog messages
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * 
	 * @return int returns the port configured by this {@link BoundaryRouteBuilder}
	 */
	public int getPort() {
		return this.port;
	}
}
