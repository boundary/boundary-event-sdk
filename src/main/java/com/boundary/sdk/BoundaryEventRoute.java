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
import org.apache.camel.component.syslog.SyslogMessage;

/**
 * 
 * @author davidg
 *
 */
public class BoundaryEventRoute extends RouteBuilder {

	protected String apiHost = "api.boundary.com";
	protected String orgID;
	protected String apiKey;
	protected String authorization;
	protected StringBuffer url = new StringBuffer();
	protected String contentType = "application/json";
	protected String routeName;
	protected String fromID;
	
	public BoundaryEventRoute(String orgID, String apiKey) {
		this(orgID,apiKey,"BOUNDARY-EVENT-ROUTE","boundary-event");
	}
	public BoundaryEventRoute(String orgID, String apiKey, String routeName, String fromID) {
		this.orgID = orgID;
		this.apiKey = apiKey;
		this.authorization = apiKey + ":";
		url.append("https://" + apiHost + "/" + orgID + "/" + "events");
		System.out.println("url: " + url + ", orgID: " + orgID + ", apiKey: " + apiKey);
		this.routeName = routeName;
		this.fromID = fromID;
	}
	
	protected String getBody(Exchange exchange) {
		String s1 = "{ \"title\": \"example\", \"message\": \"test\",\"tags\": [\"example\", \"test\", \"stuff\"], \"fingerprintFields\": [\"@title\"], \"source\": { \"ref\": \"myhost\",\"type\": \"host\"}}";
		String s2 = "{ \"title\": \"%s\", \"severity\": \"%s\",\"status\":\"OPEN\",\"message\": \"test\",\"fingerprintFields\": [\"@title\"], \"source\": { \"ref\": \"myhost\",\"type\": \"host\"}}";
		Message m = exchange.getIn();
		Event event = (Event) m.getBody(Event.class);
		System.out.println("EVENT: " + event);
		
		String result = String.format(s2,event.getTitle(),event.getSeverity().toString());
		
		return result;
	}
	@Override
	public void configure() {
            HttpConfiguration config = new HttpConfiguration();
            config.setAuthMethod(AuthMethod.Basic);
            config.setAuthUsername(this.apiKey);
            config.setAuthPassword("");

            HttpComponent http = this.getContext().getComponent("https", HttpComponent.class);
            http.setHttpConfiguration(config);

		from("direct:" + this.fromID)
				.routeId(this.routeName)
				.setHeader(Exchange.ACCEPT_CONTENT_TYPE,constant(contentType))
				.setHeader(Exchange.CONTENT_TYPE,constant(contentType))
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				// .setBody().simple(getBody())
				.unmarshal().serialization()
				.process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received event: " + exchange.getIn().getBody(Event.class));
                        Message m = exchange.getIn();
                        m.setBody(getBody(exchange));
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
