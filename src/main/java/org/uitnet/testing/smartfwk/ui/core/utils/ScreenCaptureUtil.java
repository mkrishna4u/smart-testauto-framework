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
package org.uitnet.testing.smartfwk.ui.core.utils;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ScreenCaptureUtil {
	private static int screenshotId = 0;

	public static Dimension getScreenSize() {
		try {
			return Toolkit.getDefaultToolkit().getScreenSize();
		} catch(Throwable th) {
			return new Dimension(ScreenUtil.getScreenWidth(), ScreenUtil.getScreenHeight());
		}
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
	public static String capture(String dir, String testClassName, String testCaseName, Rectangle screenArea,
			SmartAppDriver appDriver) {
		String imageFile = null;
		try {
			Rectangle screenRectangle = screenArea;
			if (screenArea == null) {
				Dimension screenSize = getScreenSize();
				screenRectangle = new Rectangle(screenSize);
			}

			File dirObj = new File(dir);

			if (!dirObj.exists()) {
				dirObj.mkdirs();
			}

			if (testClassName == null) {
				imageFile = dir + File.separator + testCaseName + "-" + getNextScreenshotId() + ".png";
			} else {
				imageFile = dir + File.separator + testClassName + "-" + testCaseName + "-" + getNextScreenshotId()
						+ ".png";
			}

			captureScreenshot(screenRectangle, imageFile, appDriver);

		} catch (Exception ex) {
			Assert.fail("Failed to take screenshot.", ex);
		}
		return imageFile;
	}

	private synchronized static int getNextScreenshotId() {
		return ++screenshotId;
	}

	private synchronized static void captureScreenshot(Rectangle screenRectangle, String targetImageFileName,
			SmartAppDriver appDriver) throws AWTException, IOException {
		boolean preferDriverScreenshots = TestConfigManager.getInstance().preferDriverScreenshots();
		if (preferDriverScreenshots && appDriver != null) {
			TakesScreenshot takeScreenshot = (TakesScreenshot) appDriver.getWebDriver();
			File imageFile = takeScreenshot.getScreenshotAs(OutputType.FILE);
			File targetFile = new File(targetImageFileName);
			FileUtils.copyFile(imageFile, targetFile);
		} else {
			if(!GraphicsEnvironment.isHeadless()) {
				Robot robot = new Robot();
				BufferedImage image = robot.createScreenCapture(screenRectangle);
				ImageIO.write(image, "png", new File(targetImageFileName));
			} else {
				Assert.fail("ERROR: Due to headless server mode, screenshots can not be taken. "
						+ "Try prefer driver screenshot by setting preferDriverScreenshots property in TextConfig.yaml file.");
			}
		}
	}

}
