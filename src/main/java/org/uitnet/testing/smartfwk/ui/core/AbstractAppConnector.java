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

import java.awt.Point;
import java.awt.Rectangle;
import java.io.File;
import java.nio.file.Files;

import org.sikuli.script.Screen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriverFactory;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.config.UserProfile;
import org.uitnet.testing.smartfwk.ui.core.handler.ScrollElementToViewportHandler;
import org.uitnet.testing.smartfwk.ui.core.objects.logon.LoginPageValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.logon.LoginSuccessPageValidator;
import org.uitnet.testing.smartfwk.ui.core.utils.ScreenCaptureUtil;

import com.jayway.jsonpath.DocumentContext;

import io.cucumber.java.Scenario;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class AbstractAppConnector {
	protected Logger logger;

	protected boolean logonTest;
	protected TestConfigManager testConfigManager;

	protected String appName;
	protected ApplicationType appType;
	protected PlatformType testPlatformType;
	protected AppConfig appConfig;
	protected PlatformType hostPlatformType;

	protected String activeUserProfileName = "";
	protected UserProfile activeUserProfile;

	protected SmartAppDriver appDriver;

	/**
	 * 
	 * @param appName
	 * @param overrideDriverProps - can be set null if we do not want to override the existing driver properties for this session only.
	 */
	protected AbstractAppConnector(String appName, DocumentContext overrideDriverProps) {
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.logonTest = false;
		this.testConfigManager = TestConfigManager.getInstance();

		this.appName = appName;
		this.appConfig = testConfigManager.getAppConfig(appName);
		this.appType = appConfig.getAppType();
		this.testPlatformType = appConfig.getTestPlatformType();
		this.hostPlatformType = testConfigManager.getHostPlatformType();
		appDriver = SmartAppDriverFactory.getInstance().getNewAppDriver(appName);
	}
	
	public void scenarioSetup() {

	}

	public void scenarioTearDown() {

	}

	public void setScrollElementToViewportHandler(ScrollElementToViewportHandler handler) {
		appDriver.setScrollElementToViewportHandler(handler);
	}

	public String getAppName() {
		return appName;
	}

	public ApplicationType getAppType() {
		return appType;
	}

	public PlatformType getTestPlatformType() {
		return testPlatformType;
	}

	public String getActiveUserProfileName() {
		return activeUserProfileName;
	}

	public UserProfile getActiveUserProfile() {
		return activeUserProfile;
	}

	public SmartAppDriver getAppDriver() {
		return appDriver;
	}

	protected LoginPageValidator createNewLoginPageValidator() {
		String loginPageValidatorCls = testConfigManager.getAppConfig(appName).getAppLoginPageValidatorClass();
		LoginPageValidator loginPageValidator = null;
		try {
			loginPageValidator = (LoginPageValidator) Class.forName(loginPageValidatorCls).getDeclaredConstructor()
					.newInstance();
			loginPageValidator.setInitParams(appDriver);
		} catch (Throwable th) {
			Assert.fail("Failed to load login page validator class '" + loginPageValidatorCls + "'.", th);
		}
		return loginPageValidator;
	}

	protected LoginSuccessPageValidator createNewLoginSuccessPageValidator() {
		String loginSuccessPageValidatorCls = testConfigManager.getAppConfig(appName)
				.getAppLoginSuccessPageValidatorClass();
		LoginSuccessPageValidator loginSuccessPageValidator = null;
		try {
			loginSuccessPageValidator = (LoginSuccessPageValidator) Class.forName(loginSuccessPageValidatorCls)
					.getDeclaredConstructor().newInstance();
			loginSuccessPageValidator.setInitParams(appDriver);
		} catch (Throwable th) {
			Assert.fail("Failed to load login page validator class '" + loginSuccessPageValidatorCls + "'.", th);
		}
		return loginSuccessPageValidator;
	}

	public SmartAppDriver checkLogoutAndLoginAgain(String userProfileName) {
		LoginPageValidator loginPageValidator = createNewLoginPageValidator();
		LoginSuccessPageValidator loginSuccessPageValidator = createNewLoginSuccessPageValidator();

		if (loginPageValidator.isLoginPageVisible(userProfileName)) {
			appDriver.openAppIfNotOpened(userProfileName);
			loginPageValidator.login(userProfileName);
			loginSuccessPageValidator.validate(userProfileName);
		} else if (!loginSuccessPageValidator.isLoginSuccessPageVisible(userProfileName)) {
			appDriver.openAppIfNotOpened(userProfileName);
			loginPageValidator.login(userProfileName);
			loginSuccessPageValidator.validate(userProfileName);
		}
		return this.appDriver;
	}

	public TestConfigManager getTestConfigManager() {
		return TestConfigManager.getInstance();
	}

	public AppConfig getAppConfig(String appName) {
		return testConfigManager.getAppConfig(appName);
	}

	public SmartAppDriver setActiveUserProfileName(String userProfileName) {
		if (!this.activeUserProfileName.equals(userProfileName)) {
			relogin(userProfileName);
			this.activeUserProfileName = userProfileName;
			this.activeUserProfile = appDriver.getAppConfig().getUserProfile(userProfileName);
		} else {
			checkLogoutAndLoginAgain(userProfileName);
			this.activeUserProfileName = userProfileName;
			this.activeUserProfile = appDriver.getAppConfig().getUserProfile(userProfileName);
		}
		return this.appDriver;
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
		LoginPageValidator loginPageValidator = createNewLoginPageValidator();
		LoginSuccessPageValidator loginSuccessPageValidator = createNewLoginSuccessPageValidator();

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
		try {
			logoutAndNoQuit();
		} finally {
			SmartAppDriverFactory.getInstance().removeAppDriver(appName, appDriver.getAppId());
			appDriver = null;
			// appDriver =
			// SmartAppDriverFactory.getInstance().getLatestAppDriverOrCreateOne(appName);
		}
	}

	public void closeChildrenWindows() {
		if (appDriver != null) {
			appDriver.closeChildWindows();
		}
	}

	public String captureScreenshot(Scenario scenario) {
		String fileNameHint = prepareScreenshotFileName(scenario) + "-" + scenario.getStatus();

		Rectangle screenArea = null;
		if ((appConfig.getTestPlatformType() != PlatformType.windows
				|| appConfig.getTestPlatformType() != PlatformType.linux
				|| appConfig.getTestPlatformType() != PlatformType.mac)
				&& appConfig.getAppType() == ApplicationType.web_app) {
			screenArea = new Rectangle(new Point(0, 0), appConfig.getBrowserWindowSize());
		} else {
			screenArea = Screen.getPrimaryScreen().getRect();
		}

		String imageFile = ScreenCaptureUtil.capture(testConfigManager.getAppScreenCaptureDirectory(), null,
				fileNameHint, screenArea, appDriver);
		logOrAttachScreenshot(scenario, imageFile, fileNameHint);
		
		return imageFile;
	}

	public String captureScreenshot(Scenario scenario, String status) {
		String fileNameHint = prepareScreenshotFileName(scenario);

		if (status != null) {
			fileNameHint = fileNameHint + "-" + status;
		}

		Rectangle screenArea = null;
		if ((appConfig.getTestPlatformType() != PlatformType.windows
				|| appConfig.getTestPlatformType() != PlatformType.linux
				|| appConfig.getTestPlatformType() != PlatformType.mac)
				&& appConfig.getAppType() == ApplicationType.web_app) {
			screenArea = new Rectangle(new Point(0, 0), appConfig.getBrowserWindowSize());
		} else {
			screenArea = Screen.getPrimaryScreen().getRect();
		}

		String imageFile = ScreenCaptureUtil.capture(testConfigManager.getAppScreenCaptureDirectory(), null,
				fileNameHint, screenArea, appDriver);
		
		logOrAttachScreenshot(scenario, imageFile, fileNameHint);
		
		return imageFile;
	}

	public String captureScreenshot(String fileNameHint) {
		Rectangle screenArea = null;
		if ((appConfig.getTestPlatformType() != PlatformType.windows
				|| appConfig.getTestPlatformType() != PlatformType.linux
				|| appConfig.getTestPlatformType() != PlatformType.mac)
				&& appConfig.getAppType() == ApplicationType.web_app) {
			screenArea = new Rectangle(new Point(0, 0), appConfig.getBrowserWindowSize());
		} else {
			screenArea = Screen.getPrimaryScreen().getRect();
		}

		return ScreenCaptureUtil.capture(testConfigManager.getAppScreenCaptureDirectory(), null, fileNameHint, screenArea,
				appDriver);
	}

	private String prepareScreenshotFileName(Scenario scenario) {
		String scenName = scenario.getName().replaceAll(" ", "-").replaceAll("[^-a-zA-Z0-9-]", "");
		scenName = scenName.substring(0, scenName.length() >= 100 ? 100 : scenName.length());
		String fileName = scenName + "(Line-" + scenario.getLine() + ")";
		return fileName;
	}
	
	private void logOrAttachScreenshot(Scenario scenario, String imageFile, String fileNameHint) {
		try {
			if(testConfigManager.embedScreenshotsInTestReport()) {
				File imgFile = new File(imageFile);
				scenario.attach(Files.readAllBytes(imgFile.toPath()), "image/png", "Screenshot: " + fileNameHint);
				imgFile.delete();
			} else {
				scenario.log("Screenshot Path: " + imageFile);
			}
		} catch (Exception ex) {
			// do nothing
		}
	}

}
