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

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PageScrollUtil {

	public static void scrollToTopLeft(SmartAppDriver appDriver) {
		JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
		jse.executeScript("window.scrollTo(0, 0);");
	}

	public static void scrollToTopRight(SmartAppDriver appDriver) {
		JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
		jse.executeScript("window.scrollTo(window.innerWidth, 0);");
	}

	public static void scrollToBottomLeft(SmartAppDriver appDriver) {
		JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
		jse.executeScript("window.scrollTo(0, window.innerHeight);");
	}

	public static void scrollToBottomRight(SmartAppDriver appDriver) {
		JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
		jse.executeScript("window.scrollTo(window.innerWidth, window.innerHeight);");
	}

	public static void scrollElemToViewport(SmartAppDriver appDriver, WebElement element) {
		if (element == null) {
			return;
		}
		
		try {
			Rectangle rect = element.getRect();
			JavascriptExecutor jse = (JavascriptExecutor) appDriver.getWebDriver();
			jse.executeScript("window.scrollTo(" + rect.getX() + ", " + rect.getY() + ");");
		} catch(Exception | Error ex) {
			// Do nothing
		}
	}

	public static void scrollToViewportAndClick(SmartAppDriver appDriver, WebElement element) {
		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).click().perform();
	}

	public static void scrollToViewportAndDoubleClick(SmartAppDriver appDriver, WebElement element) {
		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).doubleClick().perform();
	}

	public static void scrollToViewportAndClickAndHold(SmartAppDriver appDriver, WebElement element) {
		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).clickAndHold().perform();
	}

	public static void scrollToViewportAndRelease(SmartAppDriver appDriver, WebElement element) {
		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(element).release().perform();
	}

	public static void scrollToViewportAndDragAndDrop(SmartAppDriver appDriver, WebElement source, WebElement target) {
		Actions action = new Actions(appDriver.getWebDriver());
		action.moveToElement(source).dragAndDrop(source, target).perform();
	}
}
