/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
package org.uitnet.testing.smartfwk.ui.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SingletonAppConnectorMap {
	private static SingletonAppConnectorMap instance;
	private Map<String, AbstractAppConnector> map = new HashMap<>();
	private String activeAppName = null;

	private SingletonAppConnectorMap() {
		// do nothing
	}

	public static SingletonAppConnectorMap getInstance() {
		if (instance != null) {
			return instance;
		}

		synchronized (SingletonAppConnectorMap.class) {
			if (instance == null) {
				instance = new SingletonAppConnectorMap();
			}
		}

		return instance;
	}

	public Map<String, AbstractAppConnector> getMap() {
		return map;
	}

	public String getActiveAppName() {
		return activeAppName;
	}

	public void setActiveAppName(String activeAppName) {
		this.activeAppName = activeAppName;
	}
}