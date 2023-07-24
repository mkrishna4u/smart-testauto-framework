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

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PullDownMenuInfo {
	protected int width;
	protected int height;
	protected PullDownMenuLocation location;

	/**
	 * 
	 * @param width    in pixel. value < 1 indicate calculate automatically.
	 * @param height   in pixel. value < 1 indicate calculate automatically.
	 * @param location
	 */
	public PullDownMenuInfo(int width, int height, PullDownMenuLocation location) {
		this.width = width;
		this.height = height;
		this.location = location;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public PullDownMenuLocation getLocation() {
		return location;
	}
}
