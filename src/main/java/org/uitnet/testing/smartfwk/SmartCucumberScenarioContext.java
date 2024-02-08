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
package org.uitnet.testing.smartfwk;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.defaults.ApiTestManager;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.MethodSequenceNumberGenerator;
import org.uitnet.testing.smartfwk.database.DatabaseManager;
import org.uitnet.testing.smartfwk.remote_machine.RemoteMachineManager;
import org.uitnet.testing.smartfwk.ui.core.AbstractAppConnector;
import org.uitnet.testing.smartfwk.ui.core.DefaultAppConnector;
import org.uitnet.testing.smartfwk.ui.core.SingletonAppConnectorMap;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.cache.DefaultSmartCache;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObject;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.jayway.jsonpath.DocumentContext;

import io.cucumber.java.Scenario;

/**
 * Generic interface for cucumber scenario context.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCucumberScenarioContext {
	protected Map<String, Object> params;
	protected Map<String, Boolean> conditions;
	protected Scenario scenario = null;
	protected boolean isUiScenario = false;
	protected String screenCaptureFailedStatus = null;

	protected String activeAppName = null;

	// Key: <application-name>-<browser-type>, Value: driver properties as
	// json document (similar to mentioned in AppDriver.yaml file), this will
	// overwrite properties specified in AppDriver.yaml file.
	private Map<String, DocumentContext> driverConfigs;

	// Key: appName, Value: AbstractAppConnector
	private Map<String, AbstractAppConnector> appConnectors;
		
	// Key: methodName, Value: String
	private Map<String, String> afterScenarioHooks;

	public SmartCucumberScenarioContext() {
		isUiScenario = false;
		params = new TreeMap<>(Collections.reverseOrder());
		conditions = new LinkedHashMap<>(1);
		driverConfigs = new HashMap<>();
		afterScenarioHooks = new LinkedHashMap<>(1);

		if (getTestConfigManager().isParallelMode()) {
			appConnectors = new HashMap<>();
		} else {
			appConnectors = SingletonAppConnectorMap.getInstance().getMap();
			activeAppName = SingletonAppConnectorMap.getInstance().getActiveAppName();
		}
	}
	
	/**
	 * Connect or switch to application.
	 * 
	 * @param appName
	 */
	public synchronized AbstractAppConnector connectOrSwitch(String appName) {
		isUiScenario = true;
		AbstractAppConnector appConnector = appConnectors.get(appName);
		if (appConnector == null) {
			appConnector = new DefaultAppConnector(appName,
					getOverriddenDriverProps(appName, getAppConfig(appName).getAppWebBrowser().getType()));
			appConnector.setActiveUserProfileName(DefaultInfo.DEFAULT_USER_PROFILE_NAME);
			appConnectors.put(appName, appConnector);
		}

		this.activeAppName = appName;

		return appConnector;
	}

	/**
	 * Connect or switch to specified application and set the user profile.
	 * 
	 * @param appName
	 * @param userProfileName
	 * @return
	 */
	public SmartAppDriver connectOrSwitch(String appName, String userProfileName) {
		return setActiveUserProfile(appName, userProfileName);
	}

	/**
	 * Sets the active user profile on the application. If application is not
	 * connected then it will connect to application first and then set the user
	 * profile.
	 * 
	 * @param appName
	 * @param userProfileName
	 * @return
	 */
	public SmartAppDriver setActiveUserProfile(String appName, String userProfileName) {
		isUiScenario = true;
		AbstractAppConnector appConnector = connectOrSwitch(appName);

		if (StringUtil.isEmptyAfterTrim(userProfileName)) {
			return appConnector.setActiveUserProfileName(DefaultInfo.DEFAULT_USER_PROFILE_NAME);
		} else {
			return appConnector.setActiveUserProfileName(userProfileName);
		}
	}

	public SmartAppDriver setActiveUserProfileOnActiveApp(String userProfileName) {
		return setActiveUserProfile(activeAppName, userProfileName);
	}

	public String getActiveUserProfileNameOfActiveApp() {
		return getActiveAppConnector().getActiveUserProfileName();
	}

	public String getActiveUserProfileName(String appName) {
		return getAppConnector(appName).getActiveUserProfileName();
	}

	public SmartAppDriver getActiveAppDriver() {
		return appConnectors.get(activeAppName).getAppDriver();
	}

	public SmartAppDriver getAppDriver(String appName) {
		return appConnectors.get(appName).getAppDriver();
	}

	public AbstractAppConnector getActiveAppConnector() {
		return appConnectors.get(activeAppName);
	}

	public AbstractAppConnector getAppConnector(String appName) {
		return appConnectors.get(appName);
	}

	public void overrideDriverProps(String applicationName, String browserType, String propsAsJson) {
		driverConfigs.put(applicationName + "-" + browserType,
				new JsonDocumentReader(propsAsJson, true).getDocumentContext());
		appConnectors = new HashMap<>();
	}

	public DocumentContext getOverriddenDriverProps(String applicationName, String browserType) {
		return driverConfigs.get(applicationName + "-" + browserType);
	}

	public void captureScreenshot() {
		if (appConnectors.get(activeAppName) != null) {
			appConnectors.get(activeAppName).captureScreenshot(scenario);
		}
	}

	public String captureScreenshot(String fileNameHint) {
		if (appConnectors.get(activeAppName) != null) {
			return appConnectors.get(activeAppName).captureScreenshot(fileNameHint);
		}
		return null;
	}

	public void captureScreenshotWithScenarioStatus(String status) {
		if (appConnectors.get(activeAppName) != null) {
			try {
				screenCaptureFailedStatus = status;
				appConnectors.get(activeAppName).captureScreenshot(scenario, status);
				screenCaptureFailedStatus = null;
			} catch(Throwable th) {
				// do nothing
			}
		}
	}

	public void captureScreenshotOfApp(String appName) {
		if (appConnectors.get(appName) != null) {
			appConnectors.get(appName).captureScreenshot(scenario);
		}
	}

	public void captureScreenshotOfAppWithScenarioStatus(String appName, String status) {
		if (appConnectors.get(appName) != null) {
			appConnectors.get(appName).captureScreenshot(scenario, status);
		}
	}

	/**
	 * Switches all apps to default contents.
	 */
	public void switchToDefaultContent() {
		for (AbstractAppConnector connector : appConnectors.values()) {
			connector.getAppDriver().getWebDriver().switchTo().defaultContent();
		}
	}

	/**
	 * Switches the specified app to default content.
	 * 
	 * @param appName
	 */
	public void switchToDefaultContent(String appName) {
		try {
			appConnectors.get(appName).getAppDriver().getWebDriver().switchTo().defaultContent();
		} catch (Exception | Error e) {
			Assert.fail("Failed to switch '" + appName + "' app to default content.", e);
		}
	}

	public void switchToWindow(String nameOrHandle) {
		appConnectors.get(activeAppName).getAppDriver().getWebDriver().switchTo().window(nameOrHandle);
	}

	public void switchToWindow(String appName, String nameOrHandle) {
		appConnectors.get(appName).getAppDriver().getWebDriver().switchTo().window(nameOrHandle);
	}

	public void switchToFrame(String nameOrId) {
		appConnectors.get(activeAppName).getAppDriver().getWebDriver().switchTo().frame(nameOrId);
	}

	public void switchToFrame(UIObject pageElement) {
		appConnectors.get(activeAppName).getAppDriver().getWebDriver().switchTo()
				.frame((WebElement) pageElement.getValidator(getActiveAppDriver(), null).findElement(2));
	}

	public void switchToFrame(int index) {
		appConnectors.get(activeAppName).getAppDriver().getWebDriver().switchTo().frame(index);
	}

	/**
	 * It creates the new window and switches the focus on it for future commands.
	 * 
	 * @param typeHint
	 */
	public void switchToNewWindow(WindowType typeHint) {
		appConnectors.get(activeAppName).getAppDriver().getWebDriver().switchTo().newWindow(typeHint);
	}

	public void closeAllChildWindows() {
		for (AbstractAppConnector connector : appConnectors.values()) {
			connector.getAppDriver().closeChildWindows();
		}
	}

	public void close() {
		close(false);
	}
	
	public void close(boolean captureScreenshot) {
		AppConfig appConfig = null;
		if(getActiveAppName() != null) {
			appConfig = getTestConfigManager().getAppConfig(getActiveAppName());
		}
		
		if(captureScreenshot && isUiScenario()) {
			captureScreenshotWithScenarioStatus("scenario-" + scenario.getStatus());
		}
		
		String alertText = null;
		if(appConfig != null && isUiScenario() && appConfig.getAppType() == ApplicationType.web_app) {
			// check if any alert present, close it.
			WebDriver webDriver;
			Alert alert = null;
			
			try {
				webDriver = getActiveAppDriver().getWebDriver();
				if(webDriver != null) {
					alert = webDriver.switchTo().alert();
					if(alert != null) {
						try {
							alertText = alert.getText();
							alert.accept();
						} finally {
							alert.dismiss();
							webDriver.switchTo().defaultContent();
						}
					}
				}
				
			} catch(Throwable ex) {
				// Do nothing
			}
		}
		
		if (getTestConfigManager().isParallelMode() || !driverConfigs.isEmpty()) {
			for (AbstractAppConnector connector : appConnectors.values()) {
				connector.logoutAndQuit();
			}
			appConnectors.clear();
		} else {
			closeAllChildWindows();
		}	
		
		if(alertText != null) {
			Assert.fail("Web browser alert should not be present (Existing alert is closed). Found alert presence with message: " + alertText);
		}
	}

	public Scenario getScenario() {
		return this.scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public String getActiveAppName() {
		return activeAppName;
	}

	public TestConfigManager getTestConfigManager() {
		return TestConfigManager.getInstance();
	}

	public AppConfig getActiveAppConfig() {
		return getTestConfigManager().getAppConfig(activeAppName);
	}

	public AppConfig getAppConfig(String appName) {
		return getTestConfigManager().getAppConfig(appName);
	}

	public void log(String message) {
		scenario.log("" + message);
	}

	public void addParamValue(String paramName, Object value) {
		if (StringUtil.isEmptyAfterTrim(paramName)) {
			return;
		}
		params.put(paramName, value);
	}

	/**
	 * This method returns param value. If does not exist then returns as null.
	 * 
	 * @param paramName
	 * @return
	 */
	public Object getParamValue(String paramName) {
		if (paramName == null) {
			return null;
		}
		return params.get(paramName);
	}

	public String getParamValueAsString(String paramName) {
		if (paramName == null) {
			return null;
		}
		return ObjectUtil.valueAsString(params.get(paramName));
	}

	public Integer getParamValueAsInteger(String paramName) {
		if (paramName == null) {
			return null;
		}
		return ObjectUtil.valueAsInteger(params.get(paramName));
	}

	public Long getParamValueAsLong(String paramName) {
		if (paramName == null) {
			return null;
		}
		return ObjectUtil.valueAsLong(params.get(paramName));
	}

	public Double getParamValueAsDouble(String paramName) {
		if (paramName == null) {
			return null;
		}
		return ObjectUtil.valueAsDouble(params.get(paramName));
	}

	public Boolean getParamValueAsBoolean(String paramName) {
		if (paramName == null) {
			return null;
		}
		return ObjectUtil.valueAsBoolean(params.get(paramName));
	}

	/**
	 * MultiValue params are like Array, List, Set
	 * 
	 * @param paramName
	 * @param delimitter          - could be , or any string, if null then it will
	 *                            use default as ,
	 * @param valueEnclosingChars like ' or " or empty/null (denotes no enclosing)
	 * @return
	 */
	public String getMultiValueParamValueAsString(String paramName, String delimitter, String valueEnclosingChars) {
		if (paramName == null) {
			return null;
		}
		return ObjectUtil.listSetArrayValueAsString(params.get(paramName), delimitter, valueEnclosingChars);
	}

	public Map<String, Object> getAllParams() {
		return params;
	}

	public Map<String, Object> getEntriesForParamsEndsWithText(String text) {
		Map<String, Object> fparams = new TreeMap<>(new Comparator<String>() {
			
			public int compare(String o1, String o2) {
				if (o1 == null && o2 == null) {
					return 0;
				}
				if (o1 == null && o2 != null) {
					return o2.length();
				}
				if (o1 != null && o2 == null) {
					return 0 - o1.length();
				}
				return o2.length() - o1.length();
			}
		});

		for (Map.Entry<String, Object> k : params.entrySet()) {
			if (k.getKey().endsWith(text)) {
				fparams.put(k.getKey(), k.getValue());
			}
		}
		return fparams;
	}

	public Map<String, Object> getEntriesForParamsStartsWithText(String text) {
		Map<String, Object> fparams = new TreeMap<>(new Comparator<String>() {
			
			public int compare(String o1, String o2) {
				if (o1 == null && o2 == null) {
					return 0;
				}
				if (o1 == null && o2 != null) {
					return o2.length();
				}
				if (o1 != null && o2 == null) {
					return 0 - o1.length();
				}
				return o2.length() - o1.length();
			}
		});

		for (Map.Entry<String, Object> k : params.entrySet()) {
			if (k.getKey().startsWith(text)) {
				fparams.put(k.getKey(), k.getValue());
			}
		}
		return fparams;
	}

	/**
	 * This method returns param value. If does not exist then returns paramName as
	 * value.
	 * 
	 * @param paramName
	 * @return
	 */
	public Object getParamValueNullAsParamName(String paramName) {
		if (paramName == null) {
			return null;
		}
		Object val = params.get(paramName);
		if (val == null) {
			return paramName;
		}
		return val;
	}

	public void removeParam(String paramName) {
		if (paramName == null) {
			return;
		}
		params.remove(paramName);
	}

	public DatabaseManager getDatabaseManager() {
		return SmartRegistry.getDatabaseManager();
	}

	public ApiTestManager getApiTestManager() {
		return SmartRegistry.getApiTestManager();
	}

	public RemoteMachineManager getRemoteMachineManager() {
		return SmartRegistry.getRemoteMachineManager();
	}
	
	public boolean isUiScenario() {
		return this.isUiScenario;
	}
	
	public void setCondition(String conditionName, Boolean value) {
		this.conditions.put(conditionName, value);
	}
	
	public boolean isConditionSet(String conditionName) {
		Boolean value = this.conditions.get(conditionName);
		if(value == null) {
			return false;
		} 
		return true;
	}
	
	public void unsetCondition(String conditionName) {
		this.conditions.remove(conditionName);
	}
	
	public void unsetAllConditions() {
		this.conditions.clear();;
	}
	
	public boolean isLastConditionSetToTrue() {
		if(conditions.size() == 0) {
			return true;
		}
		
		int counter = 0;
		for(Map.Entry<String, Boolean> condition : conditions.entrySet()) {
			counter++;
			if(counter == conditions.size()) {
				if(condition.getValue() != null && condition.getValue().equals(Boolean.TRUE)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public String getLastConditionName() {
		if(conditions.size() == 0) {
			return "";
		}
		
		int counter = 0;
		for(Map.Entry<String, Boolean> condition : conditions.entrySet()) {
			counter++;
			if(counter == conditions.size()) {
				return condition.getKey();
			}
		}
		
		return "";
	}

	/**
	 * It will apply all params value to the text. It will convert param value to
	 * string then apply. Apply order: 1. First applies the DefaultSmartCache
	 * params. 2. Then applies the scenarioContext params.
	 * 
	 * 
	 * @param text
	 * @return the updated text
	 */
	public String applyParamsValueOnText(String text) {
		if (text == null) {
			return null;
		}

		for (Map.Entry<String, Object> e : DefaultSmartCache.getInstance().getCache().entrySet()) {
			text = text.replace(e.getKey(), "" + e.getValue());
		}

		for (Map.Entry<String, Object> e : params.entrySet()) {
			text = text.replace(e.getKey(), "" + e.getValue());
		}

		return text;
	}
			
	public void registerAfterScenarioHook(String qualifiedMethodName, String data) {
		int nextNum = MethodSequenceNumberGenerator.getInstance().next();
		String newMethodName = qualifiedMethodName + "-" + nextNum;
		SmartCucumberScenarioHooksExecuter.getInstance().checkHookExists(this, newMethodName);
		this.afterScenarioHooks.put(newMethodName, data);
	}
	
	public Map<String, String> getRegisteredAfterScenarioHooks() {
		return afterScenarioHooks;
	}

	public void waitForSeconds(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
		}
	}
	
	public void waitForMilliSeconds(int milliseconds) {
		try {
			TimeUnit.MILLISECONDS.sleep(milliseconds);
		} catch (InterruptedException e) {
		}
	}
}
