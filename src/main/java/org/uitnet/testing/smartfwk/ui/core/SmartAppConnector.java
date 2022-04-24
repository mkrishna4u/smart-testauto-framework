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
package org.uitnet.testing.smartfwk.ui.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartAppConnector {
	private static Map<String, DefaultAppConnector> appConnectorMap = new HashMap<String, DefaultAppConnector>();

	private SmartAppConnector() {

	}

	/**
	 * Connects with the configured app. If connector does not exist for the app then creates new.
	 * @param appName
	 * @return
	 */
	public static DefaultAppConnector connect(String appName) {
		DefaultAppConnector appConnector = appConnectorMap.get(appName);
		if (appConnector == null) {
			appConnector = new DefaultAppConnector(appName);
			appConnectorMap.put(appName, appConnector);
		}
		return appConnector;
	}
	
	public static void disconnect(String appName) {
		DefaultAppConnector appConnector = appConnectorMap.get(appName);
		if (appConnector != null) {
			appConnector.logoutAndQuit();
			appConnectorMap.remove(appName);
		}
	}

	public static void close() {
		for (Map.Entry<String, DefaultAppConnector> connector : appConnectorMap.entrySet()) {
			connector.getValue().logoutAndQuit();
		}

		appConnectorMap.clear();
	}
}
