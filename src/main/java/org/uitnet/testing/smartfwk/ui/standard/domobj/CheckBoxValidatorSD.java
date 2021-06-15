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
import org.uitnet.testing.smartfwk.ui.core.objects.checkbox.CheckBoxValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class CheckBoxValidatorSD extends CheckBoxValidator {
	protected DOMObjectValidator domObjValidator;

	public CheckBoxValidatorSD(WebBrowser browser, CheckBoxSD uiObject, Region region) {
		super(browser, uiObject, region);
		domObjValidator = new DOMObjectValidator(browser,
				new DOMObject(uiObject.getDisplayName(), uiObject.getLocatorXPath()), region);
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
	public CheckBoxValidatorSD scrollElementOnViewport(Scrollbar scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public boolean isCheckBoxChecked(int numRetries) {
		WebElement webElem = findElement(numRetries);
		return webElem.isSelected();
	}

	@Override
	public void validateCheckBoxChecked(int numRetries) {
		Assert.assertTrue(isCheckBoxChecked(numRetries), "Checkbox '" + uiObject.getDisplayName() + "' is not checked.");	
	}

	@Override
	public void validateCheckBoxUnchecked(int numRetries) {
		Assert.assertFalse(isCheckBoxChecked(numRetries), "Checkbox '" + uiObject.getDisplayName() + "' is checked.");
	}

	@Override
	public void checkAndValidateChecked(int numRetries) {
		WebElement webElem = findElement(numRetries);
		webElem.click();
		
		try {
			validateCheckBoxChecked(numRetries);
		} catch(Throwable th) {
			Assert.fail("Failed to check the Checkbox '" + uiObject.getDisplayName() + "'.");
		}
	}

	@Override
	public void uncheckAndValidateUnchecked(int numRetries) {
		WebElement webElem = findElement(numRetries);
		webElem.click();
		try {
			validateCheckBoxUnchecked(numRetries);
		} catch(Throwable th) {
			Assert.fail("Failed to uncheck the Checkbox '" + uiObject.getDisplayName() + "'.");
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
