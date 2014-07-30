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
