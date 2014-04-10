package com.boundary.sdk;


import org.apache.camel.Converter;
import org.apache.camel.Exchange;
import org.apache.camel.TypeConverter;
import org.apache.camel.component.syslog.SyslogMessage;
import org.apache.camel.component.syslog.SyslogSeverity;
import com.boundary.sdk.RawEvent;


@Converter
public final class SyslogMessageConverter {

	public SyslogMessageConverter() {
		
	}
	
	@Converter
    public static RawEvent toRawEvent(SyslogMessage message, Exchange exchange) {
		
		return RawEvent.getDefaultEvent();
	}
}
