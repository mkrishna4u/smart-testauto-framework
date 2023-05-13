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
import org.uitnet.testing.smartfwk.ui.core.objects.listbox.ListBoxValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.PageScrollUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ListBoxValidatorSD extends ListBoxValidator {
	protected DOMObjectValidator domObjValidator;

	public ListBoxValidatorSD(SmartAppDriver appDriver, ListBoxSD uiObject, Region region) {
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
	public boolean isEnabled(int maxIterationsToLocateElements) {
		return domObjValidator.isEnabled(maxIterationsToLocateElements);
	}

	@Override
	public ListBoxValidatorSD validateDisabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public ListBoxValidatorSD validateEnabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isEnabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements);
	}

	@Override
	public ListBoxValidatorSD validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public ListBoxValidatorSD validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertFalse(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	@Override
	public ListBoxValidatorSD typeText(String textToType, NewTextLocation location, int maxIterationsToLocateElements) {
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
	public ListBoxValidatorSD click(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public ListBoxValidatorSD forceClick(int maxIterationsToLocateElements) {
		domObjValidator.forceClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ListBoxValidatorSD doubleClick(int maxIterationsToLocateElements) {
		domObjValidator.doubleClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ListBoxValidatorSD rightClick(int maxIterationsToLocateElements) {
		domObjValidator.rightClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ListBoxValidatorSD clickAndHold(int maxIterationsToLocateElements) {
		domObjValidator.clickAndHold(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ListBoxValidatorSD release(int maxIterationsToLocateElements) {
		domObjValidator.release(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public ListBoxValidatorSD mouseHoverOver(int maxIterationsToLocateElements) {
		domObjValidator.mouseHoverOver(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public ListBoxValidatorSD performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyDown(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ListBoxValidatorSD performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyUp(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ListBoxValidatorSD performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyPressed(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ListBoxValidatorSD scrollElementOnViewport(ScrollbarSI scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public ListBoxValidatorSD validateSelectedItem(String expectedSelectedValue, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for Choices '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in Choices '" + uiObject.getDisplayName() + "'. Found 0 items.");

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
						Assert.fail("Failed to find selected item '" + expectedSelectedValue + "' in Choices '"
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
			Assert.fail("Failed to validate selected item '" + expectedSelectedValue + "' for element '"
					+ uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public String getSelectedItem(int maxIterationsToLocateElements) {
		WebElement selectElement = domObjValidator.findElement(maxIterationsToLocateElements);

		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for Choices '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0,
				"Failed to find items in Choices '" + uiObject.getDisplayName() + "'. Found 0 items.");

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
		Assert.assertNotNull(options, "Failed to find items for Choices '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0,
				"Failed to find items in Choices '" + uiObject.getDisplayName() + "'. Found 0 items.");

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
	public ListBoxValidatorSD selectFirstItem(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for Choices '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in Choices '" + uiObject.getDisplayName() + "'. Found 0 items.");

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
			Assert.fail("Failed to select first item for element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ListBoxValidatorSD selectLastItem(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for Choices '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in Choices '" + uiObject.getDisplayName() + "'. Found 0 items.");

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
			Assert.fail("Failed to select last item for element '" + uiObject.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public ListBoxValidatorSD selectItem(String itemName, int maxIterationsToLocateElements) {
		return selectItem(itemName, TextMatchMechanism.exactMatchWithExpectedValue, maxIterationsToLocateElements);
	}

	@Override
	public ListBoxValidatorSD selectItem(String itemName, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for Choices '" + uiObject.getDisplayName() + "'.");

					String optionTextValue;
					boolean found = false;

					// performAction(new KeyboardEvent(KeyboardEventName.kbKeyUp, Keys.CONTROL,
					// null), 0);
					for (WebElement option : options) {
						optionTextValue = option.getText();
						if (StringUtil.isTextMatchedWithExpectedValue(optionTextValue, itemName, textMatchMechanism)) {
							if (!option.isSelected()) {
								PageScrollUtil.mouseClick(appDriver, option);
							}
							found = true;
							// break;
						} else if (option.isSelected()) {
							PageScrollUtil.mouseClick(appDriver, option);
						}
					}

					if (!found) {
						Assert.fail("Failed to find item '" + itemName + "' in Choices '" + uiObject.getDisplayName()
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
			Assert.fail("Failed to select item '" + itemName + "' for element '" + uiObject.getDisplayName() + "'.",
					th);
		}
		return this;
	}
	
	@Override
	public ListBoxValidatorSD selectItems(ItemList<String> itemsToBeSelected, int maxIterationsToLocateElements) {
		return selectItems(itemsToBeSelected, TextMatchMechanism.exactMatchWithExpectedValue, maxIterationsToLocateElements);
	}

	@Override
	public ListBoxValidatorSD selectItems(ItemList<String> itemsToBeSelected, TextMatchMechanism textMatchMechaniism, int maxIterationsToLocateElements) {
		for (String item : itemsToBeSelected.getItems()) {
			selectItem(item, textMatchMechaniism, maxIterationsToLocateElements);
		}
		return this;
	}
	
	@Override
	public ListBoxValidatorSD deselectItem(String itemName, int maxIterationsToLocateElements) {
		return deselectItem(itemName, TextMatchMechanism.exactMatchWithExpectedValue, maxIterationsToLocateElements);
	}
	
	@Override
	public ListBoxValidatorSD deselectItem(String itemName, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ListBox '" + uiObject.getDisplayName() + "'.");

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
						Assert.fail("Failed to find item '" + itemName + "' in ListBox '" + uiObject.getDisplayName()
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
	public ListBoxValidatorSD deselectAllItems(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ListBox '" + uiObject.getDisplayName() + "'.");

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
	public ListBoxValidatorSD selectAllItems(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);
					PageScrollUtil.mouseClick(appDriver, selectElement);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for ListBox '" + uiObject.getDisplayName() + "'.");

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
	public ListBoxValidatorSD deselectItems(ItemList<String> itemsToBeDeselected, int maxIterationsToLocateElements) {
		for (String item : itemsToBeDeselected.getItems()) {
			deselectItem(item, maxIterationsToLocateElements);
		}
		return this;
	}
	
	@Override
	public ListBoxValidatorSD deselectItems(ItemList<String> itemsToBeDeselected, TextMatchMechanism textMatchMechanism, int maxIterationsToLocateElements) {
		for (String item : itemsToBeDeselected.getItems()) {
			deselectItem(item, textMatchMechanism, maxIterationsToLocateElements);
		}
		return this;
	}

	@Override
	public ListBoxValidatorSD validateItemsPresent(ItemList<String> items, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for Choices '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in Choices '" + uiObject.getDisplayName() + "'. Found 0 items.");

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
							Assert.fail("Failed to find item '" + item + "' in Choices '" + uiObject.getDisplayName()
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
	public ListBoxValidatorSD validateItemsNotPresent(ItemList<String> items, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement selectElement = domObjValidator.findElement(0);

					List<WebElement> options = selectElement.findElements(By.xpath("./option"));
					Assert.assertNotNull(options,
							"Failed to find items for Choices '" + uiObject.getDisplayName() + "'.");
					Assert.assertTrue(options.size() > 0,
							"Failed to find items in Choices '" + uiObject.getDisplayName() + "'. Found 0 items.");

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
									"Item '" + item + "' is present in Choices '" + uiObject.getDisplayName() + "'.");
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
			Assert.fail("Failed to validate eitem absence for element '" + uiObject.getDisplayName() + "'.", th);
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
	public ListBoxValidatorSD validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		domObjValidator.validateElementPresentWithinArea(coordinates, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public Actions getNewSeleniumActions() {
		return domObjValidator.getNewSeleniumActions();
	}
}
