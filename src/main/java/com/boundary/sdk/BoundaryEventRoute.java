package com.boundary.sdk;

import java.util.Map;
import java.util.Set;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.AuthMethod;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.component.http.HttpConfiguration;

public class BoundaryEventRoute extends RouteBuilder {

	private String apiHost = "api.boundary.com";
	private String orgID;
	private String apiKey;
	private String authorization;
	private StringBuffer url = new StringBuffer();
	private String contentType = "application/json";
	public BoundaryEventRoute(String orgID, String apiKey) {
		this.orgID = orgID;
		this.apiKey = apiKey;
		this.authorization = apiKey + ":";
		url.append("https://" + apiHost + "/" + orgID + "/" + "events");
		System.out.println("url: " + url + ", orgID: " + orgID + ", apiKey: " + apiKey);
	}
	
	protected String getBody() {
		String s1 = "{ \"title\": \"example\", \"message\": \"test\",\"tags\": [\"example\", \"test\", \"stuff\"], \"fingerprintFields\": [\"@title\"], \"source\": { \"ref\": \"myhost\",\"type\": \"host\"}}";
		return s1;
	}
	@Override
	public void configure() {
            HttpConfiguration config = new HttpConfiguration();
            config.setAuthMethod(AuthMethod.Basic);
            config.setAuthUsername(this.apiKey);
            config.setAuthPassword("");

            HttpComponent http = this.getContext().getComponent("https", HttpComponent.class);
            http.setHttpConfiguration(config);

		from("direct:event")
				.setHeader(Exchange.ACCEPT_CONTENT_TYPE,constant(contentType))
				.setHeader(Exchange.CONTENT_TYPE,constant(contentType))
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setBody().simple(getBody())
				.process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received event: " + exchange.getIn().getBody(String.class));
                        Message m = exchange.getIn();
                        Object o = m.getBody();
                        System.out.println("Class: " + o.getClass());
                        Map <String,Object> headers = m.getHeaders();
                        System.out.println("headers: " + headers);
                    }
                })
                .to("file://?fileName=http.log")
				.to(url.toString());
		;
	}
}
