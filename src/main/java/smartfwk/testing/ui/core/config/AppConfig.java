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
package smartfwk.testing.ui.core.config;

import java.awt.Dimension;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;

import smartfwk.testing.ui.core.config.webbrowser.RemoteWebDriverProvider;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowserType;
import smartfwk.testing.ui.core.utils.ScreenCaptureUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class AppConfig {
	private String appName;
	private String appConfigDir;
	private String appLaunchUrl;
	private String appLoginPageValidatorClass;
	private String appLoginSuccessPageValidatorClass;
	private String remoteWebDriverProviderClass;
	private RemoteWebDriverProvider remoteWebDriverProvider;
	private WebBrowserType appWebBrowser;
	private boolean enableWebBrowserExtension;
	private Dimension browserWindowSize;
	private String userProfileConfigDir;
	// Key: user-profile-name, Value: UserProfile
	private Map<String, UserProfile> userProfiles;
	private String dbProfileConfigDir;
	// Key: db-profile-name, Value: DatabaseProfile
	private Map<String, DatabaseProfile> dbProfiles;
	private ProxyConfiguration proxyConfig;
	private Map<String, String> additionalProps;

	public AppConfig(String appName, Properties properties, String appConfigDir) {
		this.appName = appName;
		this.appConfigDir = appConfigDir;
		userProfiles = new LinkedHashMap<String, UserProfile>();
		dbProfiles = new LinkedHashMap<String, DatabaseProfile>();
		additionalProps = new LinkedHashMap<String, String>();

		initAppConfig(appName, properties);
	}

	private void initAppConfig(String appName, Properties properties) {
		String propValue = properties.getProperty("APPLICATION_NAME");
		if (propValue == null || !appName.equals(propValue.trim())) {
			Assert.fail("FATAL: Application name '" + propValue
					+ "' specified in file is not same as configured in TestConfig.properties file '" + appName
					+ "'. Exiting ...");
			System.exit(1);
		}

		appLaunchUrl = properties.getProperty("APP_LAUNCH_URL");
		if (appLaunchUrl == null || "".equals(appLaunchUrl.trim())) {
			Assert.fail("FATAL: Please specify APP_LAUNCH_URL in AppConfig.properties. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			appLaunchUrl = appLaunchUrl.trim();
		}

		appLoginPageValidatorClass = properties.getProperty("APP_LOGIN_PAGE_VALIDATOR_CLASS");
		if (appLoginPageValidatorClass == null || "".equals(appLoginPageValidatorClass.trim())) {
			Assert.fail("FATAL: Please specify APP_LOGIN_PAGE_VALIDATOR_CLASS in AppConfig.properties. AppName: "
					+ appName + ". Exiting ...");
			System.exit(1);
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
			Assert.fail(
					"FATAL: Please specify APP_LOGIN_SUCCESS_PAGE_VALIDATOR_CLASS in AppConfig.properties. AppName: "
							+ appName + ". Exiting ...");
			System.exit(1);
		} else {
			appLoginSuccessPageValidatorClass = appLoginSuccessPageValidatorClass.trim();
			/*
			 * Class cls = Class.forName(propValue);
			 * testConfigProfile.setLoginPageValidator((LoginPageValidator) cls
			 * .newInstance());
			 */
		}

		propValue = properties.getProperty("APP_WEB_BROWSER");
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify APP_WEB_BROWSER in AppConfig.properties. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			appWebBrowser = WebBrowserType.valueOf(propValue.trim());
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

		userProfileConfigDir = properties.getProperty("USER_PROFILE_CONFIG_DIR");
		if (userProfileConfigDir == null || "".equals(userProfileConfigDir.trim())) {
			Assert.fail("FATAL: Please specify USER_PROFILE_CONFIG_DIR in AppConfig.properties. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			userProfileConfigDir = appConfigDir + File.separator + appName + File.separator + userProfileConfigDir.trim();
		}

		propValue = properties.getProperty("USER_PROFILE_NAMES");
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify USER_PROFILE_NAMES in AppConfig.properties. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
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
			if (userProfiles.size() == 0) {
				Assert.fail(
						"FATAL: Please specify atleast one user profile (USER_PROFILE_NAMES) in AppConfig.properties. AppName: "
								+ appName + ". Exiting ...");
				System.exit(1);
			}

			initUserProfiles();
		}

		dbProfileConfigDir = properties.getProperty("DB_PROFILE_CONFIG_DIR");
		if (dbProfileConfigDir == null || "".equals(dbProfileConfigDir.trim())) {
			// Assert.fail("FATAL: Please specify DB_PROFILE_CONFIG_DIR in
			// AppConfig.properties. AppName: " + appName
			// + ". Exiting ...");
			// System.exit(1);
		} else {
			dbProfileConfigDir = appConfigDir + File.separator + appName + File.separator + dbProfileConfigDir.trim();
		}

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

		proxyConfig = new ProxyConfiguration(appName, properties);

		String keyStr;
		for (Object key : properties.keySet()) {
			keyStr = String.valueOf(key);
			if (keyStr.startsWith("_")) {
				additionalProps.put(keyStr, properties.getProperty(keyStr));
			}
		}

		remoteWebDriverProviderClass = properties.getProperty("REMOTE_WEB_DRIVER_PROVIDER_CLASS");
		if (remoteWebDriverProviderClass == null || "".equals(remoteWebDriverProviderClass.trim())) {
			remoteWebDriverProviderClass = null;
		} else {
			remoteWebDriverProviderClass = remoteWebDriverProviderClass.trim();
			try {
				remoteWebDriverProvider = (RemoteWebDriverProvider) Class.forName(remoteWebDriverProviderClass).newInstance();
				remoteWebDriverProvider.setAppConfig(this);
			} catch (Exception ex) {
				Assert.fail("Failed to instantiate remote web driver class '" + remoteWebDriverProviderClass
						+ "' defined in application '" + appName + "'.", ex);
			}
		}
	}

	private void initUserProfiles() {
		String configFile;
		FileReader fileReader;
		String currProfileName = null;
		;
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
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading user profile '" + currProfileName + ".properties' for application name '"
					+ appName + "'. Going to exit...", ex);
			System.exit(1);
		}
	}

	private void initDatabaseProfiles() {
		String configFile;
		String currProfileName = null;
		;
		try {
			DatabaseProfile dbProfile;
			for (String profileName : dbProfiles.keySet()) {
				currProfileName = profileName;
				configFile = dbProfileConfigDir + File.separator + currProfileName + ".xml";

				dbProfile = new DatabaseProfile(appName, currProfileName, configFile);
				dbProfiles.put(currProfileName, dbProfile);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading database profile '" + currProfileName + ".properties' for application name '"
					+ appName + "'. Going to exit...", ex);
			System.exit(1);
		}
	}

	public String getName() {
		return appName;
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
		Assert.assertNotNull(profile,
				"Please configure user profile '" + profileName + "' for application '" + appName + "'.");
		return profile;
	}

	public DatabaseProfile getDatabaseProfile(String profileName) {
		DatabaseProfile profile = dbProfiles.get(profileName);
		Assert.assertNotNull(profile,
				"Please configure database profile '" + profileName + "' for application '" + appName + "'.");
		return profile;
	}

	public String getAdditionalPropertyValue(String propName) {
		Assert.assertTrue(additionalProps.containsKey(propName), "Please specify the additional property '" + propName
				+ "' in application '" + appName + "' AppConfig.properties file.");
		return additionalProps.get(propName);
	}
}
