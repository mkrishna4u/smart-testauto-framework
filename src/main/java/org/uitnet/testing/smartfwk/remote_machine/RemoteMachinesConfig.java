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
package org.uitnet.testing.smartfwk.remote_machine;

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
public class RemoteMachinesConfig {
	private String appName;
	private String configFilePath;
	private List<RemoteMachineConfig> remoteMachines;
	private Map<String, Object> additionalProps;
	
	public RemoteMachinesConfig(String appName, String remoteMachinesConfigFilePath, DocumentContext yamlDoc) {
		this.appName = appName;
		this.configFilePath = remoteMachinesConfigFilePath;
		this.additionalProps = yamlDoc.read("$.additionalProps", new TypeRef<HashMap<String, Object>>() {
		});
		this.remoteMachines = yamlDoc.read("$.remoteMachines", new TypeRef<List<RemoteMachineConfig>>() {
		});
	}

	public String getAppName() {
		return appName;
	}

	public String getConfigFilePath() {
		return configFilePath;
	}

	public List<RemoteMachineConfig> getRemoteMachines() {
		return remoteMachines;
	}

	public RemoteMachineConfig getRemoteMachine(String name) {
		if (remoteMachines == null) {
			Assert.fail("Remote machine '" + name + "' is not configured with application '" + appName
					+ "'. You can configure in '" + configFilePath + "' file.");
		}

		for (RemoteMachineConfig remoteMachine : remoteMachines) {
			if (remoteMachine.getName().equals(name)) {
				return remoteMachine;
			}
		}

		Assert.fail("Remote machine '" + name + "' is not configured with application '" + appName
				+ "'. You can configure in '" + configFilePath + "' file.");
		return null;
	}

	public <T> T getAdditionalPropertyValue(String propName, Class<T> clazz) {
		Assert.assertTrue(additionalProps.containsKey(propName), "Please specify the additional property '" + propName
				+ "' in application '" + appName + "' '" + configFilePath + "' file.");
		return clazz.cast(additionalProps.get(propName));
	}
}
