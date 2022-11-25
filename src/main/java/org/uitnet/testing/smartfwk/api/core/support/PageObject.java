/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package org.uitnet.testing.smartfwk.api.core.support;

import java.util.Map;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PageObject {
	private String name;
	private Integer maxTimeToWaitInSeconds;
	private Map<String, String> params;
	
	public PageObject() {
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMaxTimeToWaitInSeconds() {
		return maxTimeToWaitInSeconds;
	}

	public void setMaxTimeToWaitInSeconds(Integer maxTimeToWaitInSeconds) {
		this.maxTimeToWaitInSeconds = maxTimeToWaitInSeconds;
	}

	/**
	 * Returns the locator parameters.
	 * @return
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * Sets the locator parameters.
	 * @param locatorParams
	 */
	public void setParams(Map<String, String> locatorParams) {
		this.params = locatorParams;
	}

}
