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
package org.uitnet.testing.smartfwk.core.validator;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public enum ParamValueType {
	STRING("string"), 
	STRING_LIST("string-list"), 
	INTEGER("integer"), 
	INTEGER_LIST("integer-list"), 
	DECIMAL("decimal"), 
	DECIMAL_LIST("decimal-list"),
	BOOLEAN("boolean"),
	BOOLEAN_LIST("boolean-list");

	private String type;

	private ParamValueType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static ParamValueType valueOf2(String type) {
		for (ParamValueType t : values()) {
			if (t.getType().equalsIgnoreCase(type) || t.name().equalsIgnoreCase(type)) {
				return t;
			}
		}
		Assert.fail("Parameter type '" + type + "' is not supported.");
		return null;
	}

	@Override
	public String toString() {
		return type;
	}
}
