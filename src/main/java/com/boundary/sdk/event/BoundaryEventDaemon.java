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
import java.util.Timer;

import org.apache.commons.daemon.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoundaryEventDaemon extends EventApplication implements Daemon {
	
	private static Logger LOG = LoggerFactory.getLogger(BoundaryEventDaemon.class);
    
    public BoundaryEventDaemon() {
    	
    }

    public static void main(String[] args) {

    }

    public void init(DaemonContext dc) throws DaemonInitException, Exception {
        LOG.info("Initializing ...");
    }

    public void start() throws Exception {
    	LOG.info("Starting ...");
        boot();
    }

    public void stop() throws Exception {
    	LOG.info("Stopping ...");
        main.stop();
    }

    public void destroy() {
    	LOG.info("Destroy ...");
    }
 }

