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
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.YamlDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.appdriver.RemoteWebDriverProvider;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.ScreenCaptureUtil;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

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
	private Map<String, Object> additionalProps;
	private ApiConfig apiConfig;
	private AppDriverConfig appDriverConfig;
	private EnvironmentConfig environmentConfig;

	public AppConfig(String appName, DocumentContext yamlDoc, String appsConfigDir) {
		this.appName = appName;
		this.appsConfigDir = appsConfigDir;

		System.out.println("Going to configure '" + this.appName + "' application.");

		userProfiles = new LinkedHashMap<String, UserProfile>();
		dbProfiles = new LinkedHashMap<String, DatabaseProfile>();
		additionalProps = new LinkedHashMap<String, Object>();

		String activeEnvironmentName = Locations.getAppActiveEnvironmentName(appName);
		environmentConfig = new EnvironmentConfig(appName, activeEnvironmentName);

		System.out.println("Going to set active environment '" + environmentConfig.getName() + "' for application '"
				+ this.appName + "'.");

		System.out.println("Environment '" + environmentConfig.getName() 
			+ "' properties: " + (environmentConfig.getDocumentContext() == null ? "" :  environmentConfig.getDocumentContext().read("$")));

		initAppConfig(appName, yamlDoc, environmentConfig.getDocumentContext());

		System.out.println("Application '" + this.appName + "' configured successfully using active environment: "
				+ environmentConfig.getName() + ".");
	}

	public String getAppName() {
		return appName;
	}

	public String getAppsConfigDir() {
		return appsConfigDir;
	}
	
	private void initAppConfig(String appName, DocumentContext appConfigDocContext, DocumentContext envAppConfigDocContext) {
		String propValue = JsonYamlUtil.readNoException("$.applicationName", String.class, appConfigDocContext, envAppConfigDocContext);
		if (propValue == null || !appName.equals(propValue.trim())) {
			Assert.fail("FATAL: Application name '" + propValue
					+ "' specified in file is not same as configured in TestConfig.yaml file '" + appName
					+ "'. Exiting ...");
			System.exit(1);
		}

		propValue = JsonYamlUtil.readNoException("$.applicationType", String.class, appConfigDocContext, envAppConfigDocContext);
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify 'applicationType' in AppConfig.yaml. AppName: " + appName
					+ ", environmentName: . Exiting ...");
			System.exit(1);
		} else {
			this.appType = ApplicationType.valueOf2(propValue);
		}

		propValue = JsonYamlUtil.readNoException("$.testPlatformType", String.class, appConfigDocContext, envAppConfigDocContext);
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify 'testPlatformType' in AppConfig.yaml. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			this.testPlatformType = PlatformType.valueOf2(propValue);
		}

		appLaunchUrl = JsonYamlUtil.readNoException("$.appLaunchURL", String.class, appConfigDocContext, envAppConfigDocContext);
		if (appLaunchUrl == null || "".equals(appLaunchUrl.trim())) {
			if(!DefaultInfo.DEFAULT_APP_NAME.equals(this.appName)) {
				Assert.fail("FATAL: Please specify 'appLaunchURL' in AppConfig.yaml. AppName: " + appName
						+ ". Exiting ...");
				System.exit(1);
			} else {
				appLaunchUrl = "";
			}
		} else {
			appLaunchUrl = appLaunchUrl.trim();
		}

		propValue = JsonYamlUtil.readNoException("$.appWebBrowser", String.class, appConfigDocContext, envAppConfigDocContext);
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify 'appWebBrowser' in AppConfig.yaml. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			appWebBrowser = WebBrowserType.valueOf2(propValue.trim());
		}

		Map<String, String> propValuesAsMap = JsonYamlUtil.readNoException("$.proxySettings", (new TypeRef<Map<String, String>>() {}), appConfigDocContext, envAppConfigDocContext);
		proxyConfig = new ProxyConfiguration(appName, propValuesAsMap);
		remoteWebDriverProviderClass = JsonYamlUtil.readNoException("$.remoteWebDriverProviderClass", String.class, appConfigDocContext, envAppConfigDocContext);

		appLoginPageValidatorClass = JsonYamlUtil.readNoException("$.appLoginPageValidatorClass", String.class, appConfigDocContext, envAppConfigDocContext);
		if (appLoginPageValidatorClass == null || "".equals(appLoginPageValidatorClass.trim())) {
			appLoginPageValidatorClass = DefaultInfo.DEFAULT_APP_LOGIN_PAGE_VALIDATOR;
		} else {
			appLoginPageValidatorClass = appLoginPageValidatorClass.trim();
		}

		appLoginSuccessPageValidatorClass = JsonYamlUtil.readNoException("$.appLoginSuccessPageValidatorClass", String.class, appConfigDocContext, envAppConfigDocContext);
		if (appLoginSuccessPageValidatorClass == null || "".equals(appLoginSuccessPageValidatorClass.trim())) {
			appLoginSuccessPageValidatorClass = DefaultInfo.DEFAULT_APP_LOGIN_SUCCESS_PAGE_VALIDATOR;
		} else {
			appLoginSuccessPageValidatorClass = appLoginSuccessPageValidatorClass.trim();
		}

		propValue = JsonYamlUtil.readNoException("$.enableBrowserExtensions", String.class, appConfigDocContext, envAppConfigDocContext);
		if (propValue == null || "".equals(propValue.trim())) {
			Assert.fail("FATAL: Please specify 'enableBrowserExtensions' in AppConfig.yaml. AppName: " + appName
					+ ". Exiting ...");
			System.exit(1);
		} else {
			enableWebBrowserExtension = Boolean.valueOf(propValue.trim());
		}

		propValue = JsonYamlUtil.readNoException("$.browserWindowSize", String.class, appConfigDocContext, envAppConfigDocContext);
		if (propValue == null || "".equals(propValue.trim()) || !propValue.contains("x")) {
			Assert.fail("FATAL: Please specify correct 'browserWindowSize' in AppConfig.yaml. AppName: " + appName
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
							"FATAL: please specify the 'browserWindowSize' (browserWidth) <= screenWidth in AppConfig.yaml. AppName: "
									+ appName + ". Exiting ...");
					System.exit(1);
				} else if (browserHeight > screenSize.getHeight()) {
					Assert.fail(
							"FATAL: please specify the 'browserWindowSize' (browserHeight) <= screenHeight in AppConfig.yaml. AppName: "
									+ appName + ". Exiting ...");
					System.exit(1);
				}
				browserWindowSize = new Dimension(browserWidth, browserHeight);
			} catch (Exception ex) {
				Assert.fail("FATAL: Please specify correct 'browserWindowSize' in AppConfig.yaml. AppName: "
						+ appName + ". Exiting ...");
				System.exit(1);
			}
		}

		userProfileConfigDir = appsConfigDir + File.separator + appName + File.separator + userProfileConfigDir.trim();

		List<String> propValues = JsonYamlUtil.readNoException("$.userProfileNames", (new TypeRef<List<String>>() {}), appConfigDocContext, envAppConfigDocContext);
		if (propValues == null || propValues.isEmpty()) {
			userProfiles.put(DefaultInfo.DEFAULT_USER_PROFILE_NAME, null);
		} else {
			String keyStr;
			for (String item : propValues) {
				keyStr = item.trim();
				if ("".equals(keyStr)) {
					continue;
				}
				userProfiles.put(keyStr, null);
			}
			
			initUserProfiles();
		}

		dbProfileConfigDir = appsConfigDir + File.separator + appName + File.separator + dbProfileConfigDir.trim();

		propValues = JsonYamlUtil.readNoException("$.dbProfileNames", (new TypeRef<List<String>>() {}), appConfigDocContext, envAppConfigDocContext);
		if (propValues == null || propValues.isEmpty()) {
			// Do nothing
		} else {
			String keyStr;
			for (String item : propValues) {
				keyStr = item.trim();
				if ("".equals(keyStr)) {
					continue;
				}
				
				dbProfiles.put(keyStr, null);
			}

			initDatabaseProfiles();
		}

		additionalProps = JsonYamlUtil.readNoException("$.additionalProps", new TypeRef<HashMap<String, Object>>() {}, appConfigDocContext, envAppConfigDocContext);
		
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

		propValue = JsonYamlUtil.readNoException("$.apiConfigFileName", String.class, appConfigDocContext, envAppConfigDocContext);
		if (propValue == null || "".equals(propValue.trim())) {
			if(!DefaultInfo.DEFAULT_APP_NAME.equals(this.appName)) {
				Assert.fail("FATAL: Please specify 'apiConfigFileName' in AppConfig.yaml. AppName: " + appName
						+ ". Exiting ...");
				System.exit(1);
			}
		} else {
			String apiConfigFile = appsConfigDir + File.separator + this.appName + File.separator + "api-configs"
					+ File.separator + propValue;
			try {
				YamlDocumentReader yamlReader = new YamlDocumentReader(new File(apiConfigFile));
				this.apiConfig = new ApiConfig(this.appName, apiConfigFile, yamlReader.getDocumentContext());
			} catch (Exception ex) {
				Assert.fail("Failed to load '" + apiConfigFile + "' file for application '" + this.appName + "'.", ex);
			}
		}

		propValue = JsonYamlUtil.readNoException("$.appDriverConfigFileName", String.class, appConfigDocContext, envAppConfigDocContext);
		initAppDriverConfig(propValue);
	}

	public ApiConfig getApiConfig() {
		return this.apiConfig;
	}

	public AppDriverConfig getAppDriverConfig() {
		return appDriverConfig;
	}

	private void initUserProfiles() {
		String currProfileName = null;
		try {
			UserProfile userProfile;
			
			for (String profileName : userProfiles.keySet()) {
				currProfileName = profileName + "-" + environmentConfig.getName();
				if(!isUserProfileExist(currProfileName)) {
					currProfileName = profileName;
					if(!isUserProfileExist(currProfileName)) {
						throw new RuntimeException("'" + currProfileName + "' user profile does not exist for app '" + appName + "' and environment '" + environmentConfig.getName() + "'.");
					}
				}
				
				userProfile = prepareUserProfile(currProfileName);
				userProfile.setEnvFileName(currProfileName + ".yaml");
				userProfile.validateInfo();
				if(!profileName.equals(userProfile.getProfileName())) {
					throw new RuntimeException("'" + profileName + "' user profile does not exist for app '" + appName + "' and environment '" + environmentConfig.getName() + "'.");
				}
				
				userProfiles.put(userProfile.getProfileName(), userProfile);
			}
			userProfiles.put(DefaultInfo.DEFAULT_USER_PROFILE_NAME, null);
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading user profile '" + currProfileName + ".yaml' for application name '"
					+ appName + "'. Going to exit...", ex);
			System.exit(1);
		}
	}

	private void initDatabaseProfiles() {
		String currProfileName = null;

		try {
			DatabaseProfile dbProfile;
			for (String profileName : dbProfiles.keySet()) {
				currProfileName = profileName + "-" + environmentConfig.getName();
				
				if(!isDatabaseProfileExist(currProfileName)) {
					currProfileName = profileName;
					if(!isDatabaseProfileExist(currProfileName)) {
						throw new RuntimeException("'" + currProfileName + "' database profile does not exist for app '" + appName + "' and environment '" + environmentConfig.getName() + "'.");
					}
				}
				
				dbProfile = prepareDatabaseProfile(currProfileName);
				dbProfile.setEnvFileName(currProfileName + ".yaml");
				dbProfile.validateInfo();
				if(!profileName.equals(dbProfile.getProfileName())) {
					throw new RuntimeException("'" + profileName + "' database profile does not exist for app '" + appName + "' and environment '" + environmentConfig.getName() + "'.");
				}
				dbProfiles.put(dbProfile.getProfileName(), dbProfile);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			Assert.fail("Error in loading database profile '" + currProfileName + ".yaml' for application name '"
					+ appName + "'. Going to exit...", ex);
			System.exit(1);
		}
	}
	
	private boolean isUserProfileExist(String profileName) {
		String path = userProfileConfigDir + "/" + profileName + ".yaml";
		return new File(path).exists();
	}
	
	private UserProfile prepareUserProfile(String profileName) {
		String path = userProfileConfigDir + "/" + profileName + ".yaml";
		YamlDocumentReader reader = new YamlDocumentReader(new File(path));
		UserProfile profile = reader.readValueAsObject("$", UserProfile.class);
		profile.setAppName(appName);
		return profile;
	}
	
	private boolean isDatabaseProfileExist(String profileName) {
		String path = dbProfileConfigDir + "/" + profileName + ".yaml";
		return new File(path).exists();
	}
	
	private DatabaseProfile prepareDatabaseProfile(String profileName) {
		String path = dbProfileConfigDir + "/" + profileName + ".yaml";
		YamlDocumentReader reader = new YamlDocumentReader(new File(path));
		DatabaseProfile profile = reader.readValueAsObject("$", DatabaseProfile.class);
		profile.setAppName(appName);
		return profile;
	}

	private void initAppDriverConfig(String appDriverConfigFileName) {
		if (appDriverConfigFileName == null || "".equals(appDriverConfigFileName.trim())) {
			if(!DefaultInfo.DEFAULT_APP_NAME.equals(this.appName)) {
				Assert.fail("FATAL: Please specify 'appDriverConfigFileName' in AppConfig.yaml. AppName: " + appName
						+ ". Exiting ...");
				System.exit(1);
			}
		} else {
			String driverCfgFile = appsConfigDir + File.separator + appName + File.separator + "driver-configs"
					+ File.separator + appDriverConfigFileName;
			Assert.assertTrue(new File(driverCfgFile).exists(),
					"Missing '" + driverCfgFile + "' file for '" + appName + "' application.");

			try {
				YamlDocumentReader yamlReader = new YamlDocumentReader(new File(driverCfgFile));
				appDriverConfig = new AppDriverConfig(this, yamlReader.getDocumentContext());
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
		return profile;
	}

	public DatabaseProfile getDatabaseProfile(String profileName) {
		DatabaseProfile profile = dbProfiles.get(profileName);
		return profile;
	}

	public <T> T getAdditionalPropertyValue(String propName, Class<T> clazz) {
		Assert.assertTrue(additionalProps.containsKey(propName), "Please specify the additional property '" + propName
				+ "' in application '" + appName + "' AppConfig.yaml file.");
		return clazz.cast(additionalProps.get(propName));
	}

	public EnvironmentConfig getEnvironmentConfig() {
		return environmentConfig;
	}
	
	public Collection<UserProfile> getUserProfiles() {
		return userProfiles.values();
	}
	
	public Collection<DatabaseProfile> getDatabaseProfiles() {
		return dbProfiles.values();
	}
	
	
}
