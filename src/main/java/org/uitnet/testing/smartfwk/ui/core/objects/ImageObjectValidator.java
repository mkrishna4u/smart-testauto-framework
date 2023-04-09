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
package org.uitnet.testing.smartfwk.ui.core.objects;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Button;
import org.sikuli.script.Key;
import org.sikuli.script.KeyModifier;
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.AreaCoordinates;
import org.uitnet.testing.smartfwk.ui.core.commons.ImageSection;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.ClipboardUtil;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ImageObjectValidator extends UIObjectValidator {
	protected ImageObject imgLocator;

	public ImageObjectValidator(SmartAppDriver appDriver, ImageObject locator, Region region) {
		super(appDriver, locator, region);
		this.imgLocator = locator;
	}

	@Override
	public ImageObject getUIObject() {
		return imgLocator;
	}

	/**
	 * Finds first element with polling and it polls after 2 seconds for
	 * maxIterationsToLocateElements times.
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	@Override
	public Match findElement(int maxIterationsToLocateElements) {
		Match match = null;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				match = region.find(TestConfigManager.getInstance().getSikuliResourcesDir() + File.separator + imgLocator.getImage(appDriver.getAppConfig().getTestPlatformType(),
						appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(match, "Unable to find element '" + imgLocator.getDisplayName() + "'.");
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					Assert.fail("Unable to find element '" + imgLocator.getDisplayName()
							+ "'. Reason timeout(waited for " + (maxIterationsToLocateElements * 2) + " seconds).", th);
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return match;
	}

	/**
	 * Finds first element with polling and it polls after 2 seconds for
	 * maxIterationsToLocateElements times. It does not throw any exception
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	@Override
	public Match findElementNoException(int maxIterationsToLocateElements) {
		Match match = null;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				match = region.find(TestConfigManager.getInstance().getSikuliResourcesDir() + File.separator + imgLocator.getImage(appDriver.getAppConfig().getTestPlatformType(),
						appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				if (match != null) {
					break;
				}
			} catch (Throwable th) {
				match = null;
				if (i == maxIterationsToLocateElements) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return match;
	}

	/**
	 * This returns all the elements based on the locator. It waits for the
	 * configured timeout if the element is not present. Performs polling
	 * maxIterationsToLocateElements times.
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	@Override
	public List<Match> findElements(int maxIterationsToLocateElements) {
		List<Match> list = new LinkedList<Match>();
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				Iterator<Match> matches = region
						.findAll(TestConfigManager.getInstance().getSikuliResourcesDir() + File.separator + imgLocator.getImage(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));

				Assert.assertNotNull(matches, "Unable to find elements for '" + imgLocator.getDisplayName() + "'.");
				while (matches.hasNext()) {
					list.add(matches.next());
				}
				Assert.assertTrue(list.size() > 0,
						"Unable to find elements for '" + imgLocator.getDisplayName() + "' locator.");
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					Assert.fail("Unable to find elements for '" + uiObject.getDisplayName()
							+ "' locator. Reason timeout(waited for " + (maxIterationsToLocateElements * 2)
							+ " seconds).", th);
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return list;
	}

	@Override
	public ImageObjectValidator scrollElementOnViewport(ScrollbarSI scrollbar) {
		if (scrollbar == null) {
			return this;
		}

		return this;
	}

	/**
	 * Return true only if first element is present but it might not be visible.
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	public boolean isPresent(int maxIterationsToLocateElements) {
		boolean elemPresent = false;
		Match match = null;
		match = findElementNoException(maxIterationsToLocateElements);
		if (match != null) {
			elemPresent = true;
		}
		return elemPresent;
	}

	/**
	 * Return true only if first element is visible.
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	public boolean isVisible(int maxIterationsToLocateElements) {
		return isPresent(maxIterationsToLocateElements);
	}

	public ImageObjectValidator click(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public ImageObjectValidator forceClick(int maxIterationsToLocateElements) {
		return click(maxIterationsToLocateElements);
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

	public ImageObjectValidator click(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	public ImageObjectValidator doubleClick(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	public ImageObjectValidator doubleClick(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	public ImageObjectValidator rightClick(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	public ImageObjectValidator rightClick(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ImageObjectValidator clickAndHold(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ImageObjectValidator release(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseUp(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse release on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	public ImageObjectValidator dragAndDrop(ImageObject target, Region targetRegion, int maxIterationsToLocateElements) {
		try {
			Match sourceElem = findElement(maxIterationsToLocateElements);
			Match targetElem = target.getValidator(appDriver, targetRegion).findElement(maxIterationsToLocateElements);

			Assert.assertNotNull(sourceElem, "Failed to find element '" + imgLocator.getDisplayName() + "'.");
			Assert.assertNotNull(targetElem, "Failed to find element '" + target.getDisplayName() + "'.");

			sourceElem.dropAt(targetElem);
		} catch (Throwable th) {
			Assert.fail("Failed to perform dragAndDrop from source '" + imgLocator.getDisplayName() + "' to target '"
					+ target.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ImageObjectValidator performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyDown(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyDown('" + seleniumToSikuliKeyConverter(keys) + "') on element '"
					+ imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ImageObjectValidator performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyUp(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyUp ('" + seleniumToSikuliKeyConverter(keys) + "') on element '"
					+ imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ImageObjectValidator performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyDown(seleniumToSikuliKeyConverter(keys));
			match.keyUp(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyPressed ('" + seleniumToSikuliKeyConverter(keys) + "') on element '"
					+ imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ImageObjectValidator typeText(String text, NewTextLocation location, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
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
			// validateValue(text, TextMatchMechanism.containsExpectedValue, 0);
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyPressed on element '" + imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	public ImageObjectValidator validateValue(String expectedValue, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		validateTextValue(match.text(), expectedValue, validationMechanism);
		return this;
	}

	public String getText(int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		return match.text();
	}

	public String getEditableFieldTextUsingClipboard(int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		match.click();
		match.type("a", KeyModifier.CTRL);
		match.type("c", KeyModifier.CTRL);
		match.click();
		String contents = ClipboardUtil.getContents();
		ClipboardUtil.clearContents();
		return contents;
	}

	@Override
	@Deprecated
	public Actions getNewSeleniumActions() {
		Assert.fail("getNewSeleniumActions() API is not supported by Button component.");
		return null;
	}

	@Override
	public ImageObjectValidator mouseHoverOver(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseMove();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse hoverover on element '"
					+ imgLocator.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public ImageObjectValidator validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		if(!(match.x >= coordinates.getX1() && match.y >= coordinates.getY1() &&  
				(match.x + match.w) <= coordinates.getX2() && (match.y + match.h) <= coordinates.getY2())) {
			Assert.fail("Element '" + imgLocator.getDisplayName() + "' is not within the specified area [x1=" + coordinates.getX1() 
			+ ", y1=" + coordinates.getY1() + ", x2=" + coordinates.getX2() + ", y2=" + coordinates.getY2() + "]."
			+ " Actual Coordinates: [x1=" + match.x + ", y1=" + match.y + ", x2=" + (match.x  + match.w) 
			+ ", y2=" + (match.y + match.h) + "].");
		}
		return this;
	}
}
