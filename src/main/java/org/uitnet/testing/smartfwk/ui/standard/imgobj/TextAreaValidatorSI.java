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
package org.uitnet.testing.smartfwk.ui.standard.imgobj;

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
import org.uitnet.testing.smartfwk.ui.core.commons.ImageSection;
import org.uitnet.testing.smartfwk.ui.core.objects.ImageObject;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;
import org.uitnet.testing.smartfwk.ui.core.objects.textarea.TextAreaValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.ClipboardUtil;

import io.appium.java_client.MultiTouchAction;
import io.appium.java_client.TouchAction;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class TextAreaValidatorSI extends TextAreaValidator {
	protected TextAreaSI textAreaObj;

	public TextAreaValidatorSI(SmartAppDriver appDriver, TextAreaSI uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.textAreaObj = uiObject;
	}

	@Override
	@Deprecated
	public void validateDisabled(int numRetries) {
		Assert.fail("validateDisabled() API is not supported by TextAreaSI.");
	}

	@Override
	@Deprecated
	public void validateEnabled(int numRetries) {
		Assert.fail("validateEnabled() API is not supported by TextAreaSI.");
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
			Assert.fail("Failed to perform mouse click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
	}

	public void click(ImageSection imageSection, int numRetries) {
		try {
			Match match = findElement(numRetries);
			getImageSection(match, imageSection).click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void doubleClick(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
	}

	public void doubleClick(ImageSection imageSection, int numRetries) {
		try {
			Match match = findElement(numRetries);
			getImageSection(match, imageSection).doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void rightClick(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
	}

	public void rightClick(ImageSection imageSection, int numRetries) {
		try {
			Match match = findElement(numRetries);
			getImageSection(match, imageSection).rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void clickAndHold(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void release(int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void performKeyDown(Keys keys, int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.click();
			match.keyDown(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyDown on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}

	}

	@Override
	public void performKeyUp(Keys keys, int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.click();
			match.keyUp(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyUp ('" + seleniumToSikuliKeyConverter(keys) + "') on TextArea '"
					+ textAreaObj.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void performKeyPressed(Keys keys, int numRetries) {
		try {
			Match match = findElement(numRetries);
			match.click();
			match.type(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyPressed ('" + seleniumToSikuliKeyConverter(keys) + "') on TextArea '"
					+ textAreaObj.getDisplayName() + "'.", th);
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
			// validateTextValue(text, TextMatchMechanism.containsExpectedValue, 0);
		} catch (Throwable th) {
			Assert.fail("Fail to type text '" + text + "' in TextArea '" + textAreaObj.getDisplayName() + "'.");
		}
	}

	@Override
	public TextAreaValidatorSI scrollElementOnViewport(Scrollbar scrollbar) {
		// TODO
		return this;
	}

	@Override
	public Match findElement(int numRetries) {
		Match match = null;
		for (int i = 0; i <= numRetries; i++) {
			try {
				Region region = textAreaObj.getLocation().getRegionOfImageObject(appDriver,
						textAreaObj.getLeftSideImage(), textAreaObj.getRightSideImage());
				Assert.assertNotNull(region, "Failed to find TextArea '" + textAreaObj.getDisplayName() + "'.");
				match = new Match(region, 1);
				break;
			} catch (Throwable th) {
				if (i == numRetries) {
					Assert.fail("Unable to find TextArea '" + textAreaObj.getDisplayName()
							+ "'. Reason timeout(waited for " + (numRetries * 2) + " seconds).", th);
					break;
				}
			}
			appDriver.waitForSeconds(2);
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
		Assert.fail("findElements() api for TextAreaSI element is not implemented.");
		return null;
	}

	public void dragAndDrop(ImageObject target, Region targetRegion, int numRetries) {
		try {
			Match sourceElem = findElement(numRetries);
			Match targetElem = target.getValidator(appDriver, targetRegion).findElement(numRetries);

			Assert.assertNotNull(sourceElem, "Failed to find TextArea '" + textAreaObj.getDisplayName() + "'.");
			Assert.assertNotNull(targetElem, "Failed to find element '" + target.getDisplayName() + "'.");

			sourceElem.drag(targetElem);
			sourceElem.dropAt(targetElem);
		} catch (Throwable th) {
			Assert.fail("Failed to perform dragAndDrop from source '" + textAreaObj.getDisplayName() + "' to target '"
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
	public void validateTextValue(String expectedValue, TextMatchMechanism validationMechanism, int numRetries) {
		if (textAreaObj.isDisabled()) {
			Match match = findElement(numRetries);
			validateTextValue(match.text(), expectedValue, validationMechanism);
		} else {
			validateTextValue(getTextValue(numRetries), expectedValue, validationMechanism);
		}
	}

	/**
	 * Used to return value using clipboard method.
	 */
	@Override
	public String getTextValue(int numRetries) {
		Match match = findElement(numRetries);
		if (textAreaObj.isDisabled()) {
			return match.getText();
		} else {
			match.click();

			ClipboardUtil.clearContents();

			match.type("ac", KeyModifier.CTRL);

			String contents = ClipboardUtil.getContents();
			ClipboardUtil.clearContents();

			match.click();
			return contents;
		}

	}

	@Override
	@Deprecated
	public boolean isDisabled(int numRetries) {
		Assert.fail("isDisabled() API is not supported by TextArea component.");
		return false;
	}

	@Override
	@Deprecated
	public boolean isReadonly(int numRetries) {
		Assert.fail("isDisabled() API is not supported by TextArea component.");
		return false;
	}

	@Override
	@Deprecated
	public void validateReadonly(int numRetries) {
		Assert.fail("isDisabled() API is not supported by TextArea component.");

	}

	@Override
	@Deprecated
	public void validateNotReadonly(int numRetries) {
		Assert.fail("isDisabled() API is not supported by TextArea component.");

	}
	
	@SuppressWarnings("rawtypes")
	@Override
	@Deprecated
	public TouchAction getNewMobileTouchAction() {
		Assert.fail("getNewMobileTouchAction() API is not supported by TextArea component.");
		return null;
	}

	@Override
	@Deprecated
	public MultiTouchAction getNewMobileMultiTouchAction() {
		Assert.fail("getNewMobileMultiTouchAction() API is not supported by TextArea component.");
		return null;
	}

	@Override
	@Deprecated
	public Actions getNewSeleniumActions() {
		Assert.fail("getNewSeleniumActions() API is not supported by TextArea component.");
		return null;
	}

	@Override
	public boolean isDisabledButNotReadonly(int numRetries) {
		Assert.fail("isDisabledButNotReadonly() API is not supported by TextArea component.");
		return false;
	}

	@Override
	public void validateDisabledButNotReadonly(int numRetries) {
		Assert.fail("validateDisabledButNotReadonly() API is not supported by TextArea component.");
	}

	@Override
	public void validateEnabledButNotReadonly(int numRetries) {
		Assert.fail("validateEnabledButNotReadonly() API is not supported by TextArea component.");
	}
}
