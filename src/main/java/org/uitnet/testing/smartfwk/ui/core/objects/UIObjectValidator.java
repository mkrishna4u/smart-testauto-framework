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

import java.awt.Rectangle;

import org.openqa.selenium.Keys;
import org.sikuli.script.Key;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.config.webbrowser.WebBrowser;
import org.uitnet.testing.smartfwk.ui.core.events.InputEvent;
import org.uitnet.testing.smartfwk.ui.core.events.InputEventType;
import org.uitnet.testing.smartfwk.ui.core.events.KeyboardEvent;
import org.uitnet.testing.smartfwk.ui.core.events.MouseEvent;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextValidationMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.DataMatchUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public abstract class UIObjectValidator {
	protected WebBrowser browser;
	protected UIObject uiObject;
	protected Region region;

	public UIObjectValidator(WebBrowser browser, UIObject uiObject, Region region) {
		this.browser = browser;
		this.uiObject = uiObject;
		if (browser != null) {
			this.region = (region == null) ? Region.create(
					new Rectangle(0, 0, new Double(browser.getAppConfig().getBrowserWindowSize().getWidth()).intValue(),
							new Double(browser.getAppConfig().getBrowserWindowSize().getHeight()).intValue()))
					: region;

		}
	}

	public UIObject getUIObject() {
		return uiObject;
	}

	public UIObjectType geUIObjectType() {
		return uiObject.getType();
	}

	public Region getRegion() {
		return region;
	}

	public void validatePresent(int numRetries) {
		Assert.assertTrue(isPresent(numRetries), "Failed to find element '" + uiObject.getDisplayName() + "'");
	}

	public void validateNotPresent(int numRetries) {
		Assert.assertTrue(!isPresent(numRetries), "Element '" + uiObject.getDisplayName() + "' is already present.");
	}

	public <EVENTNAME> void performAction(InputEvent<EVENTNAME> event, int numRetries) {
		if (event.getType() == InputEventType.mouse) {
			MouseEvent mouseEvent = (MouseEvent) event;
			switch (mouseEvent.getName()) {
			case mouseClick:
				click(numRetries);
				break;
			case mouseRightClick:
				rightClick(numRetries);
				break;
			case mouseDoubleClick:
				doubleClick(numRetries);
				break;
			case mouseClickAndHold:
				clickAndHold(numRetries);
				break;
			case mouseRelease:
				release(numRetries);
				break;
			}
		} else if (event.getType() == InputEventType.keyBoard) {
			KeyboardEvent kbEvent = (KeyboardEvent) event;
			switch (kbEvent.getName()) {
			case kbKeyDown:
				if (kbEvent.getInputText() != null) {
					typeText(kbEvent.getInputText(), kbEvent.getTextLocation(), numRetries);
				}
				performKeyDown(kbEvent.getKey(), numRetries);
				break;
			case kbKeyUp:
				if (kbEvent.getInputText() != null) {
					typeText(kbEvent.getInputText(), kbEvent.getTextLocation(), numRetries);
				}
				performKeyUp(kbEvent.getKey(), numRetries);
				break;
			case keyPressed:
				if (kbEvent.getInputText() != null) {
					typeText(kbEvent.getInputText(), kbEvent.getTextLocation(), numRetries);
				}
				performKeyPressed(kbEvent.getKey(), numRetries);
				break;
			}
		}

	}

	protected boolean matchTextValue(String actualValue, String expectedValue,
			TextValidationMechanism validationMechanism) {
		return DataMatchUtil.matchTextValue(actualValue, expectedValue, validationMechanism);
	}

	protected void validateTextValue(String actualValue, String expectedValue,
			TextValidationMechanism validationMechanism) {
		DataMatchUtil.validateTextValue(actualValue, expectedValue, validationMechanism);		
	}

	public String seleniumToSikuliKeyConverter(Keys key) {
		switch (key.name()) {
		case "HOME":
			return Key.HOME;
		case "END":
			return Key.END;
		case "TAB":
			return Key.TAB;
		case "BACK_SPACE":
			return Key.BACKSPACE;
		case "RETURN":
			return Key.ENTER;
		case "ENTER":
			return Key.ENTER;
		case "SHIFT":
			return Key.SHIFT;
		case "CONTROL":
			return Key.CTRL;
		case "ALT":
			return Key.ALT;
		case "ESCAPE":
			return Key.ESC;
		case "SPACE":
			return Key.SPACE;
		case "PAGE_UP":
			return Key.PAGE_UP;
		case "PAGE_DOWN":
			return Key.PAGE_DOWN;
		case "LEFT":
		case "ARROW_LEFT":
			return Key.LEFT;
		case "UP":
		case "ARROW_UP":
			return Key.UP;
		case "RIGHT":
		case "ARROW_RIGHT":
			return Key.RIGHT;
		case "DOWN":
		case "ARROW_DOWN":
			return Key.DOWN;
		case "INSERT":
			return Key.INSERT;
		case "DELETE":
			return Key.DELETE;
		case "SEMICOLON":
			return ";";
		case "F1":
			return Key.F1;
		case "F2":
			return Key.F2;
		case "F3":
			return Key.F3;
		case "F4":
			return Key.F4;
		case "F5":
			return Key.F5;
		case "F6":
			return Key.F6;
		case "F7":
			return Key.F7;
		case "F8":
			return Key.F8;
		case "F9":
			return Key.F9;
		case "F10":
			return Key.F10;
		case "F11":
			return Key.F11;
		case "F12":
			return Key.F12;
		case "META":
			return Key.META;
		case "COMMAND":
			return Key.CMD;
		}
		return null;
	}

	public abstract boolean isPresent(int numRetries);

	public abstract boolean isVisible(int numRetries);

	public abstract void click(int numRetries);

	public abstract void doubleClick(int numRetries);

	public abstract void rightClick(int numRetries);

	public abstract void clickAndHold(int numRetries);

	public abstract void release(int numRetries);

	public abstract void performKeyDown(Keys keys, int numRetries);

	public abstract void performKeyUp(Keys keys, int numRetries);

	public abstract void performKeyPressed(Keys keys, int numRetries);

	public abstract void typeText(String text, NewTextLocation location, int numRetries);

	public abstract UIObjectValidator scrollElementOnViewport(Scrollbar scrollbar);

	public abstract Object findElement(int numRetries);

	public abstract Object findElementNoException(int numRetries);

	public abstract Object findElements(int numRetries);
}
