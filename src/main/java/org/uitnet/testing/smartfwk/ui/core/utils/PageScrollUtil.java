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
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PageScrollUtil {

	public static void scrollToTopLeft(WebBrowser browser) {
		JavascriptExecutor jse = (JavascriptExecutor) browser.getSeleniumWebDriver();
		jse.executeScript("window.scrollTo(0, 0);");
	}
	
	public static void scrollToTopRight(WebBrowser browser) {
		JavascriptExecutor jse = (JavascriptExecutor) browser.getSeleniumWebDriver();
		jse.executeScript("window.scrollTo(window.innerWidth, 0);");
	}
	
	public static void scrollToBottomLeft(WebBrowser browser) {
		JavascriptExecutor jse = (JavascriptExecutor) browser.getSeleniumWebDriver();
		jse.executeScript("window.scrollTo(0, window.innerHeight);");
	}
	
	public static void scrollToBottomRight(WebBrowser browser) {
		JavascriptExecutor jse = (JavascriptExecutor) browser.getSeleniumWebDriver();
		jse.executeScript("window.scrollTo(window.innerWidth, window.innerHeight);");
	}
	
	public static void scrollElemToViewport(WebBrowser browser, WebElement element) {
		if(element == null) { return; }
		Rectangle rect = element.getRect();
		JavascriptExecutor jse = (JavascriptExecutor) browser.getSeleniumWebDriver();
		jse.executeScript("window.scrollTo(" + rect.getX() + ", " + rect.getY() + ");");
	}
	
	public static void scrollToViewportAndClick(WebBrowser browser, WebElement element) {
		Actions action = new Actions(browser.getSeleniumWebDriver());
		action.moveToElement(element).click().perform();
	}
	
	public static void scrollToViewportAndDoubleClick(WebBrowser browser, WebElement element) {
		Actions action = new Actions(browser.getSeleniumWebDriver());
		action.moveToElement(element).doubleClick().perform();
	}
	
	public static void scrollToViewportAndClickAndHold(WebBrowser browser, WebElement element) {
		Actions action = new Actions(browser.getSeleniumWebDriver());
		action.moveToElement(element).clickAndHold().perform();
	}
	
	public static void scrollToViewportAndRelease(WebBrowser browser, WebElement element) {
		Actions action = new Actions(browser.getSeleniumWebDriver());
		action.moveToElement(element).release().perform();
	}
	
	public static void scrollToViewportAndDragAndDrop(WebBrowser browser, WebElement source, WebElement target) {
		Actions action = new Actions(browser.getSeleniumWebDriver());
		action.moveToElement(source).dragAndDrop(source, target).perform();
	}
}
