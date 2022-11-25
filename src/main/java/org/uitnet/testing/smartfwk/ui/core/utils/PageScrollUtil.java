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

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.config.ApplicationType;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PageScrollUtil {

	public static void scrollToTopLeftPage(SmartAppDriver appDriver) {
		JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
		jse.executeScript("window.scrollTo(0, 0);");
	}

	public static void scrollToTopRightPage(SmartAppDriver appDriver) {
		JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
		jse.executeScript("window.scrollTo(window.innerWidth, 0);");
	}

	public static void scrollToBottomLeftPage(SmartAppDriver appDriver) {
		JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
		jse.executeScript("window.scrollTo(0, window.innerHeight);");
	}

	public static void scrollToBottomRightPage(SmartAppDriver appDriver) {
		JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
		jse.executeScript("window.scrollTo(window.innerWidth, window.innerHeight);");
	}

	public static boolean isElementInViewport(SmartAppDriver appDriver, WebElement element) {
		Point winPos = appDriver.getWebDriver().manage().window().getPosition();
		Dimension winDim = appDriver.getWebDriver().manage().window().getSize();
		Point elemPos = element.getLocation();

		int winX1 = winPos.getX();
		int winY1 = winPos.getY();

		int winX2 = winX1 + winDim.getWidth();
		int winY2 = winY1 + winDim.getHeight();

		int elemX1 = elemPos.getX() >= 0 ? elemPos.getX() + 20 : elemPos.getX();
		int elemY1 = elemPos.getY() >= 0 ? elemPos.getY() + 20 : elemPos.getY();

		if ((elemX1 >= winX1 && elemX1 <= winX2) || (elemY1 >= winY1 && elemY1 <= winY2)) {
			return true;
		}

		return false;
	}

	public static void scrollElementToViewport(SmartAppDriver appDriver, WebElement element) {
		if (element == null) {
			return;
		}

		try {
//			if (isElementInViewport(appDriver, element)) {
//				return;
//			}

			JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
			if (appDriver.getAppType() == ApplicationType.web_app) {
				Rectangle rect = element.getRect();
				int elemX1 = rect.getX() >= 0 ? rect.getX() - 20 : rect.getX();
				int elemY1 = rect.getY() >= 0 ? rect.getY() - 20 : rect.getY();
				jse.executeScript("window.scrollTo(" + elemX1 + ", " + elemY1 + ");");
			} else if (appDriver.getTestPlatformType() == PlatformType.android_mobile
					|| appDriver.getTestPlatformType() == PlatformType.ios_mobile) {
//				locatableElem.getCoordinates().inViewPort();

//				Point winPos = appDriver.getWebDriver().manage().window().getPosition();
//				Dimension winDim = appDriver.getWebDriver().manage().window().getSize();
//
//				int centralX = winPos.getX() + (winDim.getWidth() / 2);
//				int centralY = winPos.getY() + (winDim.getHeight() / 2);
//				
//				Point elemPos = element.getLocation();
//				int elemX1 = elemPos.getX() >= 0 ? elemPos.getX() + 20 : elemPos.getX();
//				int elemY1 = elemPos.getY() >= 0 ? elemPos.getY() + 20 : elemPos.getY();
//				
//				AppiumDriver appiumDriver = (AppiumDriver) appDriver.getWebDriver();
//				
//				TouchAction ta = new TouchAction(appiumDriver);
//				ta.press(PointOption.point(new Point(centralX, centralY)));	
//				ta.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)));
//				ta.moveTo(PointOption.point(new Point(elemX1, elemY1)));
//				ta.release();
//				ta.perform();
			}
		} catch (Exception | Error ex) {
			// Do nothing
		}
	}

	/**
	 * This method is applicable only to mobile native apps.
	 * 
	 * @param appDriver
	 * @param xStart
	 * @param yStart
	 * @param xStop
	 * @param yStop
	 */
	@SuppressWarnings("rawtypes")
	public static void swipe(SmartAppDriver appDriver, int xStart, int yStart, int xStop, int yStop) {
		try {
			if (appDriver.getTestPlatformType() == PlatformType.android_mobile
					|| appDriver.getTestPlatformType() == PlatformType.ios_mobile) {
				Assert.fail("Pending implementation");
//				new TouchAction((AppiumDriver) appDriver.getWebDriver())
//					.press(PointOption.point(xStart, yStart))
//					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//					.moveTo(PointOption.point(xStop, yStop))
//					.release()
//					.perform();
			} else {
				Assert.fail("This method is not applicable for platform '" + appDriver.getTestPlatformType() + "'.");
			}
		} catch (Exception e) {
			// Do nothing
		}
	}

	@SuppressWarnings("rawtypes")
	public static void swipePageUp(SmartAppDriver appDriver) {
		try {
			Point winPos = appDriver.getWebDriver().manage().window().getPosition();
			Dimension winDim = appDriver.getWebDriver().manage().window().getSize();

			if (appDriver.getTestPlatformType() == PlatformType.android_mobile
					|| appDriver.getTestPlatformType() == PlatformType.ios_mobile) {
				Assert.fail("Pending implementation");
//				new TouchAction((AppiumDriver) appDriver.getWebDriver())
//					.press(PointOption.point(winPos.getX(),  (winDim.getHeight() - 10)))
//					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//					.moveTo(PointOption.point(winPos.getX(), 0))
//					.release()
//					.perform();
			} else {
				Assert.fail("This method is not applicable for platform '" + appDriver.getTestPlatformType() + "'.");
			}
		} catch (Exception e) {
			// Do nothing
		}
	}

	@SuppressWarnings("rawtypes")
	public static void swipePageDown(SmartAppDriver appDriver) {
		try {
			Point winPos = appDriver.getWebDriver().manage().window().getPosition();
			Dimension winDim = appDriver.getWebDriver().manage().window().getSize();

			if (appDriver.getTestPlatformType() == PlatformType.android_mobile
					|| appDriver.getTestPlatformType() == PlatformType.ios_mobile) {
				Assert.fail("Pending implementation");
//				new TouchAction((AppiumDriver) appDriver.getWebDriver())
//					.press(PointOption.point(winPos.getX(),  0))
//					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//					.moveTo(PointOption.point(winPos.getX(), winDim.getHeight()))
//					.release()
//					.perform();
			} else {
				Assert.fail("This method is not applicable for platform '" + appDriver.getTestPlatformType() + "'.");
			}
		} catch (Exception e) {
			// Do nothing
		}
	}

	@SuppressWarnings("rawtypes")
	public static void swipePageRight(SmartAppDriver appDriver) {
		try {
			Point winPos = appDriver.getWebDriver().manage().window().getPosition();
			Dimension winDim = appDriver.getWebDriver().manage().window().getSize();

			if (appDriver.getTestPlatformType() == PlatformType.android_mobile
					|| appDriver.getTestPlatformType() == PlatformType.ios_mobile) {
				Assert.fail("Pending implementation");
//				new TouchAction((AppiumDriver) appDriver.getWebDriver())
//					.press(PointOption.point(0, winPos.getY()))
//					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//					.moveTo(PointOption.point(winDim.getWidth(), winPos.getY()))
//					.release()
//					.perform();
			} else {
				Assert.fail("This method is not applicable for platform '" + appDriver.getTestPlatformType() + "'.");
			}
		} catch (Exception e) {
			// Do nothing
		}
	}

	@SuppressWarnings("rawtypes")
	public static void swipePageLeft(SmartAppDriver appDriver) {
		try {
			Point winPos = appDriver.getWebDriver().manage().window().getPosition();
			Dimension winDim = appDriver.getWebDriver().manage().window().getSize();

			if (appDriver.getTestPlatformType() == PlatformType.android_mobile
					|| appDriver.getTestPlatformType() == PlatformType.ios_mobile) {
				Assert.fail("Pending implementation");
//				new TouchAction((AppiumDriver) appDriver.getWebDriver())
//					.press(PointOption.point(winDim.getWidth(), winPos.getY()))
//					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000)))
//					.moveTo(PointOption.point(0, winPos.getY()))
//					.release()
//					.perform();
			} else {
				Assert.fail("This method is not applicable for platform '" + appDriver.getTestPlatformType() + "'.");
			}
		} catch (Exception e) {
			// Do nothing
		}
	}

	public static void mouseClick(SmartAppDriver appDriver, WebElement element) {
		if (appDriver.getScrollElementToViewportHandler() != null) {
			appDriver.getScrollElementToViewportHandler().handle(appDriver, element);
		}

		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).click().perform();
	}
	
	public static void mouseForceClick(SmartAppDriver appDriver, WebElement element) {
		if (appDriver.getScrollElementToViewportHandler() != null) {
			appDriver.getScrollElementToViewportHandler().handle(appDriver, element);
		}

		JavascriptExecutor executor = (JavascriptExecutor)appDriver.getWebDriver();
		executor.executeScript("arguments[0].click();", element);
	}

	public static void mouseDoubleClick(SmartAppDriver appDriver, WebElement element) {
		if (appDriver.getScrollElementToViewportHandler() != null) {
			appDriver.getScrollElementToViewportHandler().handle(appDriver, element);
		}

		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).doubleClick().perform();
	}

	public static void mouseContextClick(SmartAppDriver appDriver, WebElement element) {
		if (appDriver.getScrollElementToViewportHandler() != null) {
			appDriver.getScrollElementToViewportHandler().handle(appDriver, element);
		}

		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).contextClick().perform();
	}

	public static void mouseClickAndHold(SmartAppDriver appDriver, WebElement element) {
		if (appDriver.getScrollElementToViewportHandler() != null) {
			appDriver.getScrollElementToViewportHandler().handle(appDriver, element);
		}

		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).clickAndHold().perform();
	}

	public static void mouseRelease(SmartAppDriver appDriver, WebElement element) {
		if (appDriver.getScrollElementToViewportHandler() != null) {
			appDriver.getScrollElementToViewportHandler().handle(appDriver, element);
		}

		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).release().perform();
	}

	public static void mouseDragAndDrop(SmartAppDriver appDriver, WebElement source, WebElement target) {
		if (appDriver.getScrollElementToViewportHandler() != null) {
			appDriver.getScrollElementToViewportHandler().handle(appDriver, source);
		}

		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(source).dragAndDrop(source, target).perform();
	}
	
	public static void mouseHoverOver(SmartAppDriver appDriver, WebElement element) {
		if (appDriver.getScrollElementToViewportHandler() != null) {
			appDriver.getScrollElementToViewportHandler().handle(appDriver, element);
			appDriver.waitForMilliSeconds(1000);
		}

		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).perform();
		
		appDriver.waitForMilliSeconds(400);
	}
}
