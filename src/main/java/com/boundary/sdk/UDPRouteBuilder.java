package com.boundary.sdk;

/**
 * Abstract class for common properties for UDP route builders.
 * 
 * @author davidg
 *
 */
public abstract class UDPRouteBuilder extends BoundaryRouteBuilder {
	
	protected int port;
	protected String host;

	public UDPRouteBuilder() {
	}
	
	/**
	 * Sets the port to listen for socket messages
	 * 
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
	/**
	 * Gets the port for this route builder.
	 * 
	 * @return int returns the port configured by this {@link BoundaryRouteBuilder}
	 */
	public int getPort() {
		return this.port;
	}
	
	/**
	 * Sets the host to contact
	 * 
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Gets the host used by this route builder.
	 */
	public String getHost() {
		return this.host;
	}
}
