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
import java.io.FileReader;
import java.util.Properties;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class EnvironmentConfig {
	private String name;
	private Properties properties;
//	private ApplicationType appType;
//	private String appDriverConfigFileName;
//	private String appLaunchUrl;
//	private PlatformType testPlatformType;
//	private WebBrowserType appWebBrowser;
//	private ProxyConfiguration proxyConfig;
//	private String remoteWebDriverProviderClass;
//	private String dbProfileNames;
//	private String userProfileNames;
//	private String apiConfigFileName;

	public EnvironmentConfig(String appName, String environmentName) {
		this.name = environmentName;
		properties = new Properties();
		if(!(environmentName == null || "".equals(environmentName.trim()))) {
			init(appName);
		} else {
			this.name = "Default";
		}
	}

	private void init(String appName) {
		String activeEnvConfigPath = Locations.getConfigDirPath() + File.separator + "apps-config" + File.separator
				+ appName + File.separator + "environments" + File.separator + name + ".properties";
		Assert.assertTrue(new File(activeEnvConfigPath).exists(),
				"Missing '" + activeEnvConfigPath + "' environment file in '" + appName + "' application.");
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(activeEnvConfigPath);
			properties.load(fileReader);
			fileReader.close();
//
//			String propValue = properties.getProperty("APPLICATION_TYPE");
//			if(propValue != null) {
//				if ("".equals(propValue.trim())) {
//					Assert.fail("FATAL: Please specify APPLICATION_TYPE in '" + activeEnvConfigPath + "' file. AppName: "
//							+ appName + ". Exiting ...");
//					System.exit(1);
//				} else {
//					this.appType = ApplicationType.valueOf2(propValue);
//				}
//			}
//
//			propValue = properties.getProperty("APP_DRIVER_CONFIG_FILE_NAME");
//			if(propValue != null) {
//				if ("".equals(propValue.trim())) {
//					Assert.fail("FATAL: Please specify APP_DRIVER_CONFIG_FILE_NAME in '" + activeEnvConfigPath + "' file. AppName: "
//							+ appName + ". Exiting ...");
//					System.exit(1);
//				} else {
//					appDriverConfigFileName = propValue.trim();
//				}
//			}
//			
//			propValue = properties.getProperty("API_CONFIG_FILE_NAME");
//			if(propValue != null) {
//				if ("".equals(propValue.trim())) {
//					Assert.fail("FATAL: Please specify API_CONFIG_FILE_NAME in '" + activeEnvConfigPath + "' file. AppName: "
//							+ appName + ". Exiting ...");
//					System.exit(1);
//				} else {
//					apiConfigFileName = propValue.trim();
//				}
//			}
//
//			propValue = properties.getProperty("APP_LAUNCH_URL");
//			if(propValue != null) {
//				if ("".equals(propValue.trim())) {
//					Assert.fail("FATAL: Please specify APP_LAUNCH_URL in  '" + activeEnvConfigPath + "' file. AppName: "
//							+ appName + ". Exiting ...");
//					System.exit(1);
//				} else {
//					appLaunchUrl = propValue.trim();
//				}
//			}
//
//			propValue = properties.getProperty("TEST_PLATFORM_TYPE");
//			if(propValue != null) {
//				if ("".equals(propValue.trim())) {
//					Assert.fail("FATAL: Please specify TEST_PLATFORM_TYPE in '" + activeEnvConfigPath + "' file. AppName: "
//							+ appName + ". Exiting ...");
//					System.exit(1);
//				} else {
//					this.testPlatformType = PlatformType.valueOf2(propValue);
//				}
//			}
//
//			propValue = properties.getProperty("APP_WEB_BROWSER");
//			if(propValue != null) {
//				if ("".equals(propValue.trim())) {
//					Assert.fail("FATAL: Please specify APP_WEB_BROWSER in '" + activeEnvConfigPath + "' file. AppName: "
//							+ appName + ". Exiting ...");
//					System.exit(1);
//				} else {
//					appWebBrowser = WebBrowserType.valueOf2(propValue.trim());
//				}
//			}
//
//			proxyConfig = new ProxyConfiguration(appName, properties, true);
//			remoteWebDriverProviderClass = properties.getProperty("REMOTE_WEB_DRIVER_PROVIDER_CLASS");
//			
//			dbProfileNames = properties.getProperty("DB_PROFILE_NAMES");
//			userProfileNames = properties.getProperty("USER_PROFILE_NAMES");
//			if (userProfileNames == null || "".equals(userProfileNames.trim())) {
//				Assert.fail("FATAL: Please specify USER_PROFILE_NAMES in '" + name + ".properties' file. AppName: " + appName
//						+ ". Exiting ...");
//				System.exit(1);
//			}

		} catch (Exception ex) {
			Assert.fail("Failed to read property file - " + activeEnvConfigPath + ". Going to exit...", ex);
			System.exit(1);
		}
	}

	public String getName() {
		return name;
	}

	public Properties getProperties() {
		return properties;
	}

	
}
