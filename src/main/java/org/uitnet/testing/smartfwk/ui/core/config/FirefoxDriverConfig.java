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

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowserType;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class FirefoxDriverConfig {
	private String profilePath;
	private String driverFilePath;
	private Map<String, String> browserExtensions;
	private Map<String, String> browserPreferences;
	private Map<String, String> driverCapabilities;

	public FirefoxDriverConfig(String seleniumConfigDir, Properties properties) {
		browserExtensions = new HashMap<String, String>(3);
		browserPreferences = new HashMap<String, String>(3);
		init(seleniumConfigDir, properties);
	}

	private void init(String seleniumConfigDir, Properties properties) {
		driverFilePath = properties.getProperty("BROWSER_DRIVER_FILENAME");
		if (driverFilePath == null || "".equals(driverFilePath.trim())) {
		} else {
			driverFilePath = seleniumConfigDir + File.separator + "web-drivers" + File.separator
					+ WebBrowserType.firefox.name() + File.separator + driverFilePath.trim();
		}

		profilePath = seleniumConfigDir + File.separator + "web-drivers" + File.separator
				+ WebBrowserType.firefox.name() + File.separator + "profile";

		String prop, propValue;
		for (Object key : properties.keySet()) {
			prop = String.valueOf(key).trim();
			propValue = properties.getProperty(String.valueOf(key));

			// load extensions
			if (prop.startsWith("BROWSER-EXT_")) {
				browserExtensions.put(prop.substring("BROWSER-EXT_".length()),
						seleniumConfigDir + File.separator + "web-drivers" + File.separator
								+ WebBrowserType.firefox.name() + File.separator + "extensions" + File.separator
								+ propValue);
			}

			// load preferences
			if (prop.startsWith("BROWSER-PREF_")) {
				browserPreferences.put(prop.substring("BROWSER-PREF_".length()), propValue);
			}
		}
		
		// initialize driver capabilities
		driverCapabilities = new LinkedHashMap<String, String>();
		for(Object key : properties.keySet()) {
			String strKey = (String) key;
			if(strKey.startsWith("DriverCapability.")) {
				driverCapabilities.put(strKey.substring("DriverCapability.".length()), properties.getProperty(strKey));
			}
		}
	}

	public String getProfilePath() {
		return profilePath;
	}

	public String getDriverFilePath() {
		return driverFilePath;
	}

	public Map<String, String> getBrowserExtensions() {
		return browserExtensions;
	}

	public Map<String, String> getBrowserPrefs() {
		return browserPreferences;
	}
	
	public Map<String, String> getDriverCapabilities() {
		return driverCapabilities;
	}
}
