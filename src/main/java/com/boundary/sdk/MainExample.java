package com.boundary.sdk;

import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.Processor;

public class MainExample {

	private Main main;

	public static void main(String[] args) throws Exception {
		MainExample example = new MainExample();
		example.boot();
	}

	public void boot() throws Exception {
		// create a Main instance
		main = new Main();
		// enable hangup support so you can press ctrl + c to terminate the JVM
		main.enableHangupSupport();
		// bind MyBean into the registery
		main.bind("foo", new MyBean());
		// add routes
		// TODO Fixed values, need to make externally configurable
		String orgID = "3ehRi7uZeeaTN12dErF5XOnRXjC";
		String apiKey = "ARI0PzUzWYUo7GG1OxiHmABTpr9";

		main.addRouteBuilder(new BoundaryRoute(orgID,apiKey));

		// run until you terminate the JVM
		System.out
				.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
		main.run();
	}

	private static class MyRouteBuilder extends RouteBuilder {
		@Override
		public void configure() throws Exception {
			from("timer:foo?delay=2000").process(new Processor() {
				public void process(Exchange exchange) throws Exception {
					System.out.println("Invoked timer at " + new Date());
				}
			}).beanRef("foo");
		}
	}

	public static class MyBean {
		public void callMe() {
			System.out.println("MyBean.calleMe method has been called");
		}
	}
}