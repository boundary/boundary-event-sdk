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
package com.boundary.sdk.api.event;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.Endpoint;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.DefaultProducerTemplate;
import org.eclipse.jetty.io.EndPoint;

import com.boundary.sdk.event.BoundaryEventRouteBuilder;
import com.boundary.sdk.event.RawEvent;
import com.boundary.sdk.event.Severity;

/**
 * 
 * An alarm object with content.
 * <code>
 *
 *  // Send one alarm warning 
 *  BoundaryEvent event = new BoundaryEvent(NimAlarm.NIML_WARNING, "Disk 80% full");  // So far only a warning
 *  String id = alarm.send();
 *  
 * </code>
 * <code>
 * // Send the alarm(s) and close (optional)
 * // Use NimAlarm.close() for a polite socket close (sends the "_close" 
 * // command to the server).
 * NimAlarm alarm = new NimAlarm(NimAlarm.NIML_MAJOR, "Disk 90% full"); // A  major error
 * String id = alarm.send();                                // Send the alarm
 * alarm.setSeverity(NimAlarm.NIML_MAJOR);                  // Up to a critical error
 * alarm.setMessage("Disk 100% full");                      // with a new texst
 * id = alarm.send();                                       // Send the updated critical alarm
 * alarm.close();                                           // Disconnect (polite closedown)
 * </code>
 */
public class Event extends com.boundary.sdk.event.RawEvent {
	
	private RawEvent rawEvent;
    CamelContext context;

	
	public void start() {
	    CamelContext context = new DefaultCamelContext();
	    BoundaryEventRouteBuilder eventRoute = new BoundaryEventRouteBuilder();
	    eventRoute.setFromUri("direct:event-api-in");
	    eventRoute.setOrgId(System.getenv("BOUNDRY_ORG_ID"));
	    eventRoute.setApiKey(System.getenv("BOUNDARY_API_KEY"));
	    try {
			context.addRoutes(eventRoute);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    try {
			context.addRoutes(new RouteBuilder() {
				public void configure() {
					from("direct:event-in")
					.marshal().serialization()
					.to("direct:event-api-in");
				}
				
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //
	    // Start the Camel route
	    try {
			context.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	    try {
			context.stop ();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Setup the route
	}
	
	public void stop() {
		try {
			context.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Event() {
		rawEvent = new RawEvent();
	}
	 
	Event(Severity severity,String title,List<String>fingerPrints) {
	}
	
	void send() {
		Endpoint endPoint = context.getEndpoint("direct:event-in");
		ProducerTemplate template = new DefaultProducerTemplate(context,endPoint);
		template.sendBody(rawEvent);
	}
//	 
//	Constructor Summary
//	NimAlarm(int severity, String message) 
//	          Create a NimAlarm with the provided severity and message.
//	NimAlarm(int severity, String message, String subsystem) 
//	          Create a NimAlarm with the provided severity, message, and subsystem.
//	NimAlarm(int severity, String message, String subsystem, String suppressionid) 
//	          Constructor with severity, message, subsystem and suppression id.
//	NimAlarm(int severity, String message, String subsystem, String suppressionId, String source) 
//	          Constructor with severity, message, subsystem, suppression id and source.
//	NimAlarm(int severity, String message, String subsystem, String suppressionId, String source, ConfigurationItem ci, String metricId) 
//	          TNT2 NimAlarm constructor.
//	NimAlarm(int severity, String message, String subsystem, String suppressionId, String source, ConfigurationItem ci, String metricId, String alarmToken, PDS variables) 
//	           
//	NimAlarm(int severity, String message, String subsystem, String suppressionId, String source, ConfigurationItem ci, String metricId, String alarmToken, PDS variables, String custom1, String custom2, String custom3, String custom4, String custom5) 
//	           
//	NimAlarm(int severity, String message, String subsystem, String suppressionId, String source, ConfigurationItem ci, String metricId, String custom1, String custom2, String custom3, String custom4, String custom5) 
//	          Added 25may2010 - for custom fields For handling custom fields
//	 
//	Method Summary
//	 String	getAlarmToken() 
//	          Return the alarm token used for localization.
//	 String	getCustom1() 
//	          Return the "Custom 1" alarm property.
//	 String	getCustom2() 
//	          Return the "Custom 2" alarm property.
//	 String	getCustom3() 
//	          Return the "Custom 3" alarm property.
//	 String	getCustom4() 
//	          Return the "Custom 4" alarm property.
//	 String	getCustom5() 
//	          Return the "Custom 5" alarm property.
//	 String	getDevId() 
//	          Return the device identifier for the device that is associated with this alarm.
//	 String	getMessage() 
//	          Get the message.
//	 String	getMetId() 
//	          Return the metric identifier for the particular metric that is associated with this alarm.
//	 int	getSeverity() 
//	          Get the severity.
//	 String	getSource() 
//	          Get the source.
//	 String	getSubSystem() 
//	          Get the subsystem.
//	 String	getSuppressionId() 
//	          Get the suppression id.
//	 PDS	getVariables() 
//	          Return the variable substitution PDS to be used for localization.
//	 String	send() 
//	          Send an Alarm message.
//	 String	sendOnConnectionFrom(NimObjectSender nos) 
//	          Send an Alarm message, reusing an existing connection from another NimQoS or NimAlarm object.
//	 void	setAlarmToken(String value) 
//	          Set the alarm token used for localization.
//	 void	setCustom1(String custom) 
//	          Set the "Custom 1" alarm property.
//	 void	setCustom2(String custom) 
//	          Set the "Custom 2" alarm property.
//	 void	setCustom3(String custom) 
//	          Set the "Custom 3" alarm property.
//	 void	setCustom4(String custom) 
//	          Set the "Custom 4" alarm property.
//	 void	setCustom5(String custom) 
//	          Set the "Custom 5" alarm property.
//	 void	setDevId(String devId) 
//	          Set the device identifier for this alarm.
//	 void	setMessage(String msg) 
//	          Set the message.
//	 void	setMetId(String metId) 
//	          Set the metric identifier for this QoS measurement.
//	 void	setSeverity(int severity) 
//	          Set the severity.
//	 void	setSource(String source) 
//	          Set the source.
//	 void	setSubSystem(String subsys) 
//	          Set the subsystem.
//	 void	setSuppressionId(String suppressionid) 
//	          Set the suppression id.
//	 void	setVariables(PDS value) 
//	          Return the variable substitution PDS to be used for localization.
	
}
