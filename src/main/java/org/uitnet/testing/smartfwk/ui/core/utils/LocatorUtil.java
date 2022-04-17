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
package org.uitnet.testing.smartfwk.ui.core.utils;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.SmartConstants;
import org.uitnet.testing.smartfwk.ui.core.commons.LocateBy;
import org.uitnet.testing.smartfwk.ui.core.commons.Locator;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.config.WebBrowserType;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class LocatorUtil {
	private LocatorUtil() {
		// do nothing
	}

	public static void setPlatformLocatorForNativeApp(Map<String, Locator> platFormLocators, PlatformType platform,
			LocateBy locateBy, String locatorValue) {
		platFormLocators.put(createNativeAppKey(platform), new Locator(locateBy, locatorValue));
	}

	public static void setPlatformLocatorForWebApp(Map<String, Locator> platFormLocators, PlatformType platform,
			WebBrowserType browserType, LocateBy locateBy, String locatorValue) {
		platFormLocators.put(createWebAppKey(platform, browserType), new Locator(locateBy, locatorValue));
	}

	public static void setPlatformImageForNativeApp(Map<String, String> platFormImages, PlatformType platform,
			String image) {
		int dotLastIdx = image.lastIndexOf(".");
		String imgFileName = image.substring(0, dotLastIdx) + "-" + platform.getType();
		String imgFileExtn = image.substring(dotLastIdx + 1, image.length());
		String imgPath = imgFileName + "." + imgFileExtn;
		platFormImages.put(createNativeAppKey(platform), imgPath);
	}

	public static void setPlatformImageForWebApp(Map<String, String> platFormImages, PlatformType platform,
			WebBrowserType browserType, String image) {
		int dotLastIdx = image.lastIndexOf(".");
		String imgFileName = image.substring(0, dotLastIdx) + "-" + platform.getType() + "-" + browserType.getType();
		String imgFileExtn = image.substring(dotLastIdx + 1, image.length());
		String imgPath = imgFileName + "." + imgFileExtn;
		platFormImages.put(createWebAppKey(platform, browserType), imgPath);
	}

	public static Locator findLocator(Map<String, Locator> platFormLocators, PlatformType platform,
			ApplicationType appType, WebBrowserType browserType) {
		Locator locator = null;
		if (appType == ApplicationType.web_app) {
			locator = platFormLocators.get(createWebAppKey(platform, browserType));

			if (locator == null) {
				locator = platFormLocators.get(SmartConstants.DEFAULT_XPATH_LOCATOR);
			}
			Assert.assertNotNull(locator, "No locator found for platformType: " + platform.getType() + ", appType: "
					+ appType.getType() + ", browserType: " + browserType.getType());
		} else if (appType == ApplicationType.native_app) {
			locator = platFormLocators.get(createNativeAppKey(platform));

			if (locator == null) {
				locator = platFormLocators.get(SmartConstants.DEFAULT_XPATH_LOCATOR);
			}
			Assert.assertNotNull(locator,
					"No locator found for platformType: " + platform.getType() + ", appType: " + appType.getType());
		} else {
			Assert.fail("Application type '" + appType.getType() + "' is not supported.");
		}

		return locator;
	}

	public static String findImage(Map<String, String> platFormImages, PlatformType platform, ApplicationType appType,
			WebBrowserType browserType) {
		String image = null;
		if (appType == ApplicationType.web_app) {
			image = platFormImages.get(createWebAppKey(platform, browserType));

			if (image == null) {
				image = platFormImages.get(SmartConstants.DEFAULT_IMAGE_LOCATOR);
			}
			Assert.assertNotNull(image, "No image found for platformType: " + platform.getType() + ", appType: "
					+ appType.getType() + ", browserType: " + browserType.getType());
		} else if (appType == ApplicationType.native_app) {
			image = platFormImages.get(createNativeAppKey(platform));

			if (image == null) {
				image = platFormImages.get(SmartConstants.DEFAULT_IMAGE_LOCATOR);
			}
			Assert.assertNotNull(image,
					"No image found for platformType: " + platform.getType() + ", appType: " + appType.getType());
		} else {
			Assert.fail("Application type '" + appType.getType() + "' is not supported.");
		}

		return image;
	}

	public static WebElement findWebElement(WebDriver appDriver, Locator locator) {
		WebElement webElem = null;
		if (appDriver instanceof AppiumDriver) {
			AppiumDriver driver = (AppiumDriver) appDriver;
			switch (locator.getLocateBy()) {
			case Id:
				webElem = driver.findElement(AppiumBy.id(locator.getValue()));
				break;
			case Name:
				webElem = driver.findElement(AppiumBy.name(locator.getValue()));
				break;
			case ClassName:
				webElem = driver.findElement(AppiumBy.className(locator.getValue()));
				break;
			case CssSelector:
				webElem = driver.findElement(AppiumBy.cssSelector(locator.getValue()));
				break;
			case AccessibilityId:
				webElem = driver.findElement(AppiumBy.accessibilityId(locator.getValue()));
				break;
			case TagName:
				webElem = driver.findElement(AppiumBy.tagName(locator.getValue()));
				break;
			case Xpath:
				webElem = driver.findElement(AppiumBy.xpath(locator.getValue()));
				break;
			case LinkText:
				webElem = driver.findElement(AppiumBy.linkText(locator.getValue()));
				break;
			case PartialLinkText:
				webElem = driver.findElement(AppiumBy.partialLinkText(locator.getValue()));
				break;
			default:
				Assert.fail("Locate by '" + locator.getLocateBy().name() + "' is not supported.");
			}
		} else {
			RemoteWebDriver driver = (RemoteWebDriver) appDriver;
			switch (locator.getLocateBy()) {
			case Id:
				webElem = driver.findElement(By.id(locator.getValue()));
				break;
			case Name:
				webElem = driver.findElement(By.name(locator.getValue()));
				break;
			case ClassName:
				webElem = driver.findElement(By.className(locator.getValue()));
				break;
			case CssSelector:
				webElem = driver.findElement(By.cssSelector(locator.getValue()));
				break;
			case AccessibilityId:
				Assert.fail("Locate by '" + locator.getLocateBy().name() + "' is not supported.");
				break;
			case TagName:
				webElem = driver.findElement(By.tagName(locator.getValue()));
				break;
			case Xpath:
				webElem = driver.findElement(By.xpath(locator.getValue()));
				break;
			case LinkText:
				webElem = driver.findElement(By.linkText(locator.getValue()));
				break;
			case PartialLinkText:
				webElem = driver.findElement(By.partialLinkText(locator.getValue()));
				break;
			default:
				Assert.fail("Locate by '" + locator.getLocateBy().name() + "' is not supported.");
			}
		}

		return webElem;
	}

	public static List<WebElement> findWebElements(WebDriver appDriver, Locator locator) {
		List<WebElement> webElem = null;
		if (appDriver instanceof AppiumDriver) {
			AppiumDriver driver = (AppiumDriver) appDriver;
			switch (locator.getLocateBy()) {
			case Id:
				webElem = driver.findElements(AppiumBy.id(locator.getValue()));
				break;
			case Name:
				webElem = driver.findElements(AppiumBy.name(locator.getValue()));
				break;
			case ClassName:
				webElem = driver.findElements(AppiumBy.className(locator.getValue()));
				break;
			case CssSelector:
				webElem = driver.findElements(AppiumBy.cssSelector(locator.getValue()));
				break;
			case AccessibilityId:
				webElem = driver.findElements(AppiumBy.accessibilityId(locator.getValue()));
				break;
			case TagName:
				webElem = driver.findElements(AppiumBy.tagName(locator.getValue()));
				break;
			case Xpath:
				webElem = driver.findElements(AppiumBy.xpath(locator.getValue()));
				break;
			case LinkText:
				webElem = driver.findElements(AppiumBy.linkText(locator.getValue()));
				break;
			case PartialLinkText:
				webElem = driver.findElements(AppiumBy.partialLinkText(locator.getValue()));
				break;
			default:
				Assert.fail("Locate by '" + locator.getLocateBy().name() + "' is not supported.");
			}
		} else {
			RemoteWebDriver driver = (RemoteWebDriver) appDriver;
			switch (locator.getLocateBy()) {
			case Id:
				webElem = driver.findElements(By.id(locator.getValue()));
				break;
			case Name:
				webElem = driver.findElements(By.name(locator.getValue()));
				break;
			case ClassName:
				webElem = driver.findElements(By.className(locator.getValue()));
				break;
			case CssSelector:
				webElem = driver.findElements(By.cssSelector(locator.getValue()));
				break;
			case AccessibilityId:
				Assert.fail("Locate by '" + locator.getLocateBy().name() + "' is not supported.");
				break;
			case TagName:
				webElem = driver.findElements(By.tagName(locator.getValue()));
				break;
			case Xpath:
				webElem = driver.findElements(By.xpath(locator.getValue()));
				break;
			case LinkText:
				webElem = driver.findElements(By.linkText(locator.getValue()));
				break;
			case PartialLinkText:
				webElem = driver.findElements(By.partialLinkText(locator.getValue()));
				break;
			default:
				Assert.fail("Locate by '" + locator.getLocateBy().name() + "' is not supported.");
			}
		}

		return webElem;
	}

	public static String getCssValue(WebElement webElem, String cssKey) {
		try {
			return webElem.getCssValue(cssKey);
		} catch (Exception | Error ex) {
			return "";
		}
	}

	private static String createWebAppKey(PlatformType platform, WebBrowserType browserType) {
		return platform.getType() + ":" + ApplicationType.web_app.getType() + ":" + browserType.getType();
	}

	private static String createNativeAppKey(PlatformType platform) {
		return platform.getType() + ":" + ApplicationType.native_app.getType();
	}
}
