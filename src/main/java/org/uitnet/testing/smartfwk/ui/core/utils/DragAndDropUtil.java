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
package org.uitnet.testing.smartfwk.ui.core.utils;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DragAndDropUtil {
	private DragAndDropUtil() {
		// do nothing
	}
	
	public static void dragAndDropElement(WebElement sourceElement, WebElement targetElement, SmartAppDriver appDriver) {
		Actions action = new Actions(appDriver.getWebDriver());
		action.dragAndDrop(sourceElement, targetElement).perform();	
	}
	
	public static void dragAndDropElements(List<WebElement> sourceElements, WebElement targetElement, SmartAppDriver appDriver) {
		for(WebElement webElem : sourceElements) {
			Actions action = new Actions(appDriver.getWebDriver());
			action.dragAndDrop(webElem, targetElement).perform();	
		}
	}
	
	public static void dragAndDropFileOnFileInput(String absFilePath, WebElement fileInputElement, SmartAppDriver appDriver) {
		fileInputElement.sendKeys(absFilePath);	
	}
}
 