package com.boundary.sdk;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventIntegrationApp
{
	private static Logger LOG = LoggerFactory.getLogger(EventIntegrationApp.class);

    private Main main;
     
    public static void main(String[] args) throws Exception
    {
    	EventIntegrationApp app = new EventIntegrationApp();
        app.boot();
    }
     
    private void boot() throws Exception
    {
        // create a Main instance
        main = new Main();
        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();
        main.setApplicationContextUri("META-INF/boundary-sdk-routes.xml");
        
		// TODO Fixed values, need to make externally configurable
		String orgID = "3ehRi7uZeeaTN12dErF5XOnRXjC";
		String apiKey = "GN5HMCeu9trD5NYO5ZqYQrZe8aY";

        main.addRouteBuilder(new EventRoute(orgID,apiKey));
        main.addRouteBuilder(new BoundaryEventRoute(orgID,apiKey));
        main.addRouteBuilder(new SNMPRoute());
         
        // run until you terminate the JVM
        System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        main.run(); 
    }
}
