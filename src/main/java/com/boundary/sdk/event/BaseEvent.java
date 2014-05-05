package com.boundary.sdk.event;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Common properties between RawEvent and Event
 * 
 * @author davidg
 *
 */
public class BaseEvent {
	
	final static int MAXIMUM_STRING_LENGTH = 255;
	
	private static Logger LOG = LoggerFactory.getLogger(BaseEvent.class);
	
	/**
	 * Default constructor for BaseEvent
	 */
	public BaseEvent() {
		
	}
	
	/**
	 * Helper method that truncates {@link String}
	 * to the maximum allowed by the Boundary Event API
	 * 
	 * @param str
	 * @return String
	 */
	public static String truncateToMaximumLength(String str) {
		int length = Math.min(str.length(),MAXIMUM_STRING_LENGTH);
		String truncStr = str.substring(0, length);
		if (length < str.length() ) {
			LOG.warn("String truncated from {} to {}", str,truncStr);
		}
		return truncStr;
	}
	
	/**
	 * Helper method that truncates an {@link ArrayList} of {@link String}
	 * to the maximum allowed by the Boundary Event API
	 * 
	 * @param array
	 * @return
	 */
	public static ArrayList<String> truncateToMaximumLength(ArrayList<String> array) {
		ArrayList<String> truncatedArray = new ArrayList<String>(array.size());
		for (String s : array) {
			truncatedArray.add(truncateToMaximumLength(s));
		}
		return truncatedArray;
	}
	
	/**
	 * Helper method that truncates an {@link Object} if it is a {@link String}
	 * to the maximum allowed by the Boundary Event API
	 * @param obj
	 * @return
	 */
	public static Object truncateToMaximumLength(Object obj) {
		String s = "";
		Object truncatedObj = obj;
		if (obj instanceof String) {
			s = truncateToMaximumLength((String)obj);
			truncatedObj = s;
		}
		return truncatedObj;
	}
	
	/**
	 * Helper method that truncates a {@link LinkedHashMap} with keys of {@link String}
	 * and values of {@link Object} that are instances of {@link String}
	 * to the maximum allowed by the Boundary Event API
	 * 
	 * @param prop
	 * @return
	 */
	public static LinkedHashMap<String,Object> truncateToMaximumLength(LinkedHashMap<String,Object> prop) {
		LinkedHashMap<String,Object> truncatedProp = new LinkedHashMap<String,Object>(prop.size());
		
		for (String key : prop.keySet()) {
		    truncatedProp.put(key, truncateToMaximumLength(prop.get(key)));
		}
		return truncatedProp;
	}
}
