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
package org.uitnet.testing.smartfwk.ui.core.objects;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public enum NewTextLocation {
	start("start"), end("end"), replace("replace");

	private String value;

	private NewTextLocation(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public static NewTextLocation valueOf2(String value) {
		for (NewTextLocation t : values()) {
			if (t.getValue().equals(value.toLowerCase()) || t.name().equalsIgnoreCase(value)) {
				return t;
			}
		}
		Assert.fail("Text location '" + value + "' is not supported.");
		return null;
	}

	@Override
	public String toString() {
		return value;
	}
}
