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
package com.boundary.sdk.event.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.boundary.sdk.event.snmp.SmiSupport;

/*
 * Wraps standard logging for external tools
 */
public class Log {
	
	private static Logger LOG = LoggerFactory.getLogger(Logger.class);

	private static boolean enabled = false;
	
	public static void logStandard(boolean enable) {
		Log.enabled = enable;
	}
	public static void error(String message) {
		if (Log.enabled == true) {
			System.err.println(message);
		} else {
			LOG.error(message);
		}
	}

	public static void info(String message) {
		if (Log.enabled == true) {
			System.err.println(message);
		} else {
			LOG.info(message);
		}
	}
	
	public static void debug(String message) {
		if (Log.enabled == true) {
			System.err.println(message);
		} else {
			LOG.debug(message);
		}
	}
}
