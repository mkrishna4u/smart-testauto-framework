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
package smartfwk.testing.ui.core.utils;

import org.testng.Assert;

import smartfwk.testing.ui.core.objects.validator.mechanisms.TextValidationMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DataMatchUtil {

	public static boolean matchTextValue(String actualValue, String expectedValue,
			TextValidationMechanism validationMechanism) {
		boolean matched = false;

		if (actualValue == null) {
			return false;
		}

		switch (validationMechanism) {
		case startsWithExpectedValue:
			if (actualValue.startsWith(expectedValue)) {
				matched = true;
			}
			break;
		case containsExpectedValue:
			if (actualValue.contains(expectedValue)) {
				matched = true;
			}
			break;
		case endsWithExpectedValue:
			if (actualValue.contains(expectedValue)) {
				matched = true;
			}
			break;
		case exactMatchWithExpectedValue:
			if (actualValue.equals(expectedValue)) {
				matched = true;
			}
			break;
		case matchWithRegularExpression:
			if (actualValue.matches(expectedValue)) {
				matched = true;
			}
			break;
		case exactMatchWithExpectedValueWithRemovedWhiteSpace:
			if (actualValue.replaceAll(" ", "").equals(expectedValue.replaceAll(" ", ""))) {
				matched = true;
			}
			break;
		}

		return matched;
	}

	public static void validateTextValue(String actualValue, String expectedValue,
			TextValidationMechanism validationMechanism) {
		switch (validationMechanism) {
		case startsWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail("Actual value '" + actualValue + "' does not starts with expected value '" + expectedValue
						+ "'.");
			}
			break;
		case containsExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not contain expected value '" + expectedValue + "'.");
			}
			break;
		case endsWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail("Actual value '" + actualValue + "' does not ends with expected value '" + expectedValue
						+ "'.");
			}
			break;
		case exactMatchWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not equal to expected value '" + expectedValue + "'.");
			}
			break;
		case matchWithRegularExpression:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail("Actual value '" + actualValue + "' does not equal to expected regular expression value '"
						+ expectedValue + "'.");
			}
			break;
		case exactMatchWithExpectedValueWithRemovedWhiteSpace:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not equal to expected value '" + expectedValue + "'.");
			}
			break;
		}
	}
}
