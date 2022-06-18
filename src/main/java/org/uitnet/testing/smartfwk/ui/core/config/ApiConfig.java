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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ApiConfig {
	private String appName;
	private String apiConfigFilePath;
	private List<ApiTargetServer> targetServers;
	private Map<String, Object> additionalProps;

	public ApiConfig(String appName, String apiConfigFilePath, DocumentContext yamlDoc) {
		this.appName = appName;
		this.apiConfigFilePath = apiConfigFilePath;
		this.additionalProps = yamlDoc.read("$.additionalProps", new TypeRef<HashMap<String, Object>>() {
		});
		this.targetServers = yamlDoc.read("$.targetServers", new TypeRef<List<ApiTargetServer>>() {
		});
	}

	public String getAppName() {
		return appName;
	}

	public String getApiConfigFilePath() {
		return apiConfigFilePath;
	}

	public List<ApiTargetServer> getTargetServers() {
		return targetServers;
	}

	public ApiTargetServer getTargetServer(String name) {
		if (targetServers == null) {
			Assert.fail("API target server '" + name + "' is not configured with application '" + appName
					+ "'. You can configure in '" + apiConfigFilePath + "' file.");
		}

		for (ApiTargetServer targetServer : targetServers) {
			if (targetServer.getName().equals(name)) {
				return targetServer;
			}
		}

		Assert.fail("API target server '" + name + "' is not configured with application '" + appName
				+ "'. You can configure in '" + apiConfigFilePath + "' file.");
		return null;
	}

	public <T> T getAdditionalPropertyValue(String propName, Class<T> clazz) {
		Assert.assertTrue(additionalProps.containsKey(propName), "Please specify the additional property '" + propName
				+ "' in application '" + appName + "' '" + apiConfigFilePath + "' file.");
		return clazz.cast(additionalProps.get(propName));
	}
}
