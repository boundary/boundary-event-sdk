/**
 * Encapsulates the health check of a network service by:
 * <ol>
 * <li>PING - Uses ICMP ping to determine if the host is available<li>
 * <li>SOCKET - Opens a TCP socket of a well-known port
 * <li>HTTP - Makes an HTTP(S) request
 * </ol>
 */
package com.boundary.sdk.event.service.ping;

import com.boundary.sdk.event.BoundaryRouteBuilder;

/**
 * @author davidg
 *
 */
public class PingRouteBuilder extends BoundaryRouteBuilder {
	
	private String ipAddress;
	private int port;
	private String url;
	
	/**
	 * 
	 */
	public PingRouteBuilder() {
	}
	

	@Override
	public void configure() throws Exception {
        from("ping://tcp?host=localhost&delay=5")
        .process(new PingInfoToEventProcessor())
        .to(this.getToUri());
	}

}
