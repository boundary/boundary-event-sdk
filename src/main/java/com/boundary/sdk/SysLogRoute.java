package com.boundary.sdk;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.DataFormat;
import org.apache.camel.component.syslog.Rfc3164SyslogDataFormat;

public class SysLogRoute extends RouteBuilder {

	public SysLogRoute() {
	}
	
	@Override
	public void configure() {

        DataFormat syslogDataFormat = new Rfc3164SyslogDataFormat();

        from("netty:udp://127.0.0.1:10514?sync=false&allowDefaultCodec=false")
		.unmarshal(syslogDataFormat)
		.process(new SyslogToEventProcessor())
		.marshal().serialization()
        .to("direct:test")
        ;
	}
}
