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
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;

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

	/**
	 * String appDriverPath = Locations.getProjectRootDir() +
	 * "/test-config/app-drivers";
	 * 
	 * @param seleniumConfigDir
	 * @param properties
	 */

	public AppDriverConfig(AppConfig appConfig, DocumentContext yamlDoc) {
		this.appConfig = appConfig;
		arguments = new ArrayList<String>();
		driverCapabilities = new HashMap<String, Object>();
		experimentalOptions = new HashMap<String, Object>();
		browserExtensionFiles = new ArrayList<File>();
		browserPreferences = new HashMap<String, Object>();
		webAttrMap = new HashMap<String, String>();
		init(yamlDoc);
	}

	private void init(DocumentContext yamlDoc) {
		browserType = WebBrowserType.valueOf2(JsonYamlUtil.readNoException(yamlDoc, "$.browserType", String.class));
		driverSystemPropertyName = JsonYamlUtil.readNoException(yamlDoc, "$.driverSystemPropertyName", String.class);

		driverBinaryFilePath = JsonYamlUtil.readNoException(yamlDoc, "$.driverBinaryFilePath", String.class);
		if (driverBinaryFilePath == null) {
			driverFileName = JsonYamlUtil.readNoException(yamlDoc, "$.driverFileName", String.class);
			driverBinaryFilePath = Locations.getProjectRootDir() + File.separator + "test-config" + File.separator
					+ "app-drivers" + File.separator + OSDetectorUtil.getHostPlatform().getType() + File.separator
					+ appConfig.getAppType().getType() + File.separator + browserType.getType() + File.separator
					+ driverFileName;
		}

		remoteDriverURL = JsonYamlUtil.readNoException(yamlDoc, "$.remoteDriverURL", String.class);

		headless = Boolean.parseBoolean(JsonYamlUtil.readNoException(yamlDoc, "$.headless", String.class));
		pageLoadStrategy = PageLoadStrategy.fromString(JsonYamlUtil.readNoException(yamlDoc, "$.pageLoadStrategy", String.class));
		unexpectedAlertBehaviour = UnexpectedAlertBehaviour
				.fromString(JsonYamlUtil.readNoException(yamlDoc, "$.unexpectedAlertBehaviour", String.class));
		if (JsonYamlUtil.readNoException(yamlDoc, "$.logLevel", String.class) != null) {
			logLevel = Level.parse(JsonYamlUtil.readNoException(yamlDoc, "$.logLevel", String.class));
		}

		// initialize driver arguments
		String args = JsonYamlUtil.readNoException(yamlDoc, "$.driverArguments", String.class);
		if (!StringUtil.isEmptyAfterTrim(args)) {
			String[] argArr = args.split(" ");
			for (String a : argArr) {
				if (!StringUtil.isEmptyAfterTrim(a)) {
					arguments.add(a.trim());
				}
			}
		}

		// Initialize multi value properties
		driverCapabilities = JsonYamlUtil.readNoException(yamlDoc, "$.driverCapabilities", (new TypeRef<Map<String, Object>>() {}));

		// initialize web attribute map
		webAttrMap = JsonYamlUtil.readNoException(yamlDoc, "$.webAttrMap", (new TypeRef<Map<String, String>>() {}));

		// initialize driver experimental options
		experimentalOptions = JsonYamlUtil.readNoException(yamlDoc, "$.experimentalOptions", (new TypeRef<Map<String, Object>>() {}));

		// load browser extensions
		browserExtensions = JsonYamlUtil.readNoException(yamlDoc, "$.browserExtensions", (new TypeRef<Map<String, Object>>() {}));
		if(browserExtensions != null && browserExtensions.size() > 0) {
			for(Map.Entry<String, Object> e : browserExtensions.entrySet()) {
				browserExtensionFiles.add(new File(driverBinaryFilePath + File.separator
						+ OSDetectorUtil.getHostPlatform().getType() + File.separator + appConfig.getAppType().getType()
						+ File.separator + browserType.name() + File.separator + "extensions" + File.separator + e.getValue()));
			}
				
		}
		
		// initialize driver experimental options
		browserPreferences = JsonYamlUtil.readNoException(yamlDoc, "$.browserPreferences", (new TypeRef<Map<String, Object>>() {}));
		
		deleteExtensionsCacheIfItExists = Boolean
				.parseBoolean(JsonYamlUtil.readNoException(yamlDoc, "$.deleteExtensionsCacheIfItExists", String.class));
		alwaysLoadNoFocusLib = Boolean.parseBoolean(JsonYamlUtil.readNoException(yamlDoc, "$.alwaysLoadNoFocusLib", String.class));
		acceptInsecureCertificates = Boolean.parseBoolean(JsonYamlUtil.readNoException(yamlDoc, "$.acceptInsecureCertificates", String.class));
		acceptUntrustedCertificates = Boolean.parseBoolean(JsonYamlUtil.readNoException(yamlDoc, "$.acceptUntrustedCertificates", String.class));
		assumeUntrustedCertificateIssuer = Boolean
				.parseBoolean(JsonYamlUtil.readNoException(yamlDoc, "$.assumeUntrustedCertificateIssuer", String.class));
		profilePath = appConfig.getAppsConfigDir() + File.separator + appConfig.getAppName() + File.separator
				+ "profile";
		if (!new File(profilePath).exists()) {
			new File(profilePath).mkdirs();
		}
		
		String value = JsonYamlUtil.readNoException(yamlDoc, "$.scriptTimeoutInSeconds", String.class);
		if(value != null) {
			scriptTimeoutInSecs = Integer.parseInt(value);
		}
		
		value = JsonYamlUtil.readNoException(yamlDoc, "$.pageLoadTimeoutInSeconds", String.class);
		if(value != null) {
			pageLoadTimeoutInSecs = Integer.parseInt(value);
		}
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
