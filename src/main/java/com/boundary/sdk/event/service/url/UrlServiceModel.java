// Copyright 2014 Boundary, Inc.
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
package com.boundary.sdk.event.service.url;

import com.boundary.camel.component.url.UrlResult;
import com.boundary.sdk.event.service.ServiceModel;

/**
 * Service Model for URL Service Tests
 *
 */
public class UrlServiceModel extends ServiceModel {
	
	private String responseBody;
	private boolean responseIgnoreBody;
	private int responseCode;
	private int responseTime;
	private boolean responseTimeMet;
	private boolean responseBodyMatched;
	private boolean responseCodeMatched;
	
	public boolean isResponseTimeMet() {
		return responseTimeMet;
	}

	public void setResponseTimeMet(boolean responseTimeMet) {
		this.responseTimeMet = responseTimeMet;
	}

	public boolean isResponseBodyMatched() {
		return responseBodyMatched;
	}

	public void setResponseBodyMatched(boolean responseBodyMatched) {
		this.responseBodyMatched = responseBodyMatched;
	}

	public boolean isResponseCodeMatched() {
		return responseCodeMatched;
	}

	public void setResponseCodeMatched(boolean responseCodeMatched) {
		this.responseCodeMatched = responseCodeMatched;
	}

	
	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public int getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(int responseTime) {
		this.responseTime = responseTime;
	}

	public UrlServiceModel() {
		responseBody = "";
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}
	
	public boolean getResponseIgnoreBody() {
		return responseIgnoreBody;
	}
	
	public void setResponseIgnoreBody(boolean responseIgnoreBody) {
		this.responseIgnoreBody = responseIgnoreBody;
	}

	public boolean isHealthly(UrlResult result)
	{
		setResponseTimeMet(result.getResponseTime() <= getResponseTime());
		setResponseCodeMatched(result.getResponseCode() == getResponseCode());
		setResponseBodyMatched(result.getResponseBody() == getResponseBody()
				|| getResponseIgnoreBody() == true);
		return isResponseTimeMet() && isResponseCodeMatched() && isResponseBodyMatched();
	}

	@Override
	public String toString() {
		return "UrlServiceModel [responseBody=" + responseBody
				+ ", responseIgnoreBody=" + responseIgnoreBody
				+ ", responseCode=" + responseCode + ", responseTime="
				+ responseTime + ", responseTimeMet=" + responseTimeMet
				+ ", responseBodyMatched=" + responseBodyMatched
				+ ", responseCodeMatched=" + responseCodeMatched + "]";
	}
}
