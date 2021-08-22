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
package org.uitnet.testing.smartfwk.ui.core.config.webbrowser;

import java.io.File;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.Proxy.ProxyType;
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
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaDriverService;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariDriverService;
import org.openqa.selenium.safari.SafariOptions;
import org.sikuli.basics.Settings;
import org.sikuli.script.OCR;
import org.sikuli.script.OCR.Options;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ProxyConfiguration;
import org.uitnet.testing.smartfwk.ui.core.config.SeleniumDriverConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

/**
 * This is a factory class used to create web browser that are used to perform
 * operation on the web pages.
 * 
 * @author Madhav Krishna
 */
public class WebBrowserFactory {
	private static WebBrowserFactory instance = null;
	// Key: browserID, Value WebBrowser
	private Map<String, WebBrowser> appBrowserMap;
	private TestConfigManager testConfigMgr;

	public static WebBrowserFactory getInstance() {
		if (instance == null) {
			synchronized (WebBrowserFactory.class) {
				if (instance == null) {
					instance = new WebBrowserFactory();
				}
			}
		}
		return instance;
	}

	private WebBrowserFactory() {
		this.testConfigMgr = TestConfigManager.getInstance();
		appBrowserMap = new HashMap<String, WebBrowser>(3);
		initializeSikuli();
	}

	protected void initializeSikuli() {
		try {
			Map<String, String> settings = testConfigMgr.getSikuliSettings().getAllSettings();
			String value;
			for (String name : settings.keySet()) {
				value = settings.get(name);

				Field f = Settings.class.getDeclaredField(name);
				if (f.isAccessible()) {
					f.set(null, createObjectFromTypedValue(name, value));
				} else {
					f.setAccessible(true);
					f.set(null, createObjectFromTypedValue(name, value));
					f.setAccessible(false);
				}
			}

			System.out.println("Sikuli OCRDataPath set to: " + testConfigMgr.getSikuliSettings().getOcrDataPath());
			Options ocrOptions = OCR.globalOptions();
			ocrOptions.dataPath(testConfigMgr.getSikuliSettings().getOcrDataPath());
		} catch (Throwable th) {
			Assert.fail("Failed to initialize the sikuli driver.", th);
		}
	}

	protected Object createObjectFromTypedValue(String propertyName, String typedValue) {
		String typeValueArr[] = typedValue.split(":");
		Assert.assertTrue(typeValueArr.length > 1,
				"typedValue format is wrong for property '" + propertyName + "'. It should be <data-type>:<value>");

		switch (typeValueArr[0]) {
		case "integer":
			return Integer.parseInt(typeValueArr[1]);
		case "string":
			return typeValueArr[1];
		case "float":
			return Float.parseFloat(typeValueArr[1]);
		case "double":
			return Double.parseDouble(typeValueArr[1]);
		case "boolean":
			return Boolean.parseBoolean(typeValueArr[1]);
		}

		Assert.fail("'" + typeValueArr[0] + "' datatype is not supported for '" + propertyName + "' property.");
		return null;
	}

	public synchronized WebBrowser getAppWebBrowser(String appName, String browserId) {
		AppConfig appConfig = testConfigMgr.getAppConfig(appName);
		WebBrowserType browserType = appConfig.getAppWebBrowser();
		String loginURL = appConfig.getAppLaunchUrl();

		WebBrowser browser = appBrowserMap.get(appName + ":" + browserId);
		try {
			SeleniumDriverConfig webDriverCfg = testConfigMgr.getSeleniumDriverConfig(browserType);

			switch (browserType) {
			case firefox:
				if (browser == null) {
					System.setProperty(webDriverCfg.getDriverSystemPropertyName(),
							webDriverCfg.getDriverBinaryFilePath());

					FirefoxProfile firefoxProfile = new FirefoxProfile(new File(webDriverCfg.getProfilePath()));

					if(appConfig.isEnableWebBrowserExtension()) {
						for (File file : webDriverCfg.getBrowserExtensionFiles()) {
							firefoxProfile.addExtension(file);
						}
					}

					if (webDriverCfg.isDeleteExtensionsCacheIfItExists()) {
						firefoxProfile.deleteExtensionsCacheIfItExists(new File(webDriverCfg.getProfilePath()));
					}

					firefoxProfile.setAlwaysLoadNoFocusLib(webDriverCfg.isAlwaysLoadNoFocusLib());
					firefoxProfile.setAcceptUntrustedCertificates(webDriverCfg.isAcceptUntrustedCertificates());
					firefoxProfile
							.setAssumeUntrustedCertificateIssuer(webDriverCfg.isAssumeUntrustedCertificateIssuer());

					FirefoxOptions options = new FirefoxOptions().setProfile(firefoxProfile)
							.setBinary(new FirefoxBinary());

					options.setHeadless(webDriverCfg.isHeadless());
					options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
					options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
					options.setLogLevel(FirefoxDriverLogLevel.fromLevel(webDriverCfg.getLogLevel()));
					options.addArguments(webDriverCfg.getArguments());

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

					Proxy proxy = getProxyInfo(appConfig);
					if (proxy != null) {
						options.setCapability(CapabilityType.PROXY, proxy);
					}

					FirefoxDriver wdriver = new FirefoxDriver(options);
					wdriver.setLogLevel(webDriverCfg.getLogLevel());

					browser = new WebBrowser(browserId, appName, appConfig, testConfigMgr, this, wdriver, browserType);

					browser.getSeleniumWebDriver().manage().timeouts()
							.setScriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
					browser.getSeleniumWebDriver().manage().timeouts()
							.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

					browser.getSeleniumWebDriver().manage().window()
							.setSize(new Dimension(new Double(appConfig.getBrowserWindowSize().getWidth()).intValue(),
									new Double(appConfig.getBrowserWindowSize().getHeight()).intValue()));
					browser.getSeleniumWebDriver().manage().window().setPosition(new Point(0, 0));

					browser.getSeleniumWebDriver().navigate().to(loginURL);
					appBrowserMap.put(appName + ":" + browserId, browser);
				} else {
					browser.setNewInstance(false);
				}

				break;
			case chrome:
				if (browser == null) {
					System.setProperty(webDriverCfg.getDriverSystemPropertyName(),
							webDriverCfg.getDriverBinaryFilePath());

					ChromeOptions options = new ChromeOptions();
					options.setHeadless(webDriverCfg.isHeadless());
					options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
					options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
					options.addArguments(webDriverCfg.getArguments());
					
					if(appConfig.isEnableWebBrowserExtension()) {
						options.addExtensions(webDriverCfg.getBrowserExtensionFiles());
					}

					for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
						options.setCapability(entry.getKey(), entry.getValue());
					}

					for (Map.Entry<String, Object> entry : webDriverCfg.getExperimentalOptions().entrySet()) {
						options.setExperimentalOption(entry.getKey(), entry.getValue());
					}

					options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());

					Proxy proxy = getProxyInfo(appConfig);
					if (proxy != null) {
						options.setCapability(CapabilityType.PROXY, proxy);
					}

					ChromeDriver wdriver = new ChromeDriver(options);
					wdriver.setLogLevel(webDriverCfg.getLogLevel());

					browser = new WebBrowser(browserId, appName, appConfig, testConfigMgr, this, wdriver, browserType);

					browser.getSeleniumWebDriver().manage().timeouts()
							.setScriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
					browser.getSeleniumWebDriver().manage().timeouts()
							.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

					browser.getSeleniumWebDriver().manage().window()
							.setSize(new Dimension(new Double(appConfig.getBrowserWindowSize().getWidth()).intValue(),
									new Double(appConfig.getBrowserWindowSize().getHeight()).intValue()));
					browser.getSeleniumWebDriver().manage().window().setPosition(new Point(0, 0));

					browser.getSeleniumWebDriver().navigate().to(loginURL);
					appBrowserMap.put(appName + ":" + browserId, browser);
				} else {
					browser.setNewInstance(false);
				}
				break;
			case safari:
				if (browser == null) {
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

					Proxy proxy = getProxyInfo(appConfig);
					if (proxy != null) {
						options.setCapability(CapabilityType.PROXY, proxy);
					}

					SafariDriver wdriver = new SafariDriver(service, options);
					wdriver.setLogLevel(webDriverCfg.getLogLevel());

					browser = new WebBrowser(browserId, appName, appConfig, testConfigMgr, this, wdriver, browserType);

					browser.getSeleniumWebDriver().manage().timeouts()
							.setScriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));//webDriverCfg.getScriptTimeoutInSecs(), TimeUnit.SECONDS);
					browser.getSeleniumWebDriver().manage().timeouts()
							.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

					browser.getSeleniumWebDriver().manage().window()
							.setSize(new Dimension(new Double(appConfig.getBrowserWindowSize().getWidth()).intValue(),
									new Double(appConfig.getBrowserWindowSize().getHeight()).intValue()));
					browser.getSeleniumWebDriver().manage().window().setPosition(new Point(0, 0));

					browser.getSeleniumWebDriver().navigate().to(loginURL);
					appBrowserMap.put(appName + ":" + browserId, browser);
				} else {
					browser.setNewInstance(false);
				}
				break;
			case edge:
				if (browser == null) {
					System.setProperty(webDriverCfg.getDriverSystemPropertyName(),
							webDriverCfg.getDriverBinaryFilePath());
					System.setProperty(EdgeDriverService.EDGE_DRIVER_VERBOSE_LOG_PROPERTY,
							String.valueOf("OFF".equals(webDriverCfg.getLogLevel())));

					EdgeDriverService service = EdgeDriverService.createDefaultService();

					EdgeOptions options = new EdgeOptions();
					options.setHeadless(webDriverCfg.isHeadless());
					options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
					options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
					options.addArguments(webDriverCfg.getArguments());
					
					options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());
					
					if(appConfig.isEnableWebBrowserExtension()) {
						options.addExtensions(webDriverCfg.getBrowserExtensionFiles());
					}

					for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
						options.setCapability(entry.getKey(), entry.getValue());
					}

					for (Map.Entry<String, Object> entry : webDriverCfg.getExperimentalOptions().entrySet()) {
						options.setExperimentalOption(entry.getKey(), entry.getValue());
					}

					Proxy proxy = getProxyInfo(appConfig);
					if (proxy != null) {
						options.setCapability(CapabilityType.PROXY, proxy);
					}

					EdgeDriver wdriver = new EdgeDriver(service, options);
					wdriver.setLogLevel(webDriverCfg.getLogLevel());

					browser = new WebBrowser(browserId, appName, appConfig, testConfigMgr, this, wdriver, browserType);

					browser.getSeleniumWebDriver().manage().timeouts()
							.setScriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
					browser.getSeleniumWebDriver().manage().timeouts()
							.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

					browser.getSeleniumWebDriver().manage().window()
							.setSize(new Dimension(new Double(appConfig.getBrowserWindowSize().getWidth()).intValue(),
									new Double(appConfig.getBrowserWindowSize().getHeight()).intValue()));
					browser.getSeleniumWebDriver().manage().window().setPosition(new Point(0, 0));

					browser.getSeleniumWebDriver().navigate().to(loginURL);
					appBrowserMap.put(appName + ":" + browserId, browser);
				} else {
					browser.setNewInstance(false);
				}
				break;
			case opera:
				if (browser == null) {
					System.setProperty(webDriverCfg.getDriverSystemPropertyName(),
							webDriverCfg.getDriverBinaryFilePath());
					System.setProperty(OperaDriverService.OPERA_DRIVER_VERBOSE_LOG_PROPERTY,
							String.valueOf("OFF".equals(webDriverCfg.getLogLevel())));

					OperaDriverService service = OperaDriverService.createDefaultService();

					OperaOptions options = new OperaOptions();
					options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
					options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
					options.addArguments(webDriverCfg.getArguments());
					
					if(appConfig.isEnableWebBrowserExtension()) {
						options.addExtensions(webDriverCfg.getBrowserExtensionFiles());
					}

					for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
						options.setCapability(entry.getKey(), entry.getValue());
					}

					for (Map.Entry<String, Object> entry : webDriverCfg.getExperimentalOptions().entrySet()) {
						options.setExperimentalOption(entry.getKey(), entry.getValue());
					}

					options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());

					Proxy proxy = getProxyInfo(appConfig);
					if (proxy != null) {
						options.setCapability(CapabilityType.PROXY, proxy);
					}

					OperaDriver wdriver = new OperaDriver(service, options);
					wdriver.setLogLevel(webDriverCfg.getLogLevel());

					browser = new WebBrowser(browserId, appName, appConfig, testConfigMgr, this, wdriver, browserType);

					browser.getSeleniumWebDriver().manage().timeouts()
							.setScriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
					browser.getSeleniumWebDriver().manage().timeouts()
							.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

					browser.getSeleniumWebDriver().manage().window()
							.setSize(new Dimension(new Double(appConfig.getBrowserWindowSize().getWidth()).intValue(),
									new Double(appConfig.getBrowserWindowSize().getHeight()).intValue()));
					browser.getSeleniumWebDriver().manage().window().setPosition(new Point(0, 0));

					browser.getSeleniumWebDriver().navigate().to(loginURL);
					appBrowserMap.put(appName + ":" + browserId, browser);
				} else {
					browser.setNewInstance(false);
				}
				break;
			case internetExplorer:
				if (browser == null) {
					System.setProperty(webDriverCfg.getDriverSystemPropertyName(),
							webDriverCfg.getDriverBinaryFilePath());
					System.setProperty(InternetExplorerDriverService.IE_DRIVER_LOGLEVEL_PROPERTY,
							"OFF".equals(webDriverCfg.getLogLevel()) ? "FATAL"
									: "INFO".equals(webDriverCfg.getLogLevel()) ? "INFO" : "DEBUG");

					InternetExplorerDriverService service = InternetExplorerDriverService.createDefaultService();

					InternetExplorerOptions options = new InternetExplorerOptions();
					options.setPageLoadStrategy(webDriverCfg.getPageLoadStrategy());
					options.ignoreZoomSettings();
					options.setUnhandledPromptBehaviour(webDriverCfg.getUnexpectedAlertBehaviour());
					options.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, loginURL);

					for (Map.Entry<String, Object> entry : webDriverCfg.getDriverCapabilities().entrySet()) {
						options.setCapability(entry.getKey(), entry.getValue());
					}

					Proxy proxy = getProxyInfo(appConfig);
					if (proxy != null) {
						options.setCapability(CapabilityType.PROXY, proxy);
					}

					options.setAcceptInsecureCerts(webDriverCfg.isAcceptInsecureCertificates());
					
					InternetExplorerDriver wdriver = new InternetExplorerDriver(service, options);
					wdriver.setLogLevel(webDriverCfg.getLogLevel());

					browser = new WebBrowser(browserId, appName, appConfig, testConfigMgr, this, wdriver, browserType);

					browser.getSeleniumWebDriver().manage().timeouts()
							.setScriptTimeout(Duration.ofSeconds(webDriverCfg.getScriptTimeoutInSecs()));
					browser.getSeleniumWebDriver().manage().timeouts()
							.pageLoadTimeout(Duration.ofSeconds(webDriverCfg.getPageLoadTimeoutInSecs()));

					browser.getSeleniumWebDriver().manage().window()
							.setSize(new Dimension(new Double(appConfig.getBrowserWindowSize().getWidth()).intValue(),
									new Double(appConfig.getBrowserWindowSize().getHeight()).intValue()));
					browser.getSeleniumWebDriver().manage().window().setPosition(new Point(0, 0));

					browser.getSeleniumWebDriver().navigate().to(loginURL);
					appBrowserMap.put(appName + ":" + browserId, browser);
				} else {
					browser.setNewInstance(false);
				}
				break;
			case remoteWebDriverProvider:
				if (browser == null) {
					RemoteWebDriver webDriver = appConfig.getRemoteWebDriverProvider().createRemoteWebDriver();
					browser = new WebBrowser(browserId, appName, appConfig, testConfigMgr, this, webDriver,
							browserType);
					browser.getSeleniumWebDriver().manage().window().setPosition(new Point(0, 0));
					browser.getSeleniumWebDriver().manage().window()
							.setSize(new Dimension(new Double(appConfig.getBrowserWindowSize().getWidth()).intValue(),
									new Double(appConfig.getBrowserWindowSize().getHeight()).intValue()));

					browser.getSeleniumWebDriver().navigate().to(loginURL);
					appBrowserMap.put(appName + ":" + browserId, browser);
				} else {
					browser.setNewInstance(false);
				}
				break;
			default:
				throw new IllegalArgumentException("Web browser '" + browserType.name() + "' is not supported.");
			}
		} catch (Exception | Error ex) {
			Assert.fail("Failed to initialize " + browserType.name() + " web browser. Going to exit... ", ex);
			System.exit(1);
		}

		return browser;
	}

	public void destroyAppWebBrowser(String appName, String browserId) {
		WebBrowser browser = appBrowserMap.get(appName + ":" + browserId);
		if (browser != null) {
			browser.getSeleniumWebDriver().quit();
			appBrowserMap.remove(appName + ":" + browserId);
		}
	}

	public void destroyAllWebBrowser(String appName) {
		List<String> remBrowsers = new LinkedList<String>();
		for (String browser : appBrowserMap.keySet()) {
			if (browser.startsWith(appName)) {
				remBrowsers.add(browser);
			}
		}

		for (String browser : remBrowsers) {
			appBrowserMap.get(browser).getSeleniumWebDriver().quit();
			appBrowserMap.remove(browser);
		}
	}

	public void destroyAllWebBrowser() {
		List<String> remBrowsers = new LinkedList<String>();
		for (String browser : appBrowserMap.keySet()) {
			remBrowsers.add(browser);
		}

		for (String browser : remBrowsers) {
			appBrowserMap.get(browser).getSeleniumWebDriver().quit();
			appBrowserMap.remove(browser);
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
