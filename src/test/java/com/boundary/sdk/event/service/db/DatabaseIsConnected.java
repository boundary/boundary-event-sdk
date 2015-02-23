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
package com.boundary.sdk.event.service.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseIsConnected {
	
	public static boolean databaseConnected() {
		boolean connected = false;
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/services","boundary","boundary");
			if (connection != null) {
				connection.close();
				connected=true;
			}
			
		} catch (SQLException e) {

		}
		return connected;
	}
	
	public static void main(String[] args) {
		System.out.printf("Database is %s%n",DatabaseIsConnected.databaseConnected() ? "connected" : "not connected");
	}

}
