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
import org.openqa.selenium.ElementNotVisibleException;
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
import org.uitnet.testing.smartfwk.ui.core.objects.scrollbar.Scrollbar;
import org.uitnet.testing.smartfwk.ui.core.utils.LocatorUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.PageScrollUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.WebAttrMapUtil;

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
	 * @param numRetries
	 * @return
	 */
	public String getAttributeValue(String attributeName, int numRetries) {
		try {
			WebElement webElem = findElement(numRetries);
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
	 * @param numRetries
	 */
	public String getAttributeValueWhenElementVisible(String attributeName, int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					if (isVisible(0)) {
						return getAttributeValue(attributeName, 0);
					}
					Assert.fail("Element is not visible.");
				} catch (Throwable th) {
					if (i == numRetries) {
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
	 * @param numRetries
	 */
	public String getAttributeValueWhenAttributeValueNonEmpty(String attributeName, int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					String value = getAttributeValue(attributeName, 0);
					if (!(value == null || "".equals(value.trim()))) {
						return value;
					}
					Assert.fail("Attribute value is empty.");
				} catch (Throwable th) {
					if (i == numRetries) {
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
	 * Finds first element with polling and it polls after 2 seconds for numRetries
	 * times.
	 * 
	 * @param numRetries
	 * @return
	 */
	@Override
	public WebElement findElement(int numRetries) {
		WebElement webElem = null;
		for (int i = 0; i <= numRetries; i++) {
			try {
				// appDriver.getWebDriver().manage().timeouts().pageLoadTimeout(30,
				// TimeUnit.SECONDS);
				// appDriver.getWebDriver().manage().timeouts().implicitlyWait(10,
				// TimeUnit.SECONDS);
				webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(webElem, "Unable to find element '" + domObject.getDisplayName() + "'.");
				PageScrollUtil.scrollElemToViewport(appDriver, webElem);
				// System.out.println(domObject.getDisplayName() + ", DISPLAYED:
				// " + webElem.isDisplayed() + ", " + webElem.isEnabled());
				// Assert.assertTrue(webElem.isDisplayed(), "Unable to find
				// element '" + domObject.getDisplayName() + "'. Reason - Not
				// displayed.");
				break;
			} catch (Throwable th) {
				if (i == numRetries) {
					Assert.fail("Unable to find element '" + domObject.getDisplayName()
							+ "'. Reason timeout(waited for " + (numRetries * 2) + " seconds).", th);
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
	 * Finds first element with polling and it polls after 2 seconds for numRetries
	 * times. It does not throw any exception
	 * 
	 * @param numRetries
	 * @return
	 */
	@Override
	public WebElement findElementNoException(int numRetries) {
		WebElement webElem = null;
		for (int i = 0; i <= numRetries; i++) {
			try {
				webElem = LocatorUtil.findWebElement(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				if (webElem != null) {
					break;
				}
				Assert.fail();
			} catch (Throwable th) {
				webElem = null;
				if (i == numRetries) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return webElem;
	}

	/**
	 * This returns all the elements based on the locator. It waits for the
	 * configured timeout if the element is not present. Performs polling numRetries
	 * times.
	 * 
	 * @param numRetries
	 * @return
	 */
	@Override
	public List<WebElement> findElements(int numRetries) {
		List<WebElement> webElems = null;
		for (int i = 0; i <= numRetries; i++) {
			try {
				webElems = LocatorUtil.findWebElements(appDriver.getWebDriver(),
						domObject.getLocator(appDriver.getAppConfig().getTestPlatformType(),
								appDriver.getAppConfig().getAppType(), appDriver.getAppConfig().getAppWebBrowser()));
				Assert.assertNotNull(webElems, "Unable to find elements for '" + domObject.getDisplayName() + "'.");
				Assert.assertTrue(webElems.size() > 0,
						"Unable to find elements for '" + domObject.getDisplayName() + "' locator.");
				break;
			} catch (Throwable th) {
				if (i == numRetries) {
					Assert.fail("Unable to find elements for '" + uiObject.getDisplayName()
							+ "' locator. Reason timeout(waited for " + (numRetries * 2) + " seconds).", th);
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
	 * @param numRetries
	 * @return
	 */
	@Override
	public boolean isPresent(int numRetries) {
		boolean elemPresent = false;
		WebElement webElem = null;
		for (int i = 0; i <= numRetries; i++) {
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
				if (i == numRetries) {
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
	 * @param numRetries
	 * @return
	 */
	@Override
	public boolean isVisible(int numRetries) {
		boolean elemVisible = false;
		for (int i = 0; i <= numRetries; i++) {
			try {
				if (WebAttrMapUtil.isElementVisible(appDriver, domObject)) {
					elemVisible = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == numRetries) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return elemVisible;
	}

	public boolean isReadonly(int numRetries) {
		boolean elemReadonly = false;
		for (int i = 0; i <= numRetries; i++) {
			try {
				if (WebAttrMapUtil.isElementReadonly(appDriver, domObject)) {
					elemReadonly = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == numRetries) {
					break;
				}
			}
			appDriver.waitForSeconds(2);
		}
		return elemReadonly;
	}

	public boolean isDisabled(int numRetries) {
		boolean elemDisabled = false;
		for (int i = 0; i <= numRetries; i++) {
			try {
				if (WebAttrMapUtil.isElementDisabled(appDriver, domObject)) {
					elemDisabled = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == numRetries) {
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
	public boolean isSelected(int numRetries) {
		boolean elemSelected = false;
		for (int i = 0; i <= numRetries; i++) {
			try {
				if (WebAttrMapUtil.isElementSelected(appDriver, domObject)) {
					elemSelected = true;
					break;
				}

				Assert.fail();
			} catch (Throwable th) {
				if (i == numRetries) {
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
	 * @param numRetries
	 */
	public String getText(int numRetries) {
		try {
			return WebAttrMapUtil.getElementText(appDriver, domObject, numRetries);
		} catch (Throwable th) {
			Assert.fail("Failed to get text from element '" + domObject.getDisplayName() + "'.", th);
		}
		return null;
	}

	/**
	 * Finds text from element when it is not hidden.
	 * 
	 * @param numRetries
	 */
	public String getTextWhenElementVisible(int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					if (isVisible(0)) {
						return getText(0);
					}
					Assert.fail("Text is not visible.");
				} catch (Throwable th) {
					if (i == numRetries) {
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
	 * @param numRetries
	 */
	public String getTextWhenElementValueNonEmpty(int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
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
					if (i == numRetries) {
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
	 * @param numRetries
	 */
	public void validatePresentWithNonEmptyText(int numRetries) {
		try {
			for (int i = 0; i <= numRetries; i++) {
				try {
					if (isVisible(0)) {
						String value = getText(0);
						if (!(value == null || "".equals(value.trim()))) {
							return;
						}
						Assert.fail("Text is empty.");
					}
					Assert.fail("Text is not visible.");
				} catch (Throwable th) {
					if (i == numRetries) {
						throw th;
					}
				}
				appDriver.waitForSeconds(2);
			}
		} catch (Throwable th) {
			Assert.fail("Element '" + domObject.getDisplayName() + "' is not visible with non-empty text.", th);
		}
	}

	/**
	 * Copy text into clipboard from the current cursor position. Applicable only
	 * for editable fields i.e textbox, textarea etc. First it will click on that
	 * element and then select all text and copy into clipboard.
	 * 
	 * @return
	 */
	public void copyTextToClipboard(int numRetries) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(numRetries);
					PageScrollUtil.scrollToViewportAndClick(appDriver, webElem);

					Actions webActions = new Actions(appDriver.getWebDriver());
					webActions.sendKeys(Keys.chord(Keys.CONTROL, "a")).sendKeys(Keys.chord(Keys.CONTROL, "c"));
					break;
				} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to copy contents into clipboard. Element '" + domObject.getDisplayName() + "'.", th);
		}
	}

	/**
	 * Replace the content of the element with the clipboard contents. Applicable
	 * only for editable fields i.e textbox, textarea etc.
	 * 
	 * @return
	 */
	public void pasteTextFromClipboard(int numRetries) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(numRetries);
					PageScrollUtil.scrollToViewportAndClick(appDriver, webElem);

					Actions webActions = new Actions(appDriver.getWebDriver());
					webActions.sendKeys(Keys.chord(Keys.CONTROL, "a")).sendKeys(Keys.chord(Keys.CONTROL, "v")).build()
							.perform();
					break;
				} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to paste clipboard contents into field '" + domObject.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void click(int numRetries) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(numRetries);
					PageScrollUtil.scrollToViewportAndClick(appDriver, webElem);
					break;
				} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse click on element '" + domObject.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void doubleClick(int numRetries) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement webElem = findElement(numRetries);
					PageScrollUtil.scrollToViewportAndDoubleClick(appDriver, webElem);
					break;
				} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse double click on element '" + domObject.getDisplayName() + "'.", th);
		}
	}

	@Override
	public void rightClick(int numRetries) {
		try {
			WebElement webElem = findElement(numRetries);
			Point location = webElem.getLocation();
			Dimension size = webElem.getSize();

			appDriver.getSikuliScreen()
					.rightClick(new Region(location.getX(), location.getY(), size.getWidth(), size.getHeight()));
		} catch (Throwable th) {
			Assert.fail("Failed to perform mouse right click on element '" + domObject.getDisplayName() + "'.", th);
		}
	}

	public void clickAndHold(int numRetries) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(numRetries);
				PageScrollUtil.scrollToViewportAndClickAndHold(appDriver, webElem);
				break;
			} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
				appDriver.waitForSeconds(2);
			}
		}
	}

	public void release(int numRetries) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(numRetries);
				PageScrollUtil.scrollToViewportAndRelease(appDriver, webElem);
				break;
			} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
				appDriver.waitForSeconds(2);
			}
		}
	}

	public void dragAndDrop(DOMObject target, int numRetries) {
		try {
			for (int i = 0; i < 5; i++) {
				try {
					WebElement sourceElem = findElement(numRetries);
					WebElement targetElem = target.getValidator(appDriver, region).findElement(numRetries);

					PageScrollUtil.scrollToViewportAndDragAndDrop(appDriver, sourceElem, targetElem);
					break;
				} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
					appDriver.waitForSeconds(2);
				}
			}
		} catch (Throwable th) {
			Assert.fail("Failed to perform dragAndDrop from source '" + domObject.getDisplayName() + "' to target '"
					+ target.getDisplayName() + "'.", th);
		}
	}

	public void performKeyDown(Keys keys, int numRetries) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(numRetries);
				Actions actions = new Actions(appDriver.getWebDriver());
				actions.keyDown(webElem, keys).build().perform();
				break;
			} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
				appDriver.waitForSeconds(2);
			}
		}
	}

	public void performKeyUp(Keys keys, int numRetries) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(numRetries);
				Actions actions = new Actions(appDriver.getWebDriver());
				actions.keyUp(webElem, keys).build().perform();
				break;
			} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
				appDriver.waitForSeconds(2);
			}
		}
	}

	public void performKeyPressed(Keys keys, int numRetries) {
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(numRetries);
				Actions actions = new Actions(appDriver.getWebDriver());
				actions.keyDown(webElem, keys).keyUp(webElem, keys).build().perform();
				break;
			} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
				appDriver.waitForSeconds(2);
			}
		}
	}

	public void typeText(String text, NewTextLocation location, int numRetries) {
		String newtext;
		Actions actions;
		for (int i = 0; i < 5; i++) {
			try {
				WebElement webElem = findElement(numRetries);

				switch (location) {
				case start:
					newtext = text;
					actions = new Actions(appDriver.getWebDriver());
					webElem.sendKeys(Keys.HOME);
					actions.sendKeys(webElem, newtext).build().perform();
					break;
				case end:
					newtext = text;
					actions = new Actions(appDriver.getWebDriver());
					webElem.sendKeys(Keys.END);
					actions.sendKeys(webElem, newtext).build().perform();
					break;
				case replace:
					webElem.sendKeys(Keys.chord(Keys.CONTROL, "a"));
					webElem.sendKeys(Keys.BACK_SPACE);
					actions = new Actions(appDriver.getWebDriver());
					actions.sendKeys(webElem, text).build().perform();
					break;
				}

				break;
			} catch (MoveTargetOutOfBoundsException | ElementNotVisibleException ex) {
				appDriver.waitForSeconds(2);
			}
		}
	}

}
