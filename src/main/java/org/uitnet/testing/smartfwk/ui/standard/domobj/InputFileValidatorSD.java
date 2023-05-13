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

import java.io.File;
import java.util.List;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.AreaCoordinates;
import org.uitnet.testing.smartfwk.ui.core.commons.ItemList;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObject;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.file.InputFileValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.WebElementUtil;
import org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar.ScrollbarSI;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class InputFileValidatorSD extends InputFileValidator {
	protected DOMObjectValidator domObjValidator;

	public InputFileValidatorSD(SmartAppDriver appDriver, InputFileSD uiObject, Region region) {
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
	public InputFileValidatorSD validateDisabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public InputFileValidatorSD validateEnabled(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isEnabled(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	@Override
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		return domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements);
	}

	@Override
	public InputFileValidatorSD validateDisabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not disabled.");
		return this;
	}

	@Override
	public InputFileValidatorSD validateEnabledButNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertFalse(domObjValidator.isDisabledButNotReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not enabled.");
		return this;
	}

	public boolean isReadonly(int maxIterationsToLocateElements) {
		return domObjValidator.isReadonly(maxIterationsToLocateElements);
	}

	@Override
	public InputFileValidatorSD validateReadonly(int maxIterationsToLocateElements) {
		Assert.assertTrue(domObjValidator.isReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is not readonly.");
		return this;
	}

	@Override
	public InputFileValidatorSD validateNotReadonly(int maxIterationsToLocateElements) {
		Assert.assertFalse(domObjValidator.isReadonly(maxIterationsToLocateElements),
				"'" + uiObject.getDisplayName() + "' element is readonly.");
		return this;
	}

	@Override
	public InputFileValidatorSD typeText(String textToType, NewTextLocation location, int maxIterationsToLocateElements) {
		domObjValidator.typeText(textToType, location, 0, false, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD validateValue(String expectedValue, TextMatchMechanism validationMechanism,
			int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					String actualValue = WebElementUtil.getInputTextValue(appDriver, domObjValidator.getUIObject(), 0);
					validateTextValue(actualValue, expectedValue, validationMechanism);
					return this;
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to validate expected value '" + expectedValue + "' for element '"
					+ uiObject.getDisplayName() + "'.", th);
		}
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
	public InputFileValidatorSD click(int maxIterationsToLocateElements) {
		domObjValidator.click(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public InputFileValidatorSD forceClick(int maxIterationsToLocateElements) {
		domObjValidator.forceClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD doubleClick(int maxIterationsToLocateElements) {
		domObjValidator.doubleClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD rightClick(int maxIterationsToLocateElements) {
		domObjValidator.rightClick(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD clickAndHold(int maxIterationsToLocateElements) {
		domObjValidator.clickAndHold(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD release(int maxIterationsToLocateElements) {
		domObjValidator.release(maxIterationsToLocateElements);
		return this;
	}
	
	@Override
	public InputFileValidatorSD mouseHoverOver(int maxIterationsToLocateElements) {
		domObjValidator.mouseHoverOver(maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyDown(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyUp(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		domObjValidator.performKeyPressed(keys, maxIterationsToLocateElements);
		return this;
	}

	@Override
	public InputFileValidatorSD scrollElementOnViewport(ScrollbarSI scrollbar) {
		domObjValidator.scrollElementOnViewport(scrollbar);
		return this;
	}

	@Override
	public String getValue(int maxIterationsToLocateElements) {
		return WebElementUtil.getInputTextValue(appDriver, domObjValidator.getUIObject(), maxIterationsToLocateElements);
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
	public void selectFiles(ItemList<String> relativeFilePaths, int maxIterationsToLocateElements) {
		selectFiles(relativeFilePaths, 0, maxIterationsToLocateElements);
	}
	
	@Override
	public void selectFiles(ItemList<String> relativeFilePaths, int waitTimeInSecondsAfterSelect, int maxIterationsToLocateElements) {
		String paths = "";
		for(int i = 0; i < relativeFilePaths.size(); i++) {
			if(StringUtil.isEmptyNoTrim(paths)) {
				paths = Locations.getProjectRootDir() + File.separator +  relativeFilePaths.getItem(i);
			} else {
				paths = paths + "\n" + Locations.getProjectRootDir() + File.separator +  relativeFilePaths.getItem(i);
			}
		}
		if(OSDetectorUtil.getHostPlatform() == PlatformType.windows) {
			paths = paths.replace("/", "\\");
		} else {
			paths = paths.replace("\\", "/");
		}
		
		WebElement webElem = domObjValidator.findElement(maxIterationsToLocateElements);
		webElem.sendKeys(paths);
		if(waitTimeInSecondsAfterSelect > 0) {
			appDriver.waitForSeconds(waitTimeInSecondsAfterSelect);
		}
	}
	
	@Override
	public InputFileValidatorSD validateElementPresentWithinArea(AreaCoordinates coordinates,
			int maxIterationsToLocateElements) {
		domObjValidator.validateElementPresentWithinArea(coordinates, maxIterationsToLocateElements);
		return this;
	}
}
