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
package org.uitnet.testing.smartfwk.ui.core.appdriver;

import java.io.File;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriverService;
import org.openqa.selenium.safari.SafariOptions;
import org.sikuli.script.Screen;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.AppDriverConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.ProxyConfiguration;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;
import org.uitnet.testing.smartfwk.ui.core.handler.DefaultScrollElementToViewportHandler;
import org.uitnet.testing.smartfwk.ui.core.handler.ScrollElementToViewportHandler;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

import com.jayway.jsonpath.DocumentContext;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.mac.Mac2Driver;
import io.appium.java_client.windows.WindowsDriver;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartAppDriver {
	private int appId;
	private String appName;
	private ApplicationType appType;
	private PlatformType testPlatformType;
	private WebDriver webDriver;
	private Screen sikuliDriver;
	private AppConfig appConfig;
	private boolean opened = false;
	private ScrollElementToViewportHandler scrollElemToViewportCallback;
	private String originalWindowHandle;
	private boolean shouldOpenURL = true;
	private DocumentContext overriddenDriverProps = null;
	
	public SmartAppDriver(String appName, ApplicationType appType, PlatformType testPlatformType) {
		this.appId = AppIdGenerator.getInstance().nextValue();
		this.appName = appName;
		this.appType = appType;
		this.testPlatformType = testPlatformType;
		this.sikuliDriver = new Screen();
		this.appConfig = TestConfigManager.getInstance().getAppConfig(appName);
	}

	public void setScrollElementToViewportHandler(ScrollElementToViewportHandler handler) {
		scrollElemToViewportCallback = handler;
	}

	public ScrollElementToViewportHandler getScrollElementToViewportHandler() {
		return scrollElemToViewportCallback;
	}
	
	/**
	 * Must be called before calling the set active user profile else these properties will not get overridden.
	 * @param jsonProps
	 */
	public void overrideDriverProps(DocumentContext jsonProps) {
		overriddenDriverProps = jsonProps;
	}

	public WebDriver openAppIfNotOpened(String userProfileName) {
		if(StringUtil.isEmptyAfterTrim(userProfileName) || userProfileName.equals(DefaultInfo.DEFAULT_USER_PROFILE_NAME)) {
			shouldOpenURL = false;
		} else {
			shouldOpenURL = true;
		}
		
		if (!opened) {
			prepareWebDriver();
			opened = true;
		} else {
			if (appType == ApplicationType.web_app) {
				webDriver.get(appConfig.getAppLaunchUrl());
			}
		}
		return webDriver;
	}

	private void prepareWebDriver() {
		scrollElemToViewportCallback = new DefaultScrollElementToViewportHandler();

		if (testPlatformType == PlatformType.windows) {
			if (appType == ApplicationType.native_app) {
				prepareWindowsNativeAppDriver();
			} else if (appType == ApplicationType.web_app) {
				prepareWebAppDriverForNonMobileApp();
			}
		} else if (testPlatformType == PlatformType.linux) {
			if (appType == ApplicationType.native_app) {
				Assert.fail("Automation for native app on linux platform is not supported.");
			} else if (appType == ApplicationType.web_app) {
				prepareWebAppDriverForNonMobileApp();
			}
		} else if (testPlatformType == PlatformType.mac) {
			if (appType == ApplicationType.native_app) {
				prepareMacNativeAppDriver();
			} else if (appType == ApplicationType.web_app) {
				prepareWebAppDriverForNonMobileApp();
			}
		} else if (testPlatformType == PlatformType.android_mobile) {
			if (appType == ApplicationType.native_app) {
				prepareAndroidMobileAppDriver();
			} else if (appType == ApplicationType.web_app) {
				prepareAndroidMobileAppDriver();
			}
		} else if (testPlatformType == PlatformType.ios_mobile) {
			if (appType == ApplicationType.native_app) {
				prepareIosMobileAppDriver();
			} else if (appType == ApplicationType.web_app) {
				prepareIosMobileAppDriver();
			}
		}
		
		if(webDriver != null && appType == ApplicationType.web_app) {
			originalWindowHandle = webDriver.getWindowHandle();
		}
	}

	private void prepareWindowsNativeAppDriver() {
		if(!StringUtil.isEmptyAfterTrim(appConfig.getRemoteWebDriverProviderClass())) {
			RemoteWebDriver wdriver = null;
			
			try {
				wdriver = appConfig.getRemoteWebDriverProvider().createRemoteWebDriver();
			} catch (Exception e) {
				Assert.fail(e.getMessage(), e);
			}
			
			wdriver.manage().window().setPosition(new Point(0, 0));
			wdriver.manage().window()
					.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
							Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));

			if(shouldOpenURL) {
				wdriver.navigate().to(appConfig.getAppLaunchUrl());
			}
			webDriver = wdriver;
		} else {
			AppDriverConfig webDriverCfg = appConfig.getAppDriverConfig().getUpdatedProperties(overriddenDriverProps);

			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());

			for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
				capabilities.setCapability(entry.getKey(), entry.getValue());
			}

			try {
				webDriver = new WindowsDriver(new URI(webDriverCfg.getRemoteDriverURL()).toURL(),
						capabilities);

			} catch (Exception ex) {
				Assert.fail("Failed to initialize windows driver.", ex);
			}
		}
		
	}

	private void prepareAndroidMobileAppDriver() {
		if(!StringUtil.isEmptyAfterTrim(appConfig.getRemoteWebDriverProviderClass())) {
			RemoteWebDriver wdriver = null;
			
			try {
				wdriver = appConfig.getRemoteWebDriverProvider().createRemoteWebDriver();
			} catch (Exception e) {
				Assert.fail(e.getMessage(), e);
			}
			
			wdriver.manage().window().setPosition(new Point(0, 0));
			wdriver.manage().window()
					.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
							Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));

			if(shouldOpenURL) {
				wdriver.navigate().to(appConfig.getAppLaunchUrl());
			}
			webDriver = wdriver;
		} else {
			AppDriverConfig webDriverCfg = appConfig.getAppDriverConfig().getUpdatedProperties(overriddenDriverProps);
	
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());
	
			for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
				if ("app".equals(entry.getKey())
						&& !(("" + entry.getValue()).startsWith("http:") || ("" + entry.getValue()).startsWith("https:"))) {
					capabilities.setCapability(entry.getKey(), appConfig.getAppsConfigDir() + File.separator
							+ appConfig.getAppName() + File.separator + entry.getValue());
				} else {
					capabilities.setCapability(entry.getKey(), entry.getValue());
				}
			}
	
			try {
	
				webDriver = new AndroidDriver(new URI(webDriverCfg.getRemoteDriverURL()).toURL(),
						capabilities);
	
			} catch (Exception ex) {
				Assert.fail("Failed to initialize android driver.", ex);
			}
		}
	}

	private void prepareIosMobileAppDriver() {
		if(!StringUtil.isEmptyAfterTrim(appConfig.getRemoteWebDriverProviderClass())) {
			RemoteWebDriver wdriver = null;
			
			try {
				wdriver = appConfig.getRemoteWebDriverProvider().createRemoteWebDriver();
			} catch (Exception e) {
				Assert.fail(e.getMessage(), e);
			}
			
			wdriver.manage().window().setPosition(new Point(0, 0));
			wdriver.manage().window()
					.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
							Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));

			if(shouldOpenURL) {
				wdriver.navigate().to(appConfig.getAppLaunchUrl());
			}
			webDriver = wdriver;
		} else {
			AppDriverConfig webDriverCfg = appConfig.getAppDriverConfig().getUpdatedProperties(overriddenDriverProps);
	
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());
	
			for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
				capabilities.setCapability(entry.getKey(), entry.getValue());
			}
	
			webDriver = new IOSDriver(capabilities);
		}
	}

	private void prepareMacNativeAppDriver() {
		if(!StringUtil.isEmptyAfterTrim(appConfig.getRemoteWebDriverProviderClass())) {
			RemoteWebDriver wdriver = null;
			
			try {
				wdriver = appConfig.getRemoteWebDriverProvider().createRemoteWebDriver();
			} catch (Exception e) {
				Assert.fail(e.getMessage(), e);
			}
			
			wdriver.manage().window().setPosition(new Point(0, 0));
			wdriver.manage().window()
					.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
							Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));

			if(shouldOpenURL) {
				wdriver.navigate().to(appConfig.getAppLaunchUrl());
			}
			webDriver = wdriver;
		} else {
			AppDriverConfig webDriverCfg = appConfig.getAppDriverConfig().getUpdatedProperties(overriddenDriverProps);
	
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());
	
			for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
				capabilities.setCapability(entry.getKey(), entry.getValue());
			}
	
			webDriver = new Mac2Driver(capabilities);
		}
	}

	private void prepareWebAppDriverForNonMobileApp() {
		AppDriverConfig webDriverCfg = appConfig.getAppDriverConfig().getUpdatedProperties(overriddenDriverProps);
		
		Proxy proxy;
		try {
			if(!StringUtil.isEmptyAfterTrim(appConfig.getRemoteWebDriverProviderClass())) {
				RemoteWebDriver wdriver = appConfig.getRemoteWebDriverProvider().createRemoteWebDriver();
				wdriver.manage().window().setPosition(new Point(0, 0));
				wdriver.manage().window()
						.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
								Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));

				if(shouldOpenURL) {
					wdriver.navigate().to(appConfig.getAppLaunchUrl());
				}
				webDriver = wdriver;

				return;
			}
			
			switch (appConfig.getAppWebBrowser()) {
			case firefox: {
				System.setProperty(webDriverCfg.getDriverSystemPropertyName(), webDriverCfg.getDriverBinaryFilePath());

				FirefoxProfile firefoxProfile = new FirefoxProfile(new File(webDriverCfg.getProfilePath()));

				if (appConfig.isEnableWebBrowserExtension()) {
					for (File file : webDriverCfg.getBrowserExtensionFiles()) {
						firefoxProfile.addExtension(file);
					}
				}

				if (webDriverCfg.isDeleteExtensionsCacheIfItExists()) {
					firefoxProfile.deleteExtensionsCacheIfItExists(new File(webDriverCfg.getProfilePath()));
				}

				firefoxProfile.setAlwaysLoadNoFocusLib(webDriverCfg.isAlwaysLoadNoFocusLib());
				firefoxProfile.setAcceptUntrustedCertificates(webDriverCfg.isAcceptUntrustedCertificates());
				firefoxProfile.setAssumeUntrustedCertificateIssuer(webDriverCfg.isAssumeUntrustedCertificateIssuer());

				FirefoxOptions options = new FirefoxOptions().setProfile(firefoxProfile).setBinary(new FirefoxBinary());

				//options.setHeadless(webDriverCfg.isHeadless());
				options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
				options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
				options.setLogLevel(FirefoxDriverLogLevel.fromLevel(webDriverCfg.getLogLevel()));
				options.addArguments(webDriverCfg.getArguments());
				
				if(webDriverCfg.isHeadless() && !webDriverCfg.getArguments().contains("-headless")) {
					options.addArguments("-headless");
				}

				for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
					options.setCapability(entry.getKey(), entry.getValue());
				}

				for (Map.Entry<String, Object> entry : webDriverCfg.getBrowserPreferences().entrySet()) {
					if (entry.getValue() instanceof String) {
						options.addPreference(entry.getKey(), (String) entry.getValue());
					} else if (entry.getValue() instanceof Boolean) {
						options.addPreference(entry.getKey(), (Boolean) entry.getValue());
					} else if (entry.getValue() instanceof Integer) {
						options.addPreference(entry.getKey(), (Integer) entry.getValue());
					}
				}

				options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());

				proxy = getProxyInfo(appConfig);
				if (proxy != null) {
					options.setCapability(CapabilityType.PROXY, proxy);
				}

				FirefoxDriver wdriver = new FirefoxDriver(options);
				wdriver.setLogLevel(webDriverCfg.getLogLevel());

				wdriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
				wdriver.manage().timeouts()
						.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

				wdriver.manage().window()
						.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
								Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));
				wdriver.manage().window().setPosition(new Point(0, 0));

				if(shouldOpenURL) {
					wdriver.navigate().to(appConfig.getAppLaunchUrl());
				}
				webDriver = wdriver;
				break;
			}
			case chrome: {
				System.setProperty(webDriverCfg.getDriverSystemPropertyName(), webDriverCfg.getDriverBinaryFilePath());

				ChromeOptions options = new ChromeOptions();
				//options.setHeadless(webDriverCfg.isHeadless());
				options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
				options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
				options.addArguments(webDriverCfg.getArguments());
				
			    if (webDriverCfg.isHeadless() && !webDriverCfg.getArguments().contains("--headless")) {
			    	options.addArguments("--headless");
			    }

				if (appConfig.isEnableWebBrowserExtension()) {
					options.addExtensions(webDriverCfg.getBrowserExtensionFiles());
				}

				for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
					options.setCapability(entry.getKey(), entry.getValue());
				}

				for (Map.Entry<String, Object> entry : webDriverCfg.getExperimentalOptions().entrySet()) {
					options.setExperimentalOption(entry.getKey(), entry.getValue());
				}

				options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());

				proxy = getProxyInfo(appConfig);
				if (proxy != null) {
					options.setCapability(CapabilityType.PROXY, proxy);
				}

				ChromeDriver wdriver = new ChromeDriver(options);
				wdriver.setLogLevel(webDriverCfg.getLogLevel());

				wdriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
				wdriver.manage().timeouts()
						.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

				wdriver.manage().window()
						.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
								Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));
				wdriver.manage().window().setPosition(new Point(0, 0));

				if(shouldOpenURL) {
					wdriver.navigate().to(appConfig.getAppLaunchUrl());
				}
				webDriver = wdriver;
				break;
			}
			case safari: {
				System.setProperty(SafariDriverService.SAFARI_DRIVER_EXE_PROPERTY,
						webDriverCfg.getDriverBinaryFilePath());
				SafariDriverService service = SafariDriverService.createDefaultService();

				SafariOptions options = new SafariOptions();
				options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
				options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
				options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());
				for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
					options.setCapability(entry.getKey(), entry.getValue());
				}

				proxy = getProxyInfo(appConfig);
				if (proxy != null) {
					options.setCapability(CapabilityType.PROXY, proxy);
				}

				SafariDriver wdriver = new SafariDriver(service, options);
				wdriver.setLogLevel(webDriverCfg.getLogLevel());

				wdriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));// webDriverCfg.getScriptTimeoutInSecs(),
																														// TimeUnit.SECONDS);
				wdriver.manage().timeouts()
						.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

				wdriver.manage().window()
						.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
								Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));
				wdriver.manage().window().setPosition(new Point(0, 0));

				if(shouldOpenURL) {
					wdriver.navigate().to(appConfig.getAppLaunchUrl());
				}
				webDriver = wdriver;
				break;
			}
			case edge: {
				System.setProperty(webDriverCfg.getDriverSystemPropertyName(), webDriverCfg.getDriverBinaryFilePath());
				System.setProperty(EdgeDriverService.EDGE_DRIVER_VERBOSE_LOG_PROPERTY,
						String.valueOf("OFF".equals(webDriverCfg.getLogLevel())));

				EdgeDriverService service = EdgeDriverService.createDefaultService();

				EdgeOptions options = new EdgeOptions();
				//options.setHeadless(webDriverCfg.isHeadless());
				options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
				options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
				options.addArguments(webDriverCfg.getArguments());

				if (webDriverCfg.isHeadless() && !webDriverCfg.getArguments().contains("--headless")) {
			    	options.addArguments("--headless");
			    }
				
				options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());

				if (appConfig.isEnableWebBrowserExtension()) {
					options.addExtensions(webDriverCfg.getBrowserExtensionFiles());
				}

				for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
					options.setCapability(entry.getKey(), entry.getValue());
				}

				for (Map.Entry<String, Object> entry : webDriverCfg.getExperimentalOptions().entrySet()) {
					options.setExperimentalOption(entry.getKey(), entry.getValue());
				}

				proxy = getProxyInfo(appConfig);
				if (proxy != null) {
					options.setCapability(CapabilityType.PROXY, proxy);
				}

				EdgeDriver wdriver = new EdgeDriver(service, options);
				wdriver.setLogLevel(webDriverCfg.getLogLevel());

				wdriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
				wdriver.manage().timeouts()
						.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

				wdriver.manage().window()
						.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
								Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));
				wdriver.manage().window().setPosition(new Point(0, 0));

				if(shouldOpenURL) {
					wdriver.navigate().to(appConfig.getAppLaunchUrl());
				}
				webDriver = wdriver;
				break;
			}
			case opera: {
				System.setProperty(webDriverCfg.getDriverSystemPropertyName(), webDriverCfg.getDriverBinaryFilePath());

				ChromeOptions options = new ChromeOptions();
				//options.setHeadless(webDriverCfg.isHeadless());
				options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
				options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
				options.addArguments(webDriverCfg.getArguments());

				if (webDriverCfg.isHeadless() && !webDriverCfg.getArguments().contains("--headless")) {
			    	options.addArguments("--headless");
			    }
				
				if (appConfig.isEnableWebBrowserExtension()) {
					options.addExtensions(webDriverCfg.getBrowserExtensionFiles());
				}

				for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
					options.setCapability(entry.getKey(), entry.getValue());
				}

				for (Map.Entry<String, Object> entry : webDriverCfg.getExperimentalOptions().entrySet()) {
					options.setExperimentalOption(entry.getKey(), entry.getValue());
				}

				options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());

				proxy = getProxyInfo(appConfig);
				if (proxy != null) {
					options.setCapability(CapabilityType.PROXY, proxy);
				}

				ChromeDriver wdriver = new ChromeDriver(options);
				wdriver.setLogLevel(webDriverCfg.getLogLevel());

				wdriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
				wdriver.manage().timeouts()
						.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

				wdriver.manage().window()
						.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
								Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));
				wdriver.manage().window().setPosition(new Point(0, 0));

				if(shouldOpenURL) {
					wdriver.navigate().to(appConfig.getAppLaunchUrl());
				}
				webDriver = wdriver;
				break;
			}
			case internetExplorer: {
				System.setProperty(webDriverCfg.getDriverSystemPropertyName(), webDriverCfg.getDriverBinaryFilePath());
				System.setProperty(InternetExplorerDriverService.IE_DRIVER_LOGLEVEL_PROPERTY,
						"OFF".equals(webDriverCfg.getLogLevel()) ? "FATAL"
								: "INFO".equals(webDriverCfg.getLogLevel()) ? "INFO" : "DEBUG");

				InternetExplorerDriverService service = InternetExplorerDriverService.createDefaultService();

				InternetExplorerOptions options = new InternetExplorerOptions();
				options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
				options.ignoreZoomSettings();
				options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
				options.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, appConfig.getAppLaunchUrl());

				for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
					options.setCapability(entry.getKey(), entry.getValue());
				}

				proxy = getProxyInfo(appConfig);
				if (proxy != null) {
					options.setCapability(CapabilityType.PROXY, proxy);
				}

				options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());

				InternetExplorerDriver wdriver = new InternetExplorerDriver(service, options);
				wdriver.setLogLevel(webDriverCfg.getLogLevel());

				wdriver.manage().timeouts().scriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
				wdriver.manage().timeouts()
						.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

				wdriver.manage().window()
						.setSize(new Dimension(Double.valueOf(appConfig.getBrowserWindowSize().getWidth()).intValue(),
								Double.valueOf(appConfig.getBrowserWindowSize().getHeight()).intValue()));
				wdriver.manage().window().setPosition(new Point(0, 0));

				if(shouldOpenURL) {
					wdriver.navigate().to(appConfig.getAppLaunchUrl());
				}
				webDriver = wdriver;
				break;
			}
			
			default:
				throw new IllegalArgumentException(
						"Web browser '" + appConfig.getAppWebBrowser().getType() + "' is not supported.");
			}
		} catch (Exception | Error ex) {
			Assert.fail("Failed to initialize " + appConfig.getAppWebBrowser().getType()
					+ " web browser. Going to exit... ", ex);
			System.exit(1);
		}
	}

	/**
	 * Closes current window, if it is a last window then close the app.
	 */
	public void closeCurrentWindow() {
		if (webDriver != null) {
			webDriver.close();
		}
	}

	/**
	 * Closes all the windows associated with this app and the app itself.
	 */
	public void closeApp() {
		if (webDriver != null) {
			webDriver.quit();
		}
		opened = false;
	}
	
	public void closeChildWindows() {
		if (webDriver != null && appType == ApplicationType.web_app) {
			Set<String> windowHandles = webDriver.getWindowHandles();
			WebDriver wd;
			for(String windowHandle : windowHandles) {
				if(windowHandle.equals(originalWindowHandle)) {
					return;
				}
				
				wd = webDriver.switchTo().window(windowHandle);
				if(wd != null) {
					wd.close();
				}
			}
			webDriver.switchTo().defaultContent();
		}
	}

	public int getAppId() {
		return appId;
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

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public AppConfig getAppConfig() {
		return appConfig;
	}

	public boolean isOpened() {
		return opened;
	}

	public String getOSName() {
		return System.getProperty("os.name");
	}

	public Screen getSikuliScreen() {
		return sikuliDriver;
	}
	
	public String getOriginalWindowHandle() {
		return originalWindowHandle;
	}

	public void openURL(String url) {
		webDriver.navigate().to(url);
	}

	public void openDefaultURL() {
		openURL(appConfig.getAppLaunchUrl());
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
	
	public boolean shouldOpenURL() {
		return this.shouldOpenURL;
	}

	/**
	 * Refreshes the web browser.
	 */
	public void refresh() {
		webDriver.navigate().refresh();
	}

	/**
	 * This method is used to bring the window to front to perform the operation.
	 */
	public void setFocus() {
		// TODO: This api doesnt work. check for the alternative to fix it.
		if(webDriver != null && appType == ApplicationType.web_app) {
			webDriver.switchTo().window(webDriver.getWindowHandle());
		}
	}

	private Proxy getProxyInfo(AppConfig appConfig) {
		ProxyConfiguration proxyConfig = appConfig.getProxyConfig();
		Proxy proxy = new Proxy();

		switch (proxyConfig.getProxyConfigType()) {
		case NO_PROXY:
			proxy = null;
			break;
		case AUTO_DETECT:
			proxy.setProxyType(ProxyType.AUTODETECT);
			proxy.setAutodetect(true);
			break;
		case USE_SYSTEM_PROXY:
			proxy.setProxyType(ProxyType.SYSTEM);
			break;
		case MANUAL_PROXY:
			proxy.setProxyType(ProxyType.MANUAL);
			proxy.setHttpProxy(proxyConfig.getHttpProxyHostname() + ":" + proxyConfig.getHttpProxyPort());
			proxy.setSslProxy(proxyConfig.getSslProxyHostname() + ":" + proxyConfig.getSslProxyPort());
			proxy.setFtpProxy(proxyConfig.getFtpProxyHostname() + ":" + proxyConfig.getFtpProxyPort());

			proxy.setSocksProxy(proxyConfig.getSocksHostname() + ":" + proxyConfig.getSocksPort());
			proxy.setSocksUsername(proxyConfig.getSocksUsername());
			proxy.setSocksPassword(proxyConfig.getSocksPassword());

			proxy.setNoProxy(proxyConfig.getNoProxyFor());

			break;
		}

		return proxy;
	}
}
