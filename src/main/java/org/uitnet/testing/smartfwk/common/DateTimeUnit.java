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
package org.uitnet.testing.smartfwk.common;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public enum DateTimeUnit {
	years, months, weeks, days, hours, minutes, seconds, milliseconds;

	public static DateTimeUnit valueOf2(String unitAsStr) {
		for (DateTimeUnit type1 : values()) {
			if (type1.name().equalsIgnoreCase(unitAsStr)) {
				return type1;
			}
		}
		Assert.fail("Date Time unit '" + unitAsStr + "' is not supported.");
		return null;
	}
}
