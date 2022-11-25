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
import org.sikuli.script.Location;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.ImageSection;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.objects.ImageObject;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.radio.RadioButtonValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class RadioButtonValidatorSI extends RadioButtonValidator {
	protected RadioButtonSI rbObject;

	public RadioButtonValidatorSI(SmartAppDriver appDriver, RadioButtonSI uiObject, Region region) {
		super(appDriver, uiObject, region);
		this.rbObject = uiObject;
	}

	@Override
	public boolean isPresent(int maxIterationsToLocateElements) {
		Match m = findElementNoException(maxIterationsToLocateElements);
		return (m != null);
	}

	@Override
	public boolean isVisible(int maxIterationsToLocateElements) {
		return isPresent(maxIterationsToLocateElements);
	}

	@Override
	public RadioButtonValidatorSI click(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public RadioButtonValidatorSI forceClick(int maxIterationsToLocateElements) {
		return click(maxIterationsToLocateElements);
	}

	public RadioButtonValidatorSI click(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).click();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSI doubleClick(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	public RadioButtonValidatorSI doubleClick(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).doubleClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSI rightClick(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	public RadioButtonValidatorSI rightClick(ImageSection imageSection, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			getImageSection(match, imageSection).rightClick();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSI clickAndHold(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSI release(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseDown(Button.LEFT);
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse clickAndHold on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public RadioButtonValidatorSI mouseHoverOver(int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.mouseMove();
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse hoverover on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSI performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyDown(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyDown on RadioButton '" + rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSI performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyUp(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyUp ('" + seleniumToSikuliKeyConverter(keys) + "') on RadioButton '"
					+ rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public RadioButtonValidatorSI performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		try {
			Match match = findElement(maxIterationsToLocateElements);
			match.click();
			match.keyDown(seleniumToSikuliKeyConverter(keys));
			match.keyUp(seleniumToSikuliKeyConverter(keys));
		} catch (Throwable th) {
			Assert.fail("Failed to perform keyPressed ('" + seleniumToSikuliKeyConverter(keys) + "') on RadioButton '"
					+ rbObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	@Deprecated
	public RadioButtonValidatorSI typeText(String text, NewTextLocation location, int maxIterationsToLocateElements) {
		Assert.fail("typeText() API is not supported for RadioButton element.");
		return this;
	}

	@Override
	public RadioButtonValidatorSI scrollElementOnViewport(Scrollbar scrollbar) {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public Match findElement(int maxIterationsToLocateElements) {
		Match match = null;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				Region region = rbObject.getRadioButtonImageLocation().getRegionOfImageObject(appDriver,
						rbObject.getRadioButtonImage(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(region, "Failed to find RadioButton '" + rbObject.getDisplayName() + "'.");
				match = new Match(region, 1);
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					Assert.fail("Unable to find RadioButton '" + rbObject.getDisplayName()
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
		Region r = rbObject.getRadioButtonImageLocation().getRegion(appDriver);

		return new ImageObject(UIObjectType.radioButton, rbObject.getDisplayName(),
				rbObject.getRadioButtonImage(appDriver.getAppConfig().getTestPlatformType(),
						appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()))
				.getValidator(appDriver, r).findElements(maxIterationsToLocateElements);
	}

	public RadioButtonValidatorSI dragAndDrop(ImageObject target, Region targetRegion, int maxIterationsToLocateElements) {
		try {
			Match sourceElem = findElement(maxIterationsToLocateElements);
			Match targetElem = target.getValidator(appDriver, targetRegion).findElement(maxIterationsToLocateElements);

			Assert.assertNotNull(sourceElem, "Failed to find RadioButton '" + rbObject.getDisplayName() + "'.");
			Assert.assertNotNull(targetElem, "Failed to find element '" + target.getDisplayName() + "'.");

			sourceElem.drag(targetElem);
			sourceElem.dropAt(targetElem);
		} catch (Throwable th) {
			Assert.fail("Failed to perform dragAndDrop from source '" + rbObject.getDisplayName() + "' to target '"
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
	@Deprecated
	public RadioButtonValidatorSI validateDisabled(int maxIterationsToLocateElements) {
		Assert.fail("validateDisabled() is not supported for RadioButton component.");
		return this;
	}

	@Override
	@Deprecated
	public RadioButtonValidatorSI validateEnabled(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabled() is not supported for RadioButton component.");
		return this;
	}

	@Override
	public RadioButtonValidatorSI select(int maxIterationsToLocateElements) {
		click(maxIterationsToLocateElements);
		return this;
	}

	@Override
	@Deprecated
	public RadioButtonValidatorSI validateSelected(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabled() is not supported for RadioButton component.");
		return this;
	}

	@Override
	@Deprecated
	public RadioButtonValidatorSI validateNotSelected(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabled() is not supported for RadioButton component.");
		return this;
	}

	@Override
	public boolean isDisabled(int maxIterationsToLocateElements) {
		Assert.fail("isDisabled() API is not supported by RadioButton component.");
		return false;
	}

	@Override
	@Deprecated
	public Actions getNewSeleniumActions() {
		Assert.fail("getNewSeleniumActions() API is not supported by RadioButton component.");
		return null;
	}

	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabledButNotReadonly() API is not supported by RadioButton component.");
		return false;
	}

	@Override
	public RadioButtonValidatorSI validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabledButNotReadonly() API is not supported by RadioButton component.");
		return this;
	}

	@Override
	public RadioButtonValidatorSI validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.fail("validateEnabledButNotReadonly() API is not supported by RadioButton component.");
		return this;
	}

	@Override
	public boolean isSelected(int maxIterationsToLocateElements) {
		Assert.fail("isSelected() API is not supported by RadioButton component.");
		return false;
	}
}
