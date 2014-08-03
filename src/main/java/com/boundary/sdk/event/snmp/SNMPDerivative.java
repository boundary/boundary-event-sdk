package com.boundary.sdk.event.snmp;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SNMPDerivative {
	
	public SNMPGauge gauge = new SNMPGauge();
	
	public class SNMPGauge {
		
		private final int RANGE= 100;
		
		private Random random = new Random(1L);
		
		private int value;
		
		SNMPGauge() {
			value = random.nextInt(RANGE);
		}
		
		int getValue() {
			return value;
		}
		
		int next() {
			value = value + random.nextInt(RANGE);
			return value;
		}
	};
	
	class SNMPSample {
		private String key;
		private int value;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public int getValue() {
			return value;
		}
		public void setValue(int value) {
			this.value = value;
		}
	};

	private Map derivative = new HashMap<String,SNMPSample>();
	
	public long getDelta(String key,long value) {
		Long newValue = Long.valueOf(value);
		return 0;
	}
	
	public static void main(String [] args) {
		SNMPDerivative d = new SNMPDerivative();
		SNMPGauge gauge = d.gauge;
		long lastValue = 0;

		for (int i=100 ; i != 0 ; i--) {
			long value = gauge.getValue();
			System.out.println("value: " + value + ", diff: " + (value - lastValue));
			lastValue = value;
			gauge.next();
		}
	}
}






