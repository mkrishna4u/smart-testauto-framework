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
package org.uitnet.testing.smartfwk.ui.core.config;

import org.testng.Assert;

/**
 * Test Platform Type on which testing automation can be performed.
 * 
 * @author Madhav Krishna
 *
 */
public enum PlatformType {
	unknown("unknown"), windows("windows"), linux("linux"), mac("mac"), android_mobile("android-mobile"),
	ios_mobile("ios-mobile");

	private String type;

	private PlatformType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static PlatformType valueOf2(String type) {
		for (PlatformType t : values()) {
			if (t.getType().equalsIgnoreCase(type)) {
				return t;
			}
		}
		Assert.fail("Platform type '" + type + "' is not supported.");
		return null;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
