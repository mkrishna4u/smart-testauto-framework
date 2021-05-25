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
package smartfwk.testing.ui.core;

import java.awt.Point;
import java.awt.Rectangle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import io.cucumber.java.Scenario;
import smartfwk.testing.ui.core.config.AppConfig;
import smartfwk.testing.ui.core.config.TestConfigManager;
import smartfwk.testing.ui.core.config.UserProfile;
import smartfwk.testing.ui.core.config.database.orm.OrmDatabaseQueryHandler;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.logon.LoginPageValidator;
import smartfwk.testing.ui.core.objects.logon.LoginSuccessPageValidator;
import smartfwk.testing.ui.core.objects.webpage.WebPage;
import smartfwk.testing.ui.core.utils.ScreenCaptureUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class AbstractUITestHelper {
	protected Logger logger;

	protected boolean logonTest;
	protected TestConfigManager testConfigManager;

	protected String initAppName;
	protected String initUserProfileName;
	protected String initWebBrowserId;
	protected static WebBrowser initWebBrowser;
	protected WebPage initWebPage;
	protected AppConfig initAppConfig;
	protected UserProfile initUserProfile;

	protected String activeUserProfileName;

	protected AbstractUITestHelper() {
		logger = LoggerFactory.getLogger(this.getClass());
		logonTest = false;
	}

	public void init(String appName, String webBrowserId, WebPage webPage, String userProfileName) {

		testConfigManager = TestConfigManager.getInstance();
		initAppConfig = testConfigManager.getAppConfig(appName);
		initUserProfile = testConfigManager.getAppConfig(appName).getUserProfile(userProfileName);

		this.initAppName = appName;
		this.initUserProfileName = userProfileName;
		this.activeUserProfileName = userProfileName;
		this.initWebPage = webPage;
		this.initWebBrowserId = webBrowserId;
	}

	/**
	 * Opens the new browser if it is not opened by the web driver associated to it.
	 * As soon as the browser is opened, this will login to the system
	 * automatically.
	 */
	public void scenarioSetup() {
		// checkLogoutAndLoginAgain(activeUserProfileName);
	}

	public void scenarioTearDown() {

	}

	public String getInitAppName() {
		return initAppName;
	}

	public String getInitUserProfileName() {
		return initUserProfileName;
	}

	protected LoginPageValidator createNewLoginPageValidator(WebBrowser browser) {
		String loginPageValidatorCls = testConfigManager.getAppConfig(browser.getAppName())
				.getAppLoginPageValidatorClass();
		LoginPageValidator loginPageValidator = null;
		try {
			loginPageValidator = (LoginPageValidator) Class.forName(loginPageValidatorCls).newInstance();
			loginPageValidator.setInitParams(browser);
		} catch (Throwable th) {
			Assert.fail("Failed to load login page validator class '" + loginPageValidatorCls + "'.", th);
		}
		return loginPageValidator;
	}

	protected LoginSuccessPageValidator createNewLoginSuccessPageValidator(WebBrowser browser) {
		String loginSuccessPageValidatorCls = testConfigManager.getAppConfig(browser.getAppName())
				.getAppLoginSuccessPageValidatorClass();
		LoginSuccessPageValidator loginSuccessPageValidator = null;
		try {
			loginSuccessPageValidator = (LoginSuccessPageValidator) Class.forName(loginSuccessPageValidatorCls)
					.newInstance();
			loginSuccessPageValidator.setInitParams(browser);
		} catch (Throwable th) {
			Assert.fail("Failed to load login page validator class '" + loginSuccessPageValidatorCls + "'.", th);
		}
		return loginSuccessPageValidator;
	}

	public void checkLogoutAndLoginAgain(String userProfileName) {
		initWebBrowser = testConfigManager.getWebBrowser(initAppName, initWebBrowserId);
		LoginPageValidator loginPageValidator = createNewLoginPageValidator(initWebBrowser);
		LoginSuccessPageValidator loginSuccessPageValidator = createNewLoginSuccessPageValidator(initWebBrowser);

		if (loginPageValidator.isLoginPageVisible(userProfileName)) {
			loginPageValidator.login(userProfileName);
			loginSuccessPageValidator.validate(userProfileName);
		} else if (!loginSuccessPageValidator.isLoginSuccessPageVisible(userProfileName)) {
			loginPageValidator.login(userProfileName);
			loginSuccessPageValidator.validate(userProfileName);
		}

		if (initWebPage != null) {
			initWebPage.getValidator(initWebBrowser, null).openWebPage();
		}
	}

	public TestConfigManager getTestConfigManager() {
		return TestConfigManager.getInstance();
	}

	public AppConfig getAppConfig(String appName) {
		return testConfigManager.getAppConfig(appName);
	}

	public String getActiveUserProfileName() {
		return activeUserProfileName;
	}

	public void setActiveUserProfileName(String userProfileName) {
		if (!initUserProfileName.equals(userProfileName)) {
			relogin(userProfileName);
			this.activeUserProfileName = userProfileName;
		}
	}

	/**
	 * Logout first and login again. It does not close the browser.
	 */
	private void relogin(String userProfileName) {
		logoutAndNoQuit();
		checkLogoutAndLoginAgain(userProfileName);
	}

	public void relogin() {
		logoutAndNoQuit();
		checkLogoutAndLoginAgain(activeUserProfileName);
	}

	/**
	 * Logout and does not close browser but the child windows/browsers will get
	 * closed.
	 */
	public void logoutAndNoQuit() {
		initWebBrowser = testConfigManager.getWebBrowser(initAppName, initWebBrowserId);
		LoginPageValidator loginPageValidator = createNewLoginPageValidator(initWebBrowser);
		LoginSuccessPageValidator loginSuccessPageValidator = createNewLoginSuccessPageValidator(initWebBrowser);

		if (loginSuccessPageValidator.isLoginSuccessPageVisible(activeUserProfileName)) {
			loginSuccessPageValidator.logout(activeUserProfileName);
			loginPageValidator.validate(activeUserProfileName);
		}
	}

	/**
	 * Logout and closes the browser and the child windows/browsers. NOTE: To
	 * execute further testcases, new browser will be opened.
	 */
	public void logoutAndQuit() {
		logoutAndNoQuit();

		initWebBrowser.quit();
	}

	public WebBrowser getInitWebBrowser() {
		return initWebBrowser;
	}

	/**
	 * Returns the base web page associated with the test class.
	 * 
	 * @return
	 */
	public WebPage getInitWebPage() {
		return initWebPage;
	}

	public OrmDatabaseQueryHandler getDatabaseQueryHandler(String appName, String dbProfileName) {
		return testConfigManager.getDatabaseQueryHandler(appName, dbProfileName);
	}

	public void captureScreenshot(Scenario scenario) {
		String fileNameHint = prepareScreenshotFileName(scenario) + "-" + scenario.getStatus();

		Rectangle screenArea = null;
		if (initWebBrowser != null) {
			screenArea = new Rectangle(new Point(0, 0), initWebBrowser.getWindowSize());
		}

		ScreenCaptureUtil.capture(testConfigManager.getAppScreenCaptureDirectory(), null, fileNameHint, screenArea);
	}

	public void captureScreenshot(Scenario scenario, String status) {
		String fileNameHint = prepareScreenshotFileName(scenario);

		if (status != null) {
			fileNameHint = fileNameHint + "-" + status;
		}

		Rectangle screenArea = null;
		if (initWebBrowser != null) {
			screenArea = new Rectangle(new Point(0, 0), initWebBrowser.getWindowSize());
		}

		ScreenCaptureUtil.capture(testConfigManager.getAppScreenCaptureDirectory(), null, fileNameHint, screenArea);
	}

	public void captureScreenshot(String fileNameHint) {
		Rectangle screenArea = null;
		if (initWebBrowser != null) {
			screenArea = new Rectangle(new Point(0, 0), initWebBrowser.getWindowSize());
		}

		ScreenCaptureUtil.capture(testConfigManager.getAppScreenCaptureDirectory(), null, fileNameHint, screenArea);
	}

	private String prepareScreenshotFileName(Scenario scenario) {
		String[] idParts = scenario.getId().split("/");
		String fileName = idParts[idParts.length - 1].replace("[.]", "-").replace(":", "-ScenrioLine(") + ")";
		return fileName;
	}

}
