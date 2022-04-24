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
package org.uitnet.testing.smartfwk.ui.core.appdriver;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartAppDriverFactory {
	private static SmartAppDriverFactory instance;

	// appDriverMap Key: <app_name>:<app-id>, value: SmartAppDriver
	private Map<String, SmartAppDriver> appDriverMap;

	private SmartAppDriverFactory() {
		appDriverMap = new LinkedHashMap<String, SmartAppDriver>();
	}

	public static SmartAppDriverFactory getInstance() {
		if (instance == null) {
			synchronized (SmartAppDriverFactory.class) {
				if (instance == null) {
					instance = new SmartAppDriverFactory();
				}
			}
		}
		return instance;
	}

	public synchronized SmartAppDriver getNewAppDriver(String appName) {
		AppConfig appConfig = TestConfigManager.getInstance().getAppConfig(appName);
		SmartAppDriver appDriver = new SmartAppDriver(appName, appConfig.getAppType(), appConfig.getTestPlatformType());
		appDriverMap.put(appName + ":" + appDriver.getAppId(), appDriver);

		return appDriver;
	}

	/**
	 * Returns the latest app driver. If nothing exist then create one and return a
	 * new one.
	 * 
	 * @param appName
	 * @return
	 */
	public synchronized SmartAppDriver getLatestAppDriverOrCreateOne(String appName) {
		SmartAppDriver driver = null;
		String appKey = appName + ":";
		for (Map.Entry<String, SmartAppDriver> entry : appDriverMap.entrySet()) {
			if (entry.getKey().startsWith(appKey)) {
				driver = entry.getValue();
			}
		}

		if (driver == null) {
			return getNewAppDriver(appName);
		}
		return driver;
	}

	public List<SmartAppDriver> getAllAppDrivers(String appName) {
		List<SmartAppDriver> drivers = new LinkedList<SmartAppDriver>();
		String appKey = appName + ":";
		for (Map.Entry<String, SmartAppDriver> entry : appDriverMap.entrySet()) {
			if (entry.getKey().startsWith(appKey)) {
				drivers.add(entry.getValue());
			}
		}
		return drivers;
	}

	public synchronized void removeAppDriver(String appName, int appId) {
		String key = appName + ":" + appId;
		SmartAppDriver driver = appDriverMap.get(key);
		if (driver != null) {
			driver.closeApp();
			appDriverMap.remove(key);
		}
	}

	public synchronized void removeAllAppDrivers(String appName) {
		List<SmartAppDriver> drivers = new LinkedList<SmartAppDriver>();
		for (Map.Entry<String, SmartAppDriver> entry : appDriverMap.entrySet()) {
			if (entry.getKey().startsWith(appName)) {
				entry.getValue().closeApp();
				drivers.add(entry.getValue());
			}
		}

		for (SmartAppDriver driver : drivers) {
			appDriverMap.remove(driver.getAppName() + ":" + driver.getAppId());
		}
	}

	public synchronized void removeAllAppDrivers() {
		List<SmartAppDriver> drivers = new LinkedList<SmartAppDriver>();
		for (Map.Entry<String, SmartAppDriver> entry : appDriverMap.entrySet()) {
			entry.getValue().closeApp();
			drivers.add(entry.getValue());
		}

		for (SmartAppDriver driver : drivers) {
			appDriverMap.remove(driver.getAppName() + ":" + driver.getAppId());
		}
	}
}
