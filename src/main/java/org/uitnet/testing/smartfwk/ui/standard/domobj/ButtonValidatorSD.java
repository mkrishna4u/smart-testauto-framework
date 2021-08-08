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
package org.uitnet.testing.smartfwk.ui.standard.domobj;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.button.ButtonValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ButtonValidatorSD extends ButtonValidator {
	protected DOMObjectValidator domObjValidator;
	
	public ButtonValidatorSD(WebBrowser browser, ButtonSD uiObject, Region region) {
		super(browser, uiObject, region);
		domObjValidator = new DOMObjectValidator(browser, 
				new DOMObject(uiObject.getDisplayName(), uiObject.getLocatorXPath()), 
				region);
	}

	public DOMObjectValidator getDOMObjectValidator() {
		return domObjValidator;
	}

	@Override
	public void validateDisabled(int numRetries) {
		Assert.assertTrue(domObjValidator.isDisabled(numRetries),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");		
	}

	@Override
	public void validateEnabled(int numRetries) {
		Assert.assertFalse(domObjValidator.isDisabled(numRetries),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");	
	}

	@Override
	public void typeText(String textToType, NewTextLocation location, int numRetries) {
		domObjValidator.typeText(textToType, location, numRetries);
	}

	@Override
	public boolean isPresent(int numRetries) {		
		return domObjValidator.isPresent(numRetries);
	}

	@Override
	public boolean isVisible(int numRetries) {
		return domObjValidator.isVisible(numRetries);
	}

	@Override
	public void click(int numRetries) {
		domObjValidator.click(numRetries);
	}

	@Override
	public void doubleClick(int numRetries) {
		domObjValidator.doubleClick(numRetries);
	}

	@Override
	public void rightClick(int numRetries) {
		domObjValidator.rightClick(numRetries);
	}

	@Override
	public void clickAndHold(int numRetries) {
		domObjValidator.clickAndHold(numRetries);
	}

	@Override
	public void release(int numRetries) {
		domObjValidator.release(numRetries);
	}

	@Override
	public void performKeyDown(Keys keys, int numRetries) {
		domObjValidator.performKeyDown(keys, numRetries);
	}

	@Override
	public void performKeyUp(Keys keys, int numRetries) {
		domObjValidator.performKeyUp(keys, numRetries);
	}

	@Override
	public void performKeyPressed(Keys keys, int numRetries) {
		domObjValidator.performKeyPressed(keys, numRetries);
	}

	@Override
	public ButtonValidatorSD scrollElementOnViewport(Scrollbar scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public void validateName(String expectedName, TextMatchMechanism validationMechanism, int numRetries) {
		try {
			for(int i = 0; i <= numRetries; i++) {
				try {
					WebElement webElem = domObjValidator.findElement(0);
					String actualValue = webElem.getAttribute("value");
					validateTextValue(actualValue, expectedName, validationMechanism);
					return;
				} catch(Throwable th) {
					if(i == numRetries) {
						throw th;
					}
				}
				browser.waitForSeconds(2);
			}
		} catch(Throwable th) {
			Assert.fail("Failed to validate expected name '" + expectedName + "' for element '" + uiObject.getDisplayName() + "'.", th);
		}
	}
	
	@Override
	public WebElement findElement(int numRetries) {		
		return domObjValidator.findElement(numRetries);
	}

	@Override
	public WebElement findElementNoException(int numRetries) {
		return domObjValidator.findElementNoException(numRetries);
	}

	@Override
	public List<WebElement> findElements(int numRetries) {
		return domObjValidator.findElements(numRetries);
	}

}
