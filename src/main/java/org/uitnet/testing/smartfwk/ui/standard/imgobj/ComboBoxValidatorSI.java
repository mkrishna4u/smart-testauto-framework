/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
package org.uitnet.testing.smartfwk.ui.standard.imgobj;

import java.io.File;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Button;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.AreaCoordinates;
import org.uitnet.testing.smartfwk.ui.core.commons.ImageSection;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.objects.ImageObject;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.combobox.ComboBoxValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.ClipboardUtil;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ComboBoxValidatorSI extends ComboBoxValidator {
	protected ComboBoxSI comboBoxObj;

	public ComboBoxValidatorSI(SmartAppDriver appDriver, ComboBoxSI uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.comboBoxObj = uiObject;
	}

	@Override
	@Deprecated
	public ComboBoxValidatorSI validateDisabled(int maxIterationsToLocateElements) {
		Assert.fail("validateDisabled() API is not supported by ComboBoxSI.");
		return this;
	}

	@Override
	@Deprecated
	public ComboBoxValidatorSI validateEnabled(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabled() API is not supported by ComboBoxSI.");
		return this;
	}

	@Override
	public boolean isPresent(int maxIterationsToLocateElements) {
		Match m = findElementNoException(maxIterationsToLocateElements);
		return (m != null);
	}

	@Override
	public boolean isNotPresent(int maxIterationsToLocateElements) {
		Match m = findElementNoException(maxIterationsToLocateElements);
		return (m == null);
	}
	
	@Override
	public boolean isVisible(int maxIterationsToLocateElements) {
		return isPresent(maxIterationsToLocateElements);
	}

	@Override
	public boolean isHidden(int maxIterationsToLocateElements) {
		return isHidden(maxIterationsToLocateElements);
	}

	@Override
	public ComboBoxValidatorSI click(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSI forceClick(int maxIterationsToLocateElements) {
		return click(maxIterationsToLocateElements);
	}

	public ComboBoxValidatorSI click(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI doubleClick(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	public ComboBoxValidatorSI doubleClick(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI rightClick(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	public ComboBoxValidatorSI rightClick(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI clickAndHold(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI release(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public ComboBoxValidatorSI mouseHoverOver(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseMove();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse hoverover on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyDown(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyDown on ComboBox '" + comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyUp(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyUp ('" + seleniumToSikuliKeyConverter(keys) + "') on ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.type(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyPressed ('" + seleniumToSikuliKeyConverter(keys) + "') on ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI typeText(String text, NewTextLocation location, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
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
			Assert.fail("Fail to type text '" + text + "' in ComboBox '" + comboBoxObj.getDisplayName() + "'.");
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI scrollElementOnViewport(ScrollbarSI scrollbar) {
		// TODO
		return this;
	}

	@Override
	public Match findElement(int maxIterationsToLocateElements) {
		Match match = null;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				Region region = comboBoxObj.getLocation().getRegionOfImageObject(appDriver,
						comboBoxObj.getLeftSideImage(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()),
						comboBoxObj.getRightSideImage(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(region, "Failed to find ComboBox '" + comboBoxObj.getDisplayName() + "'.");
				match = new Match(region, 1);
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					Assert.fail("Unable to find ComboBox '" + comboBoxObj.getDisplayName()
							+ "'. Reason timeout(waited for " + (maxIterationsToLocateElements * 2) + " seconds).", th);
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return match;
	}

	@Override
	public Match findElementNoException(int maxIterationsToLocateElements) {
		Match match = null;
		try {
			match = findElement(maxIterationsToLocateElements);
		} catch (Throwable th) {
			// Do nothing
		}
		return match;
	}

	@Override
	public List<Match> findElements(int maxIterationsToLocateElements) {
		Assert.fail("findElements() api for ComboBoxSI element is not implemented.");
		return null;
	}

	public ComboBoxValidatorSI dragAndDrop(ImageObject target, Region targetRegion, int maxIterationsToLocateElements) {
		try {
			Match sourceElem = findElement(maxIterationsToLocateElements);
			Match targetElem = target.getValidator(appDriver, targetRegion).findElement(maxIterationsToLocateElements);

			Assert.assertNotNull(sourceElem, "Failed to find ComboBox '" + comboBoxObj.getDisplayName() + "'.");
			Assert.assertNotNull(targetElem, "Failed to find element '" + target.getDisplayName() + "'.");

			sourceElem.drag(targetElem);
			sourceElem.dropAt(targetElem);
		} catch (Throwable th) {
			Assert.fail("Failed to perform dragAndDrop from source '" + comboBoxObj.getDisplayName() + "' to target '"
					+ target.getDisplayName() + "'.", th);
		}
		return this;
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
	public ComboBoxValidatorSI validateSelectedItem(String expectedValue, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements) {
		if (comboBoxObj.isDisabled() || comboBoxObj.isReadOnly()) {
			Match match = findElement(maxIterationsToLocateElements);
			validateTextValue(match.text(), expectedValue, validationMechanism);
		} else {
			validateTextValue(getSelectedItem(maxIterationsToLocateElements), expectedValue, validationMechanism);
		}
		return this;
	}

	/**
	 * Used to return value using clipboard method.
	 */
	@Override
	public String getSelectedItem(int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		if (comboBoxObj.isDisabled() || comboBoxObj.isReadOnly()) {
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
	public List<String> getSelectedItems(int maxIterationsToLocateElements) {
		Assert.fail("getSelectedItems() API is not implemented.");
		return null;
	}

	@Override
	public ComboBoxValidatorSI selectFirstItem(int maxIterationsToLocateElements) {
		Assert.fail("selectFirstItem() API is not implemented.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI selectLastItem(int maxIterationsToLocateElements) {
		Assert.fail("selectLastItem() API is not implemented.");
		return this;
	}

	/**
	 * It just search the text in pull down menu visible area if present then click
	 * on it to select that.
	 */
	@Override
	public ComboBoxValidatorSI selectItem(String itemName, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		Region pullDownMenuRegion = calculatePullDownMenuRegion(match);

		try {
			match.click();
			ImageObject menuItemObj = new ImageObject(UIObjectType.menuItem, comboBoxObj.getDisplayName() + "-MenuItem",
					itemName);
			Match menuItemMatch = menuItemObj.getValidator(appDriver, pullDownMenuRegion).findElement(5);

			Assert.assertNotNull(menuItemMatch, "Failed to find item '" + itemName + "' in pull down menu of ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.");
			menuItemMatch.click();
		} catch (Throwable th) {
			match.click();
			Assert.fail("Failed to find item '" + itemName + "' in pull down menu of ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	/**
	 * It just search the image item in pull down menu visible area if present then
	 * click on it to select that.
	 */
	public ComboBoxValidatorSI selectItemByImage(String imageItem, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		Region pullDownMenuRegion = calculatePullDownMenuRegion(match);

		try {
			match.click();
			ImageObject menuItemObj = new ImageObject(UIObjectType.menuItem, comboBoxObj.getDisplayName() + "-MenuItem",
					imageItem);
			Match menuItemMatch = menuItemObj.getValidator(appDriver, pullDownMenuRegion).findElement(5);
			Assert.assertNotNull(menuItemMatch, "Failed to find item '" + imageItem
					+ "' in pull down menu of ComboBox '" + comboBoxObj.getDisplayName() + "'.");
			menuItemMatch.click();
		} catch (Throwable th) {
			match.click();
			Assert.fail("Failed to find item '" + imageItem + "' in pull down menu of ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	protected Region calculatePullDownMenuRegion(Match match) {
		int pullDownX1 = 0, pullDownY1 = 0, pullDownW = 0, pullDownH = 0;
		if (comboBoxObj.getPullDownMenuInfo().getWidth() < 1) {
			pullDownW = match.getW() + 10;
		} else {
			pullDownW = comboBoxObj.getPullDownMenuInfo().getWidth();
		}

		if (comboBoxObj.getPullDownMenuInfo().getHeight() < 1) {
			pullDownH = 20;
		} else {
			pullDownH = comboBoxObj.getPullDownMenuInfo().getHeight();
		}

		switch (comboBoxObj.getPullDownMenuInfo().getLocation()) {
		case bottom:
			pullDownX1 = match.getX() - 7;
			pullDownY1 = match.getY() + match.getH();
			break;
		case top:
			pullDownX1 = match.getX() - 7;
			pullDownY1 = match.getY() - pullDownH;
			break;
		}

		Region pullDownMenuRegion = new Region(pullDownX1, pullDownY1, pullDownW, pullDownH);
		return pullDownMenuRegion;
	}

	/**
	 * It selects only the visible item in the pull down menu.
	 */
	@Override
	public ComboBoxValidatorSI selectItems(ItemList<String> itemsToBeSelected, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		Region pullDownMenuRegion = calculatePullDownMenuRegion(match);

		String currentItemName = "";
		try {
			match.click();
			for (String itemName : itemsToBeSelected.getItems()) {
				currentItemName = itemName;
				ImageObject menuItemObj = new ImageObject(UIObjectType.menuItem,
						comboBoxObj.getDisplayName() + "-MenuItem", itemName);
				Match menuItemMatch = menuItemObj.getValidator(appDriver, pullDownMenuRegion).findElement(2);
				// Match menuItemMatch = pullDownMenuRegion.find(itemName);
				Assert.assertNotNull(menuItemMatch, "Failed to find item '" + itemName
						+ "' in pull down menu of ComboBox '" + comboBoxObj.getDisplayName() + "'.");
				menuItemMatch.click();
			}

		} catch (Throwable th) {
			Assert.fail("Failed to find item '" + currentItemName + "' in pull down menu of ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.");
		}
		return this;
	}

	/**
	 * It selects only the visible image items in the pull down menu.
	 */
	public ComboBoxValidatorSI selectItemsByImage(ItemList<String> imageItemsToBeSelected, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		Region pullDownMenuRegion = calculatePullDownMenuRegion(match);

		String currentImageItem = "";
		try {
			match.click();
			for (String imageItem : imageItemsToBeSelected.getItems()) {
				currentImageItem = imageItem;
				ImageObject menuItemObj = new ImageObject(UIObjectType.menuItem,
						comboBoxObj.getDisplayName() + "-MenuItem", imageItem);
				Match menuItemMatch = menuItemObj.getValidator(appDriver, pullDownMenuRegion).findElement(2);
				Assert.assertNotNull(menuItemMatch, "Failed to find item '" + imageItem
						+ "' in pull down menu of ComboBox '" + comboBoxObj.getDisplayName() + "'.");
				menuItemMatch.click();
			}

		} catch (Throwable th) {
			Assert.fail("Failed to find item '" + currentImageItem + "' in pull down menu of ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.");
		}
		return this;
	}

	/**
	 * It only checks the item in visible area of pull down menu.
	 */
	@Override
	public ComboBoxValidatorSI validateItemsPresent(ItemList<String> items, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		Region pullDownMenuRegion = calculatePullDownMenuRegion(match);

		String currentItemName = "";
		match.click();
		try {
			for (String itemName : items.getItems()) {
				currentItemName = itemName;
				ImageObject menuItemObj = new ImageObject(UIObjectType.menuItem,
						comboBoxObj.getDisplayName() + "-MenuItem", itemName);
				Match menuItemMatch = menuItemObj.getValidator(appDriver, pullDownMenuRegion).findElement(2);
				if (menuItemMatch == null) {
					throw new FindFailed("Found no match.");
				}
			}
			match.click();
		} catch (Throwable th) {
			match.click();
			Assert.fail("Failed to find item '" + currentItemName + "' in pull down menu of ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	/**
	 * It only checks the image items in visible area of pull down menu.
	 */
	public ComboBoxValidatorSI validateItemsPresentByImage(ItemList<String> imageItems, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		Region pullDownMenuRegion = calculatePullDownMenuRegion(match);

		String currentImageItem = "";
		match.click();
		try {
			for (String imageItem : imageItems.getItems()) {
				currentImageItem = imageItem;
				ImageObject menuItemObj = new ImageObject(UIObjectType.menuItem,
						comboBoxObj.getDisplayName() + "-MenuItem", imageItem);
				Match menuItemMatch = menuItemObj.getValidator(appDriver, pullDownMenuRegion).findElement(5);
				if (menuItemMatch == null) {
					throw new FindFailed("Found no match.");
				}
			}
			match.click();
		} catch (Throwable th) {
			match.click();
			Assert.fail("Failed to find item '" + currentImageItem + "' in pull down menu of ComboBox '"
					+ comboBoxObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ComboBoxValidatorSI validateItemsNotPresent(ItemList<String> items, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		Region pullDownMenuRegion = calculatePullDownMenuRegion(match);

		match.click();
		for (String itemName : items.getItems()) {
			try {
				Match menuItemMatch = pullDownMenuRegion
						.find(TestConfigManager.getInstance().getSikuliResourcesDir() + File.separator + itemName);
				Assert.assertNull(menuItemMatch, "Item '" + itemName + "' in pull down menu of ComboBox '"
						+ comboBoxObj.getDisplayName() + "' is already present.");
			} catch (FindFailed th) {
				// do not do anything here
			}
		}
		return this;
	}

	public ComboBoxValidatorSI validateItemsNotPresentByImage(ItemList<String> imageItems, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		Region pullDownMenuRegion = calculatePullDownMenuRegion(match);

		match.click();
		for (String imageItem : imageItems.getItems()) {
			try {
				Match menuItemMatch = pullDownMenuRegion
						.find(TestConfigManager.getInstance().getSikuliResourcesDir() + File.separator + imageItem);
				Assert.assertNull(menuItemMatch, "Item '" + imageItem + "' in pull down menu of ComboBox '"
						+ comboBoxObj.getDisplayName() + "' is already present.");
			} catch (FindFailed th) {
				// do not do anything here
			}
		}
		return this;
	}

	@Override
	@Deprecated
	public boolean isDisabled(int maxIterationsToLocateElements) {
		Assert.fail("isDisabled() API is not supported by ComboBoxSI component.");
		return false;
	}

	@Override
	@Deprecated
	public boolean isEnabled(int maxIterationsToLocateElements) {
		Assert.fail("isEnabled() API is not supported by ComboBoxSI component.");
		return false;
	}

	@Override
	@Deprecated
	public Actions getNewSeleniumActions() {
		Assert.fail("getNewSeleniumActions() API is not supported by ComboBoxSI component.");
		return null;
	}

	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("isDisabledButNotReadonly() API is not supported by ComboBoxSI component.");
		return false;
	}

	@Override
	public ComboBoxValidatorSI validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("validateDisabledButNotReadonly() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabledButNotReadonly() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI selectAllItems(int maxIterationsToLocateElements) {
		Assert.fail("selectAllItems() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI deselectItem(String itemName, int maxIterationsToLocateElements) {
		Assert.fail("deselectItem() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI deselectAllItems(int maxIterationsToLocateElements) {
		Assert.fail("deselectAllItems() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI deselectItems(ItemList<String> itemsToBeDeselected, int maxIterationsToLocateElements) {
		Assert.fail("deselectItems() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI selectItem(String itemName, TextMatchMechanism textMatchMechanism,
			int maxIterationsToLocateElements) {
		Assert.fail("selectItem() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI selectItems(ItemList<String> itemsToBeSelected, TextMatchMechanism textMatchMechanism,
			int maxIterationsToLocateElements) {
		Assert.fail("selectItems() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI deselectItem(String itemName, TextMatchMechanism textMatchMechanism,
			int maxIterationsToLocateElements) {
		Assert.fail("deselectItem() API is not supported by ComboBoxSI component.");
		return this;
	}

	@Override
	public ComboBoxValidatorSI deselectItems(ItemList<String> itemsToBeDeselected, TextMatchMechanism textMatchMechanism,
			int maxIterationsToLocateElements) {
		Assert.fail("deselectItems() API is not supported by ComboBoxSI component.");
		return this;
	}
	
	@Override
	public List<String> getAvailableItems(int maxIterationsToLocateElements) {
		Assert.fail("getAvailableItems() API is not supported by ComboBoxSI component.");
		return null;
	}
	
	@Override
	public ComboBoxValidatorSI validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		if(!(match.x >= coordinates.getX1() && match.y >= coordinates.getY1() &&  
				(match.x + match.w) <= coordinates.getX2() && (match.y + match.h) <= coordinates.getY2())) {
			Assert.fail("Element '" + comboBoxObj.getDisplayName() + "' is not within the specified area [x1=" + coordinates.getX1() 
			+ ", y1=" + coordinates.getY1() + ", x2=" + coordinates.getX2() + ", y2=" + coordinates.getY2() + "]."
			+ " Actual Coordinates: [x1=" + match.x + ", y1=" + match.y + ", x2=" + (match.x  + match.w) 
			+ ", y2=" + (match.y + match.h) + "].");
		}
		return this;
	}
}
