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

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import org.sikuli.script.Region;
import org.sikuli.script.Screen;
import org.testng.Assert;

/**
 * @Author Madhav Krishna
 */
public class ScreenUtil {
	
	private ScreenUtil() {
		
	}
	
	public static int getScreenWidth() {
		int screenWidth = 1600;
		try {
			screenWidth = Screen.getPrimaryScreen().w;
		} catch(Throwable th) {
			// do nothing.
		}
		
		return screenWidth;
	}
	
	public static int getScreenHeight() {
		int screenHeight = 1600;
		try {
			screenHeight = Screen.getPrimaryScreen().h;
		} catch(Throwable th) {
			// do nothing.
		}
		
		return screenHeight;
	}
	
	public static Rectangle getPrimaryScreenRectangle() {
		Rectangle rect = null;
		try {
			rect = Screen.getPrimaryScreen().getRect();
		} catch(Throwable th) {
			rect = new Rectangle(getScreenWidth(), getScreenHeight());
		}
		
		return rect;
	}
	
	public static Region createRegionWithNoError(Rectangle rect) {
		Region region = null;
		try {
			region = Region.create(rect);
		} catch(Throwable th) {
			// do nothing
		}
		
		return region;
	}
	
	public static Region createRegion(int x1, int y1, int width, int height) {
		Region region = null;
		try {
			region = Region.create(x1, y1, width, height);
		} catch(Throwable th) {
			if(GraphicsEnvironment.isHeadless()) {
				Assert.fail("ERROR: Due to headless server mode. Screen verification using Sikuli is disabled.");
			}
			throw th;
		}
		
		return region;
	}
}