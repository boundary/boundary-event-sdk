/**
 * Encapsulates the health check of a network service by:
 * <ol>
 * <li>PING - Uses ICMP ping to determine if the host is available<li>
 * <li>SOCKET - Opens a TCP socket of a well-known port
 * <li>HTTP - Makes an HTTP(S) request
 * </ol>
 */
package com.boundary.sdk.event.service.port;

import com.boundary.camel.component.ping.PingInfo;
import com.boundary.sdk.event.BoundaryRouteBuilder;
import com.boundary.sdk.event.service.ServiceCheckRequest;
import com.boundary.sdk.event.service.ServiceTest;
import static com.boundary.sdk.event.util.BoundaryHeaderNames.*;

/**
 * @author davidg
 *
 */
public class PortRouteBuilder extends BoundaryRouteBuilder {
	
	private String host;
	private int port;
	private int delay = 5;
	
	public PortRouteBuilder() {
		host = "localhost";
		port = 7;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public int getPort() {
		return this.port;
	}
	
	public void setDelay(int delay) {
		this.delay = delay;
	}
	
	public int getDelay() {
		return this.delay;
	}

	@Override
	public void configure() throws Exception {
		ServiceCheckRequest request = new ServiceCheckRequest();
		PingInfo info = new PingInfo();
		ServiceTest<PingInfo> serviceTest = new ServiceTest<PingInfo>("Sample Ping Test","Ping Test",request.getRequestId(),info);
		String uri = "port://" + getHost() + ":" + getPort() + "/tcp?delay=" + getDelay();
		System.out.println("URI: " + uri);
        from(uri)
        .setHeader(BOUNDARY_SERVICE_TEST).constant(serviceTest)
        .process(new PortInfoToEventProcessor())
        .marshal().serialization()
        .to(getToUri());
	}
}
