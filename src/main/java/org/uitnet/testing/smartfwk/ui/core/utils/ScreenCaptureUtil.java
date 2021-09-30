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
package org.uitnet.testing.smartfwk.ui.core.utils;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ScreenCaptureUtil {
	private static int screenshotId = 0;

	public static Dimension getScreenSize() {
		return Toolkit.getDefaultToolkit().getScreenSize();
	}

	/**
	 * Captures screenshot and return the name of screenshot.
	 * 
	 * @param dir
	 * @param testClassName
	 * @param testCaseName
	 * @param screenArea
	 * @return
	 */
	public static String capture(String dir, String testClassName, String testCaseName, Rectangle screenArea) {
		String imageFile = null;
		try {
			Rectangle screenRectangle = screenArea;
			if (screenArea == null) {
				Dimension screenSize = getScreenSize();
				screenRectangle = new Rectangle(screenSize);
			}

			Robot robot = new Robot();
			BufferedImage image = robot.createScreenCapture(screenRectangle);

			File dirObj = new File(dir);

			if (!dirObj.exists()) {
				dirObj.mkdirs();
//				Assert.assertTrue(dirObj.mkdirs(),
//						"Failed to create the directory '" + dir
//								+ "' for saving the screenshots.");
			}

			if (testClassName == null) {
				imageFile = dir + File.separator + testCaseName + "-" + getNextScreenshotId() + ".png";
			} else {
				imageFile = dir + File.separator + testClassName + "-" + testCaseName + "-" + getNextScreenshotId()
						+ ".png";
			}

			ImageIO.write(image, "png", new File(imageFile));
		} catch (Exception ex) {
			Assert.fail("Failed to take screenshot.", ex);
		}
		return imageFile;
	}

	private synchronized static int getNextScreenshotId() {
		return ++screenshotId;
	}

}
