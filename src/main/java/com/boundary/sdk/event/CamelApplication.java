// Copyright 2014-2015 Boundary, Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.boundary.sdk.event;
import org.apache.camel.spring.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper class for creating camel applications
 * Derived classes need only provide the name of the application and a URI
 * to the Spring camel configuration. Allows the quick creation of applications
 * from just a spring configuration file.
 * 
 * <pre>
 *  public class SyslogEventAdapter extends CamelApplication {
 *  
 *      public static void main(String [] args) throws Exception {
 *          MyCamelApp app = new MyCamelApp(
 *          	"META-INF/spring/syslog-event-adapter.xml",
 *              "My Camel App");
 *          app.boot();
 *      }
 * }
 * </pre>
 *
 */
public class CamelApplication
{
	private static Logger LOG = LoggerFactory.getLogger(CamelApplication.class);

    protected Main main;

	private String uri;
	private String name;
    
	/**
	 * Construct a camel application with a name identifying the application and
	 * a Spring camel configuration file
	 * 
	 * @param uri Points to Spring camel configuration file
	 * @param name Name of the application, outputs on start up
	 */
    public CamelApplication(String uri,String name) {
    	this.uri = uri;
    	this.name = name;
    }
    /**
     * Starts a camel application context
     * 
     * @throws Exception Upon any error
     */
    protected void boot() throws Exception
    {
        // create a Main instance
        main = new Main();
        
        // enable hangup support so you can press ctrl + c to terminate the JVM
        main.enableHangupSupport();
        
        // Get spring definition of the routes to start
        LOG.info("Loading application context from: " + uri);
        
        // Set the application context that configures the camel routes
        main.setApplicationContextUri(uri);
         
        // run until you terminate the JVM
        LOG.info("Starting {}",this.name);
        main.run(); 
    }
}
