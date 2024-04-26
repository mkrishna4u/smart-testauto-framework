/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.WebBrowserUtil;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

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
	private String browserBinaryPath=null;
	private String remoteDriverURL;
	private boolean headless = false;
	private PageLoadStrategy pageLoadStrategy = PageLoadStrategy.NORMAL;
	private UnexpectedAlertBehaviour unexpectedAlertBehaviour = UnexpectedAlertBehaviour.DISMISS;
	private Level logLevel = Level.SEVERE;

	private List<String> arguments;
	private Map<String, Object> driverCapabilities;
	private Map<String, Object> experimentalOptions;

	private Map<String, Object> browserExtensions;
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
	private DocumentContext defaultYamlProps;

	/**
	 * String appDriverPath = Locations.getProjectRootDir() +
	 * "/test-config/app-drivers";
	 * 
	 * @param seleniumConfigDir
	 * @param properties
	 */

	public AppDriverConfig(AppConfig appConfig, DocumentContext yamlDoc) {
		this.appConfig = appConfig;
		this.defaultYamlProps = yamlDoc;
		arguments = new ArrayList<String>();
		driverCapabilities = new HashMap<String, Object>();
		experimentalOptions = new HashMap<String, Object>();
		browserExtensionFiles = new ArrayList<File>();
		browserPreferences = new HashMap<String, Object>();
		webAttrMap = new HashMap<String, String>();
		init(yamlDoc, null);
	}
	
	private AppDriverConfig(AppConfig appConfig, DocumentContext yamlDoc, DocumentContext overriddenYamlDoc) {
		this.appConfig = appConfig;
		this.defaultYamlProps = yamlDoc;
		arguments = new ArrayList<String>();
		driverCapabilities = new HashMap<String, Object>();
		experimentalOptions = new HashMap<String, Object>();
		browserExtensionFiles = new ArrayList<File>();
		browserPreferences = new HashMap<String, Object>();
		webAttrMap = new HashMap<String, String>();
		init(yamlDoc, overriddenYamlDoc);
	}

	private void init(DocumentContext yamlDoc, DocumentContext overriddenYamlDoc) {
		browserType = WebBrowserType.valueOf2(JsonYamlUtil.readNoException("$.browserType", String.class, yamlDoc, overriddenYamlDoc));
		driverSystemPropertyName = JsonYamlUtil.readNoException("$.driverSystemPropertyName", String.class, yamlDoc, overriddenYamlDoc);

		driverBinaryFilePath = JsonYamlUtil.readNoException("$.driverBinaryFilePath", String.class, yamlDoc, overriddenYamlDoc);
		if (driverBinaryFilePath == null) {
			driverFileName = JsonYamlUtil.readNoException("$.driverFileName", String.class, yamlDoc, overriddenYamlDoc);
			driverBinaryFilePath = Locations.getProjectRootDir() + File.separator + "test-config" + File.separator
					+ "app-drivers" + File.separator + OSDetectorUtil.getHostPlatform().getType() + File.separator
					+ appConfig.getAppType().getType() + File.separator + browserType.getType() + File.separator
					+ driverFileName;
		}
		
		browserBinaryPath = JsonYamlUtil.readNoException("$.browserBinaryPath", String.class, yamlDoc, overriddenYamlDoc);
		browserBinaryPath = WebBrowserUtil.fixBrowserBinaryPath(browserBinaryPath);

		remoteDriverURL = JsonYamlUtil.readNoException("$.remoteDriverURL", String.class, yamlDoc, overriddenYamlDoc);

		headless = Boolean.parseBoolean(JsonYamlUtil.readNoException("$.headless", String.class, yamlDoc, overriddenYamlDoc));
		pageLoadStrategy = PageLoadStrategy.fromString(JsonYamlUtil.readNoException("$.pageLoadStrategy", String.class, yamlDoc, overriddenYamlDoc));
		unexpectedAlertBehaviour = UnexpectedAlertBehaviour
				.fromString(JsonYamlUtil.readNoException("$.unexpectedAlertBehaviour", String.class, yamlDoc, overriddenYamlDoc));
		if (JsonYamlUtil.readNoException("$.logLevel", String.class, yamlDoc, overriddenYamlDoc) != null) {
			logLevel = Level.parse(JsonYamlUtil.readNoException("$.logLevel", String.class, yamlDoc, overriddenYamlDoc));
		}

		// initialize driver arguments
		String args = JsonYamlUtil.readNoException("$.driverArguments", String.class, yamlDoc, overriddenYamlDoc);
		if (!StringUtil.isEmptyAfterTrim(args)) {
			String[] argArr = args.split(" ");
			for (String a : argArr) {
				if (!StringUtil.isEmptyAfterTrim(a)) {
					arguments.add(a.trim());
				}
			}
		}

		// Initialize multi value properties
		driverCapabilities = JsonYamlUtil.readNoException("$.driverCapabilities", (new TypeRef<Map<String, Object>>() {}), yamlDoc, overriddenYamlDoc);

		// initialize web attribute map
		webAttrMap = JsonYamlUtil.readNoException("$.webAttrMap", (new TypeRef<Map<String, String>>() {}), yamlDoc, overriddenYamlDoc);

		// initialize driver experimental options
		experimentalOptions = JsonYamlUtil.readNoException("$.experimentalOptions", (new TypeRef<Map<String, Object>>() {}), yamlDoc, overriddenYamlDoc);

		// load browser extensions
		browserExtensions = JsonYamlUtil.readNoException("$.browserExtensions", (new TypeRef<Map<String, Object>>() {}), yamlDoc, overriddenYamlDoc);
		if(browserExtensions != null && browserExtensions.size() > 0) {
			for(Map.Entry<String, Object> e : browserExtensions.entrySet()) {
				browserExtensionFiles.add(new File(driverBinaryFilePath + File.separator
						+ OSDetectorUtil.getHostPlatform().getType() + File.separator + appConfig.getAppType().getType()
						+ File.separator + browserType.name() + File.separator + "extensions" + File.separator + e.getValue()));
			}
				
		}
		
		// initialize driver experimental options
		browserPreferences = JsonYamlUtil.readNoException("$.browserPreferences", (new TypeRef<Map<String, Object>>() {}), yamlDoc, overriddenYamlDoc);
		
		deleteExtensionsCacheIfItExists = Boolean
				.parseBoolean(JsonYamlUtil.readNoException("$.deleteExtensionsCacheIfItExists", String.class, yamlDoc, overriddenYamlDoc));
		alwaysLoadNoFocusLib = Boolean.parseBoolean(JsonYamlUtil.readNoException("$.alwaysLoadNoFocusLib", String.class, yamlDoc, overriddenYamlDoc));
		acceptInsecureCertificates = Boolean.parseBoolean(JsonYamlUtil.readNoException("$.acceptInsecureCertificates", String.class, yamlDoc, overriddenYamlDoc));
		acceptUntrustedCertificates = Boolean.parseBoolean(JsonYamlUtil.readNoException("$.acceptUntrustedCertificates", String.class, yamlDoc, overriddenYamlDoc));
		assumeUntrustedCertificateIssuer = Boolean
				.parseBoolean(JsonYamlUtil.readNoException("$.assumeUntrustedCertificateIssuer", String.class, yamlDoc, overriddenYamlDoc));
		profilePath = appConfig.getAppsConfigDir() + File.separator + appConfig.getAppName() + File.separator
				+ "profile";
		if (!new File(profilePath).exists()) {
			new File(profilePath).mkdirs();
		}
		
		String value = JsonYamlUtil.readNoException("$.scriptTimeoutInSeconds", String.class, yamlDoc, overriddenYamlDoc);
		if(value != null) {
			scriptTimeoutInSecs = Integer.parseInt(value);
		}
		
		value = JsonYamlUtil.readNoException("$.pageLoadTimeoutInSeconds", String.class, yamlDoc, overriddenYamlDoc);
		if(value != null) {
			pageLoadTimeoutInSecs = Integer.parseInt(value);
		}
	}
	
	/**
	 * Returns the dafault properties that are overridden by updatedJsonProps.
	 * 
	 * @param updatedJsonProps
	 * @return
	 */
	public AppDriverConfig getUpdatedProperties(DocumentContext updatedJsonProps) {
		if(updatedJsonProps == null) {
			return this;
		}
				
		AppDriverConfig updatedConfig = new AppDriverConfig(this.appConfig, this.defaultYamlProps, updatedJsonProps);
		return updatedConfig;
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
	
	public String getBrowserBinaryPath() {
		return browserBinaryPath;
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
