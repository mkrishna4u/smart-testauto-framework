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
package org.uitnet.testing.smartfwk.ui.core;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;
import org.uitnet.testing.smartfwk.ui.core.objects.UIObject;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.jayway.jsonpath.DocumentContext;

/**
 * This class is used as cucumber scenario context to keep the information for
 * the running scenario. using this class we can pass the information from one
 * scenario to another scenario.
 * 
 * This class must be injected in the cucumber step definition constructor.
 * After that any step definition as part of one scenarion can access the
 * information from this class.
 * 
 * NOTE: We must have cucumber-picocontainer jar dependency in our Maven or
 * gradle build configuration (as part of cucumber documentation).
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCucumberUiScenarioContext extends SmartCucumberScenarioContext {
	// Key: <application-name>-<browser-type>, Value: driver properties as
	// json document (similar to mentioned in AppDriver.yaml file), this will
	// overwrite properties specified in AppDriver.yaml file.
	private Map<String, DocumentContext> driverConfigs;

	// Key: appName, Value: AbstractAppConnector
	private Map<String, AbstractAppConnector> appConnectors;

	public SmartCucumberUiScenarioContext() {
		super();
		driverConfigs = new HashMap<>();

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
				new JsonDocumentReader(propsAsJson).getDocumentContext());
		appConnectors = new HashMap<>();
	}

	public DocumentContext getOverriddenDriverProps(String applicationName, String browserType) {
		return driverConfigs.get(applicationName + "-" + applicationName);
	}

	public void captureScreenshot() {
		if (appConnectors.get(activeAppName) != null) {
			appConnectors.get(activeAppName).captureScreenshot(scenario);
		}
	}

	public void captureScreenshot(String fileNameHint) {
		if (appConnectors.get(activeAppName) != null) {
			appConnectors.get(activeAppName).captureScreenshot(fileNameHint);
		}
	}

	public void captureScreenshotWithScenarioStatus(String status) {
		if (appConnectors.get(activeAppName) != null) {
			appConnectors.get(activeAppName).captureScreenshot(scenario, status);
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

	public void waitForSeconds(int seconds) {
		SmartAppDriver driver = getActiveAppDriver();
		if (driver != null) {
			driver.waitForSeconds(seconds);
		}
	}

	@Override
	public void close() {
		if (getTestConfigManager().isParallelMode() || !driverConfigs.isEmpty()) {
			for (AbstractAppConnector connector : appConnectors.values()) {
				connector.logoutAndQuit();
			}
			appConnectors.clear();
		} else {
			closeAllChildWindows();
		}
	}
}
