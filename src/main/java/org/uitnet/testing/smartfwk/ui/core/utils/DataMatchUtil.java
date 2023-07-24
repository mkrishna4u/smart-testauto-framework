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
package org.uitnet.testing.smartfwk.ui.core.utils;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DataMatchUtil {

	public static boolean matchTextValue(String actualValue, String expectedValue,
			TextMatchMechanism validationMechanism) {
		return indexOfExpectedValue(actualValue, expectedValue, validationMechanism) >= 0;
	}
	
	/**
	 * Matches the expected values with actual value and returns the index of expected values in actual value.
	 * @param actualValue
	 * @param expectedValue
	 * @param validationMechanism
	 * @return returns the index of expected values in actual value. -1 is returned when value is not matched.
	 */
	public static int indexOfExpectedValue(String actualValue, String expectedValue,
			TextMatchMechanism validationMechanism) {
		
		if(validationMechanism == null) {
			validationMechanism = TextMatchMechanism.exactMatchWithExpectedValue;
		}
		
		int index = -1;

		if (actualValue == null && expectedValue == null &&
				(validationMechanism == TextMatchMechanism.exactMatchWithExpectedValue ||
				validationMechanism == TextMatchMechanism.icExactMatchWithExpectedValue ||
				validationMechanism == TextMatchMechanism.exactMatchWithExpectedValueAfterRemovingSpaces ||
				validationMechanism == TextMatchMechanism.icExactMatchWithExpectedValueAfterRemovingSpaces)) {
			return 0;
		}
		
		if (actualValue == null) {
			return -1;
		}

		switch (validationMechanism) {
		case startsWithExpectedValue:
			if (actualValue.startsWith(expectedValue)) {
				index = 0;
			}
			break;
		case containsExpectedValue:
			index = actualValue.indexOf(expectedValue);
			break;
		case endsWithExpectedValue:
			if (actualValue.endsWith(expectedValue)) {
				index = actualValue.lastIndexOf(expectedValue);
			}
			break;
		case exactMatchWithExpectedValue:
			if (actualValue.equals(expectedValue)) {
				index = 0;
			}
			break;
		case matchWithRegularExpression:
			if (actualValue.matches(expectedValue)) {
				index = 0;
			}
			break;
		case exactMatchWithExpectedValueAfterRemovingSpaces:
			if (actualValue.replaceAll(" ", "").equals(expectedValue.replaceAll(" ", ""))) {
				index = 0;
			}
			break;
			
		case icStartsWithExpectedValue:
			if (actualValue.toLowerCase().startsWith(expectedValue.toLowerCase())) {
				index = 0;
			}
			break;
		case icContainsExpectedValue:
			if (actualValue.toLowerCase().contains(expectedValue.toLowerCase())) {
				index = actualValue.toLowerCase().indexOf(expectedValue.toLowerCase());
			}
			break;
		case icEndsWithExpectedValue:
			if (actualValue.toLowerCase().endsWith(expectedValue.toLowerCase())) {
				index = actualValue.toLowerCase().lastIndexOf(expectedValue.toLowerCase());
			}
			break;
		case icExactMatchWithExpectedValue:
			if (actualValue.toLowerCase().equals(expectedValue.toLowerCase())) {
				index = 0;
			}
			break;		
		case icExactMatchWithExpectedValueAfterRemovingSpaces:
			if (actualValue.replaceAll(" ", "").toLowerCase().equals(expectedValue.replaceAll(" ", "").toLowerCase())) {
				index = 0;
			}
			break;
		}

		return index;
	}

	public static void validateTextValue(String actualValue, String expectedValue,
			TextMatchMechanism validationMechanism) {
		switch (validationMechanism) {
		case startsWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail("Actual value '" + actualValue + "' does not starts with expected value '" + expectedValue
						+ "'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case containsExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not contain expected value '" + expectedValue + 
						"'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case endsWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail("Actual value '" + actualValue + "' does not ends with expected value '" + expectedValue
						+ "'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case exactMatchWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not equal to expected value '" + expectedValue + 
						"'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case matchWithRegularExpression:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail("Actual value '" + actualValue + "' does not equal to expected regular expression value '"
						+ expectedValue + "'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case exactMatchWithExpectedValueAfterRemovingSpaces:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not equal to expected value '" + expectedValue + 
						"'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case icStartsWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail("Actual value '" + actualValue + "' does not starts with expected value '" + expectedValue
						+ "'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case icContainsExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not contain expected value '" + expectedValue + 
						"'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case icEndsWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail("Actual value '" + actualValue + "' does not ends with expected value '" + expectedValue
						+ "'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		case icExactMatchWithExpectedValue:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not equal to expected value '" + expectedValue + 
						"'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;		
		case icExactMatchWithExpectedValueAfterRemovingSpaces:
			if (!matchTextValue(actualValue, expectedValue, validationMechanism)) {
				Assert.fail(
						"Actual value '" + actualValue + "' does not equal to expected value '" + expectedValue + 
						"'. TextMatchMechanism = " + validationMechanism.name() + ".");
			}
			break;
		}
	}
}
