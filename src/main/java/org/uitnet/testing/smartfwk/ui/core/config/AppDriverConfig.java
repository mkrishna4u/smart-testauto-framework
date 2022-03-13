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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class AppDriverConfig {
	private WebBrowserType browserType = null;
	private String driverSystemPropertyName;
	private String driverBinaryFilePath;
	private String driverFileName;
	private String remoteDriverURL;
	private boolean headless = false;
	private PageLoadStrategy pageLoadStrategy = PageLoadStrategy.NORMAL;
	private UnexpectedAlertBehaviour unexpectedAlertBehaviour = UnexpectedAlertBehaviour.DISMISS;
	private Level logLevel = Level.SEVERE;

	private List<String> arguments;
	private Map<String, Object> driverCapabilities;
	private Map<String, Object> experimentalOptions;

	private List<File> browserExtensionFiles;
	private boolean deleteExtensionsCacheIfItExists = true;

	private boolean alwaysLoadNoFocusLib = false;

	private Map<String, Object> browserPreferences;

	private boolean acceptInsecureCertificates = false;
	private boolean acceptUntrustedCertificates = false;
	private boolean assumeUntrustedCertificateIssuer = false;
	
	private Map<String, String> webAttrMap;

	private String profilePath;

	private Integer scriptTimeoutInSecs = 60;
	private Integer pageLoadTimeoutInSecs = 60;
	private AppConfig appConfig;

	/**
	 * String appDriverPath = Locations.getProjectRootDir() +
	 * "/test-config/app-drivers";
	 * 
	 * @param seleniumConfigDir
	 * @param properties
	 */

	public AppDriverConfig(AppConfig appConfig, Properties properties) {
		this.appConfig = appConfig;
		arguments = new ArrayList<String>();
		driverCapabilities = new HashMap<String, Object>();
		experimentalOptions = new HashMap<String, Object>();
		browserExtensionFiles = new ArrayList<File>();
		browserPreferences = new HashMap<String, Object>();
		webAttrMap = new HashMap<String, String>();
		init(properties);
	}

	@SuppressWarnings("unchecked")
	private void init(Properties properties) {
		browserType = WebBrowserType.valueOf2(getProperty(properties, "BROWSER_TYPE"));
		driverSystemPropertyName = getProperty(properties, "DRIVER_SYSTEM_PROPERTY_NAME");

		driverBinaryFilePath = getProperty(properties, "DRIVER_BINARY_FILE_PATH");
		if (driverBinaryFilePath == null) {
			driverFileName = getProperty(properties, "DRIVER_FILENAME");
			driverBinaryFilePath = Locations.getProjectRootDir() + File.separator + "test-config" + File.separator
					+ "app-drivers" + File.separator + OSDetectorUtil.getHostPlatform().getType() + File.separator
					+ appConfig.getAppType().getType() + File.separator + browserType.getType() + File.separator
					+ driverFileName;
		}

		remoteDriverURL = getProperty(properties, "REMOTE_DRIVER_URL");

		headless = Boolean.parseBoolean(getProperty(properties, "HEADLESS"));
		pageLoadStrategy = PageLoadStrategy.fromString(getProperty(properties, "PAGE_LOAD_STRATEGY"));
		unexpectedAlertBehaviour = UnexpectedAlertBehaviour
				.fromString(getProperty(properties, "UNEXPECTED_ALERT_BEHAVIOUR"));
		if (getProperty(properties, "LOG_LEVEL") != null) {
			logLevel = Level.parse(getProperty(properties, "LOG_LEVEL"));
		}

		// initialize driver arguments
		String args = getProperty(properties, "DRIVER_ARGUMENTS");
		if (!StringUtil.isEmptyAfterTrim(args)) {
			String[] argArr = args.split(" ");
			for (String a : argArr) {
				if (!StringUtil.isEmptyAfterTrim(a)) {
					arguments.add(a.trim());
				}
			}
		}

		// Initialize multi value properties
		for (Object key : properties.keySet()) {
			String strKey = (String) key;
			String value = properties.getProperty(strKey);

			// initialize driver capabilities
			if (strKey.startsWith("DriverCapability.")) {
				String propName = getPropertyName(strKey, "DriverCapability");
				if(strKey.endsWith("}")) {
					Map<String, Object> keyValueMap = (Map<String, Object>) driverCapabilities.get(propName);
					if(keyValueMap == null) {
						keyValueMap = new HashMap<>();
						driverCapabilities.put(propName, keyValueMap);
					}
					String subProbName = getSubPropertyName(strKey);
					keyValueMap.put(subProbName, parseTypedValue(strKey, value));
				} else if(strKey.endsWith("]")) {
					List<Object> propValues = (List<Object>) driverCapabilities.get(propName);
					if(propValues == null) {
						propValues = new LinkedList<Object>();
						driverCapabilities.put(propName, propValues);
					}
					propValues.add(parseTypedValue(strKey, value));
				} else {
					driverCapabilities.put(strKey.substring("DriverCapability.".length()), parseTypedValue(strKey, value));
				}
			}
			
			// initialize web attribute map			
			if (strKey.startsWith("WebAttrMap.")) {
				webAttrMap.put(strKey.substring("WebAttrMap.".length()), value.trim());
			}

			// initialize driver experimental options
			if (strKey.startsWith("ExperimentalOption.")) {
				String propName = getPropertyName(strKey, "ExperimentalOption");
				if(strKey.endsWith("}")) {
					Map<String, Object> keyValueMap = (Map<String, Object>) experimentalOptions.get(propName);
					if(keyValueMap == null) {
						keyValueMap = new HashMap<>();
						experimentalOptions.put(propName, keyValueMap);
					}
					String subProbName = getSubPropertyName(strKey);
					keyValueMap.put(subProbName, parseTypedValue(strKey, value));
				} else if(strKey.endsWith("]")) {
					List<Object> propValues = (List<Object>) experimentalOptions.get(propName);
					if(propValues == null) {
						propValues = new LinkedList<Object>();
						experimentalOptions.put(propName, propValues);
					}
					propValues.add(parseTypedValue(strKey, value));
				} else {
					experimentalOptions.put(strKey.substring("ExperimentalOption.".length()),
						parseTypedValue(strKey, value));
				}
			}

			// load browser extensions
			if (strKey.startsWith("BrowserExtension.")) {
				browserExtensionFiles
						.add(new File(driverBinaryFilePath + File.separator + OSDetectorUtil.getHostPlatform().getType()
								+ File.separator + appConfig.getAppType().getType() + File.separator
								+ browserType.name() + File.separator + "extensions" + File.separator + value));
			}

			// initialize driver experimental options
			if (strKey.startsWith("BrowserPreference.")) {
				experimentalOptions.put(strKey.substring("BrowserPreference.".length()),
						parseTypedValue(strKey, value));
			}
		}

		deleteExtensionsCacheIfItExists = Boolean
				.parseBoolean(getProperty(properties, "DELETE_EXTENSIONS_CACHE_IF_IT_EXISTS"));
		alwaysLoadNoFocusLib = Boolean.parseBoolean(getProperty(properties, "ALWAYS_LOAD_NO_FOCUS_LIB"));
		acceptInsecureCertificates = Boolean.parseBoolean(getProperty(properties, "ACCEPT_INSECURE_CERTIFICATES"));
		acceptUntrustedCertificates = Boolean.parseBoolean(getProperty(properties, "ACCEPT_UNTRUSTED_CERTIFICATES"));
		assumeUntrustedCertificateIssuer = Boolean
				.parseBoolean(getProperty(properties, "ASSUME_UNTRUSTED_CERTIFICATE_ISSUER"));
		profilePath = appConfig.getAppsConfigDir() + File.separator + appConfig.getAppName() + File.separator
				+ "profile";
		if(!new File(profilePath).exists()) {
			new File(profilePath).mkdirs();
		}
		scriptTimeoutInSecs = Integer.parseInt(getProperty(properties, "SCRIPT_TIMEOUT_IN_SECONDS"));
		pageLoadTimeoutInSecs = Integer.parseInt(getProperty(properties, "PAGE_LOAD_TIMEOUT_IN_SECONDS"));
	}
	
	private String getPropertyName(String strKey, String keyPrefix) {
		String keyPart = null;
		try {
			keyPart = strKey.substring((keyPrefix + ".").length());
			if(keyPart.endsWith("}")) {
				String str1 = keyPart.substring(0, keyPart.lastIndexOf("{")).trim();
				return str1;
			} else if(keyPart.endsWith("]")) {
				String str1 = keyPart.substring(0, keyPart.lastIndexOf("[")).trim();
				return str1;
			} 
			Assert.fail("Failed to parse property '" + strKey + "'.");
		}catch(Exception | Error e) {
			Assert.fail("Failed to parse property '" + strKey + "'.");
		}
		return null;
	}
	
	private String getSubPropertyName(String strKey) {
		String keyPart = null;
		try {
			keyPart = strKey.substring(strKey.lastIndexOf("{") + 1, strKey.lastIndexOf("}")).trim();
		}catch(Exception | Error e) {
			Assert.fail("Failed to parse property '" + strKey + "' to retrieve map key.");
		}
		return keyPart;
	}

	private Object parseTypedValue(String key, String typedValue) {
		String[] typeAndValue = typedValue.split(":");
		Assert.assertNotEquals(typeAndValue, 2,
				"Value defined in '" + browserType.name() + "' browser DriverConfig.properties is incorrect for key '"
						+ key + "'. Correct format is: <dataType>:<value>");

		switch (typeAndValue[0]) {
		case "boolean":
			return Boolean.parseBoolean(typeAndValue[1]);
		case "string":
			return typeAndValue[1];
		case "integer":
			return Integer.parseInt(typeAndValue[1]);
		case "float":
			return Float.parseFloat(typeAndValue[1]);
		case "double":
			return Double.parseDouble(typeAndValue[1]);
		}

		Assert.fail("Value datatype defined in '" + browserType.name()
				+ "' browser DriverConfig.properties is incorrect for key '" + key
				+ "'. Supported data types are: boolean, string, integer, float, double");
		return null;
	}

	private String getProperty(Properties properties, String propName) {
		return StringUtil.trim(properties.getProperty(propName));
	}

	public WebBrowserType getBrowserType() {
		return browserType;
	}

	public void setBrowserType(WebBrowserType browserType) {
		this.browserType = browserType;
	}

	public String getDriverSystemPropertyName() {
		return driverSystemPropertyName;
	}

	public void setDriverSystemPropertyName(String driverSystemPropertyName) {
		this.driverSystemPropertyName = driverSystemPropertyName;
	}

	public String getDriverBinaryFilePath() {
		return driverBinaryFilePath;
	}

	public void setDriverBinaryFilePath(String driverBinaryFilePath) {
		this.driverBinaryFilePath = driverBinaryFilePath;
	}

	public String getDriverFileName() {
		return driverFileName;
	}

	public String getRemoteDriverURL() {
		return remoteDriverURL;
	}

	public boolean isHeadless() {
		return headless;
	}

	public void setHeadless(boolean headless) {
		this.headless = headless;
	}

	public PageLoadStrategy getPageLoadStrategy() {
		return pageLoadStrategy;
	}

	public void setPageLoadStrategy(PageLoadStrategy pageLoadStrategy) {
		this.pageLoadStrategy = pageLoadStrategy;
	}

	public UnexpectedAlertBehaviour getUnexpectedAlertBehaviour() {
		return unexpectedAlertBehaviour;
	}

	public void setUnexpectedAlertBehaviour(UnexpectedAlertBehaviour unexpectedAlertBehaviour) {
		this.unexpectedAlertBehaviour = unexpectedAlertBehaviour;
	}

	public Level getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(Level logLevel) {
		this.logLevel = logLevel;
	}

	public List<String> getArguments() {
		return arguments;
	}

	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

	public Map<String, Object> getDriverCapabilities() {
		return driverCapabilities;
	}

	public void setDriverCapabilities(Map<String, Object> driverCapabilities) {
		this.driverCapabilities = driverCapabilities;
	}

	public Map<String, Object> getExperimentalOptions() {
		return experimentalOptions;
	}

	public void setExperimentalOptions(Map<String, Object> experimentalOptions) {
		this.experimentalOptions = experimentalOptions;
	}

	public List<File> getBrowserExtensionFiles() {
		return browserExtensionFiles;
	}

	public void setBrowserExtensionFiles(List<File> browserExtensionFiles) {
		this.browserExtensionFiles = browserExtensionFiles;
	}

	public boolean isDeleteExtensionsCacheIfItExists() {
		return deleteExtensionsCacheIfItExists;
	}

	public void setDeleteExtensionsCacheIfItExists(boolean deleteExtensionsCacheIfItExists) {
		this.deleteExtensionsCacheIfItExists = deleteExtensionsCacheIfItExists;
	}

	public boolean isAlwaysLoadNoFocusLib() {
		return alwaysLoadNoFocusLib;
	}

	public void setAlwaysLoadNoFocusLib(boolean alwaysLoadNoFocusLib) {
		this.alwaysLoadNoFocusLib = alwaysLoadNoFocusLib;
	}

	public Map<String, Object> getBrowserPreferences() {
		return browserPreferences;
	}
	
	public Map<String, String> getWebAttrMap() {
		return webAttrMap;
	}

	public void setBrowserPreferences(Map<String, Object> browserPreferences) {
		this.browserPreferences = browserPreferences;
	}

	public boolean isAcceptInsecureCertificates() {
		return acceptInsecureCertificates;
	}

	public void setAcceptInsecureCertificates(boolean acceptInsecureCertificates) {
		this.acceptInsecureCertificates = acceptInsecureCertificates;
	}

	public boolean isAcceptUntrustedCertificates() {
		return acceptUntrustedCertificates;
	}

	public void setAcceptUntrustedCertificates(boolean acceptUntrustedCertificates) {
		this.acceptUntrustedCertificates = acceptUntrustedCertificates;
	}

	public boolean isAssumeUntrustedCertificateIssuer() {
		return assumeUntrustedCertificateIssuer;
	}

	public void setAssumeUntrustedCertificateIssuer(boolean assumeUntrustedCertificateIssuer) {
		this.assumeUntrustedCertificateIssuer = assumeUntrustedCertificateIssuer;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}

	public Integer getScriptTimeoutInSecs() {
		return scriptTimeoutInSecs;
	}

	public void setScriptTimeoutInSecs(Integer scriptTimeoutInSecs) {
		this.scriptTimeoutInSecs = scriptTimeoutInSecs;
	}

	public Integer getPageLoadTimeoutInSecs() {
		return pageLoadTimeoutInSecs;
	}

	public void setPageLoadTimeoutInSecs(Integer pageLoadTimeoutInSecs) {
		this.pageLoadTimeoutInSecs = pageLoadTimeoutInSecs;
	}

}
