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
package com.boundary.sdk.snmp.metric;

import com.snmp4j.smi.SmiModule;
import com.snmp4j.smi.SmiObject;

/**
 * Interface to handle transformation of MIB objects into
 * an alternative form.
 */
public interface MibTransform {
	public void beginTransform();
	public void transform(SmiModule module,SmiObject object);
	public void beginModule(SmiModule module);
	public void endModule(SmiModule module);
	public void endTransform();
}
