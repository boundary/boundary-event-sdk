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

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Exec implements Serializable {

	private static final long serialVersionUID = -941245839174286954L;
	private String executable;
	private String args;
	private List<String> argList;
	private String workingDirectory;
	int timeout;
	
	public Exec() {
		argList = new ArrayList<String>();
		workingDirectory = new File("").getAbsolutePath();
		args = "";
		timeout = 10;
	}
	
	public Exec(String executable,String args) {
		argList = new ArrayList<String>();
		workingDirectory = new File("").getAbsolutePath();
		this.executable = executable;
		this.args = args;
	}
	
	public Exec(String executable,String args,String workingDirectory,int timeout) {
		argList = new ArrayList<String>();
		this.executable = executable;
		this.args = args;
		this.workingDirectory = workingDirectory;
		this.timeout = timeout;
	}
	
	public Exec(String executable,List<String> argList,String workingDirectory,int timeout) {
		this.argList = argList;
		this.args = "";
		this.executable = executable;
		this.workingDirectory = workingDirectory;
		this.timeout = timeout;
	}

	public String getExecutable() {
		return executable;
	}
	public void setExecutable(String executable) {
		this.executable = executable;
	}
	public String getArgs() {
		return args;
	}
	public void setArgs(String args) {
		this.args = args;
	}
	public List<String> getArgList() {
		return argList;
	}
	public void setArgList(List<String> argList) {
		this.argList = argList;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public String getWorkingDirectory() {
		return workingDirectory;
	}
	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}
}
