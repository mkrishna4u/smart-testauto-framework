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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.config.database.orm.OrmDatabaseQueryHandler;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class TestConfigManager {
	private static TestConfigManager instance;
	private static final String TEST_CONFIG_FILE_PATH = Locations.getConfigDirPath() + File.separator
			+ "TestConfig.properties";

	private String appsConfigDir;
	private List<String> appNames;
	private String cucumberTestcasesDir;
	private String appScreenCaptureDir;
	private String sikuliConfigDir;
	private String sikuliResourcesDir;
	private PlatformType hostPlatformType;

	// Key: App-Name, Value AppConfig
	private Map<String, AppConfig> appConfigs;

	private Map<String, String> additionalProps;

	private Map<String, AppDriverConfig> seleniumDriverConfigs;
	private SikuliSettings sikuliSettings;

	private TestConfigManager() {
		init();
		initSikuliSettings();
	}

	public static TestConfigManager getInstance() {
		if (instance == null) {
			synchronized (TestConfigManager.class) {
				if (instance == null) {
					instance = new TestConfigManager();
				}
			}
		}
		return instance;
	}

	private void init() {
		hostPlatformType = OSDetectorUtil.getHostPlatform();
		appNames = new LinkedList<String>();
		appConfigs = new LinkedHashMap<String, AppConfig>();
		additionalProps = new LinkedHashMap<String, String>();

		try (FileReader fileReader = new FileReader(TEST_CONFIG_FILE_PATH)) {
			Properties properties = new Properties();
			properties.load(fileReader);

			initTestConfig(properties);
			initAppConfigs();
		} catch (Exception ex) {
			Assert.fail("Failed to read property file - " + TEST_CONFIG_FILE_PATH + ". Going to exit...", ex);
			System.exit(1);
		}
	}

	private void initTestConfig(Properties properties) {
		try {
			// Apply config file properties to TestConfiguration
			appsConfigDir = Locations.getProjectRootDir() + File.separator + "test-config" + File.separator + "apps-config";

			String appNamesAsStr = System.getProperty("APP_NAMES");
			if (appNamesAsStr == null || "".equals(appNamesAsStr.trim())) {
				appNamesAsStr = properties.getProperty("APP_NAMES");
				if (appNamesAsStr == null || "".equals(appNamesAsStr.trim())) {
					Assert.fail("FATAL: No application configured.");
					System.exit(1);
				}
			}
			String[] arr = appNamesAsStr.split(",");
			for (String appName : arr) {
				if ("".equals(appName.trim())) {
					continue;
				}
				appNames.add(appName.trim());
			}
			if (appNames.size() == 0) {
				Assert.fail("FATAL: No application configured.");
				System.exit(1);
			}

			cucumberTestcasesDir = Locations.getProjectRootDir() + File.separator + "cucumber-testcases";

			appScreenCaptureDir = Locations.getProjectRootDir() + File.separator + "test-results" + File.separator + "screen-captures";

			sikuliConfigDir = Locations.getProjectRootDir() + File.separator + "test-config" + File.separator + "sikuli-config";

			sikuliResourcesDir = Locations.getProjectRootDir() + File.separator + "sikuli-resources";

			String keyStr;
			for (Object key : properties.keySet()) {
				keyStr = String.valueOf(key);
				if (keyStr.startsWith("_")) {
					additionalProps.put(keyStr, properties.getProperty(keyStr));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading configured testcase config class. Going to exit...", ex);
			System.exit(1);
		}
	}

	private void initAppConfigs() {
		String appConfigFile;
		FileReader fileReader;
		String currAppName = null;
		
		try {
			AppConfig appConfig;
			for (String appName : appNames) {
				currAppName = appName;
				appConfigFile = appsConfigDir + File.separator + appName + File.separator + "AppConfig.properties";
				fileReader = new FileReader(appConfigFile);
				Properties properties = new Properties();
				properties.load(fileReader);
				fileReader.close();
				
				appConfig = new AppConfig(currAppName, properties, appsConfigDir);
				appConfigs.put(appName, appConfig);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading AppConfig.properties file for application name '" + currAppName
					+ "'. Going to exit...", ex);
			System.exit(1);
		}
	}

	public AppConfig getAppConfig(String appName) {
		AppConfig appConfig = appConfigs.get(appName);
		Assert.assertNotNull(appConfig, "Please configure application config for application '" + appName + "'.");
		return appConfig;
	}

	public String getCucumberTestcasesDir() {
		return cucumberTestcasesDir;
	}

	public UserProfile getUserProfile(String appName, String profileName) {
		AppConfig appConfig = getAppConfig(appName);
		return appConfig.getUserProfile(profileName);
	}

	public DatabaseProfile getDatabaseProfile(String appName, String profileName) {
		AppConfig appConfig = getAppConfig(appName);
		return appConfig.getDatabaseProfile(profileName);
	}

	public OrmDatabaseQueryHandler getDatabaseQueryHandler(String appName, String profileName) {
		return getDatabaseProfile(appName, profileName).getQueryHandler();
	}

	public String getAdditionalPropertyValue(String propName) {
		Assert.assertTrue(additionalProps.containsKey(propName),
				"Please specify the additional property '" + propName + "' in TestConfig.properties file.");
		return additionalProps.get(propName);
	}

	public String getAdditionalPropertyValue(String appName, String propName) {
		return getAppConfig(appName).getAdditionalPropertyValue(propName);
	}

	public String getAppScreenCaptureDirectory() {
		return appScreenCaptureDir;
	}

	public String getUserProfileAdditionalPropertyValue(String appName, String profileName, String propName) {
		return getUserProfile(appName, profileName).getAdditionalPropertyValue(propName);
	}

	private void initSikuliSettings() {
		String sikuliSettingsFile = sikuliConfigDir + File.separator + "SikuliSettings.properties";
		try (FileReader fileReader = new FileReader(sikuliSettingsFile)) {
			Properties properties = new Properties();
			properties.load(fileReader);

			sikuliSettings = new SikuliSettings(sikuliConfigDir, properties);
		} catch (Exception ex) {
			Assert.fail("Failed to read property file - " + sikuliSettingsFile + ". Going to exit...", ex);
			System.exit(1);
		}
	}

	public Map<String, AppDriverConfig> getSeleniumDriverConfings() {
		return seleniumDriverConfigs;
	}

	public AppDriverConfig getSeleniumDriverConfig(WebBrowserType browserType) {
		return seleniumDriverConfigs.get(browserType.name());
	}

	public SikuliSettings getSikuliSettings() {
		return sikuliSettings;
	}

	public String getSikuliResourcesDir() {
		return sikuliResourcesDir;
	}

	public PlatformType getHostPlatformType() {
		return hostPlatformType;
	}

}
