/*
 * SmartTestAutoFwk
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
package smartfwk.testing.ui.core.objects.scrollbar;

import java.awt.Rectangle;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.testng.Assert;

import smartfwk.testing.ui.core.commons.LocatorType;
import smartfwk.testing.ui.core.config.webbrowser.WebBrowser;
import smartfwk.testing.ui.core.objects.UIObject;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class Scrollbar {
	// protected WebBrowser browser;
	protected Scrollbar parent;
	protected HorizontalScrollbar hScrollbar;
	protected VerticalScrollbar vScrollbar;
	protected Region hScrollbarRegion;
	protected Region vScrollbarRegion;

	public Scrollbar(HorizontalScrollbar hScrollbar, Region hScrollbarRegion, VerticalScrollbar vScrollbar,
			Region vScrollbarRegion) {
		// this.browser = browser;

		this.hScrollbar = hScrollbar;
		this.hScrollbarRegion = hScrollbarRegion;

		this.vScrollbar = vScrollbar;
		this.vScrollbarRegion = vScrollbarRegion;
	}

	public Scrollbar getParent() {
		return parent;
	}

	public Scrollbar setParent(Scrollbar parent) {
		this.parent = parent;
		return this;
	}

	public VerticalScrollbar getVScrollbar() {
		return vScrollbar;
	}

	public HorizontalScrollbar getHScrollbar() {
		return hScrollbar;
	}

	public Rectangle findHScrollbarThumbGripLeftPartImageLocation() {
		if (hScrollbar != null) {
			Match match = hScrollbar.findThumbGripLeftPartImage(hScrollbarRegion);
			if (match != null) {
				return match.getRect();
			}
		}

		return null;
	}

	public Rectangle findVScrollbarThumbGripTopPartImageLocation() {
		if (vScrollbar != null) {
			Match match = vScrollbar.findThumbGripTopPartImage(vScrollbarRegion);
			if (match != null) {
				return match.getRect();
			}
		}

		return null;
	}
	
	public void scrollThumbGripToExtremeTop() {
		if(vScrollbar != null) {
			vScrollbar.scrollThumbGripToExtremeTop(vScrollbarRegion);
		}
	}
	
	public void scrollThumbGripToExtremeBottom() {
		if(vScrollbar != null) {
			vScrollbar.scrollThumbGripToExtremeBottom(vScrollbarRegion);
		}
	}
	
	public void scrollThumbGripToExtremeLeft() {
		if(hScrollbar != null) {
			hScrollbar.scrollThumbGripToExtremeLeft(hScrollbarRegion);
		}
	}
	
	public void scrollThumbGripToExtremeRight() {
		if(hScrollbar != null) {
			hScrollbar.scrollThumbGripToExtremeRight(hScrollbarRegion);
		}
	}

	/**
	 * Scroll element into
	 * 
	 * @param elemLocator
	 * @param searchDirection
	 *            direction to locate element from the current focus point.
	 */
	public void scrollElementToViewport(WebBrowser browser, UIObject elemLocator) {
		if (elemLocator.getLocatorType() == LocatorType.DOM) {
			UIObject domLocator = elemLocator;
			WebElement webElem = null;
			try {
				// Rectangle hScrollbarThumbGripCurrLocation  =
				// findHScrollbarThumbGripLeftPartImageLocation();
				System.out.println("AAA");
				Rectangle vScrollbarThumbGripCurrLocation = findVScrollbarThumbGripTopPartImageLocation();

				webElem = (WebElement) domLocator.getValidator(browser, null).findElementNoException(0);
				boolean hsbEndReached, vsbEndReached = false;
				boolean vsbTGCurrPointReached = false;
				int vsbEndReachedCounter = 0;
				do { // Move vertical scrollbar
					hsbEndReached = false;
					do { // Move horizontal scrollbar
						if (webElem != null) {
							if (hScrollbar != null) {
								Point webElemPoint = webElem.getLocation();
								Match hscM1 = hScrollbar.findLeftScrollImage(hScrollbarRegion);
								if (webElemPoint.getX() - hscM1.getX() <= 80) {
									break;
								} else if (hsbEndReached) {
									break;
								} else {
									hsbEndReached = hScrollbar.clickRightScrollImage(hScrollbarRegion, 2);
									webElem = (WebElement) domLocator.getValidator(browser, null)
											.findElementNoException(0);
									if (webElem == null) {
										hScrollbar.clickLeftScrollImage(hScrollbarRegion, 2);
										webElem = (WebElement) domLocator.getValidator(browser, null)
												.findElementNoException(0);
										break;
									}
								}
							} else {
								break;
							}
						} else {
							if (hScrollbar != null) {
								if (hsbEndReached) {
									hScrollbar.scrollThumbGripToExtremeLeft(hScrollbarRegion);
									break;
								} else {
									hsbEndReached = hScrollbar.clickRightScrollImage(hScrollbarRegion, 2);
									webElem = (WebElement) domLocator.getValidator(browser, null)
											.findElementNoException(0);
									continue;
								}
							} else {
								break;
							}
						}
					} while (true); // -----done ----

					if (webElem != null) {
						if (vScrollbar != null) {
							Point webElemPoint = webElem.getLocation();
							Match vscM1 = vScrollbar.findTopScrollImage(vScrollbarRegion);
							if (webElemPoint.getY() - vscM1.getY() <= 80) {
								break;
							} else if (vsbEndReached) {
								break;
							} else {
								vsbEndReached = vScrollbar.clickBottomScrollImage(vScrollbarRegion, 2);
								webElem = (WebElement) domLocator.getValidator(browser, null).findElementNoException(0);
								if (webElem == null) {
									vScrollbar.clickTopScrollImage(vScrollbarRegion, 2);
									webElem = (WebElement) domLocator.getValidator(browser, null)
											.findElementNoException(0);
									break;
								}
							}
						} else {
							break;
						}
					} else {
						if (vScrollbar != null) {
							if (vsbEndReachedCounter > 0 && vsbTGCurrPointReached) {
								break;
							} else if (vsbEndReachedCounter == 0 && vsbEndReached) {
								vScrollbar.scrollThumbGripToExtremeTop(vScrollbarRegion);
								vsbEndReached = false;
								vsbEndReachedCounter++;
								continue;
							} else {
								vsbEndReached = vScrollbar.clickBottomScrollImage(vScrollbarRegion, 2);
								Rectangle vScrollbarThumbGripRunLocation = findVScrollbarThumbGripTopPartImageLocation();
								if (vScrollbarThumbGripRunLocation.getY()
										- vScrollbarThumbGripCurrLocation.getY() >= 0) {
									vsbTGCurrPointReached = true;
								}
								webElem = (WebElement) domLocator.getValidator(browser, null).findElementNoException(0);
								continue;
							}
						} else {
							break;
						}
					}
				} while (true);

			} catch (Throwable th) {
				Assert.fail("Failed to scroll element '" + elemLocator.getDisplayName() + "' into view port.", th);
			}
			Assert.assertNotNull(webElem, "Failed to find element '" + elemLocator.getDisplayName() + "'");

		} else if (elemLocator.getLocatorType() == LocatorType.IMAGE) {
			UIObject imgLocator = elemLocator;
			Match match = null;
			try {
				// Rectangle hScrollbarThumbGripCurrLocation =
				// findHScrollbarThumbGripLeftPartImageLocation();
				Rectangle vScrollbarThumbGripCurrLocation = findVScrollbarThumbGripTopPartImageLocation();

				match = (Match) imgLocator.getValidator(browser, null).findElementNoException(0);
				boolean hsbEndReached, vsbEndReached = false;
				boolean vsbTGCurrPointReached = false;
				int vsbEndReachedCounter = 0;
				do { // Move vertical scrollbar
					hsbEndReached = false;
					do { // Move horizontal scrollbar
						if (match != null) {
							if (hScrollbar != null) {
								// Point webElemPoint = match.getLocation();
								Match hscM1 = hScrollbar.findLeftScrollImage(hScrollbarRegion);
								if (match.getX() - hscM1.getX() <= 80) {
									break;
								} else if (hsbEndReached) {
									break;
								} else {
									hsbEndReached = hScrollbar.clickRightScrollImage(hScrollbarRegion, 2);
									match = (Match) imgLocator.getValidator(browser, null).findElementNoException(0);
									if (match == null) {
										hScrollbar.clickLeftScrollImage(hScrollbarRegion, 2);
										match = (Match) imgLocator.getValidator(browser, null)
												.findElementNoException(0);
										break;
									}
								}
							} else {
								break;
							}
						} else {
							if (hScrollbar != null) {
								if (hsbEndReached) {
									hScrollbar.scrollThumbGripToExtremeLeft(hScrollbarRegion);
									break;
								} else {
									hsbEndReached = hScrollbar.clickRightScrollImage(hScrollbarRegion, 2);
									match = (Match) imgLocator.getValidator(browser, null).findElementNoException(0);
									continue;
								}
							} else {
								break;
							}
						}
					} while (true); // -----done ----

					if (match != null) {
						if (vScrollbar != null) {
							// Point webElemPoint = match.getLocation();
							Match vscM1 = vScrollbar.findTopScrollImage(vScrollbarRegion);
							if (match.getY() - vscM1.getY() <= 80) {
								break;
							} else if (vsbEndReached) {
								break;
							} else {
								vsbEndReached = vScrollbar.clickBottomScrollImage(vScrollbarRegion, 2);
								match = (Match) imgLocator.getValidator(browser, null).findElementNoException(0);
								if (match == null) {
									vScrollbar.clickTopScrollImage(vScrollbarRegion, 2);
									match = (Match) imgLocator.getValidator(browser, null).findElementNoException(0);
									break;
								}
							}
						} else {
							break;
						}
					} else {
						if (vScrollbar != null) {
							if (vsbEndReachedCounter > 0 && vsbTGCurrPointReached) {
								break;
							} else if (vsbEndReachedCounter == 0 && vsbEndReached) {
								vScrollbar.scrollThumbGripToExtremeTop(vScrollbarRegion);
								vsbEndReached = false;
								vsbEndReachedCounter++;
								continue;
							} else {
								vsbEndReached = vScrollbar.clickBottomScrollImage(vScrollbarRegion, 2);
								Rectangle vScrollbarThumbGripRunLocation = findVScrollbarThumbGripTopPartImageLocation();
								if (vScrollbarThumbGripRunLocation.getY()
										- vScrollbarThumbGripCurrLocation.getY() >= 0) {
									vsbTGCurrPointReached = true;
								}
								match = (Match) imgLocator.getValidator(browser, null).findElementNoException(0);
								continue;
							}
						} else {
							break;
						}
					}
				} while (true);

			} catch (Throwable th) {
				Assert.fail("Failed to scroll element '" + elemLocator.getDisplayName() + "' into view port.", th);
			}
			Assert.assertNotNull(match, "Failed to find element '" + elemLocator.getDisplayName() + "'");
		}

	}

}
