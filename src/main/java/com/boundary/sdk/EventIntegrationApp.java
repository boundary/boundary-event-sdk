package com.boundary.sdk;
import org.apache.camel.spring.Main;

public class EventIntegrationApp
{
     
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
		String apiKey = "ARI0PzUzWYUo7GG1OxiHmABTpr9";

        main.addRouteBuilder(new EventRoute(orgID,apiKey));
        main.addRouteBuilder(new BoundaryEventRoute(orgID,apiKey));
         
        // run until you terminate the JVM
        System.out.println("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        main.run(); 
    }
}
