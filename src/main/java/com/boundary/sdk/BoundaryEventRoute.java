package com.boundary.sdk;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

public class BoundaryEventRoute extends RouteBuilder {

	//private String apiHost = "api.boundary.com";
	private String apiHost = "localhost";
	private String orgID;
	private String apiKey;
	private String authorization;
	private StringBuffer url = new StringBuffer();
	private String contentType = "application/json";
	public BoundaryEventRoute(String orgID, String apiKey) {
		this.orgID = orgID;
		this.apiKey = apiKey;
		this.authorization = "Basic: " + apiKey;
		//
		// https://$APIHOST/$APIID/events"
		// url.append("https://" + apiHost + "/" + orgID + "/" + "events");
		url.append("http://" + apiHost + "/~davidg/hello.txt");
		//System.out.println("url: " + url + ", orgID: " + orgID + ", apiKey: " + apiKey);
	}
	
	protected String getBody() {
		return "{\"fingerprintFields\": [\"mytitle\"],\"source\": {\"ref\": \"mymeter\",\"type\": \"host\"},\"title\": \"My title\"}";
	}
	@Override
	public void configure() {
		//RouteBuilder r = new RouteBuilder();
		// body().append(getBody()).
		from("direct:event")
//				.setHeader(Exchange.ACCEPT_CONTENT_TYPE,constant(contentType))
//				.setHeader(Exchange.AUTHENTICATION, constant(authorization))
//				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
//				.process(new Processor() {
//                    public void process(Exchange exchange) throws Exception {
//                        System.out.println("Received order: " + exchange.getIn().getBody(String.class));
//                    }
//                })
//				.setBody().simple(getBody())
//				.to(url.toString())
//				.to("file://target/test-reports")
				.beanRef("boundary");
	}
}
