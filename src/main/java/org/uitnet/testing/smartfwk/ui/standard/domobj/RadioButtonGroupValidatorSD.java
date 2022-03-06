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

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemMap;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.radio.RadioButtonGroupValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class RadioButtonGroupValidatorSD extends RadioButtonGroupValidator {
	protected DOMObjectValidator domObjValidator;

	public RadioButtonGroupValidatorSD(SmartAppDriver appDriver, RadioButtonGroupSD uiObject, Region region) {
		super(appDriver, uiObject, region);
		domObjValidator = new DOMObjectValidator(appDriver,
				new DOMObject(uiObject.getType(), uiObject.getDisplayName(), uiObject.getPlatformLocators()), region);
	}

	public DOMObjectValidator getDOMObjectValidator() {
		return domObjValidator;
	}

	@Override
	public void typeText(String textToType, NewTextLocation location, int maxIterationsToLocateElements) {
		domObjValidator.typeText(textToType, location, maxIterationsToLocateElements);
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
	public void click(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);
	}

	@Override
	public void doubleClick(int maxIterationsToLocateElements) {
		domObjValidator.doubleClick(maxIterationsToLocateElements);
	}

	@Override
	public void rightClick(int maxIterationsToLocateElements) {
		domObjValidator.rightClick(maxIterationsToLocateElements);
	}

	@Override
	public void clickAndHold(int maxIterationsToLocateElements) {
		domObjValidator.clickAndHold(maxIterationsToLocateElements);
	}

	@Override
	public void release(int maxIterationsToLocateElements) {
		domObjValidator.release(maxIterationsToLocateElements);
	}

	@Override
	public void performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyDown(keys, maxIterationsToLocateElements);
	}

	@Override
	public void performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyUp(keys, maxIterationsToLocateElements);
	}

	@Override
	public void performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyPressed(keys, maxIterationsToLocateElements);
	}

	@Override
	public RadioButtonGroupValidatorSD scrollElementOnViewport(Scrollbar scrollbar) {
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
	public void validateDisabled(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					List<WebElement> webElems = findElements(0);
					List<String> enabledItems = new LinkedList<String>();
					for (WebElement elem : webElems) {
						if (elem.isEnabled()) {
							enabledItems.add(elem.getAttribute("value"));
						}
					}
					if (enabledItems.size() > 0) {
						Assert.fail("Radio button group '" + uiObject.getDisplayName()
								+ "' is not disabled. It has the following enabled items: " + enabledItems);
					}
					return;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate disabled items for radio group '" + uiObject.getDisplayName() + "'.", th);
		}

	}

	@Override
	public void validateEnabled(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					List<WebElement> webElems = findElements(0);
					List<String> disabledItems = new LinkedList<String>();
					for (WebElement elem : webElems) {
						if (!elem.isEnabled()) {
							disabledItems.add(elem.getAttribute("value"));
						}
					}
					if (disabledItems.size() > 0) {
						Assert.fail("Radio button group '" + uiObject.getDisplayName()
								+ "' is not enabled. It has the following disabled items: " + disabledItems);
					}
					return;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate enabled items for radio group '" + uiObject.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void selectOption(String value, String displayValue, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					List<WebElement> webElems = findElements(0);
					String elemVal;
					for (WebElement elem : webElems) {
						elemVal = elem.getAttribute("value");
						if (elemVal != null && elemVal.equals(value)) {
							elem.click();
							break;
						}
					}

					try {
						validateSelectedOption(value, displayValue, 2);
					} catch (Throwable th) {
						Assert.fail("Failed to select option with value '" + displayValue + "' in radio button group '"
								+ uiObject.getDisplayName() + "'.");
					}
					return;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to select option '" + displayValue + "' for radio group '" + uiObject.getDisplayName()
					+ "'.", th);
		}

	}

	@Override
	public void validateSelectedOption(String value, String displayValue, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					List<WebElement> webElems = findElements(0);
					String elemVal;
					boolean optionFound = false;
					for (WebElement elem : webElems) {
						elemVal = elem.getAttribute("value");
						if (elemVal != null && elemVal.equals(value) && elem.isSelected()) {
							optionFound = true;
							break;
						}
					}
					Assert.assertTrue(optionFound, "Radio button with value '" + displayValue + "' is not selected.");
					return;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate selected option '" + displayValue + "' for element '"
					+ uiObject.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void validateNotSelectedOptions(ItemMap<String, String> options, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					List<WebElement> webElems = findElements(0);
					String elemVal;
					LinkedHashMap<String, String> selectedOptions = new LinkedHashMap<String, String>();
					for (String optionValue : options.getItems().keySet()) {
						for (WebElement elem : webElems) {
							elemVal = elem.getAttribute("value");
							if (elemVal != null && elemVal.equals(optionValue) && elem.isSelected()) {
								selectedOptions.put(optionValue, options.getItems().get(optionValue));
							}
						}
					}

					if (selectedOptions.size() > 0) {
						Assert.fail("Radio button group '" + uiObject.getDisplayName()
								+ "' has some of the options selected. Selected options: " + selectedOptions);
					}
					return;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate not selected item for element '" + uiObject.getDisplayName() + "'.", th);
		}
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public TouchAction getNewMobileTouchAction() {
		return domObjValidator.getNewMobileTouchAction();
	}

	@Override
	public MultiTouchAction getNewMobileMultiTouchAction() {
		return domObjValidator.getNewMobileMultiTouchAction();
	}

	@Override
	public Actions getNewSeleniumActions() {
		return domObjValidator.getNewSeleniumActions();
	}

}
