package com.boundary.sdk;

import java.util.Date;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

public class BoundaryIntegrationApp {
 
    private Main main;
 
    public static void main(String[] args) throws Exception {
        MainExample example = new MainExample();
        example.boot();
    }
 
    public void boot() throws Exception {
		// TODO Fixed values, need to make externally configurable
		//String orgID = "3ehRi7uZeeaTN12dErF5XOnRXjC";
		//String apiKey = "ARI0PzUzWYUo7GG1OxiHmABTpr9";

        //
		// Create new main instance and enable hang support so the process can be killed.
		// TODO: Option to run daemonized
        main = new Main();
        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();
        
        //
        // Bind any beans if we are using any to the registry.
        //TODO: Configurable or dynamic configuration.
        
        main.bind("foo", new MyBean());
        
        //
        // Add routes
        // TODO: how to integrate spring configuration routes
        // TODO: Dynamic loading of route objects using reflection
        // main.addRouteBuilder(new BoundaryRoute(orgID,apiKey));
        main.addRouteBuilder(new MyRouteBuilder());
 
        // run until you terminate the JVM
        System.out.println("Starting Boundary Integration Application. Use ctrl + c to terminate the JVM.\n");
        main.run();
    }
 
    private static class MyRouteBuilder extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            from("timer:foo?delay=4000")
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Invoked timer at " + new Date());
                    }
                })
                .beanRef("foo");
        }
    }
 
    public static class MyBean {
        public void callMe() {
            System.out.println("MyBean.calleMe method has been called");
        }
    }
}