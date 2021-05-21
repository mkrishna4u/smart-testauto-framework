/*
 * SmartTestAutoFwk
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

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.sikuli.script.Region;
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.ItemList;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.DOMObject;
import smartfwk.testing.ui.core.objects.DOMObjectValidator;
import smartfwk.testing.ui.core.objects.NewTextLocation;
import smartfwk.testing.ui.core.objects.combobox.ComboBoxValidator;
import smartfwk.testing.ui.core.objects.scrollbar.Scrollbar;
import smartfwk.testing.ui.core.objects.validator.mechanisms.TextValidationMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ComboBoxValidatorSD extends ComboBoxValidator {
	protected DOMObjectValidator domObjValidator;
	
	public ComboBoxValidatorSD(WebBrowser browser, ComboBoxSD uiObject, Region region) {
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
	public ComboBoxValidatorSD scrollElementOnViewport(Scrollbar scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public void validateSelectedItem(String expectedSelectedValue, TextValidationMechanism validationMechanism,
			int numRetries) {
		WebElement selectElement = domObjValidator.findElement(numRetries);
		
		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0, "Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");
		
		String optionTextValue;
		boolean found = false;
		for(WebElement option : options) {
			optionTextValue = option.getText();
			if(optionTextValue != null && option.isSelected() &&
					matchTextValue(optionTextValue.trim(), expectedSelectedValue, validationMechanism)) {				
				found = true;
				break;
			}
		}
		
		if(!found) {
			Assert.fail("Failed to find selected item '" + expectedSelectedValue + "' in ComboBox '" + uiObject.getDisplayName() + "'.");
		}		
	}

	@Override
	public String getSelectedItem(int numRetries) {
		WebElement selectElement = domObjValidator.findElement(numRetries);
		
		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0, "Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");
		
		String optionTextValue;		
		for(WebElement option : options) {
			optionTextValue = option.getText();
			if(optionTextValue != null && option.isSelected()) {			
				return optionTextValue;
			}
		}
		return null;
	}
	
	@Override
	public List<String> getSelectedItems(int numRetries) {
		WebElement selectElement = domObjValidator.findElement(numRetries);
		
		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0, "Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");
		
		List<String> selectedItems = new LinkedList<String>();
		String optionTextValue;		
		for(WebElement option : options) {
			optionTextValue = option.getText();
			if(optionTextValue != null && option.isSelected()) {			
				selectedItems.add(optionTextValue);
			}
		}
		return selectedItems;
	}

	@Override
	public void selectFirstItem(int numRetries) {
		WebElement selectElement = domObjValidator.findElement(numRetries);
		selectElement.click();
		
		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0, "Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");
		
		options.get(0).click();
	}

	@Override
	public void selectLastItem(int numRetries) {
		WebElement selectElement = domObjValidator.findElement(numRetries);
		selectElement.click();
		
		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0, "Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");
		
		options.get(options.size() - 1).click();
	}

	@Override
	public void selectItem(String itemName, int numRetries) {
		WebElement selectElement = domObjValidator.findElement(numRetries);
		selectElement.click();
		
		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		
		String optionTextValue;
		boolean found = false;
		for(WebElement option : options) {
			optionTextValue = option.getText();
			if(optionTextValue != null && itemName.equals(optionTextValue.trim())) {
				option.click();
				found = true;
				break;
			}
		}
		
		if(!found) {
			Assert.fail("Failed to find item '" + itemName + "' in ComboBox '" + uiObject.getDisplayName() + "'.");
		}
	}

	@Override
	public void selectItems(ItemList<String> itemsToBeSelected, int numRetries) {
		for(String item : itemsToBeSelected.getItems()) {
			selectItem(item, numRetries);
		}
	}

	@Override
	public void validateItemsPresent(ItemList<String> items, int numRetries) {
		WebElement selectElement = domObjValidator.findElement(numRetries);
		
		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0, "Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");
		
		String optionTextValue;
		for (String item : items.getItems()) {
			boolean found = false;
			for (WebElement option : options) {
				optionTextValue = option.getText();
				if (optionTextValue != null
						&& matchTextValue(optionTextValue.trim(), item, TextValidationMechanism.exactMatchWithExpectedValue)) {
					found = true;
					break;
				}
			}
			
			if(!found) {
				Assert.fail("Failed to find item '" + item + "' in ComboBox '" + uiObject.getDisplayName() + "'.");
			}	
		}
	}

	@Override
	public void validateItemsNotPresent(ItemList<String> items, int numRetries) {
		WebElement selectElement = domObjValidator.findElement(numRetries);
		
		List<WebElement> options = selectElement.findElements(By.xpath("./option"));
		Assert.assertNotNull(options, "Failed to find items for ComboBox '" + uiObject.getDisplayName() + "'.");
		Assert.assertTrue(options.size() > 0, "Failed to find items in ComboBox '" + uiObject.getDisplayName() + "'. Found 0 items.");
		
		String optionTextValue;
		for (String item : items.getItems()) {
			boolean found = false;
			for (WebElement option : options) {
				optionTextValue = option.getText();
				if (optionTextValue != null
						&& matchTextValue(optionTextValue.trim(), item, TextValidationMechanism.exactMatchWithExpectedValue)) {
					found = true;
					break;
				}
			}
			
			if(found) {
				Assert.fail("Item '" + item + "' is present in ComboBox '" + uiObject.getDisplayName() + "'.");
			}	
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
