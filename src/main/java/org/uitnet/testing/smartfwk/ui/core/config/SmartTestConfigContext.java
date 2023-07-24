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

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartTestConfigContext {
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

	private String downloadLocation;
	private boolean useDefaultStepDefsHooks;

	private SikuliSettings sikuliSettings;
	private Map<String, MessageHandlerTargetConfig> messageHandlers;

	public SmartTestConfigContext() {

	}

	public String getAppsConfigDir() {
		return appsConfigDir;
	}

	public void setAppsConfigDir(String appsConfigDir) {
		this.appsConfigDir = appsConfigDir;
	}

	public List<String> getAppNames() {
		return appNames;
	}

	public void setAppNames(List<String> appNames) {
		this.appNames = appNames;
	}

	public String getCucumberTestcasesDir() {
		return cucumberTestcasesDir;
	}

	public void setCucumberTestcasesDir(String cucumberTestcasesDir) {
		this.cucumberTestcasesDir = cucumberTestcasesDir;
	}

	public String getAppScreenCaptureDir() {
		return appScreenCaptureDir;
	}

	public void setAppScreenCaptureDir(String appScreenCaptureDir) {
		this.appScreenCaptureDir = appScreenCaptureDir;
	}

	public String getHtmlReportsDir() {
		return htmlReportsDir;
	}

	public void setHtmlReportsDir(String htmlReportsDir) {
		this.htmlReportsDir = htmlReportsDir;
	}

	public String getSikuliConfigDir() {
		return sikuliConfigDir;
	}

	public void setSikuliConfigDir(String sikuliConfigDir) {
		this.sikuliConfigDir = sikuliConfigDir;
	}

	public String getSikuliResourcesDir() {
		return sikuliResourcesDir;
	}

	public void setSikuliResourcesDir(String sikuliResourcesDir) {
		this.sikuliResourcesDir = sikuliResourcesDir;
	}

	public PlatformType getHostPlatformType() {
		return hostPlatformType;
	}

	public void setHostPlatformType(PlatformType hostPlatformType) {
		this.hostPlatformType = hostPlatformType;
	}

	public boolean isParallelMode() {
		return parallelMode;
	}

	public void setParallelMode(boolean parallelMode) {
		this.parallelMode = parallelMode;
	}

	public int getParallelThreads() {
		return parallelThreads;
	}

	public void setParallelThreads(int parallelThreads) {
		this.parallelThreads = parallelThreads;
	}

	public boolean isPreferDriverScreenshots() {
		return preferDriverScreenshots;
	}

	public void setPreferDriverScreenshots(boolean preferDriverScreenshots) {
		this.preferDriverScreenshots = preferDriverScreenshots;
	}

	public boolean isEmbedScreenshotsInTestReport() {
		return embedScreenshotsInTestReport;
	}

	public void setEmbedScreenshotsInTestReport(boolean embedScreenshotsInTestReport) {
		this.embedScreenshotsInTestReport = embedScreenshotsInTestReport;
	}

	public Map<String, AppConfig> getAppConfigs() {
		return appConfigs;
	}

	public void setAppConfigs(Map<String, AppConfig> appConfigs) {
		this.appConfigs = appConfigs;
	}

	public Map<String, Object> getAdditionalProps() {
		return additionalProps;
	}

	public void setAdditionalProps(Map<String, Object> additionalProps) {
		this.additionalProps = additionalProps;
	}

	public SikuliSettings getSikuliSettings() {
		return sikuliSettings;
	}

	public Map<String, MessageHandlerTargetConfig> getMessageHandlers() {
		return messageHandlers;
	}

	public void setMessageHandlers(Map<String, MessageHandlerTargetConfig> messageHandlers) {
		this.messageHandlers = messageHandlers;
	}

	public void setSikuliSettings(SikuliSettings sikuliSettings) {
		this.sikuliSettings = sikuliSettings;
	}

	public String getDownloadLocation() {
		return downloadLocation;
	}

	public void setDownloadLocation(String downloadLocation) {
		this.downloadLocation = downloadLocation;
	}

	public boolean isUseDefaultStepDefsHooks() {
		return useDefaultStepDefsHooks;
	}

	public void setUseDefaultStepDefsHooks(boolean useDefaultStepDefsHooks) {
		this.useDefaultStepDefsHooks = useDefaultStepDefsHooks;
	}

}
