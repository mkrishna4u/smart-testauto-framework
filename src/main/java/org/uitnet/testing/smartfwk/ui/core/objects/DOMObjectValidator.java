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

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.config.PlatformType;
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;
import org.uitnet.testing.smartfwk.ui.core.utils.LocatorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.OSDetectorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.PageScrollUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.WebElementUtil;

import com.google.common.base.Function;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DOMObjectValidator extends UIObjectValidator {
	private DOMObject domObject;

	public DOMObjectValidator(SmartAppDriver appDriver, DOMObject domObject, Region region) {
		super(appDriver, domObject, region);
		this.domObject = domObject;
	}

	@Override
	public DOMObject getUIObject() {
		return domObject;
	}

	/**
	 * Returns the attribute value of the first element.
	 * 
	 * @param attributeName
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	public String getAttributeValue(String attributeName, int maxIterationsToLocateElements) {
		try {
			WebElement webElem = findElement(maxIterationsToLocateElements);
			return webElem.getAttribute(attributeName);
		} catch (Throwable th) {
			Assert.fail("Failed to get attribute (name='" + attributeName + "') value for element '"
					+ domObject.getDisplayName() + "'.", th);
		}
		return null;
	}

	/**
	 * Finds attribute value from element when it is not hidden.
	 * 
	 * @param maxIterationsToLocateElements
	 */
	public String getAttributeValueWhenElementVisible(String attributeName, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					if (isVisible(0)) {
						return getAttributeValue(attributeName, 0);
					}
					Assert.fail("Element is not visible.");
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to get attribute '" + attributeName + "' value from visible element '"
					+ domObject.getDisplayName() + "'.", th);
		}
		return null;
	}

	/**
	 * Finds attribute value from visible element when it's value is not empty.
	 * Leading and trailing whitespace will be removed in comparision.
	 * 
	 * @param maxIterationsToLocateElements
	 */
	public String getAttributeValueWhenAttributeValueNonEmpty(String attributeName, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					String value = getAttributeValue(attributeName, 0);
					if (!(value == null || "".equals(value.trim()))) {
						return value;
					}
					Assert.fail("Attribute value is empty.");
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to get non-empty attribute '" + attributeName + "' value from element '"
					+ domObject.getDisplayName() + "'.", th);
		}
		return null;
	}

	/**
	 * Finds first element with polling and it polls after 2 seconds for maxIterationsToLocateElements
	 * times.
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	@Override
	public WebElement findElement(int maxIterationsToLocateElements) {
		WebElement webElem = null;
		appDriver.waitForMilliSeconds(100);
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(webElem, "Unable to find element '" + domObject.getDisplayName() + "'.");
				
				if(appDriver.getScrollElementToViewportHandler() != null) {
					appDriver.getScrollElementToViewportHandler().handle(appDriver, webElem);
				}
				
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					Assert.fail("Unable to find element '" + domObject.getDisplayName()
							+ "'. Reason timeout(waited for " + (maxIterationsToLocateElements * 2) + " seconds).", th);
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		
		return webElem;
	}

	public void waitForPageLoad() {
		WebDriverWait wait = new WebDriverWait(appDriver.getWebDriver(), Duration.ofSeconds(30));
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				String readyState = String.valueOf(
						((JavascriptExecutor) appDriver.getWebDriver()).executeScript("return document.readyState"));
				// System.out.println("Current Window State: " + readyState);
				return "complete".equals(readyState);
			}
		});
	}

	/**
	 * Finds first element with polling and it polls after 2 seconds for maxIterationsToLocateElements
	 * times. It does not throw any exception
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	@Override
	public WebElement findElementNoException(int maxIterationsToLocateElements) {
		WebElement webElem = null;
		appDriver.waitForMilliSeconds(100);
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				if (webElem != null) {
					if(appDriver.getScrollElementToViewportHandler() != null) {
						appDriver.getScrollElementToViewportHandler().handle(appDriver, webElem);
					}
					break;
				}
				Assert.fail();
			} catch (Throwable th) {
				webElem = null;
				if (i == maxIterationsToLocateElements) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		
		return webElem;
	}

	/**
	 * This returns all the elements based on the locator. It waits for the
	 * configured timeout if the element is not present. Performs polling maxIterationsToLocateElements
	 * times.
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	@Override
	public List<WebElement> findElements(int maxIterationsToLocateElements) {
		List<WebElement> webElems = null;
		appDriver.waitForMilliSeconds(100);
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				webElems = LocatorUtil.findWebElements(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(webElems, "Unable to find elements for '" + domObject.getDisplayName() + "'.");
				Assert.assertTrue(webElems.size() > 0,
						"Unable to find elements for '" + domObject.getDisplayName() + "' locator.");
				break;
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					Assert.fail("Unable to find elements for '" + uiObject.getDisplayName()
							+ "' locator. Reason timeout(waited for " + (maxIterationsToLocateElements * 2) + " seconds).", th);
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		
		return webElems;
	}

	@Override
	public DOMObjectValidator scrollElementOnViewport(Scrollbar scrollbar) {
		if (scrollbar == null) {
			return this;
		}
		scrollbar.scrollElementToViewport(appDriver, getUIObject());
		return this;
	}

	/**
	 * Return true only if first element is present but it might not be visible.
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	@Override
	public boolean isPresent(int maxIterationsToLocateElements) {
		boolean elemPresent = false;
		WebElement webElem = null;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				if (webElem != null) {
					elemPresent = true;
					break;
				}
				Assert.fail();
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return elemPresent;
	}

	/**
	 * Return true only if element is visible.
	 * 
	 * @param maxIterationsToLocateElements
	 * @return
	 */
	@Override
	public boolean isVisible(int maxIterationsToLocateElements) {
		boolean elemVisible = false;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				if (WebElementUtil.isElementVisible(appDriver, domObject)) {
					elemVisible = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return elemVisible;
	}

	public boolean isReadonly(int maxIterationsToLocateElements) {
		boolean elemReadonly = false;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				if (WebElementUtil.isElementReadonly(appDriver, domObject)) {
					elemReadonly = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return elemReadonly;
	}

	public boolean isDisabled(int maxIterationsToLocateElements) {
		boolean elemDisabled = false;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				if (WebElementUtil.isElementDisabled(appDriver, domObject)) {
					elemDisabled = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return elemDisabled;
	}
	
	public boolean isDisabledButNotReadonly(int maxIterationsToLocateElements) {
		boolean elemDisabled = false;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				if (WebElementUtil.isElementDisabledButNotReadonly(appDriver, domObject)) {
					elemDisabled = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return elemDisabled;
	}

	/**
	 * Determine whether or not this element is selected or not. This operation only
	 * applies to input elements such as checkboxes, options in a select and radio
	 * buttons.
	 * 
	 * @return True if the element is currently selected or checked, false
	 *         otherwise.
	 */
	public boolean isSelected(int maxIterationsToLocateElements) {
		boolean elemSelected = false;
		for (int i = 0; i <= maxIterationsToLocateElements; i++) {
			try {
				if (WebElementUtil.isElementSelected(appDriver, domObject)) {
					elemSelected = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == maxIterationsToLocateElements) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return elemSelected;
	}

	/**
	 * Finds text from element when element is present in HTML.
	 * 
	 * @param maxIterationsToLocateElements
	 */
	public String getText(int maxIterationsToLocateElements) {
		try {
			return WebElementUtil.getElementText(appDriver, domObject, maxIterationsToLocateElements);
		} catch (Throwable th) {
			Assert.fail("Failed to get text from element '" + domObject.getDisplayName() + "'.", th);
		}
		return null;
	}

	/**
	 * Finds text from element when it is not hidden.
	 * 
	 * @param maxIterationsToLocateElements
	 */
	public String getTextWhenElementVisible(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					if (isVisible(0)) {
						return getText(0);
					}
					Assert.fail("Text is not visible.");
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to get text from visible element '" + domObject.getDisplayName() + "'.", th);
		}
		return null;
	}

	/**
	 * Finds text from visible element when it's value is not empty. Leading and
	 * trailing whitespace will be removed.
	 * 
	 * @param maxIterationsToLocateElements
	 */
	public String getTextWhenElementValueNonEmpty(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					if (isVisible(0)) {
						String value = getText(0);
						if (!(value == null || "".equals(value.trim()))) {
							return value;
						}
						Assert.fail("Text is empty.");
					}
					Assert.fail("Text is not visible.");
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Failed to get non-empty text from visible element '" + domObject.getDisplayName() + "'.", th);
		}
		return null;
	}

	/**
	 * Validates whether the element is visible with non-empty text on screen.
	 * 
	 * @param maxIterationsToLocateElements
	 */
	public DOMObjectValidator validatePresentWithNonEmptyText(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i <= maxIterationsToLocateElements; i++) {
				try {
					if (isVisible(0)) {
						String value = getText(0);
						if (!(value == null || "".equals(value.trim()))) {
							return this;
						}
						Assert.fail("Text is empty.");
					}
					Assert.fail("Text is not visible.");
				} catch (Throwable th) {
					if (i == maxIterationsToLocateElements) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Element '" + domObject.getDisplayName() + "' is not visible with non-empty text.", th);
		}
		return this;
	}

	/**
	 * Copy text into clipboard from the current cursor position. Applicable only
	 * for editable fields i.e textbox, textarea etc. First it will click on that
	 * element and then select all text and copy into clipboard.
	 * 
	 * @return
	 */
	public DOMObjectValidator copyTextToClipboard(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(maxIterationsToLocateElements);
					PageScrollUtil.mouseClick(appDriver, webElem);

					Actions webActions = new Actions(appDriver.getWebDriver());
					if(OSDetectorUtil.getHostPlatform() == PlatformType.mac || OSDetectorUtil.getHostPlatform() == PlatformType.ios_mobile) {
						webActions.sendKeys(Keys.chord(Keys.COMMAND, "a")).sendKeys(Keys.chord(Keys.COMMAND, "c"));
					} else {
						webActions.sendKeys(Keys.chord(Keys.CONTROL, "a")).sendKeys(Keys.chord(Keys.CONTROL, "c"));
					}
					
					break;
				} catch (MoveTargetOutOfBoundsException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to copy contents into clipboard. Element '" + domObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	/**
	 * Replace the content of the element with the clipboard contents. Applicable
	 * only for editable fields i.e textbox, textarea etc.
	 * 
	 * @return
	 */
	public DOMObjectValidator pasteTextFromClipboard(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(maxIterationsToLocateElements);
					PageScrollUtil.mouseClick(appDriver, webElem);

					Actions webActions = new Actions(appDriver.getWebDriver());
					if(OSDetectorUtil.getHostPlatform() == PlatformType.mac || OSDetectorUtil.getHostPlatform() == PlatformType.ios_mobile) {
						webActions.sendKeys(Keys.chord(Keys.COMMAND, "a")).sendKeys(Keys.chord(Keys.COMMAND, "v")).build()
						.perform();
					} else {
						webActions.sendKeys(Keys.chord(Keys.CONTROL, "a")).sendKeys(Keys.chord(Keys.CONTROL, "v")).build()
						.perform();
					}
					
					break;
				} catch (MoveTargetOutOfBoundsException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to paste clipboard contents into field '" + domObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public DOMObjectValidator click(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(maxIterationsToLocateElements);
					PageScrollUtil.mouseClick(appDriver, webElem);
					break;
				} catch (MoveTargetOutOfBoundsException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on element '" + domObject.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public DOMObjectValidator forceClick(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(maxIterationsToLocateElements);
					PageScrollUtil.mouseForceClick(appDriver, webElem);
					break;
				} catch (MoveTargetOutOfBoundsException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse force click on element '" + domObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public DOMObjectValidator doubleClick(int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(maxIterationsToLocateElements);
					PageScrollUtil.mouseDoubleClick(appDriver, webElem);
					break;
				} catch (MoveTargetOutOfBoundsException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on element '" + domObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	@Override
	public DOMObjectValidator rightClick(int maxIterationsToLocateElements) {
		try {
			WebElement webElem = findElement(maxIterationsToLocateElements);
			Point location = webElem.getLocation();
			Dimension size = webElem.getSize();

			appDriver.getSikuliScreen()
					.rightClick(new Region(location.getX(), location.getY(), size.getWidth(), size.getHeight()));
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on element '" + domObject.getDisplayName() + "'.", th);
		}
		return this;
	}

	public DOMObjectValidator clickAndHold(int maxIterationsToLocateElements) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(maxIterationsToLocateElements);
				PageScrollUtil.mouseClickAndHold(appDriver, webElem);
				break;
			} catch (MoveTargetOutOfBoundsException ex) {
				appDriver.waitForSeconds(2);
			}
		}
		return this;
	}

	public DOMObjectValidator release(int maxIterationsToLocateElements) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(maxIterationsToLocateElements);
				PageScrollUtil.mouseRelease(appDriver, webElem);
				break;
			} catch (MoveTargetOutOfBoundsException ex) {
				appDriver.waitForSeconds(2);
			}
		}
		return this;
	}

	public DOMObjectValidator dragAndDrop(DOMObject target, int maxIterationsToLocateElements) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement sourceElem = findElement(maxIterationsToLocateElements);
					WebElement targetElem = target.getValidator(appDriver, region).findElement(maxIterationsToLocateElements);

					PageScrollUtil.mouseDragAndDrop(appDriver, sourceElem, targetElem);
					break;
				} catch (MoveTargetOutOfBoundsException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to perform dragAndDrop from source '" + domObject.getDisplayName() + "' to target '"
					+ target.getDisplayName() + "'.", th);
		}
		return this;
	}
	
	@Override
	public DOMObjectValidator mouseHoverOver(int maxIterationsToLocateElements) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(maxIterationsToLocateElements);
				PageScrollUtil.mouseHoverOver(appDriver, webElem);
				break;
			} catch (MoveTargetOutOfBoundsException ex) {
				appDriver.waitForSeconds(2);
			}
		}
		return this;
	}

	public DOMObjectValidator performKeyDown(Keys keys, int maxIterationsToLocateElements) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(maxIterationsToLocateElements);
				Actions actions = new Actions(appDriver.getWebDriver());
				actions.keyDown(webElem, keys).build().perform();
				break;
			} catch (MoveTargetOutOfBoundsException ex) {
				appDriver.waitForSeconds(2);
			}
		}
		return this;
	}

	public DOMObjectValidator performKeyUp(Keys keys, int maxIterationsToLocateElements) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(maxIterationsToLocateElements);
				Actions actions = new Actions(appDriver.getWebDriver());
				actions.keyUp(webElem, keys).build().perform();
				break;
			} catch (MoveTargetOutOfBoundsException ex) {
				appDriver.waitForSeconds(2);
			}
		}
		return this;
	}

	public DOMObjectValidator performKeyPressed(Keys keys, int maxIterationsToLocateElements) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(maxIterationsToLocateElements);
				Actions actions = new Actions(appDriver.getWebDriver());
				actions.keyDown(webElem, keys).keyUp(webElem, keys).build().perform();
				break;
			} catch (MoveTargetOutOfBoundsException ex) {
				appDriver.waitForSeconds(2);
			}
		}
		return this;
	}

	public DOMObjectValidator typeText(String text, NewTextLocation location, int maxIterationsToLocateElements) {
		return typeText(text, location, 0, true, maxIterationsToLocateElements);
	}
	
	/**
	 * Used to type the text on textbox and textarea componenets.
	 * 
	 * @param text - text to type
	 * @param location - location where to start typing.
	 * @param typeSpeedInMspc - type speed in milliseconds per character.
	 * @param clickBeforeType - should click before typing.
	 * @param maxIterationsToLocateElements - max iteration to locate elements.
	 * @return
	 */
	public DOMObjectValidator typeText(String text, NewTextLocation location, int typeSpeedInMspc, boolean clickBeforeType, int maxIterationsToLocateElements) {
		String newtext;
		for (int i = 0; i < 5; i++) {
			try {				
				WebElement webElem = findElement(maxIterationsToLocateElements);
				if(clickBeforeType) {
					webElem.click();
				}

				switch (location) {
				case start:
					newtext = text;
					
					webElem.sendKeys(Keys.HOME);
					
					typeWithInterval(newtext, typeSpeedInMspc, webElem);
					
					break;
				case end:
					newtext = text;
					webElem.sendKeys(Keys.END);
					typeWithInterval(newtext, typeSpeedInMspc, webElem);
					break;
				case replace:
					if(OSDetectorUtil.getHostPlatform() == PlatformType.mac || OSDetectorUtil.getHostPlatform() == PlatformType.ios_mobile) {
						webElem.sendKeys(Keys.chord(Keys.COMMAND, "a"));
					} else {
						webElem.sendKeys(Keys.chord(Keys.CONTROL, "a"));
					}
					
					webElem.sendKeys(Keys.BACK_SPACE);
					typeWithInterval(text, typeSpeedInMspc, webElem);
					break;
				}

				break;
			} catch (MoveTargetOutOfBoundsException ex) {
				appDriver.waitForSeconds(2);
			}
		}
		return this;
	}
	
	public DOMObjectValidator sendKeys(int maxIterationsToLocateElements, CharSequence... keys) {
		WebElement webElem = findElement(maxIterationsToLocateElements);
		webElem.sendKeys(keys);
		return this;
	}

	@Override
	public Actions getNewSeleniumActions() {
		return new Actions(appDriver.getWebDriver());
	}
	
	public void typeWithInterval(String textToType, int typeSpeedInMspc, WebElement webElem) {
		Actions actions;
		if(typeSpeedInMspc < 1) {
			actions = new Actions(appDriver.getWebDriver());
			actions.sendKeys(webElem, textToType).build().perform();
		} else {
			if(textToType != null) {
				for(int ci = 0; ci < textToType.length(); ci++) {
					actions = new Actions(appDriver.getWebDriver());
					if(ci == 0) {
						actions.sendKeys(webElem, "" + textToType.charAt(ci)).build().perform();
					} else {
						actions.pause(typeSpeedInMspc).sendKeys(webElem, "" + textToType.charAt(ci)).build().perform();
					}
				}
			}
		}
	}
}
