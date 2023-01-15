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

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.multi_state.MultiStateElementValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * This is the default implementation of MultiStateElementValidator. 
 * Generally you may have to extend this class and implement the following 
 * two method as per your custom component. Methods:
 * 
 * 1. isStateSelected(String state, int maxIterationsToLocateElements)
 * 2. selectState(String state, int maxIterationsToLocateElements)
 * 3. in your custome validator class you have to fill the valid states in validStates variable.
 * 
 * NOTE: Current implementation checks the element attribute value whether it contains the
 * state value.
 * 
 * 
 * @author Madhav Krishna
 *
 */
public class MultiStateElementValidatorSD extends MultiStateElementValidator {
	protected DOMObjectValidator domObjValidator;
	protected List<String> validStates;

	public MultiStateElementValidatorSD(SmartAppDriver appDriver, MultiStateElementSD uiObject, Region region) {
		super(appDriver, uiObject, region);
		validStates = new LinkedList<>();
		domObjValidator = new DOMObjectValidator(appDriver,
				new DOMObject(uiObject.getType(), uiObject.getDisplayName(), uiObject.getPlatformLocators()), region);
	}

	public DOMObjectValidator getDOMObjectValidator() {
		return domObjValidator;
	}
	
	private void validateStateIsValid(String state) {
		if(!validStates.contains(state)) {
			Assert.fail("'" + state + "' is not a valid state. Valid states are: " + validStates);
		}
	}

	@Override
	public MultiStateElementValidatorSD typeText(String textToType, NewTextLocation location,
			int maxIterationsToLocateElements) {
		domObjValidator.typeText(textToType, location, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public boolean isPresent(int maxIterationsToLocateElements) {
		return domObjValidator.isPresent(maxIterationsToLocateElements);
	}

	@Override
	public boolean isVisible(int maxIterationsToLocateElements) {
		return domObjValidator.isVisible(maxIterationsToLocateElements);
	}

	@Override
	public MultiStateElementValidatorSD click(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD forceClick(int maxIterationsToLocateElements) {
		domObjValidator.forceClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD doubleClick(int maxIterationsToLocateElements) {
		domObjValidator.doubleClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD rightClick(int maxIterationsToLocateElements) {
		domObjValidator.rightClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD clickAndHold(int maxIterationsToLocateElements) {
		domObjValidator.clickAndHold(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD release(int maxIterationsToLocateElements) {
		domObjValidator.release(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD mouseHoverOver(int maxIterationsToLocateElements) {
		domObjValidator.mouseHoverOver(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyDown(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyUp(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyPressed(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public MultiStateElementValidatorSD scrollElementOnViewport(Scrollbar scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public boolean isStateSelected(String state, int maxIterationsToLocateElements) {
		validateStateIsValid(state);
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				WebElement webElem = findElement(0);
				String attrValue = webElem.getAttribute("value");
				if (webElem != null && StringUtil.containsText(attrValue, state)) {
					return true;
				} else {
					Assert.fail("");
				}
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					// do nothing
				}
			}
			appDriver.waitForSeconds(2);
		}
		return false;
	}

	@Override
	public MultiStateElementValidatorSD validateStateSelected(String state, int maxIterationsToLocateElements) {
		if (!isStateSelected(state, maxIterationsToLocateElements)) {
			Assert.fail("Failed to validate '" + state + "' state as selected for element '" + uiObject.getDisplayName() + "'.");
		}
		return this;
	}

	@Override
	public MultiStateElementValidatorSD selectState(String state, int maxIterationsToLocateElements) {
		if (!isStateSelected(state, maxIterationsToLocateElements)) {
			WebElement webElem = findElement(0);
			webElem.click();
		}

		return this;
	}

	@Override
	public WebElement findElement(int maxIterationsToLocateElements) {
		return domObjValidator.findElement(maxIterationsToLocateElements);
	}

	@Override
	public WebElement findElementNoException(int maxIterationsToLocateElements) {
		return domObjValidator.findElementNoException(maxIterationsToLocateElements);
	}

	@Override
	public List<WebElement> findElements(int maxIterationsToLocateElements) {
		return domObjValidator.findElements(maxIterationsToLocateElements);
	}

	@Override
	public boolean isDisabled(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabled(maxIterationsToLocateElements);
	}

	@Override
	public MultiStateElementValidatorSD validateDisabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public MultiStateElementValidatorSD validateEnabled(int maxIterationsToLocateElements) {
		Assert.assertFalse(domObjValidator.isDisabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements);
	}

	@Override
	public MultiStateElementValidatorSD validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public MultiStateElementValidatorSD validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertFalse(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	@Override
	public Actions getNewSeleniumActions() {
		return domObjValidator.getNewSeleniumActions();
	}
	
}
