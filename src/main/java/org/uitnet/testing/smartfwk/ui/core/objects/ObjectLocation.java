/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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
import java.util.List;

import org.openqa.selenium.WebElement;
import org.sikuli.script.Match;
import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.commons.UIObjectType;
import org.uitnet.testing.smartfwk.ui.core.utils.ScreenUtil;

/**
 * 
 * @author Madhav Krishna
 */
public class ObjectLocation {
	private ObjectLocationType objLocType;
	private int x, y, width, height;
	private UIObject refObject;
	private ReferenceObjectPosition refObjectPosition;
	private int refObjectDistanceInPx;
	private int leftMarginOfRefObjectInPx;

	public ObjectLocation() {
		this.objLocType = ObjectLocationType.WITHIN_SCREEN_LIMIT;
		this.refObjectDistanceInPx = 0;
		this.leftMarginOfRefObjectInPx = 10;
	}

	/**
	 * Object is within this limit.
	 * 
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public ObjectLocation(int x, int y, int width, int height) {
		this.objLocType = ObjectLocationType.WITHIN_CUSTOM_LIMIT;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.refObjectDistanceInPx = 0;
		this.leftMarginOfRefObjectInPx = 10;
	}

	/**
	 * Object is within this area.
	 * 
	 * @param area
	 */
	public ObjectLocation(Rectangle area) {
		this.objLocType = ObjectLocationType.WITHIN_CUSTOM_LIMIT;
		this.x = area.x;
		this.y = area.y;
		this.width = area.width;
		this.height = area.height;
		this.refObjectDistanceInPx = 0;
		this.leftMarginOfRefObjectInPx = 10;
	}

	/**
	 * Object is nearby reference object.
	 * 
	 * @param refObject
	 * @param refObjectPosition
	 * @param refObjDistanceInPx approximate distance of ref object from actual
	 *                           object to determine object area where it can be
	 *                           found.
	 */
	public ObjectLocation(UIObject refObject, ReferenceObjectPosition refObjectPosition, int refObjDistanceInPx) {
		this.objLocType = ObjectLocationType.RELATIVE_TO_REF_OBJ;
		this.refObject = refObject;
		this.refObjectPosition = refObjectPosition;
		this.refObjectDistanceInPx = refObjDistanceInPx;
		this.leftMarginOfRefObjectInPx = 10;
	}

	/**
	 * Left Margin of the ref object is applicable if ReferenceObjectPosition is TOP
	 * or Bottom. It is used to determine the object distance from left side
	 * refObject.
	 * 
	 * @param leftMarginOfRefObjectInPx
	 * @return
	 */
	public ObjectLocation setLeftMarginOfRefObject(int leftMarginOfRefObjectInPx) {
		if (leftMarginOfRefObjectInPx > 10) {
			this.leftMarginOfRefObjectInPx = leftMarginOfRefObjectInPx;
		} else {
			this.leftMarginOfRefObjectInPx = 10;
		}
		return this;
	}

	public Rectangle getRectangleOfImageObject(SmartAppDriver appDriver, String leftSideImageOfImageObject,
			String rightSideImageOfImageObject) {
		switch (objLocType) {
		case WITHIN_CUSTOM_LIMIT:
		case WITHIN_SCREEN_LIMIT:
			Region region = null;
			if (objLocType == ObjectLocationType.WITHIN_SCREEN_LIMIT) {
				region = ScreenUtil.createRegionWithNoError(appDriver.getSikuliScreen().getRect());
			} else {
				region = ScreenUtil.createRegion(x, y, width, height);
			}

			region.setAutoWaitTimeout(1);
			ImageObject obj = new ImageObject(UIObjectType.leftSideImageOfRectangle, "Left Side Image",
					leftSideImageOfImageObject);
			List<Match> m1 = obj.getValidator(appDriver, region).findElements(3);
			obj = new ImageObject(UIObjectType.rightSideImageOfRectangle, "Right Side Image",
					rightSideImageOfImageObject);
			List<Match> m2 = obj.getValidator(appDriver, region).findElements(3);

			Rectangle objDim = new Rectangle();
			objDim.x = m1.get(0).x;
			objDim.y = m1.get(0).y;
			objDim.width = m2.get(0).x - objDim.x;
			objDim.height = m2.get(0).y - objDim.y;

			return objDim;
		case RELATIVE_TO_REF_OBJ:
			return calculateRectangleOfImgObjUsingRefObj(appDriver, leftSideImageOfImageObject,
					rightSideImageOfImageObject);
		}
		return null;
	}

	public Rectangle getRectangleOfImageObject(SmartAppDriver appDriver, int width, int height) {
		switch (objLocType) {
		case WITHIN_CUSTOM_LIMIT:
		case WITHIN_SCREEN_LIMIT:
			Assert.fail("Size based components not supported for WITHIN_CUSTOM_LIMIT, WITHIN_SCREEN_LIMIT.");
			return null;
		case RELATIVE_TO_REF_OBJ:
			return calculateRectangleOfImgObjUsingRefObj(appDriver, width, height);
		}
		return null;
	}

	/**
	 * Applicable if ObjectLocationType = RELATIVE_TO_REF_OBJ
	 * 
	 * @param appDriver
	 * @param leftSideImageOfImageObject
	 * @param rightSideImageOfImageObject
	 * @return
	 */
	private Rectangle calculateRectangleOfImgObjUsingRefObj(SmartAppDriver appDriver, String leftSideImageOfImageObject,
			String rightSideImageOfImageObject) {
		Rectangle rect = null;
		ImageObject leftSideImgObj = new ImageObject(UIObjectType.leftSideImageOfRectangle, "Left Side Image",
				leftSideImageOfImageObject);
		ImageObjectValidator imgValidator = leftSideImgObj.getValidator(appDriver, null);
		// imgValidator.getRegion().setAutoWaitTimeout(1);
		List<Match> leftSideImgMatches = imgValidator.findElements(3);

		ImageObject rightSideImgObj = new ImageObject(UIObjectType.rightSideImageOfRectangle, "Right Side Image",
				rightSideImageOfImageObject);
		imgValidator = rightSideImgObj.getValidator(appDriver, null);
		imgValidator.getRegion().setAutoWaitTimeout(1);
		List<Match> rightSideImgMatches = imgValidator.findElements(0);

		Object refElem = refObject.getValidator(appDriver, null).findElement(0);
		Rectangle refElemRect = null;
		if (refElem instanceof Match) {
			Match refElemAsImg = (Match) refElem;
			refElemRect = refElemAsImg.getRect();
		} else if (refElem instanceof WebElement) {
			WebElement refElemAsDOM = (WebElement) refElem;
			refElemRect = new Rectangle(refElemAsDOM.getRect().getX(), refElemAsDOM.getRect().getY(),
					refElemAsDOM.getRect().getWidth(), refElemAsDOM.getRect().getHeight());
		}

		Assert.assertNotNull(refElemRect, "Failed to find reference element '" + refObject.getDisplayName() + "'.");

		int refObjX1 = Double.valueOf(refElemRect.getX()).intValue();
		int refObjY1 = Double.valueOf(refElemRect.getY()).intValue();
		int refObjX2 = Double.valueOf(refElemRect.getX() + refElemRect.getWidth()).intValue();
		int refObjY2 = Double.valueOf(refElemRect.getY() + refElemRect.getHeight()).intValue();

		int refObjCenterY;
		int refObjXDistanceFromM1;
		int refObjXDistanceFromM2;
		int objectRangeFromRefObject;
		int nearestRightSideMatchX2;
		int leftObjX2;
		Match nearestLeftSideMatch;
		Match nearestRightSideMatch;

		switch (refObjectPosition) {
		case LEFT:
			refObjCenterY = refObjY1 + (refObjY2 - refObjY1) / 2;
			// Find nearest leftSideImage match relative to ref object
			refObjXDistanceFromM1 = 999999999;
			nearestLeftSideMatch = null;
			for (Match m1 : leftSideImgMatches) {
				int m1Y2 = m1.getY() + m1.getH();
				int m1X2 = m1.getX() + m1.getW();
				if ((refObjCenterY >= m1.getY() && refObjCenterY <= m1Y2) && (m1X2 >= refObjX2)
						&& (m1X2 - refObjX2) < refObjXDistanceFromM1) {
					refObjXDistanceFromM1 = m1X2 - refObjX2;
					nearestLeftSideMatch = m1;
				}
			}

			Assert.assertNotNull(nearestLeftSideMatch,
					"Failed to find nearest leftSideImage '" + leftSideImageOfImageObject
							+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			objectRangeFromRefObject = refObjX2 + refObjectDistanceInPx;
			Assert.assertTrue(nearestLeftSideMatch.getX() <= objectRangeFromRefObject,
					"Failed to find object within the horizontal range (" + refObjX2 + ", " + objectRangeFromRefObject
							+ "). Probably you can increase refObjectDistanceInPx (" + refObjectDistanceInPx
							+ ") parameter value.");

			// Find nearest rightSideImage match relative to leftSideImage
			refObjXDistanceFromM2 = 999999999;
			nearestRightSideMatch = null;
			for (Match m2 : rightSideImgMatches) {
				int m2Y2 = m2.getY() + m2.getH();
				if ((refObjCenterY >= m2.getY() && refObjCenterY <= m2Y2) && (m2.getX() >= nearestLeftSideMatch.getX())
						&& (m2.getX() - nearestLeftSideMatch.getX()) < refObjXDistanceFromM2
						&& (m2.getX() > (nearestLeftSideMatch.getX() + nearestLeftSideMatch.getW()))) {
					refObjXDistanceFromM2 = m2.getX() - nearestLeftSideMatch.getX();
					nearestRightSideMatch = m2;
				}
			}

			Assert.assertNotNull(nearestRightSideMatch,
					"Failed to find nearest rightSideImage '" + rightSideImageOfImageObject
							+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			rect = new Rectangle(nearestLeftSideMatch.getX() + nearestLeftSideMatch.getW(), nearestLeftSideMatch.getY(),
					nearestRightSideMatch.getX() - nearestLeftSideMatch.getX(), nearestLeftSideMatch.getH());
			break;

		case RIGHT:
			refObjCenterY = refObjY1 + (refObjY2 - refObjY1) / 2;
			// Find nearest rightSideImage match relative to ref object
			refObjXDistanceFromM1 = 999999999;
			nearestRightSideMatch = null;
			for (Match m1 : rightSideImgMatches) {
				int m1Y2 = m1.getY() + m1.getH();
				if ((refObjCenterY >= m1.getY() && refObjCenterY <= m1Y2) && (refObjX1 >= m1.getX())
						&& (refObjX1 - m1.getX()) < refObjXDistanceFromM1) {
					refObjXDistanceFromM1 = refObjX1 - m1.getX();
					nearestRightSideMatch = m1;
				}
			}

			Assert.assertNotNull(nearestRightSideMatch,
					"Failed to find nearest rightSideImage '" + rightSideImageOfImageObject
							+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			objectRangeFromRefObject = refObjX1 - refObjectDistanceInPx;
			nearestRightSideMatchX2 = nearestRightSideMatch.getX() + nearestRightSideMatch.getW();
			Assert.assertTrue(nearestRightSideMatchX2 >= objectRangeFromRefObject,
					"Failed to find object within the horizontal range (" + refObjX1 + ", " + objectRangeFromRefObject
							+ "). Probably you can increase refObjectDistanceInPx (" + refObjectDistanceInPx
							+ ") parameter value.");

			// Find nearest leftSideImage match relative to rightSideImage
			refObjXDistanceFromM2 = 999999999;
			nearestLeftSideMatch = null;
			for (Match m2 : leftSideImgMatches) {
				int m2Y2 = m2.getY() + m2.getH();
				if ((refObjCenterY >= m2.getY() && refObjCenterY <= m2Y2) && (nearestRightSideMatchX2 >= m2.getX())
						&& (nearestRightSideMatchX2 - m2.getX()) < refObjXDistanceFromM2
						&& (nearestRightSideMatchX2 > (m2.getX() + m2.getW()))) {
					refObjXDistanceFromM2 = nearestRightSideMatch.getX() - m2.getX();
					nearestLeftSideMatch = m2;
				}
			}

			Assert.assertNotNull(nearestLeftSideMatch,
					"Failed to find nearest leftSideImage '" + leftSideImageOfImageObject
							+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			rect = new Rectangle(nearestLeftSideMatch.getX() + nearestLeftSideMatch.getW(), nearestLeftSideMatch.getY(),
					nearestRightSideMatch.getX() - nearestLeftSideMatch.getX(), nearestLeftSideMatch.getH());
			break;

		case TOP:
			refObjCenterY = refObjY2 + refObjectDistanceInPx;
			leftObjX2 = refObjX1 - leftMarginOfRefObjectInPx;
			// Find nearest leftSideImage match relative to ref object
			refObjXDistanceFromM1 = 999999999;
			nearestLeftSideMatch = null;
			for (Match m1 : leftSideImgMatches) {
				int m1Y2 = m1.getY() + m1.getH();
				if ((refObjCenterY >= m1.getY() && refObjCenterY <= m1Y2) && (m1.getX() >= leftObjX2)
						&& (m1.getX() - leftObjX2) < refObjXDistanceFromM1) {
					refObjXDistanceFromM1 = m1.getX() - leftObjX2;
					nearestLeftSideMatch = m1;
				}
			}

			Assert.assertNotNull(nearestLeftSideMatch,
					"Failed to find nearest leftSideImage '" + leftSideImageOfImageObject
							+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			objectRangeFromRefObject = leftObjX2 + Double.valueOf(refElemRect.getWidth()).intValue();
			Assert.assertTrue(nearestLeftSideMatch.getX() <= objectRangeFromRefObject,
					"Failed to find object within the horizontal range (" + leftObjX2 + ", " + objectRangeFromRefObject
							+ "). Probably you can increase refObject size by taking the bigger horizontal snapshot.");

			// Find nearest rightSideImage match relative to leftSideImage
			refObjXDistanceFromM2 = 999999999;
			nearestRightSideMatch = null;
			for (Match m2 : rightSideImgMatches) {
				int m2Y2 = m2.getY() + m2.getH();
				if ((refObjCenterY >= m2.getY() && refObjCenterY <= m2Y2) && (m2.getX() >= nearestLeftSideMatch.getX())
						&& (m2.getX() - nearestLeftSideMatch.getX()) < refObjXDistanceFromM2
						&& (m2.getX() > (nearestLeftSideMatch.getX() + nearestLeftSideMatch.getW()))) {
					refObjXDistanceFromM2 = m2.getX() - nearestLeftSideMatch.getX();
					nearestRightSideMatch = m2;
				}
			}

			Assert.assertNotNull(nearestRightSideMatch,
					"Failed to find nearest rightSideImage '" + rightSideImageOfImageObject
							+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			rect = new Rectangle(nearestLeftSideMatch.getX() + nearestLeftSideMatch.getW(), nearestLeftSideMatch.getY(),
					nearestRightSideMatch.getX() - nearestLeftSideMatch.getX(), nearestLeftSideMatch.getH());
			break;

		case BOTTOM:
			refObjCenterY = refObjY1 - refObjectDistanceInPx;
			leftObjX2 = refObjX1 - leftMarginOfRefObjectInPx;
			// Find nearest leftSideImage match relative to ref object
			refObjXDistanceFromM1 = 999999999;
			nearestLeftSideMatch = null;
			for (Match m1 : leftSideImgMatches) {
				int m1Y2 = m1.getY() + m1.getH();
				if ((refObjCenterY >= m1.getY() && refObjCenterY <= m1Y2) && (m1.getX() >= leftObjX2)
						&& (m1.getX() - leftObjX2) < refObjXDistanceFromM1) {
					refObjXDistanceFromM1 = m1.getX() - leftObjX2;
					nearestLeftSideMatch = m1;
				}
			}

			Assert.assertNotNull(nearestLeftSideMatch,
					"Failed to find nearest leftSideImage '" + leftSideImageOfImageObject
							+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			objectRangeFromRefObject = leftObjX2 + Double.valueOf(refElemRect.getWidth()).intValue();
			Assert.assertTrue(nearestLeftSideMatch.getX() <= objectRangeFromRefObject,
					"Failed to find object within the horizontal range (" + leftObjX2 + ", " + objectRangeFromRefObject
							+ "). Probably you can increase refObject size by taking the bigger horizontal snapshot.");

			// Find nearest rightSideImage match relative to leftSideImage
			refObjXDistanceFromM2 = 999999999;
			nearestRightSideMatch = null;
			for (Match m2 : rightSideImgMatches) {
				int m2Y2 = m2.getY() + m2.getH();
				if ((refObjCenterY >= m2.getY() && refObjCenterY <= m2Y2) && (m2.getX() >= nearestLeftSideMatch.getX())
						&& (m2.getX() - nearestLeftSideMatch.getX()) < refObjXDistanceFromM2
						&& (m2.getX() > (nearestLeftSideMatch.getX() + nearestLeftSideMatch.getW()))) {
					refObjXDistanceFromM2 = m2.getX() - nearestLeftSideMatch.getX();
					nearestRightSideMatch = m2;
				}
			}

			Assert.assertNotNull(nearestRightSideMatch,
					"Failed to find nearest rightSideImage '" + rightSideImageOfImageObject
							+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			rect = new Rectangle(nearestLeftSideMatch.getX() + nearestLeftSideMatch.getW(), nearestLeftSideMatch.getY(),
					nearestRightSideMatch.getX() - nearestLeftSideMatch.getX(), nearestLeftSideMatch.getH());
			break;
		}

		return rect;
	}

	private Rectangle calculateRectangleOfImgObjUsingRefObj(SmartAppDriver appDriver, int width, int height) {
		Rectangle rect = null;

		Object refElem = refObject.getValidator(appDriver, null).findElement(0);
		Rectangle refElemRect = null;
		if (refElem instanceof Match) {
			Match refElemAsImg = (Match) refElem;
			refElemRect = refElemAsImg.getRect();
		} else if (refElem instanceof WebElement) {
			WebElement refElemAsDOM = (WebElement) refElem;
			refElemRect = new Rectangle(refElemAsDOM.getRect().getX(), refElemAsDOM.getRect().getY(),
					refElemAsDOM.getRect().getWidth(), refElemAsDOM.getRect().getHeight());
		}

		Assert.assertNotNull(refElemRect, "Failed to find reference element '" + refObject.getDisplayName() + "'.");

		int refObjX1 = Double.valueOf(refElemRect.getX()).intValue();
		int refObjY1 = Double.valueOf(refElemRect.getY()).intValue();
		int refObjX2 = Double.valueOf(refElemRect.getX() + refElemRect.getWidth()).intValue();
		int refObjY2 = Double.valueOf(refElemRect.getY() + refElemRect.getHeight()).intValue();

		int elemX1, elemY1;

		switch (refObjectPosition) {
		case LEFT:
			elemX1 = refObjX2;
			elemY1 = refObjY1 + refObjectDistanceInPx;

			rect = new Rectangle(elemX1, elemY1, width, height);
			break;

		case RIGHT:
			elemX1 = refObjX1 - width;
			elemY1 = refObjY1 + refObjectDistanceInPx;

			rect = new Rectangle(elemX1, elemY1, width, height);
			break;

		case TOP:
			elemX1 = refObjX1 - leftMarginOfRefObjectInPx;
			elemY1 = refObjY2;

			rect = new Rectangle(elemX1, elemY1, width, height);
			break;

		case BOTTOM:
			elemX1 = refObjX1 - leftMarginOfRefObjectInPx;
			elemY1 = refObjY1 - height;

			rect = new Rectangle(elemX1, elemY1, width, height);
			break;
		}

		return rect;
	}

	/**
	 * Applicable if ObjectLocationType = RELATIVE_TO_REF_OBJ
	 * 
	 * @param appDriver
	 * @param imageObject
	 * @return
	 */
	private Rectangle calculateRectangleOfImgObjUsingRefObj(SmartAppDriver appDriver, String imageObject) {
		Rectangle rect = null;
		ImageObject imgObject = new ImageObject(UIObjectType.image, "Image Object", imageObject);
		ImageObjectValidator imgValidator = imgObject.getValidator(appDriver, null);
		imgValidator.getRegion().setAutoWaitTimeout(1);
		List<Match> imgMatches = imgValidator.findElements(3);

		Object refElem = refObject.getValidator(appDriver, null).findElement(0);
		Rectangle refElemRect = null;
		if (refElem instanceof Match) {
			Match refElemAsImg = (Match) refElem;
			refElemRect = refElemAsImg.getRect();
		} else if (refElem instanceof WebElement) {
			WebElement refElemAsDOM = (WebElement) refElem;
			refElemRect = new Rectangle(refElemAsDOM.getRect().getX(), refElemAsDOM.getRect().getY(),
					refElemAsDOM.getRect().getWidth(), refElemAsDOM.getRect().getHeight());
		}

		Assert.assertNotNull(refElemRect, "Failed to find reference element '" + refObject.getDisplayName() + "'.");

		int refObjX1 = Double.valueOf(refElemRect.getX()).intValue();
		int refObjY1 = Double.valueOf(refElemRect.getY()).intValue();
		int refObjX2 = Double.valueOf(refElemRect.getX() + refElemRect.getWidth()).intValue();
		int refObjY2 = Double.valueOf(refElemRect.getY() + refElemRect.getHeight()).intValue();

		int refObjCenterY;
		int refObjXDistanceFromM1;
		int objectRangeFromRefObject;
		int leftObjX2;
		Match nearestImageMatch;

		switch (refObjectPosition) {
		case LEFT:
			refObjCenterY = refObjY1 + (refObjY2 - refObjY1) / 2;
			// Find nearest leftSideImage match relative to ref object
			refObjXDistanceFromM1 = 999999999;
			nearestImageMatch = null;
			for (Match m1 : imgMatches) {
				int m1Y2 = m1.getY() + m1.getH();
				if ((refObjCenterY >= m1.getY() && refObjCenterY <= m1Y2) && (m1.getX() >= refObjX2)
						&& (m1.getX() - refObjX2) < refObjXDistanceFromM1) {
					refObjXDistanceFromM1 = m1.getX() - refObjX2;
					nearestImageMatch = m1;
				}
			}

			Assert.assertNotNull(nearestImageMatch, "Failed to find nearest image '" + imageObject
					+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			objectRangeFromRefObject = refObjX2 + refObjectDistanceInPx;
			Assert.assertTrue(nearestImageMatch.getX() <= objectRangeFromRefObject,
					"Failed to find object within the horizontal range (" + refObjX2 + ", " + objectRangeFromRefObject
							+ "). Probably you can increase refObjectDistanceInPx (" + refObjectDistanceInPx
							+ ") parameter value.");

			rect = nearestImageMatch.getRect();
			break;

		case RIGHT:
			refObjCenterY = refObjY1 + (refObjY2 - refObjY1) / 2;
			// Find nearest rightSideImage match relative to ref object
			refObjXDistanceFromM1 = 999999999;
			nearestImageMatch = null;
			for (Match m1 : imgMatches) {
				int m1Y2 = m1.getY() + m1.getH();
				if ((refObjCenterY >= m1.getY() && refObjCenterY <= m1Y2) && (refObjX2 >= m1.getX())
						&& (refObjX2 - m1.getX()) < refObjXDistanceFromM1) {
					refObjXDistanceFromM1 = refObjX2 - m1.getX();
					nearestImageMatch = m1;
				}
			}

			Assert.assertNotNull(nearestImageMatch, "Failed to find nearest image '" + imageObject
					+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			objectRangeFromRefObject = refObjX1 - refObjectDistanceInPx;
			Assert.assertTrue(nearestImageMatch.getX() >= objectRangeFromRefObject,
					"Failed to find object within the horizontal range (" + refObjX1 + ", " + objectRangeFromRefObject
							+ "). Probably you can increase refObjectDistanceInPx (" + refObjectDistanceInPx
							+ ") parameter value.");

			rect = nearestImageMatch.getRect();
			break;

		case TOP:
			refObjCenterY = refObjY2 + refObjectDistanceInPx;
			leftObjX2 = refObjX1 - leftMarginOfRefObjectInPx;
			// Find nearest leftSideImage match relative to ref object
			refObjXDistanceFromM1 = 999999999;
			nearestImageMatch = null;
			for (Match m1 : imgMatches) {
				int m1Y2 = m1.getY() + m1.getH();
				if ((refObjCenterY >= m1.getY() && refObjCenterY <= m1Y2) && (m1.getX() >= leftObjX2)
						&& (m1.getX() - leftObjX2) < refObjXDistanceFromM1) {
					refObjXDistanceFromM1 = m1.getX() - leftObjX2;
					nearestImageMatch = m1;
				}
			}

			Assert.assertNotNull(nearestImageMatch, "Failed to find nearest leftSideImage '" + imageObject
					+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			objectRangeFromRefObject = leftObjX2 + Double.valueOf(refElemRect.getWidth()).intValue();
			Assert.assertTrue(nearestImageMatch.getX() <= objectRangeFromRefObject,
					"Failed to find object within the horizontal range (" + leftObjX2 + ", " + objectRangeFromRefObject
							+ "). Probably you can increase refObject size by taking the bigger horizontal snapshot.");

			rect = nearestImageMatch.getRect();
			break;

		case BOTTOM:
			refObjCenterY = refObjY1 - refObjectDistanceInPx;
			leftObjX2 = refObjX1 - leftMarginOfRefObjectInPx;
			// Find nearest leftSideImage match relative to ref object
			refObjXDistanceFromM1 = 999999999;
			nearestImageMatch = null;
			for (Match m1 : imgMatches) {
				int m1Y2 = m1.getY() + m1.getH();
				if ((refObjCenterY >= m1.getY() && refObjCenterY <= m1Y2) && (m1.getX() >= leftObjX2)
						&& (m1.getX() - leftObjX2) < refObjXDistanceFromM1) {
					refObjXDistanceFromM1 = m1.getX() - leftObjX2;
					nearestImageMatch = m1;
				}
			}

			Assert.assertNotNull(nearestImageMatch, "Failed to find nearest leftSideImage '" + imageObject
					+ "' relative to refernce object '" + refObject.getDisplayName() + "'.");

			objectRangeFromRefObject = leftObjX2 + Double.valueOf(refElemRect.getWidth()).intValue();
			Assert.assertTrue(nearestImageMatch.getX() <= objectRangeFromRefObject,
					"Failed to find object within the horizontal range (" + leftObjX2 + ", " + objectRangeFromRefObject
							+ "). Probably you can increase refObject size by taking the bigger horizontal snapshot.");

			rect = nearestImageMatch.getRect();
			break;
		}

		return rect;
	}

	public Rectangle getImageObjectMatch(SmartAppDriver appDriver, String imageObject) {
		switch (objLocType) {
		case WITHIN_CUSTOM_LIMIT:
		case WITHIN_SCREEN_LIMIT:
			Region region = null;
			if (objLocType == ObjectLocationType.WITHIN_SCREEN_LIMIT) {
				region = ScreenUtil.createRegionWithNoError(appDriver.getSikuliScreen().getRect());
			} else {
				region = ScreenUtil.createRegion(x, y, width, height);
			}

			region.setAutoWaitTimeout(1);
			ImageObject obj = new ImageObject(UIObjectType.image, "Image Object", imageObject);
			List<Match> m1 = obj.getValidator(appDriver, region).findElements(3);

			Rectangle objDim = m1.get(0).getRect();
			return objDim;
		case RELATIVE_TO_REF_OBJ:
			return calculateRectangleOfImgObjUsingRefObj(appDriver, imageObject);
		}
		return null;
	}

	/**
	 * This is applicable only for WITHIN LIMIT object.
	 * 
	 * @return
	 */
	public Region getRegion(SmartAppDriver appDriver) {
		if (ObjectLocationType.WITHIN_CUSTOM_LIMIT == objLocType) {
			return ScreenUtil.createRegion(x, y, width, height);
		} else if (ObjectLocationType.WITHIN_SCREEN_LIMIT == objLocType) {
			return ScreenUtil.createRegionWithNoError(appDriver.getSikuliScreen().getRect());
		}

		Assert.fail("getRegion() api is applicable only for absolute location.");
		return null;
	}

	public Region getRegionOfImageObject(SmartAppDriver appDriver, String leftSideImageOfImageObject,
			String rightSideImageOfImageObject) {
		return ScreenUtil.createRegionWithNoError(
				getRectangleOfImageObject(appDriver, leftSideImageOfImageObject, rightSideImageOfImageObject));

	}

	public Region getRegionOfImageObject(SmartAppDriver appDriver, int width, int height) {
		return ScreenUtil.createRegionWithNoError(getRectangleOfImageObject(appDriver, width, height));

	}

	public Region getRegionOfImageObject(SmartAppDriver appDriver, String imageObject) {
		return ScreenUtil.createRegionWithNoError(getImageObjectMatch(appDriver, imageObject));

	}
}
