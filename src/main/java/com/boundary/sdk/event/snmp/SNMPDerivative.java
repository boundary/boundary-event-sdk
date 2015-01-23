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






