/*
 * SmartTestAutoFwk
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
package smartfwk.testing.ui.core.config.webbrowser;

import java.awt.Dimension;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.Screen;

import smartfwk.testing.ui.core.config.AppConfig;
import smartfwk.testing.ui.core.config.TestConfigManager;

/**
 * This is class used to perform operation on the web browser and its web pages.
 * 
 * @author Madhav Krishna
 */
public class WebBrowser {
	private String appName;
	private String id;
	private WebDriver seleniumWebDriver;
	private boolean newInstance;
	private WebBrowserType type;
	private WebBrowserFactory browserFactory;
	private Screen sikuliDriver;
	private AppConfig appConfig;
	private TestConfigManager testCfgMgr;

	public WebBrowser(String id, String appName, AppConfig appConfig, TestConfigManager testCfgMgr,
			WebBrowserFactory factory, WebDriver seleniumWebDriver,
			WebBrowserType type) {
		this.id = id;
		this.appName = appName;
		this.appConfig = appConfig;
		this.testCfgMgr = testCfgMgr;
		this.browserFactory = factory;
		this.seleniumWebDriver = seleniumWebDriver;
		this.type = type;
		this.sikuliDriver = new Screen();
		this.newInstance = true;
	}
	
	public Dimension getWindowSize() {
		return appConfig.getBrowserWindowSize();
	}

	public String getAppName() {
		return appName;
	}
	
	public AppConfig getAppConfig() {
		return appConfig;
	}
	
	public TestConfigManager getTestConfigManager() {
		return testCfgMgr;
	}

	public String getId() {
		return id;
	}

	public WebDriver getSeleniumWebDriver() {
		return seleniumWebDriver;
	}

	public WebBrowserType getWebBrowserType() {
		return type;
	}

	public Screen getSikuliScreen() {
		return sikuliDriver;
	}

	public void openURL(String url) {
		seleniumWebDriver.navigate().to(url);
	}

	public void openDefaultURL() {
		openURL(appConfig.getAppLaunchUrl());
	}

	/**
	 * Returns true if this object is created first time else it will return
	 * false.
	 * 
	 * @return
	 */
	public boolean isNewInstance() {
		return newInstance;
	}

	/**
	 * NOTE: This API is used by WebBrowserFactory class. Please avoid to use
	 * this api other places.
	 * 
	 * Sets the flag to indicate weather the returning object is a new object or
	 * the existing object. Based on that Framework will do the automatic login.
	 * 
	 * @param newInstance
	 */
	public void setNewInstance(boolean newInstance) {
		if (this.newInstance != newInstance) {
			this.newInstance = newInstance;
		}
	}

	public void waitForMilliSeconds(long milliSeconds) {
		try {
			Thread.sleep(milliSeconds);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void waitForSeconds(int seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Returns the operating system name.
	 * @return the operating system name.
	 */
	public String getOSName() {
		return System.getProperty("os.name");		
	}
	
	/**
	 * Returns the operating system short name i.e. windows, linux
	 * @return the operating system short name.
	 */
	public String getOSShortName() {
		String osName = getOSName();				
		if(osName != null) {
			return osName.trim().split(" ")[0];
		}
		return null;
	}

	/**
	 * Refreshes the web browser.
	 */
	public void refresh() {
		seleniumWebDriver.navigate().refresh();		
	}

	/**
	 * This method is used to bring the window to front to perform the
	 * operation.
	 */
	public void setFocus() {
		// TODO: This api doesnt work. check for the alternative to fix it.
		seleniumWebDriver.switchTo().window(seleniumWebDriver.getWindowHandle());
	}

	/**
	 * Closes web browser window.
	 */
	public void quit() {
		browserFactory.destroyAppWebBrowser(appName, id);
	}
}
