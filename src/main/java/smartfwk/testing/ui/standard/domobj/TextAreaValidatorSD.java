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
package smartfwk.testing.ui.standard.domobj;

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.sikuli.script.Region;
import org.testng.Assert;

import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.DOMObject;
import smartfwk.testing.ui.core.objects.DOMObjectValidator;
import smartfwk.testing.ui.core.objects.NewTextLocation;
import smartfwk.testing.ui.core.objects.scrollbar.Scrollbar;
import smartfwk.testing.ui.core.objects.textarea.TextAreaValidator;
import smartfwk.testing.ui.core.objects.validator.mechanisms.TextValidationMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class TextAreaValidatorSD extends TextAreaValidator {
	protected DOMObjectValidator domObjValidator;
	
	public TextAreaValidatorSD(WebBrowser browser, TextAreaSD uiObject, Region region) {
		super(browser, uiObject, region);
		domObjValidator = new DOMObjectValidator(browser, 
				new DOMObject(uiObject.getDisplayName(), uiObject.getLocatorXPath()), 
				region);
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
	public void validateTextValue(String expectedValue, TextValidationMechanism validationMechanism, int numRetries) {
		WebElement webElem = domObjValidator.findElement(numRetries);
		String actualValue = webElem.getText();
		
		validateTextValue(actualValue, expectedValue, validationMechanism);
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
	public TextAreaValidatorSD scrollElementOnViewport(Scrollbar scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public String getTextValue(int numRetries) {
		WebElement webElem = domObjValidator.findElement(numRetries);
		return webElem.getText();
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
