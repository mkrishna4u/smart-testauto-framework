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
package smartfwk.testing.ui.standard.imgobj;

import java.util.List;

import org.openqa.selenium.Keys;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.ImageSection;
import smartfwk.testing.ui.core.commons.ItemList;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.ImageObject;
import smartfwk.testing.ui.core.objects.NewTextLocation;
import smartfwk.testing.ui.core.objects.listbox.ListBoxValidator;
import smartfwk.testing.ui.core.objects.scrollbar.Scrollbar;
import smartfwk.testing.ui.core.objects.validator.mechanisms.TextValidationMechanism;
import smartfwk.testing.ui.core.utils.ClipboardUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ListBoxValidatorSI extends ListBoxValidator {
	protected ListBoxSI listBoxObj;

	public ListBoxValidatorSI(WebBrowser browser, ListBoxSI uiObject, Region region) {
		super(browser, uiObject, region);
		this.listBoxObj = uiObject;
	}

	@Override
	@Deprecated
	public void validateDisabled(int numRetries) {
		Assert.fail("validateDisabled() API is not supported by ListBoxSI.");
	}

	@Override
	@Deprecated
	public void validateEnabled(int numRetries) {
		Assert.fail("validateEnabled() API is not supported by ListBoxSI.");
	}

	@Override
	public boolean isPresent(int numRetries) {
		Match m = findElementNoException(numRetries);
		return (m != null);
	}

	@Override
	public boolean isVisible(int numRetries) {
		return isPresent(numRetries);
	}

	@Override
	public void click(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	public void click(ImageSection imageSection, int numRetries) {
		try {
			Match match = findElement(numRetries);
			getImageSection(match, imageSection).click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void doubleClick(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	public void doubleClick(ImageSection imageSection, int numRetries) {
		try {
			Match match = findElement(numRetries);
			getImageSection(match, imageSection).doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void rightClick(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	public void rightClick(ImageSection imageSection, int numRetries) {
		try {
			Match match = findElement(numRetries);
			getImageSection(match, imageSection).rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void clickAndHold(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void release(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void performKeyDown(Keys keys, int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.click();
			match.keyDown(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyDown on ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}

	}

	@Override
	public void performKeyUp(Keys keys, int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.click();
			match.keyUp(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyUp ('" + seleniumToSikuliKeyConverter(keys) + "') on ListBox '"
					+ listBoxObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void performKeyPressed(Keys keys, int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.click();
			match.type(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyPressed ('" + seleniumToSikuliKeyConverter(keys) + "') on ListBox '"
					+ listBoxObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void typeText(String text, NewTextLocation location, int numRetries) {
		Match match = findElement(numRetries);
		try {
			match.click();
			switch (location) {
			case start:
				match.type(Key.HOME);
				break;
			case end:
				match.type(Key.END);
				break;
			case replace:
				match.type("a", KeyModifier.CTRL);
				break;
			}

			match.type(text);
		} catch (Throwable th) {
			Assert.fail("Fail to type text '" + text + "' in ListBox '" + listBoxObj.getDisplayName() + "'.");
		}
	}

	@Override
	public ListBoxValidatorSI scrollElementOnViewport(Scrollbar scrollbar) {
		// TODO
		return this;
	}

	@Override
	public Match findElement(int numRetries) {
		Match match = null;
		for (int i = 0; i <= numRetries; i++) {
			try {
				Region region = listBoxObj.getLocation().getRegionOfImageObject(browser, listBoxObj.getWidth(),
						listBoxObj.getHeight());
				Assert.assertNotNull(region, "Failed to find ListBox '" + listBoxObj.getDisplayName() + "'.");
				match = new Match(region, 1);
				break;
			} catch (Throwable th) {
				if (i == numRetries) {
					Assert.fail("Unable to find ListBox '" + listBoxObj.getDisplayName()
							+ "'. Reason timeout(waited for " + (numRetries * 2) + " seconds).", th);
					break;
				}
			}
			browser.waitForSeconds(2);
		}
		return match;
	}

	@Override
	public Match findElementNoException(int numRetries) {
		Match match = null;
		try {
			match = findElement(numRetries);
		} catch (Throwable th) {
			// Do nothing
		}
		return match;
	}

	@Override
	public List<Match> findElements(int numRetries) {
		Assert.fail("findElements() api for ListBoxSI element is not implemented.");
		return null;
	}

	public void dragAndDrop(ImageObject target, Region targetRegion, int numRetries) {
		try {
			Match sourceElem = findElement(numRetries);
			Match targetElem = target.getValidator(browser, targetRegion).findElement(numRetries);

			Assert.assertNotNull(sourceElem, "Failed to find ListBox '" + listBoxObj.getDisplayName() + "'.");
			Assert.assertNotNull(targetElem, "Failed to find element '" + target.getDisplayName() + "'.");

			sourceElem.drag(targetElem);
			sourceElem.dropAt(targetElem);
		} catch (Throwable th) {
			Assert.fail("Failed to perform dragAndDrop from source '" + listBoxObj.getDisplayName() + "' to target '"
					+ target.getDisplayName() + "'.", th);
		}
	}

	protected Location getImageSection(Match imageMatch, ImageSection imageSection) {
		switch (imageSection) {
		case topLeft:
			return imageMatch.getTopLeft();
		case topRight:
			return imageMatch.getTopRight();
		case bottomLeft:
			return imageMatch.getBottomLeft();
		case bottomRight:
			return imageMatch.getBottomRight();
		case center:
			return imageMatch.getCenter();
		}
		return null;
	}

	@Override
	public void validateSelectedItem(String expectedValue, TextValidationMechanism validationMechanism,
			int numRetries) {
		if (listBoxObj.isDisabled()) {
			Match match = findElement(numRetries);
			validateTextValue(match.text(), expectedValue, validationMechanism);
		} else {
			validateTextValue(getSelectedItem(numRetries), expectedValue, validationMechanism);
		}
	}

	/**
	 * Used to return value using clipboard method.
	 */
	@Override
	public String getSelectedItem(int numRetries) {
		Match match = findElement(numRetries);
		if (listBoxObj.isDisabled()) {
			return match.text();
		} else {
			match.click();

			match.type("ac", KeyModifier.CTRL);

			String contents = ClipboardUtil.getContents();
			ClipboardUtil.clearContents();

			match.click();
			return contents;
		}

	}

	@Override
	public List<String> getSelectedItems(int numRetries) {
		Assert.fail("getSelectedItems() API is not implemented.");
		return null;
	}

	@Override
	public void selectFirstItem(int numRetries) {
		Assert.fail("selectFirstItem() API is not implemented.");
	}

	@Override
	public void selectLastItem(int numRetries) {
		Assert.fail("selectLastItem() API is not implemented.");
	}

	/**
	 * It just search the text in pull down menu visible area if present then
	 * click on it to select that.
	 */
	@Override
	public void selectItem(String itemName, int numRetries) {
		Match match = findElement(numRetries);

		try {
			Match menuItemMatch = match.findBest(itemName);
			Assert.assertNotNull(menuItemMatch,
					"Failed to find item '" + itemName + "' in ListBox '" + listBoxObj.getDisplayName() + "'.");
			menuItemMatch.click();
		} catch (Throwable th) {
			Assert.fail("Failed to find item '" + itemName + "' in ListBox '" + listBoxObj.getDisplayName() + "'.", th);
		}
	}

	/**
	 * It just search the image item in pull down menu visible area if present
	 * then click on it to select that.
	 */
	public void selectItemByImage(String imageItem, int numRetries) {
		Match match = findElement(numRetries);

		try {
			Match menuItemMatch = match.findBest(imageItem);
			Assert.assertNotNull(menuItemMatch,
					"Failed to find item '" + imageItem + "' in ListBox '" + listBoxObj.getDisplayName() + "'.");
			menuItemMatch.click();
		} catch (Throwable th) {
			Assert.fail("Failed to find item '" + imageItem + "' in pull down menu of ListBox '"
					+ listBoxObj.getDisplayName() + "'.", th);
		}
	}

	/**
	 * It selects only the visible item in the pull down menu.
	 */
	@Override
	public void selectItems(ItemList<String> itemsToBeSelected, int numRetries) {
		Match match = findElement(numRetries);

		String currentItemName = "";
		try {
			for (String itemName : itemsToBeSelected.getItems()) {
				currentItemName = itemName;
				Match menuItemMatch = match.find(itemName);
				Assert.assertNotNull(menuItemMatch,
						"Failed to find item '" + itemName + "' in ListBox '" + listBoxObj.getDisplayName() + "'.");
				menuItemMatch.keyDown(Key.CTRL);
				menuItemMatch.click();
				menuItemMatch.keyUp(Key.CTRL);
			}

		} catch (Throwable th) {
			Assert.fail(
					"Failed to find item '" + currentItemName + "' in ListBox '" + listBoxObj.getDisplayName() + "'.");
		}
	}

	/**
	 * It selects only the visible image items in the pull down menu.
	 */
	public void selectItemsByImage(ItemList<String> imageItemsToBeSelected, int numRetries) {
		Match match = findElement(numRetries);

		String currentImageItem = "";
		try {
			for (String imageItem : imageItemsToBeSelected.getItems()) {
				currentImageItem = imageItem;
				Match menuItemMatch = match.find(imageItem);
				Assert.assertNotNull(menuItemMatch,
						"Failed to find item '" + imageItem + "' in ListBox '" + listBoxObj.getDisplayName() + "'.");
				menuItemMatch.keyDown(Key.CTRL);
				menuItemMatch.click();
				menuItemMatch.keyUp(Key.CTRL);
			}

		} catch (Throwable th) {
			Assert.fail(
					"Failed to find item '" + currentImageItem + "' in ListBox '" + listBoxObj.getDisplayName() + "'.");
		}
	}

	/**
	 * It only checks the item in visible area of pull down menu.
	 */
	@Override
	public void validateItemsPresent(ItemList<String> items, int numRetries) {
		Match match = findElement(numRetries);
		String currentItemName = "";
		try {
			for (String itemName : items.getItems()) {
				currentItemName = itemName;
				Match menuItemMatch = match.find(itemName);
				if (menuItemMatch == null) {
					throw new FindFailed("Found no match.");
				}
			}
		} catch (Throwable th) {
			Assert.fail(
					"Failed to find item '" + currentItemName + "' in ListBox '" + listBoxObj.getDisplayName() + "'.",
					th);
		}
	}

	/**
	 * It only checks the image items in visible area of pull down menu.
	 */
	public void validateItemsPresentByImage(ItemList<String> imageItems, int numRetries) {
		Match match = findElement(numRetries);

		String currentImageItem = "";
		try {
			for (String imageItem : imageItems.getItems()) {
				currentImageItem = imageItem;
				Match menuItemMatch = match.find(imageItem);
				if (menuItemMatch == null) {
					throw new FindFailed("Found no match.");
				}
			}
		} catch (Throwable th) {
			Assert.fail(
					"Failed to find item '" + currentImageItem + "' in ListBox '" + listBoxObj.getDisplayName() + "'.",
					th);
		}
	}

	@Override
	public void validateItemsNotPresent(ItemList<String> items, int numRetries) {
		Match match = findElement(numRetries);
		for (String itemName : items.getItems()) {
			try {
				Match menuItemMatch = match.find(itemName);
				Assert.assertNull(menuItemMatch,
						"Item '" + itemName + "' in ListBox '" + listBoxObj.getDisplayName() + "' is already present.");
			} catch (FindFailed th) {
				// do not do anything here
			}
		}

	}

	public void validateItemsNotPresentByImage(ItemList<String> imageItems, int numRetries) {
		Match match = findElement(numRetries);
		for (String imageItem : imageItems.getItems()) {
			try {
				Match menuItemMatch = match.find(imageItem);
				Assert.assertNull(menuItemMatch, "Item '" + imageItem + "' in ListBox '" + listBoxObj.getDisplayName()
						+ "' is already present.");
			} catch (FindFailed th) {
				// do not do anything here
			}
		}

	}

}
