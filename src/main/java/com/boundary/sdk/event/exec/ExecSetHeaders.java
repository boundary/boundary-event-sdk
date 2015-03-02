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
package com.boundary.sdk.event.exec;

import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_ARGS;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_EXECUTABLE;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_TIMEOUT;
import static org.apache.camel.component.exec.ExecBinding.EXEC_COMMAND_WORKING_DIR;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

/**
 * Implements a processor to take a {@link Exec} extract its values
 * and create headers in the message to that the {@link ExecRouteBuilder} can
 * execute the script or program presented by the {@link Exec}
 */
public class ExecSetHeaders implements Processor {
	
	public ExecSetHeaders() {
		
	}

	@Override
	public void process(Exchange exchange) throws Exception {
		Message message = exchange.getIn();
		Exec exec = message.getBody(Exec.class);
		message.setHeader(EXEC_COMMAND_EXECUTABLE,exec.getExecutable());
		if (exec.getArgList().size() > 0) {
			message.setHeader(EXEC_COMMAND_ARGS,exec.getArgList());
		} else {
			message.setHeader(EXEC_COMMAND_ARGS,exec.getArgs());
		}
		String workingDirectory = exec.getWorkingDirectory();
		if (workingDirectory != null) {
			message.setHeader(EXEC_COMMAND_WORKING_DIR,exec.getWorkingDirectory());
		}
		message.setHeader(EXEC_COMMAND_TIMEOUT,exec.getTimeout());
	}
}
