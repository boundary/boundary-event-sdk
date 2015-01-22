package com.boundary.sdk.event.esper;

import java.util.Random;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

public class EsperProcessor implements Processor {
	
	private Random random;
	
	int count;

	public EsperProcessor() {
		this.random = new Random();
		count = 0;
	}

	@Override
	public void process(Exchange arg0) throws Exception {
		
		Message in = arg0.getIn();
		EsperEvent e = new EsperEvent();
		if (count % 2 == 0) {
			e.setName("Bar");
		} else {
			e.setName("Foo");
		}
		count++;
		e.setValue(random.nextInt(10));
		
		in.setBody(e);
	}

}
