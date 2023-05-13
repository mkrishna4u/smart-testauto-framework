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
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.AreaCoordinates;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.radio.RadioButtonValidator;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class RadioButtonValidatorSD extends RadioButtonValidator {
	protected DOMObjectValidator domObjValidator;

	public RadioButtonValidatorSD(SmartAppDriver appDriver, RadioButtonSD uiObject, Region region) {
		super(appDriver, uiObject, region);
		domObjValidator = new DOMObjectValidator(appDriver,
				new DOMObject(uiObject.getType(), uiObject.getDisplayName(), uiObject.getPlatformLocators()), region);
	}

	public DOMObjectValidator getDOMObjectValidator() {
		return domObjValidator;
	}

	@Override
	public RadioButtonValidatorSD typeText(String textToType, NewTextLocation location, int maxIterationsToLocateElements) {
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
	public RadioButtonValidatorSD click(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public RadioButtonValidatorSD forceClick(int maxIterationsToLocateElements) {
		domObjValidator.forceClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public RadioButtonValidatorSD doubleClick(int maxIterationsToLocateElements) {
		domObjValidator.doubleClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public RadioButtonValidatorSD rightClick(int maxIterationsToLocateElements) {
		domObjValidator.rightClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public RadioButtonValidatorSD clickAndHold(int maxIterationsToLocateElements) {
		domObjValidator.clickAndHold(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public RadioButtonValidatorSD release(int maxIterationsToLocateElements) {
		domObjValidator.release(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public RadioButtonValidatorSD mouseHoverOver(int maxIterationsToLocateElements) {
		domObjValidator.mouseHoverOver(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public RadioButtonValidatorSD performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyDown(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public RadioButtonValidatorSD performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyUp(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public RadioButtonValidatorSD performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyPressed(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public RadioButtonValidatorSD scrollElementOnViewport(ScrollbarSI scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
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
	public RadioButtonValidatorSD validateDisabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public RadioButtonValidatorSD validateEnabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isEnabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements);
	}

	@Override
	public RadioButtonValidatorSD validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public RadioButtonValidatorSD validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertFalse(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}
	
	@Override
	public boolean isSelected(int maxIterationsToLocateElements) {
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				WebElement webElem = findElement(0);
				return webElem.isSelected();
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					throw th;
				}
			}
			appDriver.waitForSeconds(2);
		}
		
		return false;
	}

	@Override
	public RadioButtonValidatorSD select(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement webElem = findElement(0);
					webElem.click();
					try {
						validateSelected(maxIterationsToLocateElements);
					} catch (Throwable th) {
						Assert.fail("Failed to select radio button '" + uiObject.getDisplayName() + "'.");
					}
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to select element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSD validateSelected(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement webElem = findElement(0);
					Assert.assertTrue(webElem.isSelected(),
							"Radio button '" + uiObject.getDisplayName() + "' is not selected.");
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate element '" + uiObject.getDisplayName() + "' as selected.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSD validateNotSelected(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement webElem = findElement(0);
					Assert.assertFalse(webElem.isSelected(),
							"Radio button '" + uiObject.getDisplayName() + "' is selected.");
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate element '" + uiObject.getDisplayName() + "' as not selected.", th);
		}
		return this;
	}
	
	@Override
	public RadioButtonValidatorSD validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		domObjValidator.validateElementPresentWithinArea(coordinates, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public Actions getNewSeleniumActions() {
		return domObjValidator.getNewSeleniumActions();
	}

}
