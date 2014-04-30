package com.boundary.sdk.event;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventApplication
{
	private static Logger LOG = LoggerFactory.getLogger(EventApplication.class);

    private Main main;
     
    public static void main(String[] args) throws Exception
    {
    	EventApplication app = new EventApplication();
        app.boot();
    }
     
    private void boot() throws Exception
    {
        // create a Main instance
        main = new Main();
        
        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();
        
        // Get spring definition of the routes to start
        main.setApplicationContextUri(System.getProperty("boundary.application.context.uri"));
         
        // run until you terminate the JVM
        LOG.info("Starting Camel. Use ctrl + c to terminate the JVM.\n");
        main.run(); 
    }
}
