package com.boundary.sdk.event;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventApplication
{
	private static Logger LOG = LoggerFactory.getLogger(EventApplication.class);

    protected Main main;
     
    public static void main(String[] args) throws Exception
    {
    	EventApplication app = new EventApplication();
        app.boot();
    }
     
    protected void boot() throws Exception
    {
        // create a Main instance
        main = new Main();
        
        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();
        
        // Get spring definition of the routes to start
        String uri = System.getProperty("boundary.application.context.uri");
        LOG.info("Loading application context from: " + uri);
        
        // Set the application context that configures the camel routes
        main.setApplicationContextUri(uri);
         
        // run until you terminate the JVM
        LOG.info("Starting Boundary Event SDK.");
        main.run(); 
    }
}
