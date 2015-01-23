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

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PropertyUtils {
	
	private static Logger LOG = LoggerFactory.getLogger(PropertyUtils.class);
	
	public static void getProperties(Properties properties, String propertyFileName) {
		if (properties.isEmpty()) {
			try {
				properties.load(Thread.currentThread()
						.getContextClassLoader()
						.getResourceAsStream(propertyFileName));
			} catch (IOException e) {
				e.printStackTrace();
				LOG.error(e.getStackTrace().toString());
			}
		}
	}

}
