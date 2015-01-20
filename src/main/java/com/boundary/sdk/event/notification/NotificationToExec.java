package com.boundary.sdk.event.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.OutHeaders;
import static org.apache.camel.component.exec.ExecBinding.*;

public class NotificationToExec {
	
	public void setArgs(@OutHeaders Map headers, @Body Notification notification) {
		ArrayList<String> args = new ArrayList<String>();
		Event event = notification.getEvent();
		
		args.add("msend.pl");
		args.add("-o");
		args.add(event.getSource().getName());
		args.add("-r");
		args.add(event.getSeverity().toString());
		headers.put(EXEC_COMMAND_ARGS, args);
		headers.put(EXEC_COMMAND_EXECUTABLE, "echo");
	}
}
