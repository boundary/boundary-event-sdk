package com.boundary.sdk;

import java.util.Date;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.Processor;
import org.apache.camel.Exchange;

public class BoundaryIntegrationApp {
 
    private Main main;
 
    public static void main(String[] args) throws Exception {
    	BoundaryIntegrationApp app = new BoundaryIntegrationApp();
        app.boot();
    }
 
    public void boot() throws Exception {
		// TODO Fixed values, need to make externally configurable
		String orgID = "3ehRi7uZeeaTN12dErF5XOnRXjC";
		String apiKey = "ARI0PzUzWYUo7GG1OxiHmABTpr9";

        //
		// Create new main instance and enable hang support so the process can be killed.
		// TODO: Option to run as a daemon
        main = new Main();
        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();
        
        //
        // Bind any beans if we are using any to the registry.
        //TODO: Configurable or dynamic configuration.
        
        main.bind("boundary", new BoundaryBean());
        
        //
        // Add routes
        // TODO: how to integrate spring configuration routes
        // TODO: Dynamic loading of route objects using reflection
        // main.addRouteBuilder(new BoundaryRoute(orgID,apiKey));
        //main.addRouteBuilder(new BoundaryTimerRoute(5000));
        main.addRouteBuilder(new SNMPRoute());
        main.addRouteBuilder(new SysLogRoute());
        main.addRouteBuilder(new BoundaryEventRoute(orgID,apiKey));
        main.addRouteBuilder(new TestRoute());
        
        // run until you terminate the JVM
        System.out.println("Starting Boundary Integration Application. Use ctrl + c to terminate the JVM.\n");
        main.run();
    }
}