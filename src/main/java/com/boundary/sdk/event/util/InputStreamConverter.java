package com.boundary.sdk.event.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Converter;

/**
 * Uses Camel's conversion system to convert from a {@link InputStream} to {@link List}
 * 
 * @author davidg
 *
 */
@Converter
public final class InputStreamConverter {

	@Converter
	public static List<String> toList(InputStream stream) {

		BufferedReader bufferedReader = null;

		bufferedReader = new BufferedReader(new InputStreamReader(stream));
		List<String> lines = new ArrayList<String>();
		
		String line = null;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				System.out.println(line);
				lines.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return lines;
	}

}
