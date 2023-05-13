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

import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.support.ScrollbarType;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.AreaCoordinates;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.ScrollbarValidator;
import org.uitnet.testing.smartfwk.ui.core.utils.PageScrollUtil;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ScrollbarValidatorSD extends ScrollbarValidator {
	protected DOMObjectValidator domObjValidator;

	public ScrollbarValidatorSD(SmartAppDriver appDriver, ScrollbarSD uiObject, Region region) {
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
	public ScrollbarValidatorSD validateDisabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public ScrollbarValidatorSD validateEnabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isEnabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	@Override
	public ScrollbarValidatorSD typeText(String textToType, NewTextLocation location,
			int maxIterationsToLocateElements) {
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
	public ScrollbarValidatorSD click(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD forceClick(int maxIterationsToLocateElements) {
		domObjValidator.forceClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD doubleClick(int maxIterationsToLocateElements) {
		domObjValidator.doubleClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD rightClick(int maxIterationsToLocateElements) {
		domObjValidator.rightClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD clickAndHold(int maxIterationsToLocateElements) {
		domObjValidator.clickAndHold(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD release(int maxIterationsToLocateElements) {
		domObjValidator.release(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD mouseHoverOver(int maxIterationsToLocateElements) {
		domObjValidator.mouseHoverOver(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyDown(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyUp(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyPressed(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD scrollElementOnViewport(ScrollbarSI scrollbar) {
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
	public Actions getNewSeleniumActions() {
		return domObjValidator.getNewSeleniumActions();
	}

	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements);
	}

	@Override
	public ScrollbarValidatorSD setHorizontalThumbGripLocation(int numPixelsFromLeft, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement elem = findElement(0);
					PageScrollUtil.setScrollbarThumbgripLocation(appDriver, elem, ScrollbarType.HORIZONTAL,
							numPixelsFromLeft);
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to set location of horizontal scrollbar thumb grip using element '"
					+ uiObject.getDisplayName() + "'.", th);
		}

		return this;
	}
	
	@Override
	public ScrollbarValidatorSD setHorizontalThumbGripLocation(double pctValueFromLeft, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement elem = findElement(0);
					PageScrollUtil.setScrollbarThumbgripLocation(appDriver, elem, ScrollbarType.HORIZONTAL,
							pctValueFromLeft);
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to set location of horizontal scrollbar thumb grip using element '"
					+ uiObject.getDisplayName() + "'.", th);
		}

		return this;
	}

	@Override
	public ScrollbarValidatorSD setVerticalThumbGripLocation(int numPixelsFromTop, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement elem = findElement(0);
					PageScrollUtil.setScrollbarThumbgripLocation(appDriver, elem, ScrollbarType.VERTICAL,
							numPixelsFromTop);
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to set location of vertical scrollbar thumb grip using element '"
					+ uiObject.getDisplayName() + "'.", th);
		}

		return this;
	}
	
	@Override
	public ScrollbarValidatorSD validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		domObjValidator.validateElementPresentWithinArea(coordinates, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public ScrollbarValidatorSD setVerticalThumbGripLocation(double pctValueFromTop, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					WebElement elem = findElement(0);
					PageScrollUtil.setScrollbarThumbgripLocation(appDriver, elem, ScrollbarType.VERTICAL,
							pctValueFromTop);
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to set location of vertical scrollbar thumb grip using element '"
					+ uiObject.getDisplayName() + "'.", th);
		}

		return this;
	}
}
