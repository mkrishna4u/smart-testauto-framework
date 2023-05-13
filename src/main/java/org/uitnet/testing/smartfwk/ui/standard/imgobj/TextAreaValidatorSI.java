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
import org.uitnet.testing.smartfwk.ui.core.commons.AreaCoordinates;
import org.uitnet.testing.smartfwk.ui.core.commons.ImageSection;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.objects.ImageObject;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.textarea.TextAreaValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.ClipboardUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

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
	public TextAreaValidatorSI validateDisabled(int maxIterationsToLocateElements) {
		Assert.fail("validateDisabled() API is not supported by TextAreaSI.");
		return this;
	}

	@Override
	@Deprecated
	public TextAreaValidatorSI validateEnabled(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabled() API is not supported by TextAreaSI.");
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
	public TextAreaValidatorSI click(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public TextAreaValidatorSI forceClick(int maxIterationsToLocateElements) {
		return click(maxIterationsToLocateElements);
	}

	public TextAreaValidatorSI click(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI doubleClick(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	public TextAreaValidatorSI doubleClick(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI rightClick(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	public TextAreaValidatorSI rightClick(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI clickAndHold(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI release(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public TextAreaValidatorSI mouseHoverOver(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseMove();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse hoverover on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyDown(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyDown on TextArea '" + textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyUp(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyUp ('" + seleniumToSikuliKeyConverter(keys) + "') on TextArea '"
					+ textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.type(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyPressed ('" + seleniumToSikuliKeyConverter(keys) + "') on TextArea '"
					+ textAreaObj.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI typeText(String text, NewTextLocation location, int maxIterationsToLocateElements) {
		return typeText(text, location, 0, true, maxIterationsToLocateElements);
	}
	
	@Override
	public TextAreaValidatorSI typeText(String text, NewTextLocation location, int typeSpeedInMspc, boolean clickBeforeType, int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		try {
			if(clickBeforeType) {
				match.click();
			}
			
			switch (location) {
			case start:
				match.type(Key.HOME);
				break;
			case end:
				match.type(Key.END);
				break;
			case replace:				
				if(OSDetectorUtil.getHostPlatform() == PlatformType.mac || OSDetectorUtil.getHostPlatform() == PlatformType.ios_mobile) {
					match.type("a", KeyModifier.CMD);
				} else {
					match.type("a", KeyModifier.CTRL);
				}
				break;
			}
			if(typeSpeedInMspc < 1) {
				match.type(text);
			} else {
				match.delayType(typeSpeedInMspc);
				match.type(text);
			}
			
			// validateTextValue(text, TextMatchMechanism.containsExpectedValue, 0);
		} catch (Throwable th) {
			Assert.fail("Fail to type text '" + text + "' in TextArea '" + textAreaObj.getDisplayName() + "'.");
		}
		return this;
	}

	@Override
	public TextAreaValidatorSI scrollElementOnViewport(ScrollbarSI scrollbar) {
		// TODO
		return this;
	}

	@Override
	public Match findElement(int maxIterationsToLocateElements) {
		Match match = null;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				Region region = textAreaObj.getLocation().getRegionOfImageObject(appDriver,
						textAreaObj.getLeftSideImage(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()),
						textAreaObj.getRightSideImage(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(region, "Failed to find TextArea '" + textAreaObj.getDisplayName() + "'.");
				match = new Match(region, 1);
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					Assert.fail("Unable to find TextArea '" + textAreaObj.getDisplayName()
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
		Assert.fail("findElements() api for TextAreaSI element is not implemented.");
		return null;
	}

	public TextAreaValidatorSI dragAndDrop(ImageObject target, Region targetRegion, int maxIterationsToLocateElements) {
		try {
			Match sourceElem = findElement(maxIterationsToLocateElements);
			Match targetElem = target.getValidator(appDriver, targetRegion).findElement(maxIterationsToLocateElements);

			Assert.assertNotNull(sourceElem, "Failed to find TextArea '" + textAreaObj.getDisplayName() + "'.");
			Assert.assertNotNull(targetElem, "Failed to find element '" + target.getDisplayName() + "'.");

			sourceElem.drag(targetElem);
			sourceElem.dropAt(targetElem);
		} catch (Throwable th) {
			Assert.fail("Failed to perform dragAndDrop from source '" + textAreaObj.getDisplayName() + "' to target '"
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
	public TextAreaValidatorSI validateTextValue(String expectedValue, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements) {
		if (textAreaObj.isDisabled()) {
			Match match = findElement(maxIterationsToLocateElements);
			validateTextValue(match.text(), expectedValue, validationMechanism);
		} else {
			validateTextValue(getTextValue(maxIterationsToLocateElements), expectedValue, validationMechanism);
		}
		return this;
	}

	/**
	 * Used to return value using clipboard method.
	 */
	@Override
	public String getTextValue(int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
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
	public boolean isDisabled(int maxIterationsToLocateElements) {
		Assert.fail("isDisabled() API is not supported by TextArea component.");
		return false;
	}

	@Override
	@Deprecated
	public boolean isEnabled(int maxIterationsToLocateElements) {
		Assert.fail("isEnabled() API is not supported by TextArea component.");
		return false;
	}

	@Override
	@Deprecated
	public boolean isReadonly(int maxIterationsToLocateElements) {
		Assert.fail("isDisabled() API is not supported by TextArea component.");
		return false;
	}

	@Override
	@Deprecated
	public TextAreaValidatorSI validateReadonly(int maxIterationsToLocateElements) {
		Assert.fail("isDisabled() API is not supported by TextArea component.");
		return this;
	}

	@Override
	@Deprecated
	public TextAreaValidatorSI validateNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("isDisabled() API is not supported by TextArea component.");
		return this;
	}

	@Override
	@Deprecated
	public Actions getNewSeleniumActions() {
		Assert.fail("getNewSeleniumActions() API is not supported by TextArea component.");
		return null;
	}

	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("isDisabledButNotReadonly() API is not supported by TextArea component.");
		return false;
	}

	@Override
	public TextAreaValidatorSI validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("validateDisabledButNotReadonly() API is not supported by TextArea component.");
		return this;
	}

	@Override
	public TextAreaValidatorSI validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabledButNotReadonly() API is not supported by TextArea component.");
		return this;
	}
	
	@Override
	public TextAreaValidatorSI validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		Match match = findElement(maxIterationsToLocateElements);
		if(!(match.x >= coordinates.getX1() && match.y >= coordinates.getY1() &&  
				(match.x + match.w) <= coordinates.getX2() && (match.y + match.h) <= coordinates.getY2())) {
			Assert.fail("Element '" + textAreaObj.getDisplayName() + "' is not within the specified area [x1=" + coordinates.getX1() 
			+ ", y1=" + coordinates.getY1() + ", x2=" + coordinates.getX2() + ", y2=" + coordinates.getY2() + "]."
			+ " Actual Coordinates: [x1=" + match.x + ", y1=" + match.y + ", x2=" + (match.x  + match.w) 
			+ ", y2=" + (match.y + match.h) + "].");
		}
		return this;
	}
}
