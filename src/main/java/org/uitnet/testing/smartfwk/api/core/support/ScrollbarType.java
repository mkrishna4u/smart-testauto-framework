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
package org.uitnet.testing.smartfwk.api.core.support;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public enum ScrollbarType {
	HORIZONTAL("horizontal"), VERTICAL("vertical");

	private String type;

	private ScrollbarType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static ScrollbarType valueOf2(String strType) {
		for (ScrollbarType type1 : values()) {
			if (type1.getType().equalsIgnoreCase(strType) || type1.name().equalsIgnoreCase(strType)) {
				return type1;
			}
		}
		Assert.fail("Scrollbar Type '" + strType + "' is not supported.");
		return null;
	}

	@Override
	public String toString() {
		return type;
	}

}
