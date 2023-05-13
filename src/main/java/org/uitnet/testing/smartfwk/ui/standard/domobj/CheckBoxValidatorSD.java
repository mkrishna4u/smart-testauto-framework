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
import org.uitnet.testing.smartfwk.ui.core.objects.checkbox.CheckBoxValidator;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class CheckBoxValidatorSD extends CheckBoxValidator {
	protected DOMObjectValidator domObjValidator;

	public CheckBoxValidatorSD(SmartAppDriver appDriver, CheckBoxSD uiObject, Region region) {
		super(appDriver, uiObject, region);
		domObjValidator = new DOMObjectValidator(appDriver,
				new DOMObject(uiObject.getType(), uiObject.getDisplayName(), uiObject.getPlatformLocators()), region);
	}

	public DOMObjectValidator getDOMObjectValidator() {
		return domObjValidator;
	}

	@Override
	public CheckBoxValidatorSD typeText(String textToType, NewTextLocation location, int maxIterationsToLocateElements) {
		domObjValidator.typeText(textToType, location, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public boolean isPresent(int maxIterationsToLocateElements) {
		return domObjValidator.isPresent(maxIterationsToLocateElements);
	}

	@Override
	public boolean isNotPresent(int maxIterationsToLocateElements) {
		return domObjValidator.isNotPresent(maxIterationsToLocateElements);
	}

	@Override
	public boolean isVisible(int maxIterationsToLocateElements) {
		return domObjValidator.isVisible(maxIterationsToLocateElements);
	}
	
	@Override
	public boolean isHidden(int maxIterationsToLocateElements) {
		return domObjValidator.isHidden(maxIterationsToLocateElements);
	}

	@Override
	public CheckBoxValidatorSD click(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public CheckBoxValidatorSD forceClick(int maxIterationsToLocateElements) {
		domObjValidator.forceClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public CheckBoxValidatorSD doubleClick(int maxIterationsToLocateElements) {
		domObjValidator.doubleClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public CheckBoxValidatorSD rightClick(int maxIterationsToLocateElements) {
		domObjValidator.rightClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public CheckBoxValidatorSD clickAndHold(int maxIterationsToLocateElements) {
		domObjValidator.clickAndHold(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public CheckBoxValidatorSD release(int maxIterationsToLocateElements) {
		domObjValidator.release(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public CheckBoxValidatorSD mouseHoverOver(int maxIterationsToLocateElements) {
		domObjValidator.mouseHoverOver(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public CheckBoxValidatorSD performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyDown(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public CheckBoxValidatorSD performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyUp(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public CheckBoxValidatorSD performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyPressed(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public CheckBoxValidatorSD scrollElementOnViewport(ScrollbarSI scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public boolean isCheckBoxChecked(int maxIterationsToLocateElements) {
		return domObjValidator.isSelected(maxIterationsToLocateElements);
	}

	@Override
	public CheckBoxValidatorSD validateCheckBoxChecked(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					Assert.assertTrue(isCheckBoxChecked(0),
							"Checkbox '" + uiObject.getDisplayName() + "' is not checked.");
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate checkbox checked for element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public CheckBoxValidatorSD validateCheckBoxUnchecked(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					Assert.assertFalse(isCheckBoxChecked(0),
							"Checkbox '" + uiObject.getDisplayName() + "' is checked.");
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate checkbox unchecked for element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public CheckBoxValidatorSD checkAndValidateChecked(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);

		try {
			validateCheckBoxChecked(maxIterationsToLocateElements);
		} catch (Throwable th) {
			Assert.fail("Failed to check the Checkbox '" + uiObject.getDisplayName() + "'.");
		}
		return this;
	}

	@Override
	public CheckBoxValidatorSD uncheckAndValidateUnchecked(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);

		try {
			validateCheckBoxUnchecked(maxIterationsToLocateElements);
		} catch (Throwable th) {
			Assert.fail("Failed to uncheck the Checkbox '" + uiObject.getDisplayName() + "'.");
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
	public CheckBoxValidatorSD validateDisabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public CheckBoxValidatorSD validateEnabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isEnabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}
	
	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements);
	}

	@Override
	public CheckBoxValidatorSD validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public CheckBoxValidatorSD validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertFalse(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}
	
	@Override
	public CheckBoxValidatorSD validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		domObjValidator.validateElementPresentWithinArea(coordinates, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public Actions getNewSeleniumActions() {
		return domObjValidator.getNewSeleniumActions();
	}
}
