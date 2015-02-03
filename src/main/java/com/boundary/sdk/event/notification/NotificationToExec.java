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
