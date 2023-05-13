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

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.AreaCoordinates;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.combobox.ComboBoxValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.PageScrollUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ComboBoxValidatorSD extends ComboBoxValidator {
	protected DOMObjectValidator domObjValidator;

	public ComboBoxValidatorSD(SmartAppDriver appDriver, ComboBoxSD uiObject, Region region) {
		super(appDriver, uiObject, region);
		domObjValidator = new DOMObjectValidator(appDriver,
				new DOMObject(uiObject.getType(), uiObject.getDisplayName(), uiObject.getPlatformLocators()), region);
	}

	public DOMObjectValidator getDOMObjectValidator() {
		return domObjValidator;
	}

	@Override
	public boolean isDisabled(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabled(maxIterationsToLocateElements);
	}

	@Override
	public ComboBoxValidatorSD validateDisabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public ComboBoxValidatorSD validateEnabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isEnabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}
	
	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements);
	}

	@Override
	public ComboBoxValidatorSD validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public ComboBoxValidatorSD validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertFalse(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	@Override
	public ComboBoxValidatorSD typeText(String textToType, NewTextLocation location, int maxIterationsToLocateElements) {
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
	public ComboBoxValidatorSD click(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD forceClick(int maxIterationsToLocateElements) {
		domObjValidator.forceClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ComboBoxValidatorSD doubleClick(int maxIterationsToLocateElements) {
		domObjValidator.doubleClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ComboBoxValidatorSD rightClick(int maxIterationsToLocateElements) {
		domObjValidator.rightClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ComboBoxValidatorSD clickAndHold(int maxIterationsToLocateElements) {
		domObjValidator.clickAndHold(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ComboBoxValidatorSD release(int maxIterationsToLocateElements) {
		domObjValidator.release(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD mouseHoverOver(int maxIterationsToLocateElements) {
		domObjValidator.mouseHoverOver(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ComboBoxValidatorSD performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyDown(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ComboBoxValidatorSD performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyUp(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ComboBoxValidatorSD performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyPressed(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ComboBoxValidatorSD scrollElementOnViewport(ScrollbarSI scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public ComboBoxValidatorSD validateSelectedItem(String expectedSelectedValue, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");

					String optionTextValue;
					boolean found = false;
					for (WebElement option : options) {
						optionTextValue = option.getText();
						if (optionTextValue != null && option.isSelected()
								&& matchTextValue(optionTextValue.trim(), expectedSelectedValue, validationMechanism)) {
							found = true;
							break;
						}
					}

					if (!found) {
						Assert.fail("Failed to find selected item '" + expectedSelectedValue + "' in ComboBox '"
								+ uiObject.getDisplayName() + "'.");
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
			Assert.fail("Failed to validate selected value '" + expectedSelectedValue + "' for element '"
					+ uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public String getSelectedItem(int maxIterationsToLocateElements) {
		WebElement selectElement = domObjValidator.findElement(maxIterationsToLocateElements);

		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0,
				"Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");

		String optionTextValue;
		for (WebElement option : options) {
			optionTextValue = option.getText();
			if (optionTextValue != null && option.isSelected()) {
				return optionTextValue;
			}
		}
		return null;
	}

	@Override
	public List<String> getSelectedItems(int maxIterationsToLocateElements) {
		WebElement selectElement = domObjValidator.findElement(maxIterationsToLocateElements);

		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0,
				"Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");

		List<String> selectedItems = new LinkedList<String>();
		String optionTextValue;
		for (WebElement option : options) {
			optionTextValue = option.getText();
			if (optionTextValue != null && option.isSelected()) {
				selectedItems.add(optionTextValue);
			}
		}
		return selectedItems;
	}

	@Override
	public ComboBoxValidatorSD selectFirstItem(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");

					PageScrollUtil.mouseClick(appDriver, options.get(0));
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to select first item on element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSD selectLastItem(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");

					PageScrollUtil.mouseClick(appDriver, options.get(options.size() - 1));
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to select last item on element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD selectItem(String itemName, int maxIterationsToLocateElements) {
		return selectItem(itemName, TextMatchMechanism.exactMatchWithExpectedValue, maxIterationsToLocateElements);
	}

	@Override
	public ComboBoxValidatorSD selectItem(String itemName, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements) {
		try {
			textMatchMechanism = (textMatchMechanism == null) ? TextMatchMechanism.exactMatchWithExpectedValue : textMatchMechanism;
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");

					String optionTextValue;
					boolean found = false;
					for (WebElement option : options) {
						optionTextValue = option.getText();
						if (StringUtil.isTextMatchedWithExpectedValue(optionTextValue, itemName, textMatchMechanism) && !option.isSelected()) {
							PageScrollUtil.mouseClick(appDriver, option);
							found = true;
							break;
						}
					}

					if (!found) {
						Assert.fail("Failed to find item '" + itemName + "' in ComboBox '" + uiObject.getDisplayName()
								+ "'.");
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
			Assert.fail("Failed to select item '" + itemName + "' on element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD deselectItem(String itemName, int maxIterationsToLocateElements) {
		return deselectItem(itemName, TextMatchMechanism.exactMatchWithExpectedValue, maxIterationsToLocateElements);
	}
	
	@Override
	public ComboBoxValidatorSD deselectItem(String itemName, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements) {
		try {
			textMatchMechanism = (textMatchMechanism == null) ? TextMatchMechanism.exactMatchWithExpectedValue : textMatchMechanism;
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");

					String optionTextValue;
					boolean found = false;
					for (WebElement option : options) {
						optionTextValue = option.getText();
						if (StringUtil.isTextMatchedWithExpectedValue(optionTextValue, itemName, textMatchMechanism) && option.isSelected()) {
							PageScrollUtil.mouseClick(appDriver, option);
							found = true;
							break;
						}
					}

					if (!found) {
						Assert.fail("Failed to find item '" + itemName + "' in ComboBox '" + uiObject.getDisplayName()
								+ "'.");
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
			Assert.fail("Failed to deselect item '" + itemName + "' on element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD deselectAllItems(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");

					for (WebElement option : options) {
						if (option.isSelected()) {
							PageScrollUtil.mouseClick(appDriver, option);
						}
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
			Assert.fail("Failed to deselect all items on element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD selectAllItems(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");

					for (WebElement option : options) {
						if (!option.isSelected()) {
							PageScrollUtil.mouseClick(appDriver, option);
						}
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
			Assert.fail("Failed to select all items on element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSD selectItems(ItemList<String> itemsToBeSelected, int maxIterationsToLocateElements) {
		for (String item : itemsToBeSelected.getItems()) {
			selectItem(item, maxIterationsToLocateElements);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD selectItems(ItemList<String> itemsToBeSelected, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements) {
		for (String item : itemsToBeSelected.getItems()) {
			selectItem(item, textMatchMechanism, maxIterationsToLocateElements);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD deselectItems(ItemList<String> itemsToBeDeselected, int maxIterationsToLocateElements) {
		for (String item : itemsToBeDeselected.getItems()) {
			deselectItem(item, maxIterationsToLocateElements);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSD deselectItems(ItemList<String> itemsToBeDeselected, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements) {
		for (String item : itemsToBeDeselected.getItems()) {
			deselectItem(item, textMatchMechanism, maxIterationsToLocateElements);
		}
		return this;
	}
	
	@Override
	public List<String> getAvailableItems(int maxIterationsToLocateElements) {
		List<String> items = new LinkedList<>();
		try {
			String optionTextValue;
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					
					for (WebElement option : options) {
						optionTextValue = option.getText();
						items.add(optionTextValue);
					}
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			// do nothing
		}
		
		return items;
	}

	@Override
	public ComboBoxValidatorSD validateItemsPresent(ItemList<String> items, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");

					String optionTextValue;
					for (String item : items.getItems()) {
						boolean found = false;
						for (WebElement option : options) {
							optionTextValue = option.getText();
							if (optionTextValue != null && matchTextValue(optionTextValue.trim(), item,
									TextMatchMechanism.exactMatchWithExpectedValue)) {
								found = true;
								break;
							}
						}

						if (!found) {
							Assert.fail("Failed to find item '" + item + "' in ComboBox '" + uiObject.getDisplayName()
									+ "'.");
						}
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
			Assert.fail("Failed to validate item presence for element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSD validateItemsNotPresent(ItemList<String> items, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");

					String optionTextValue;
					for (String item : items.getItems()) {
						boolean found = false;
						for (WebElement option : options) {
							optionTextValue = option.getText();
							if (optionTextValue != null && matchTextValue(optionTextValue.trim(), item,
									TextMatchMechanism.exactMatchWithExpectedValue)) {
								found = true;
								break;
							}
						}

						if (found) {
							Assert.fail(
									"Item '" + item + "' is present in ComboBox '" + uiObject.getDisplayName() + "'.");
						}
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
			Assert.fail("Failed to validate item absence for element '" + uiObject.getDisplayName() + "'.", th);
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
	public ComboBoxValidatorSD validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		domObjValidator.validateElementPresentWithinArea(coordinates, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public Actions getNewSeleniumActions() {
		return domObjValidator.getNewSeleniumActions();
	}
}
