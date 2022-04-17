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

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.RemoteWebDriverProvider;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;
import org.uitnet.testing.smartfwk.ui.core.utils.ScreenCaptureUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class AppConfig {
	private String appName;
	private ApplicationType appType;
	private PlatformType testPlatformType;
	private String appsConfigDir;
	private String appLaunchUrl;
	private String appLoginPageValidatorClass;
	private String appLoginSuccessPageValidatorClass;
	private String remoteWebDriverProviderClass;
	private RemoteWebDriverProvider remoteWebDriverProvider;
	private WebBrowserType appWebBrowser;
	private boolean enableWebBrowserExtension;
	private Dimension browserWindowSize;
	private String userProfileConfigDir = "user-profiles";
	// Key: user-profile-name, Value: UserProfile
	private Map<String, UserProfile> userProfiles;
	private String dbProfileConfigDir = "database-profiles";
	// Key: db-profile-name, Value: DatabaseProfile
	private Map<String, DatabaseProfile> dbProfiles;
	private ProxyConfiguration proxyConfig;
	private Map<String, String> additionalProps;
	private ApiConfig apiConfig;
	private AppDriverConfig appDriverConfig;
	private EnvironmentConfig environmentConfig;

	public AppConfig(String appName, Properties properties, String appsConfigDir) {
		this.appName = appName;
		this.appsConfigDir = appsConfigDir;

		System.out.println("Going to configure '" + this.appName + "' application.");

		userProfiles = new LinkedHashMap<String, UserProfile>();
		dbProfiles = new LinkedHashMap<String, DatabaseProfile>();
		additionalProps = new LinkedHashMap<String, String>();

		String activeEnvironmentName = Locations.getAppActiveEnvironmentName(appName);
		environmentConfig = new EnvironmentConfig(appName, activeEnvironmentName);

		System.out.println("Going to set active environment '" + environmentConfig.getName() + "' for application '"
				+ this.appName + "'.");

		properties.putAll(environmentConfig.getProperties());

		System.out.println("Environment '" + environmentConfig.getName() + "' properties: " + properties);

		initAppConfig(appName, properties);

		System.out.println("Application '" + this.appName + "' configured successfully using active environment: "
				+ environmentConfig.getName() + ".");
	}

	public String getAppName() {
		return appName;
	}

	public String getAppsConfigDir() {
		return appsConfigDir;
	}

	private void initAppConfig(String appName, Properties properties) {
		String propValue = properties.getProperty("APPLICATION_NAME");
		if (propValue == null || !appName.equals(propValue.trim())) {
			Assert.fail("FATAL: Application name '" + propValue
					+ "' specified in file is not same as configured in TestConfig.properties file '" + appName
					+ "'. Exiting ...");
			System.exit(1);
		}

		propValue = properties.getProperty("APPLICATION_TYPE");
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify APPLICATION_TYPE in AppConfig.properties. AppName: " + appName
					+ ", environmentName: . Exiting ...");
			System.exit(1);
		} else {
			this.appType = ApplicationType.valueOf2(propValue);
		}

		propValue = properties.getProperty("TEST_PLATFORM_TYPE");
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify TEST_PLATFORM_TYPE in AppConfig.properties. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			this.testPlatformType = PlatformType.valueOf2(propValue);
		}

		appLaunchUrl = properties.getProperty("APP_LAUNCH_URL");
		if (appLaunchUrl == null || "".equals(appLaunchUrl.trim())) {
			if(!DefaultInfo.DEFAULT_APP_NAME.equals(this.appName)) {
				Assert.fail("FATAL: Please specify APP_LAUNCH_URL in AppConfig.properties. AppName: " + appName
						+ ". Exiting ...");
				System.exit(1);
			} else {
				appLaunchUrl = "";
			}
		} else {
			appLaunchUrl = appLaunchUrl.trim();
		}

		propValue = properties.getProperty("APP_WEB_BROWSER");
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify APP_WEB_BROWSER in AppConfig.properties. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			appWebBrowser = WebBrowserType.valueOf2(propValue.trim());
		}

		proxyConfig = new ProxyConfiguration(appName, properties);
		remoteWebDriverProviderClass = properties.getProperty("REMOTE_WEB_DRIVER_PROVIDER_CLASS");

		appLoginPageValidatorClass = properties.getProperty("APP_LOGIN_PAGE_VALIDATOR_CLASS");
		if (appLoginPageValidatorClass == null || "".equals(appLoginPageValidatorClass.trim())) {
			appLoginPageValidatorClass = DefaultInfo.DEFAULT_APP_LOGIN_PAGE_VALIDATOR;
		} else {
			appLoginPageValidatorClass = appLoginPageValidatorClass.trim();
			/*
			 * Class cls = Class.forName(propValue);
			 * testConfigProfile.setLoginPageValidator((LoginPageValidator) cls
			 * .newInstance());
			 */
		}

		appLoginSuccessPageValidatorClass = properties.getProperty("APP_LOGIN_SUCCESS_PAGE_VALIDATOR_CLASS");
		if (appLoginSuccessPageValidatorClass == null || "".equals(appLoginSuccessPageValidatorClass.trim())) {
			appLoginSuccessPageValidatorClass = DefaultInfo.DEFAULT_APP_LOGIN_SUCCESS_PAGE_VALIDATOR;
		} else {
			appLoginSuccessPageValidatorClass = appLoginSuccessPageValidatorClass.trim();
			/*
			 * Class cls = Class.forName(propValue);
			 * testConfigProfile.setLoginPageValidator((LoginPageValidator) cls
			 * .newInstance());
			 */
		}

		propValue = properties.getProperty("ENABLE_BROWSER_EXTENSIONS");
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify ENABLE_BROWSER_EXTENSIONS in AppConfig.properties. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			enableWebBrowserExtension = Boolean.valueOf(propValue.trim());
		}

		propValue = properties.getProperty("BROWSER_WINDOW_SIZE");
		if (propValue == null || "".equals(propValue.trim()) || !propValue.contains("x")) {
			Assert.fail("FATAL: Please specify correct BROWSER_WINDOW_SIZE in AppConfig.properties. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			String[] wh = propValue.split("x");
			try {
				int browserWidth = Integer.parseInt(wh[0].trim());
				int browserHeight = Integer.parseInt(wh[1].trim());
				Dimension screenSize = ScreenCaptureUtil.getScreenSize();
				if (browserWidth > screenSize.getWidth()) {
					Assert.fail(
							"FATAL: please specify the BROWSER_WINDOW_SIZE(browserWidth) <= screenWidth in AppConfig.properties. AppName: "
									+ appName + ". Exiting ...");
					System.exit(1);
				} else if (browserHeight > screenSize.getHeight()) {
					Assert.fail(
							"FATAL: please specify the BROWSER_WINDOW_SIZE(browserHeight) <= screenHeight in AppConfig.properties. AppName: "
									+ appName + ". Exiting ...");
					System.exit(1);
				}
				browserWindowSize = new Dimension(browserWidth, browserHeight);
			} catch (Exception ex) {
				Assert.fail("FATAL: Please specify correct BROWSER_WINDOW_SIZE in AppConfig.properties. AppName: "
						+ appName + ". Exiting ...");
				System.exit(1);
			}
		}

		userProfileConfigDir = appsConfigDir + File.separator + appName + File.separator + userProfileConfigDir.trim();

		propValue = properties.getProperty("USER_PROFILE_NAMES");
		if (propValue == null || "".equals(propValue.trim())) {
			userProfiles.put(DefaultInfo.DEFAULT_USER_PROFILE_NAME, null);
		} else {
			String[] arr = propValue.split(",");
			String keyStr;
			for (String item : arr) {
				keyStr = item.trim();
				if ("".equals(keyStr)) {
					continue;
				}
				userProfiles.put(keyStr, null);
			}
			
			initUserProfiles();
		}

		dbProfileConfigDir = appsConfigDir + File.separator + appName + File.separator + dbProfileConfigDir.trim();

		propValue = properties.getProperty("DB_PROFILE_NAMES");
		if (propValue == null || "".equals(propValue.trim())) {
			// Assert.fail("FATAL: Please specify DB_PROFILE_NAMES in
			// AppConfig.properties. AppName: " + appName
			// + ". Exiting ...");
			// System.exit(1);
		} else {
			String[] arr = propValue.split(",");
			String keyStr;
			for (String item : arr) {
				keyStr = item.trim();
				if ("".equals(keyStr)) {
					continue;
				}
				
				dbProfiles.put(keyStr, null);
			}

			initDatabaseProfiles();
		}

		String keyStr;
		for (Object key : properties.keySet()) {
			keyStr = String.valueOf(key);
			if (keyStr.startsWith("_")) {
				additionalProps.put(keyStr, properties.getProperty(keyStr));
			}
		}

		if (remoteWebDriverProviderClass == null || "".equals(remoteWebDriverProviderClass.trim())) {
			remoteWebDriverProviderClass = null;
		} else {
			remoteWebDriverProviderClass = remoteWebDriverProviderClass.trim();
			try {
				remoteWebDriverProvider = (RemoteWebDriverProvider) Class.forName(remoteWebDriverProviderClass)
						.getDeclaredConstructor().newInstance();
				remoteWebDriverProvider.setAppConfig(this);
			} catch (Exception ex) {
				Assert.fail("Failed to instantiate remote web driver class '" + remoteWebDriverProviderClass
						+ "' defined in application '" + appName + "'.", ex);
			}
		}

		propValue = properties.getProperty("API_CONFIG_FILE_NAME");
		if (propValue == null || "".equals(propValue.trim())) {
			if(!DefaultInfo.DEFAULT_APP_NAME.equals(this.appName)) {
				Assert.fail("FATAL: Please specify API_CONFIG_FILE_NAME in AppConfig.properties. AppName: " + appName
						+ ". Exiting ...");
				System.exit(1);
			}
		} else {
			String apiConfigFile = appsConfigDir + File.separator + this.appName + File.separator + "api-configs"
					+ File.separator + propValue;
			Properties apiConfigProps = new Properties();
			try (FileReader fr = new FileReader(apiConfigFile)) {
				apiConfigProps.load(fr);
				this.apiConfig = new ApiConfig(apiConfigFile, apiConfigProps);
			} catch (Exception ex) {
				Assert.fail("Failed to load '" + apiConfigFile + "' file for application '" + this.appName + "'.", ex);
			}
		}

		initAppDriverConfig(properties);
	}

	public ApiConfig getApiConfig() {
		return this.apiConfig;
	}

	public AppDriverConfig getAppDriverConfig() {
		return appDriverConfig;
	}

	private void initUserProfiles() {
		String configFile;
		FileReader fileReader;
		String currProfileName = null;
		try {
			UserProfile userProfile;
			
			for (String profileName : userProfiles.keySet()) {
				currProfileName = profileName;
				configFile = userProfileConfigDir + File.separator + currProfileName + ".properties";
				fileReader = new FileReader(configFile);
				Properties properties = new Properties();
				properties.load(fileReader);
				fileReader.close();

				userProfile = new UserProfile(appName, currProfileName, properties);
				userProfiles.put(currProfileName, userProfile);
			}
			userProfiles.put(DefaultInfo.DEFAULT_USER_PROFILE_NAME, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading user profile '" + currProfileName + ".properties' for application name '"
					+ appName + "'. Going to exit...", ex);
			System.exit(1);
		}
	}

	private void initDatabaseProfiles() {
		String currProfileName = null;

		try {
			DatabaseProfile dbProfile;
			for (String profileName : dbProfiles.keySet()) {
				currProfileName = profileName;

				dbProfile = new DatabaseProfile(appName, currProfileName);
				dbProfiles.put(currProfileName, dbProfile);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading database profile '" + currProfileName + ".xml' for application name '"
					+ appName + "'. Going to exit...", ex);
			System.exit(1);
		}
	}

	private void initAppDriverConfig(Properties properties) {
		String propValue = properties.getProperty("APP_DRIVER_CONFIG_FILE_NAME");
		if (propValue == null || "".equals(propValue.trim())) {
			if(!DefaultInfo.DEFAULT_APP_NAME.equals(this.appName)) {
				Assert.fail("FATAL: Please specify APP_DRIVER_CONFIG_FILE_NAME in AppConfig.properties. AppName: " + appName
						+ ". Exiting ...");
				System.exit(1);
			}
		} else {
			String driverCfgFile = appsConfigDir + File.separator + appName + File.separator + "driver-configs"
					+ File.separator + propValue;
			Assert.assertTrue(new File(driverCfgFile).exists(),
					"Missing '" + driverCfgFile + "' file for '" + appName + "' application.");

			try (FileReader fileReader = new FileReader(driverCfgFile)) {
				Properties properties2 = new Properties();
				properties2.load(fileReader);

				appDriverConfig = new AppDriverConfig(this, properties2);
			} catch (Exception ex) {
				Assert.fail("Failed to read property file - " + driverCfgFile + ". Going to exit...", ex);
				System.exit(1);
			}
		}
	}

	public String getName() {
		return appName;
	}

	public ApplicationType getAppType() {
		return appType;
	}

	public PlatformType getTestPlatformType() {
		return testPlatformType;
	}

	public String getAppLaunchUrl() {
		return appLaunchUrl;
	}

	public String getAppLoginPageValidatorClass() {
		return appLoginPageValidatorClass;
	}

	public String getAppLoginSuccessPageValidatorClass() {
		return appLoginSuccessPageValidatorClass;
	}

	public String getRemoteWebDriverProviderClass() {
		return remoteWebDriverProviderClass;
	}

	public RemoteWebDriverProvider getRemoteWebDriverProvider() {
		return remoteWebDriverProvider;
	}

	public WebBrowserType getAppWebBrowser() {
		return appWebBrowser;
	}

	public boolean isEnableWebBrowserExtension() {
		return enableWebBrowserExtension;
	}

	public Dimension getBrowserWindowSize() {
		return browserWindowSize;
	}

	public String getUserProfileConfigDir() {
		return userProfileConfigDir;
	}

	public String getDbProfileConfigDir() {
		return dbProfileConfigDir;
	}

	public ProxyConfiguration getProxyConfig() {
		return proxyConfig;
	}

	public UserProfile getUserProfile(String profileName) {
		UserProfile profile = userProfiles.get(profileName);
//		Assert.assertNotNull(profile,
//				"Please configure user profile '" + profileName + "' for application '" + appName + "'.");
		return profile;
	}

	public DatabaseProfile getDatabaseProfile(String profileName) {
		DatabaseProfile profile = dbProfiles.get(profileName);
//		Assert.assertNotNull(profile,
//				"Please configure database profile '" + profileName + "' for application '" + appName + "'.");
		return profile;
	}

	public String getAdditionalPropertyValue(String propName) {
		Assert.assertTrue(additionalProps.containsKey(propName), "Please specify the additional property '" + propName
				+ "' in application '" + appName + "' AppConfig.properties file.");
		return additionalProps.get(propName);
	}

	public EnvironmentConfig getEnvironmentConfig() {
		return environmentConfig;
	}
}
