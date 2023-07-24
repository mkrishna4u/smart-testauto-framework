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
package org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public enum TextMatchMechanism {
	exactMatchWithExpectedValue("exact-match-with-expected-value"), 
	startsWithExpectedValue("starts-with-expected-value"), 
	endsWithExpectedValue("ends-with-expected-value"), 
	containsExpectedValue("contains-expected-value"),
	matchWithRegularExpression("match-with-regular-expression"), 
	exactMatchWithExpectedValueAfterRemovingSpaces("exact-match-with-expected-value-after-removing-spaces"),
	
	/* Ignore case / Case insensitive text match mechanisms */
	icExactMatchWithExpectedValue("ic-exact-match-with-expected-value"), 
	icStartsWithExpectedValue("ic-starts-with-expected-value"), 
	icEndsWithExpectedValue("ic-ends-with-expected-value"), 
	icContainsExpectedValue("ic-contains-expected-value"),
	icExactMatchWithExpectedValueAfterRemovingSpaces("ic-exact-match-with-expected-value-after-removing-spaces")
	;
	
	private String value;
	
	private TextMatchMechanism(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public static TextMatchMechanism valueOf2(String value) {
		for (TextMatchMechanism t : values()) {
			if (t.getValue().equalsIgnoreCase(value) || t.name().equalsIgnoreCase(value)) {
				return t;
			}
		}
		Assert.fail("Text match mechanism '" + value + "' is not supported.");
		return null;
	}

	@Override
	public String toString() {
		return value;
	}
}
