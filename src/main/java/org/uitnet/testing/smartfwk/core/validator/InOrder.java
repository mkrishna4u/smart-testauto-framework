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
 * This is used to specify whether the values are in in order or not.
 * 
 * @author Madhav Krishna
 *
 */
public enum InOrder {
	YES("yes"),
	NO("no")
	;

	private String value;

	private InOrder(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static InOrder valueOf2(String value) {
		for (InOrder t : values()) {
			if (t.getValue().equalsIgnoreCase(value) || t.name().equalsIgnoreCase(value)) {
				return t;
			}
		}
		Assert.fail("InOrder value '" + value + "' is not supported.");
		return null;
	}

	@Override
	public String toString() {
		return value;
	}
}

