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
