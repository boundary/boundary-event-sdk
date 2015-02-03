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

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.camel.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Uses Camel's conversion system to convert from a {@link List} to {@link Iterator}
 * 
 * @author davidg
 *
 */
@Converter
public final class SelectListConverter {
	
	private static Logger LOG = LoggerFactory.getLogger(SelectListConverter.class);

	@Converter
	public static Iterator<Map<String, Object>> toIterator(List<Map<String,Object>> list) {
		LOG.info("CALLING CONVERTER");
		return list.iterator();
	}
}
