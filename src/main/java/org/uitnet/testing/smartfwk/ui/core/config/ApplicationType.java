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
package org.uitnet.testing.smartfwk.ui.core.config;

import org.testng.Assert;

/**
 * Application types for which the testing can be performed.
 * 
 * @author Madhav Krishna
 *
 */
public enum ApplicationType {
	/**
	 * Native apps are the app that are like .exe, .apk, .app or .api kind (UI
	 * application), which doesn't use web browser.
	 */
	native_app("native-app"),

	/**
	 * Web apps are the app that use the web browser.
	 */
	web_app("web-app"),
	
	/**
	 * Undefined.
	 */
	not_applicable("na");;

	private String type;

	private ApplicationType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static ApplicationType valueOf2(String type) {
		for (ApplicationType t : values()) {
			if (t.getType().equals(type)) {
				return t;
			}
		}
		Assert.fail("Application type '" + type + "' is not supported.");
		return null;
	}
}
