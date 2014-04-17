package com.boundary.sdk;

/**
 * Abstract class for common properties for UDP route builders.
 * 
 * @author davidg
 *
 */
public abstract class UDPRouterBuilder extends BoundaryRouteBuilder {
	
	protected int port;

	public UDPRouterBuilder() {
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
	 * @return
	 */
	public int getPort() {
		return this.port;
	}
}
