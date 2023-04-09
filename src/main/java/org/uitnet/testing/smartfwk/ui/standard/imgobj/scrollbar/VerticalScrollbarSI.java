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
package org.uitnet.testing.smartfwk.ui.standard.imgobj.scrollbar;

import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class VerticalScrollbarSI {
	private static Logger logger = LoggerFactory.getLogger(VerticalScrollbarSI.class);
	protected String thumbGripTopPartImage;
	protected String thumbGripTopPartImageFocused;
	protected String thumbGripBottomPartImage;
	protected String thumbGripBottomPartImageFocused;
	protected String topScrollImageEnabled;
	protected String topScrollImageEnabledFocused;
	protected String bottomScrollImageEnabled;
	protected String bottomScrollImageEnabledFocused;
	protected String topScrollImageDisabled;
	protected String bottomScrollImageDisabled;

	public VerticalScrollbarSI(String thumbGripTopPartImage, String thumbGripTopPartImageFocused,
			String thumbGripBottomPartImage, String thumbGripBottomPartImageFocused, String topScrollImageEnabled,
			String topScrollImageEnabledFocused, String bottomScrollImageEnabled,
			String bottomScrollImageEnabledFocused, String topScrollImageDisabled, String bottomScrollImageDisabled) {
		this.thumbGripTopPartImage = thumbGripTopPartImage;
		this.thumbGripTopPartImageFocused = thumbGripTopPartImageFocused;
		this.thumbGripBottomPartImage = thumbGripBottomPartImage;
		this.thumbGripBottomPartImageFocused = thumbGripBottomPartImageFocused;
		this.topScrollImageEnabled = topScrollImageEnabled;
		this.topScrollImageEnabledFocused = topScrollImageEnabledFocused;
		this.bottomScrollImageEnabled = bottomScrollImageEnabled;
		this.bottomScrollImageEnabledFocused = bottomScrollImageEnabledFocused;
		this.topScrollImageDisabled = topScrollImageDisabled;
		this.bottomScrollImageDisabled = bottomScrollImageDisabled;
	}

	public boolean isThumbGripTopPartImageVisible(Region region) {
		try {
			Match thumbGripFound = region.find(thumbGripTopPartImage);
			if (thumbGripFound != null) {
				return true;
			}
		} catch (Throwable th) {
		}

		try {
			Match thumbGripFound = region.find(thumbGripTopPartImageFocused);
			if (thumbGripFound != null) {
				return true;
			}
		} catch (Throwable th1) {

		}
		return false;
	}

	public boolean isThumbGripBottomPartImageVisible(Region region) {
		try {
			Match thumbGripFound = region.find(thumbGripBottomPartImage);
			if (thumbGripFound != null) {
				return true;
			}
		} catch (Throwable th) {
		}

		try {
			Match thumbGripFound = region.find(thumbGripBottomPartImageFocused);
			if (thumbGripFound != null) {
				return true;
			}
		} catch (Throwable th1) {

		}
		return false;
	}

	public boolean isTopScrollImageVisible(Region region) {
		try {
			Match bottomScrollFound = region.find(topScrollImageEnabled);

			if (bottomScrollFound != null) {
				return true;
			}
		} catch (Throwable th) {
		}

		try {
			Match bottomScrollFound = region.find(topScrollImageEnabledFocused);

			if (bottomScrollFound != null) {
				return true;
			}
		} catch (Throwable th1) {
		}
		return false;
	}

	public boolean isBottomScrollImageVisible(Region region) {
		try {
			Match bottomScrollFound = region.find(bottomScrollImageEnabled);

			if (bottomScrollFound != null) {
				return true;
			}
		} catch (Throwable th) {
		}

		try {
			Match bottomScrollFound = region.find(bottomScrollImageEnabledFocused);

			if (bottomScrollFound != null) {
				return true;
			}
		} catch (Throwable th1) {
		}
		return false;
	}

	public Match findThumbGripTopPartImage(Region region) {
		try {
			Match match = region.find(thumbGripTopPartImage);

			if (match != null) {
				return match;
			}
		} catch (Throwable th) {
		}

		try {
			Match match = region.find(thumbGripTopPartImageFocused);

			if (match != null) {
				return match;
			}
		} catch (Throwable th1) {
		}
		return null;
	}

	public Match findThumbGripBottomPartImage(Region region) {
		try {
			Match match = region.find(thumbGripBottomPartImage);

			if (match != null) {
				return match;
			}
		} catch (Throwable th) {
		}

		try {
			Match match = region.find(thumbGripBottomPartImageFocused);

			if (match != null) {
				return match;
			}
		} catch (Throwable th1) {
		}
		return null;
	}

	public Match findTopScrollImage(Region region) {
		try {
			Match match = region.find(topScrollImageEnabled);

			if (match != null) {
				return match;
			}
		} catch (Throwable th) {
		}

		try {
			Match match = region.find(topScrollImageEnabledFocused);

			if (match != null) {
				return match;
			}
		} catch (Throwable th1) {
		}
		return null;
	}

	public Match findBottomScrollImage(Region region) {
		try {
			Match match = region.find(bottomScrollImageEnabled);

			if (match != null) {
				return match;
			}
		} catch (Throwable th) {
		}

		try {
			Match match = region.find(bottomScrollImageEnabledFocused);

			if (match != null) {
				return match;
			}
		} catch (Throwable th1) {
		}
		return null;
	}

	public boolean isFullScrollbarVisible(Region region) {
		try {
			if (isTopScrollImageVisible(region) && isBottomScrollImageVisible(region)) {
				return true;
			}
		} catch (Throwable th) {
		}
		return false;
	}

	public void validateFullScrollbarVisible(Region region) {
		Assert.assertTrue(isTopScrollImageVisible(region) && isBottomScrollImageVisible(region),
				"Full horizontal scrollbar is not visible.");
	}

	public boolean isScrollbarDisabled(Region region) {
		Match found;
		try {
			found = region.find(topScrollImageDisabled);
			if (found != null) {
				return true;
			}
		} catch (Throwable th) {
		}

		try {
			found = region.find(bottomScrollImageDisabled);
			if (found != null) {
				return true;
			}
		} catch (Throwable th1) {
		}
		return false;
	}

	public void validateScrollbarDisabled(Region region) {
		Assert.assertTrue(isScrollbarDisabled(region), "Horizontal scrollbar is not disabled.");
	}

	private boolean isImagesCollidedVertically(Match m1, Match m2) {
		if (m1 == null || m2 == null) {
			return false;
		}

		double p1 = m1.getRect().getY() + m1.getRect().getHeight();
		double p2 = m2.getRect().getY();
		if (p2 - p1 <= 5) {
			return true;
		}

		p1 = m2.getRect().getY() + m2.getRect().getHeight();
		p2 = m1.getRect().getY();
		if (p1 - p2 <= 5) {
			return true;
		}

		return false;
	}

	/**
	 * Clicks top scroll image n times and returns true if topThumbGrip is scrolled
	 * to extreme top else returns false.
	 * 
	 * @param region
	 * @param times
	 * @return
	 */
	public boolean clickTopScrollImage(Region region, int times) {
		try {
			Match found = findTopScrollImage(region);
			if (found != null) {
				for (int i = 0; i < times; i++) {
					found.click();
				}

				Match thumbGripTopPartImgFound = findThumbGripTopPartImage(region);
				return isImagesCollidedVertically(found, thumbGripTopPartImgFound);
			} else {
				logger.error("Error during clickTopScrollImage.");
				Assert.fail("Error during clickTopScrollImage.");
			}
		} catch (Throwable th) {
			logger.error("Error during clickTopScrollImage.", th);
			Assert.fail("Error during clickTopScrollImage.", th);
		}
		return false;
	}

	public boolean clickBottomScrollImage(Region region, int times) {
		try {
			Match found = findBottomScrollImage(region);
			if (found != null) {
				for (int i = 0; i < times; i++) {
					found.click();
				}
				Match thumbGripBottomPartImgFound = findThumbGripBottomPartImage(region);
				return isImagesCollidedVertically(thumbGripBottomPartImgFound, found);
			}
		} catch (Throwable th) {
			logger.error("Error during clickBottomScrollImage.", th);
			Assert.fail("Error during clickBottomScrollImage.", th);
		}
		return false;
	}

	public void scrollThumbGripToExtremeTop(Region region) {
		try {
			Match thumbGripTopPartImageFound = findThumbGripTopPartImage(region);
			Match topScrollImageEnabledFound = findTopScrollImage(region);
			if (thumbGripTopPartImageFound != null && topScrollImageEnabledFound != null) {
				thumbGripTopPartImageFound.drag(thumbGripTopPartImageFound);
				thumbGripTopPartImageFound.dropAt(topScrollImageEnabledFound);
				System.out.println("AA");
			} else if (topScrollImageEnabledFound != null) {
				do {
					topScrollImageEnabledFound.click();
					thumbGripTopPartImageFound = findThumbGripTopPartImage(region);
					if (thumbGripTopPartImageFound != null) {
						thumbGripTopPartImageFound.drag(thumbGripTopPartImageFound);
						thumbGripTopPartImageFound.dropAt(topScrollImageEnabledFound);
						break;
					}
				} while (true);
			}
		} catch (Throwable th) {
			logger.error("Error during scrollThumbGripToExtremeTop.", th);
			Assert.fail("Error during scrollThumbGripToExtremeTop.", th);
		}
	}

	public void scrollThumbGripToExtremeBottom(Region region) {
		try {
			Match thumbGripBottomPartImageFound = findThumbGripBottomPartImage(region);
			Match bottomScrollImageEnabledFound = findBottomScrollImage(region);
			if (thumbGripBottomPartImageFound != null && bottomScrollImageEnabledFound != null) {
				thumbGripBottomPartImageFound.drag(thumbGripBottomPartImageFound);
				thumbGripBottomPartImageFound.dropAt(bottomScrollImageEnabledFound);
			} else if (bottomScrollImageEnabledFound != null) {
				do {
					bottomScrollImageEnabledFound.click();
					thumbGripBottomPartImageFound = findThumbGripBottomPartImage(region);
					if (thumbGripBottomPartImageFound != null) {
						thumbGripBottomPartImageFound.drag(thumbGripBottomPartImageFound);
						thumbGripBottomPartImageFound.dropAt(bottomScrollImageEnabledFound);
						break;
					}
				} while (true);
			}
		} catch (Throwable th) {
			logger.error("Error during scrollThumbGripToExtremeBottom.", th);
			Assert.fail("Error during scrollThumbGripToExtremeBottom.", th);
		}
	}
}
