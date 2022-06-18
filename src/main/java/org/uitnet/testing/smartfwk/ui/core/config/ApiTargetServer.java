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
package org.uitnet.testing.smartfwk.ui.core.config;

import java.util.Map;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ApiTargetServer {
	private String name;
	private String baseURL;
	private String actionHandlerClass;
	private Integer sessionExpiryDurationInSeconds;
	private Map<String, Object> additionalProps;
	private ApiConfig apiConfig;

	public ApiTargetServer() {
		// do nothing
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseURL() {
		return baseURL;
	}

	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}

	public String getActionHandlerClass() {
		return actionHandlerClass;
	}

	public void setActionHandlerClass(String actionHandlerClass) {
		this.actionHandlerClass = actionHandlerClass;
	}

	public Integer getSessionExpiryDurationInSeconds() {
		return sessionExpiryDurationInSeconds;
	}

	public void setSessionExpiryDurationInSeconds(Integer sessionExpiryDurationInSeconds) {
		this.sessionExpiryDurationInSeconds = sessionExpiryDurationInSeconds;
	}

	public Map<String, Object> getAdditionalProps() {
		return additionalProps;
	}

	public void setAdditionalProps(Map<String, Object> additionalProps) {
		this.additionalProps = additionalProps;
	}

	public void setApiConfig(ApiConfig apiConfig) {
		this.apiConfig = apiConfig;
	}

	public <T> T getAdditionalPropertyValue(String propName, Class<T> clazz) {
		Assert.assertTrue(additionalProps.containsKey(propName),
				"Please specify the additional property '" + propName + "' in application '" + apiConfig.getAppName()
						+ "' '" + apiConfig.getApiConfigFilePath() + "' file for targetServer: " + name + ".");
		return clazz.cast(additionalProps.get(propName));
	}
}
