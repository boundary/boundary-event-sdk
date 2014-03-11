package com.boundary.sdk;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.syslog.Rfc3164SyslogDataFormat;
import org.apache.camel.spi.DataFormat;

public class SysLogRoute extends RouteBuilder {

	public SysLogRoute() {
	}
	
	@Override
	public void configure() {
		//		from("mina:udp://localhost:10514?sync=false")

        DataFormat syslogDataFormat = new Rfc3164SyslogDataFormat();

		//from("netty:udp://localhost:10514")
        from("netty:udp://127.0.0.1:10514?sync=false&allowDefaultCodec=false")
		.unmarshal(syslogDataFormat)
//		.to("file://?fileName=syslog.log")
		.process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Received log: " + exchange.getIn().getBody(String.class));
                        Message m = exchange.getIn();
                        Object o = m.getBody();
                        System.out.println("Class: " + o.getClass());
                        Map <String,Object> headers = m.getHeaders();
                        System.out.println("headers: " + headers);
                    }
                })
        .to("direct:event");
	}
}