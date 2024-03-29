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
 * 
 * @author Madhav Krishna
 *
 */
public enum WebBrowserType {
	firefox("firefox"), chrome("chrome"), edge("edge"), opera("opera"), safari("safari"),
	internetExplorer("internet-explorer"), notApplicable("not-applicable");

	private String type;

	private WebBrowserType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static WebBrowserType valueOf2(String strType) {
		for (WebBrowserType type1 : values()) {
			if (type1.getType().equalsIgnoreCase(strType) || type1.name().equalsIgnoreCase(strType)) {
				return type1;
			}
		}
		Assert.fail("Web Browser Type '" + strType + "' is not supported.");
		return null;
	}
	
	@Override
	public String toString() {
		return type;
	}
}
