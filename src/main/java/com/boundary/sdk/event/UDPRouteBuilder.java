package com.boundary.sdk.event;


/**
 * Abstract class for common properties for UDP route builders.
 * 
 * @author davidg
 *
 */
public abstract class UDPRouteBuilder extends BoundaryRouteBuilder {
	
	protected int port;
	protected String bindAddress;

	public UDPRouteBuilder() {
	   bindAddress = "0.0.0.0";
	}
	
	/**
	 * Sets the port to listen for socket messages
	 * 
	 * @param port Listening port
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
	 * @param bindAddress
	 */
	public void setBindAddress(String bindAddress) {
		this.bindAddress = bindAddress;
	}
	
	/**
	 * Gets the host used by this route builder.
         * @return String
	 */
	public String getBindAddress() {
		return this.bindAddress;
	}
}
