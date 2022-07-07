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
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.sikuli.script.Screen;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.YamlDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class TestConfigManager {
	private static TestConfigManager instance;
	private static final String TEST_CONFIG_FILE_PATH = Locations.getConfigDirPath() + File.separator
			+ "TestConfig.yaml";

	private String appsConfigDir;
	private List<String> appNames;
	private String cucumberTestcasesDir;
	private String appScreenCaptureDir;
	private String htmlReportsDir;
	private String sikuliConfigDir;
	private String sikuliResourcesDir;
	private PlatformType hostPlatformType;
	private boolean parallelMode;
	private int parallelThreads;
	private boolean preferDriverScreenshots;
	private boolean embedScreenshotsInTestReport;

	// Key: App-Name, Value AppConfig
	private Map<String, AppConfig> appConfigs;

	private Map<String, Object> additionalProps;

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
		additionalProps = new LinkedHashMap<String, Object>();

		try {
			YamlDocumentReader reader = new YamlDocumentReader(new File(TEST_CONFIG_FILE_PATH));

			initTestConfig(reader.getDocumentContext());
			initAppConfigs();
		} catch (Exception ex) {
			Assert.fail("Failed to read property file - " + TEST_CONFIG_FILE_PATH + ". Going to exit...", ex);
			System.exit(1);
		}
	}

	private void initTestConfig(DocumentContext yamlDoc) {
		try {
			// Apply config file properties to TestConfiguration
			appsConfigDir = Locations.getProjectRootDir() + File.separator + "test-config" + File.separator
					+ "apps-config";

			String appNamesAsStr = System.getProperty("appNames");
			if (appNamesAsStr == null || "".equals(appNamesAsStr.trim())) {
				appNamesAsStr = JsonYamlUtil.readNoException("$.appNames", String.class, yamlDoc);
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

			try {
				parallelThreads = Integer.parseInt(System.getProperty("parallel.threads", "1"));
			} catch (Exception e) {
				parallelThreads = 1;
			}
			if (parallelThreads > 1) {
				parallelMode = true;
			} else {
				parallelMode = false;
			}
			
			Boolean boolValue = JsonYamlUtil.readNoException("$.preferDriverScreenshots", Boolean.class, yamlDoc);
			if (boolValue == null || boolValue.booleanValue() == true) {
				preferDriverScreenshots = true;
			} else {
				preferDriverScreenshots = false;
			}
			
			boolValue = JsonYamlUtil.readNoException("$.embedScreenshotsInTestReport", Boolean.class, yamlDoc);
			if (boolValue == null || boolValue.booleanValue() == true) {
				embedScreenshotsInTestReport = true;
			} else {
				embedScreenshotsInTestReport = false;
			}

			cucumberTestcasesDir = Locations.getProjectRootDir() + File.separator + "cucumber-testcases";

			appScreenCaptureDir = Locations.getProjectRootDir() + File.separator + "test-results" + File.separator
					+ "screen-captures";
			
			htmlReportsDir = Locations.getProjectRootDir() + File.separator + "test-results" + File.separator
					+ "cucumber-html-reports";

			sikuliConfigDir = Locations.getProjectRootDir() + File.separator + "test-config" + File.separator
					+ "sikuli-config";

			sikuliResourcesDir = Locations.getProjectRootDir() + File.separator + "sikuli-resources";

			additionalProps = JsonYamlUtil.readNoException("$.additionalProps", new TypeRef<Map<String, Object>>() {
			}, yamlDoc);

		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading configured testcase config class. Going to exit...", ex);
			System.exit(1);
		}
	}

	private void initAppConfigs() {
		String appConfigFile;
		String currAppName = null;

		try {
			appConfigs.put(DefaultInfo.DEFAULT_APP_NAME, createDefaultAppConfig(appsConfigDir));
			AppConfig appConfig;
			for (String appName : appNames) {
				currAppName = appName;
				appConfigFile = appsConfigDir + File.separator + appName + File.separator + "AppConfig.yaml";
				YamlDocumentReader reader = new YamlDocumentReader(new File(appConfigFile));

				appConfig = new AppConfig(currAppName, reader.getDocumentContext(), appsConfigDir);
				appConfigs.put(appName, appConfig);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail(
					"Error in loading AppConfig.yaml file for application name '" + currAppName + "'. Going to exit...",
					ex);
			System.exit(1);
		}
	}

	private AppConfig createDefaultAppConfig(String appsConfigDir) {
		String defaultConfig = "applicationName: " + DefaultInfo.DEFAULT_APP_NAME + "\n" + "applicationType: "
				+ ApplicationType.not_applicable.getType() + "\n" + "testPlatformType: "
				+ PlatformType.unknown.getType() + "\n" + "appWebBrowser: " + WebBrowserType.notApplicable.getType()
				+ "\n" + "enableBrowserExtensions: false\n" + "browserWindowSize: " + Screen.getPrimaryScreen().w
				+ " x " + Screen.getPrimaryScreen().h + "\n";

		YamlDocumentReader reader = new YamlDocumentReader(defaultConfig);
		AppConfig appConfig = new AppConfig(DefaultInfo.DEFAULT_APP_NAME, reader.getDocumentContext(), appsConfigDir);
		return appConfig;
	}

	public boolean isParallelMode() {
		return parallelMode;
	}

	public boolean preferDriverScreenshots() {
		return preferDriverScreenshots;
	}

	public boolean embedScreenshotsInTestReport() {
		return embedScreenshotsInTestReport;
	}

	public AppConfig getAppConfig(String appName) {
		AppConfig appConfig = appConfigs.get(appName);
		Assert.assertNotNull(appConfig, "Please configure application config for application '" + appName + "'.");
		return appConfig;
	}
	
	public Collection<AppConfig> getAppConfigs() {
		return appConfigs.values();
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

	public <T> T getAdditionalPropertyValue(String propName, Class<T> clazz) {
		Assert.assertTrue(additionalProps.containsKey(propName),
				"Please specify the additional property '" + propName + "' in TestConfig.yaml file.");
		return clazz.cast(additionalProps.get(propName));
	}

	public <T> T getAdditionalPropertyValue(String appName, String propName, Class<T> clazz) {
		return getAppConfig(appName).getAdditionalPropertyValue(propName, clazz);
	}

	public String getAppScreenCaptureDirectory() {
		return appScreenCaptureDir;
	}

	public String getHtmlReportsDir() {
		return htmlReportsDir;
	}

	public String getUserProfileAdditionalPropertyValue(String appName, String profileName, String propName) {
		return getUserProfile(appName, profileName).getAdditionalPropertyValue(propName);
	}

	private void initSikuliSettings() {
		String sikuliSettingsFile = sikuliConfigDir + File.separator + "SikuliSettings.yaml";
		try {
			YamlDocumentReader reader = new YamlDocumentReader(new File(sikuliSettingsFile));

			sikuliSettings = new SikuliSettings(sikuliConfigDir, reader.getDocumentContext());
		} catch (Exception ex) {
			Assert.fail("Failed to read property file - " + sikuliSettingsFile + ". Going to exit...", ex);
			System.exit(1);
		}
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
