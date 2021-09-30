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
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.radio.RadioButtonValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;

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
	public RadioButtonValidatorSD scrollElementOnViewport(Scrollbar scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
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

	@Override
	public boolean isDisabled(int numRetries) {
		return domObjValidator.isDisabled(numRetries);
	}

	@Override
	public void validateDisabled(int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					WebElement webElem = findElement(0);
					Assert.assertFalse(webElem.isEnabled(),
							"Radio button '" + uiObject.getDisplayName() + "' is not disabled.");
					return;
				} catch (Throwable th) {
					if (i == numRetries) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate disabled element '" + uiObject.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void validateEnabled(int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					WebElement webElem = findElement(0);
					Assert.assertTrue(webElem.isEnabled(),
							"Radio button '" + uiObject.getDisplayName() + "' is not enabled.");
					return;
				} catch (Throwable th) {
					if (i == numRetries) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate not disabled element '" + uiObject.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void select(int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					WebElement webElem = findElement(0);
					webElem.click();
					try {
						validateSelected(numRetries);
					} catch (Throwable th) {
						Assert.fail("Failed to select radio button '" + uiObject.getDisplayName() + "'.");
					}
					return;
				} catch (Throwable th) {
					if (i == numRetries) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to select element '" + uiObject.getDisplayName() + "'.", th);
		}

	}

	@Override
	public void validateSelected(int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					WebElement webElem = findElement(0);
					Assert.assertTrue(webElem.isSelected(),
							"Radio button '" + uiObject.getDisplayName() + "' is not selected.");
					return;
				} catch (Throwable th) {
					if (i == numRetries) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate element '" + uiObject.getDisplayName() + "' as selected.", th);
		}
	}

	@Override
	public void validateNotSelected(int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					WebElement webElem = findElement(0);
					Assert.assertFalse(webElem.isSelected(),
							"Radio button '" + uiObject.getDisplayName() + "' is selected.");
					return;
				} catch (Throwable th) {
					if (i == numRetries) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate element '" + uiObject.getDisplayName() + "' as not selected.", th);
		}

	}

}
