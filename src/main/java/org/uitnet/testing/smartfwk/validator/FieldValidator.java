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
package org.uitnet.testing.smartfwk.validator;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.DataMatchUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * Used to validate field value.
 * 
 * @author Madhav Krishna
 *
 */
public class FieldValidator {

	private FieldValidator() {
		// Do nothing
	}

	/**
	 * Validates field value as null.
	 * 
	 * @param fieldName
	 * @param actualValue
	 */
	public static void validateFieldValueAsNull(String fieldName, Object actualValue) {
		if (actualValue != null) {
			Assert.fail("Field '" + fieldName + "' value should be null.");
		}
	}

	/**
	 * Validates field value as not null.
	 * 
	 * @param fieldName
	 * @param actualValue
	 */
	public static void validateFieldValueAsNotNull(String fieldName, Object actualValue) {
		if (actualValue == null) {
			Assert.fail("Field '" + fieldName + "' value should not be null.");
		}
	}

	/**
	 * Considers null and empty value as empty. It removes all leading and trailing
	 * whitespaces to check empty.
	 * 
	 * @param fieldName
	 * @param actualValue
	 */
	@SuppressWarnings("rawtypes")
	public static void validateFieldValueAsEmpty(String fieldName, Object actualValue) {
		if (actualValue != null) {
			if (actualValue instanceof String) {
				if (!StringUtil.isEmptyAfterTrim((String) actualValue)) {
					Assert.fail("Field '" + fieldName + "' value is not empty. Found " + ((String) actualValue).length()
							+ " characters. Text: " + ((String) actualValue));
				}
			} else if (actualValue instanceof Collection) {
				if (((Collection) actualValue).size() != 0) {
					Assert.fail("Field '" + fieldName + "' value is not empty. Found "
							+ ((Collection) actualValue).size() + " elements. Elems: " + ((Collection) actualValue));
				}
			} else if (actualValue instanceof Map) {
				if (((Map) actualValue).size() != 0) {
					Assert.fail("Field '" + fieldName + "' value is not empty. Found " + ((Map) actualValue).size()
							+ " elements. Elems: " + ((Map) actualValue));
				}
			} else if (actualValue.getClass().isArray()) {
				if (((Object[]) actualValue).length != 0) {
					Assert.fail("Field '" + fieldName + "' value is not empty. Found " + ((Object[]) actualValue).length
							+ " elements. Elems: " + Arrays.asList((Object[]) actualValue));
				}
			} else {
				Assert.fail("Field '" + fieldName + "' value is not empty. Found "
						+ ((String) ("" + actualValue)).length() + " characters. Text: " + actualValue);
			}
		}

	}

	/**
	 * Considers null and empty value as empty. It removes all leading and trailing
	 * whitespaces to check empty.
	 * 
	 * @param fieldName
	 * @param actualValue
	 */
	@SuppressWarnings("rawtypes")
	public static void validateFieldValueAsNonEmpty(String fieldName, Object actualValue) {
		validateFieldValueAsNotNull(fieldName, actualValue);

		if (actualValue instanceof String) {
			if (StringUtil.isEmptyAfterTrim((String) actualValue)) {
				Assert.fail("Field '" + fieldName + "' value is empty.");
			}
		} else if (actualValue instanceof Collection) {
			if (((Collection) actualValue).size() == 0) {
				Assert.fail("Field '" + fieldName + "' does not contain any element.");
			}
		} else if (actualValue instanceof Map) {
			if (((Map) actualValue).size() == 0) {
				Assert.fail("Field '" + fieldName + "' does not contain any element.");
			}
		} else if (actualValue.getClass().isArray()) {
			if (((Object[]) actualValue).length == 0) {
				Assert.fail("Field '" + fieldName + "' does not contain any element.");
			}
		}
	}

	/**
	 * Validates field value as numeric.
	 * 
	 * @param fieldName
	 * @param actualValue
	 * @param removeComma
	 */
	public static void validateFieldValueAsNumeric(String fieldName, String actualValue, boolean removeComma) {
		if (!StringUtil.isEmptyNoTrim(actualValue)) {
			try {
				String actualValue2 = removeComma ? actualValue.replaceAll(",", "") : actualValue;
				Double.parseDouble(actualValue2);
			} catch (Exception | Error e) {
				Assert.fail("Field '" + fieldName + "' value should be numeric. Actual Value: " + actualValue);
			}
		}
	}

	/**
	 * Validates field value as numeric but not decimal.
	 * 
	 * @param fieldName
	 * @param actualValue
	 * @param removeComma
	 */
	public static void validateFieldValueAsNumericButNotDecimal(String fieldName, String actualValue,
			boolean removeComma) {
		if (!StringUtil.isEmptyNoTrim(actualValue)) {
			try {
				String actualValue2 = removeComma ? actualValue.replaceAll(",", "") : actualValue;
				Long.parseLong(actualValue2);
			} catch (Exception | Error e) {
				Assert.fail("Field '" + fieldName + "' value should be non-decimal. Actual Value: " + actualValue);
			}
		}
	}

	/**
	 * Validates field value as non-numeric.
	 * 
	 * @param fieldName
	 * @param actualValue
	 * @param removeComma
	 */
	public static void validateFieldValueAsNonNumeric(String fieldName, String actualValue, boolean removeComma) {
		if (!StringUtil.isEmptyNoTrim(actualValue)) {
			String actualValue2 = removeComma ? actualValue.replaceAll(",", "") : actualValue;
			Double.parseDouble(actualValue2);
			Assert.fail("Field '" + fieldName + "' value should not be numeric. Actual Value: " + actualValue);
		}
	}

	/**
	 * Validates field value as expected value. Only numeric and String arguments
	 * are allowed.
	 * 
	 * @param fieldName
	 * @param actualValue
	 * @param expectedValue
	 */
	public static void validateFieldValueAsExpectedValue(String fieldName, Object actualValue, Object expectedValue) {

		if (actualValue == null || expectedValue == null) {
			if (actualValue != expectedValue) {
				Assert.fail("Field '" + fieldName + "' value is not matched with expected value. Actual Value: "
						+ actualValue + ", Expected Value: " + expectedValue);
			}
		} else if (!("" + actualValue).equals(("" + expectedValue))) {
			Assert.fail("Field '" + fieldName + "' value is not matched with expected value. Actual Value: "
					+ actualValue + ", Expected Value: " + expectedValue);
		}
	}

	/**
	 * Validates field value should match with expected value based on text match
	 * mechanism. Only numeric and String arguments are allowed.
	 * 
	 * @param fieldName
	 * @param actualValue
	 * @param expectedValue
	 * @param validationMechanism
	 */
	public static void validateFieldValueAsExpectedValue(String fieldName, Object actualValue, Object expectedValue,
			TextMatchMechanism textMatchMechanism) {
		if (actualValue == null || expectedValue == null) {
			if (actualValue != expectedValue) {
				Assert.fail("Field '" + fieldName + "' value is not matched with expected value. Actual Value: "
						+ actualValue + ", Expected Value: " + expectedValue);
			}
		} else if (!DataMatchUtil.matchTextValue("" + actualValue, "" + expectedValue, textMatchMechanism)) {
			Assert.fail("Field '" + fieldName + "' value is not matched with expected value. Actual Value: "
					+ actualValue + ", Expected value: " + expectedValue + ", Text Match Mechanism: "
					+ textMatchMechanism.name());
		}
	}

	/**
	 * Validates field value as not expected value. Only numeric and String
	 * arguments are allowed.
	 * 
	 * @param fieldName
	 * @param actualValue
	 * @param expectedValue
	 */
	public static void validateFieldValueAsNotExpectedValue(String fieldName, Object actualValue,
			Object expectedValue) {
		if (actualValue == null || expectedValue == null) {
			if (actualValue == expectedValue) {
				Assert.fail("Field '" + fieldName + "' value should not match with expected value. Actual Value: "
						+ actualValue + ", Expected Value: " + expectedValue);
			}
		} else if (("" + actualValue).equals(("" + expectedValue))) {
			Assert.fail("Field '" + fieldName + "' value should not match with expected value. Actual Value: "
					+ actualValue + ", Expected Value: " + expectedValue);
		}
	}

	/**
	 * Validates Date Time format for the Date Time field. Date time format
	 * examples: 1. MM/dd/yyyy 2. MM/dd/yyyy HH:mm:ss 3. MM/dd/yyyy HH:mm:ss.SSS z
	 * 
	 * @param fieldName
	 * @param actualValue
	 * @param expectedDateFormat
	 */
	public static void validateFieldValueAsExpectedDateTimeFormat(String fieldName, String actualValue,
			String expectedDateFormat) {
		try {
			if (!StringUtil.isEmptyAfterTrim(actualValue)) {
				SimpleDateFormat formatter = new SimpleDateFormat(expectedDateFormat);
				formatter.setLenient(true);
				formatter.parse(actualValue.trim());
			}
		} catch (Exception ex) {
			Assert.fail("Field '" + fieldName + "' value (" + actualValue + ") is not in the expected format ("
					+ expectedDateFormat + ").");
		}
	}

	/**
	 * Validates field value contains the same number of characters as expected
	 * count.
	 * 
	 * @param fieldName      - field name
	 * @param actualValue    - field value
	 * @param expectedLength - expected char count.
	 */
	@SuppressWarnings("rawtypes")
	public static void validateFieldValueAsOfExpectedLength(String fieldName, Object actualValue, int expectedLength) {
		validateFieldValueAsNotNull(fieldName, actualValue);

		int actualLength = 0;
		if (actualValue instanceof Collection) {
			actualLength = ((Collection) actualValue).size();
			if (actualLength != expectedLength) {
				Assert.fail("Field '" + fieldName
						+ "' does not contain same number of elements as expected. Expected Length: " + expectedLength
						+ ", Actual Length: " + actualLength + ". Actual Elements: " + ((Collection) actualValue));
			}
		} else if (actualValue instanceof Map) {
			actualLength = ((Map) actualValue).size();
			if (actualLength != expectedLength) {
				Assert.fail("Field '" + fieldName
						+ "' does not contain same number of elements as expected. Expected Length: " + expectedLength
						+ ", Actual Length: " + actualLength + ". Actual Elements: " + ((Map) actualValue));
			}
		} else if (actualValue.getClass().isArray()) {
			actualLength = ((Object[]) actualValue).length;
			if (actualLength != expectedLength) {
				Assert.fail("Field '" + fieldName
						+ "' does not contain same number of elements as expected. Expected Length: " + expectedLength
						+ ", Actual Length: " + actualLength + ". Actual Elements: "
						+ Arrays.asList((Object[]) actualValue));
			}
		} else {
			actualLength = ("" + actualValue).length();
			if (actualLength != expectedLength) {
				Assert.fail("Field '" + fieldName
						+ "' value does not contain same number of character count as expected. Expected Length: "
						+ expectedLength + ", Actual Length: " + actualLength + ". Actual Text: " + actualValue);
			}
		}
	}

	/**
	 * Validate field value as a valid email.
	 * 
	 * @param fieldName
	 * @param actualValue
	 */
	public static void validateFieldValueAsEmail(String fieldName, String actualValue) {
		validateFieldValueAsNotNull(fieldName, actualValue);

		String actualValue2 = actualValue.trim();

		if (actualValue2.contains(" ")) {
			Assert.fail("Field '" + fieldName
					+ "' value is not a valid email. It should not contain whitespace. Actual value: " + actualValue);
		}

		String[] parts = actualValue2.split("@");

		if (parts.length < 2) {
			Assert.fail("Field '" + fieldName
					+ "' value is not a valid email. It does not contain domain part after '@' symbol. Actual value: "
					+ actualValue);
		}

		if (parts.length > 2) {
			Assert.fail("Field '" + fieldName
					+ "' value is not a valid email. It contains multiple '@' symbols. Actual value: " + actualValue);
		}

		if (parts[0].length() == 0) {
			Assert.fail("Field '" + fieldName
					+ "' value is not a valid email. It does not contain left part before '@' symbol. Actual value: "
					+ actualValue);
		}

		if (!parts[1].contains(".")) {
			Assert.fail("Field '" + fieldName
					+ "' value is not a valid email. It does not contain period(.) after '@' symbol. Actual value: "
					+ actualValue);
		}

		if (parts[1].startsWith(".")) {
			Assert.fail("Field '" + fieldName
					+ "' value is not a valid email. It should not start with period(.) after '@' symbol. Actual value: "
					+ actualValue);
		}

		if (parts[1].endsWith(".")) {
			Assert.fail("Field '" + fieldName
					+ "' value is not a valid email. It should not ends with period(.) after '@' symbol. Actual value: "
					+ actualValue);
		}
	}

	/**
	 * Validate field value in expected range.
	 * 
	 * @param fieldName
	 * @param value
	 * @param min
	 * @param max
	 */
	public static void validateFieldValueInExpectedRange(String fieldName, Double value, double min, double max) {
		validateFieldValueAsNotNull(fieldName, value);
		if (value < min || value > max) {
			Assert.fail("Field '" + fieldName + "' value '" + value + "' should be in the range (" + min + ", " + max
					+ ").");
		}
	}

	/**
	 * Validate field value in expected range.
	 * 
	 * @param fieldName
	 * @param value
	 * @param min
	 * @param max
	 */
	public static void validateFieldValueInExpectedRange(String fieldName, Integer value, int min, int max) {
		validateFieldValueAsNotNull(fieldName, value);
		if (value < min || value > max) {
			Assert.fail("Field '" + fieldName + "' value '" + value + "' should be in the range (" + min + ", " + max
					+ ").");
		}
	}

	/**
	 * Validate field value in expected range.
	 * 
	 * @param fieldName
	 * @param value
	 * @param min
	 * @param max
	 */
	public static void validateFieldValueInExpectedRange(String fieldName, Long value, long min, long max) {
		validateFieldValueAsNotNull(fieldName, value);
		if (value < min || value > max) {
			Assert.fail("Field '" + fieldName + "' value '" + value + "' should be in the range (" + min + ", " + max
					+ ").");
		}
	}

	/**
	 * This checks the actualElements length is same as expectedElements length. If
	 * not then throw error with differential information.
	 * 
	 * @param fieldName
	 * @param actualElements
	 * @param expectedElements
	 */
	public static void validateFieldLengthAsOfExpectedLength(String fieldName, Collection<String> actualElements,
			Collection<String> expectedElements) {
		validateFieldValueAsNotNull(fieldName, actualElements);
		validateFieldValueAsNotNull(fieldName, expectedElements);

		Collection<String> diffList = new LinkedList<String>();
		if (expectedElements.size() > actualElements.size()) {
			diffList.addAll(expectedElements);
			diffList.removeAll(actualElements);
			if (diffList.size() > 0) {
				Assert.fail("Field '" + fieldName + "' length is not same as expected length. Expected length: "
						+ expectedElements.size() + ", Actual length: " + actualElements.size()
						+ ", Additional Elements (Expected): " + diffList);
			}
		} else if (actualElements.size() > expectedElements.size()) {
			diffList.addAll(actualElements);
			diffList.removeAll(expectedElements);
			if (diffList.size() > 0) {
				Assert.fail("Field '" + fieldName + "' length is not same as expected length. Expected length: "
						+ expectedElements.size() + ", Actual length: " + actualElements.size()
						+ ", Additional Elements (Actual): " + diffList);
			}
		}
	}

	/**
	 * Validate field contains exactly same elements as in expected elements. Order
	 * does not match. Also removes leading and trailing spaces.
	 * 
	 * @param fieldName
	 * @param actualElements
	 * @param expectedElements
	 */
	public static void validateFieldContainsExactlySameElementsAsExpected(String fieldName,
			Collection<String> actualElements, Collection<String> expectedElements) {
		validateFieldLengthAsOfExpectedLength(fieldName, actualElements, expectedElements);

		List<String> unmatchedElements = new LinkedList<String>();
		boolean found = false;
		for (String ee : expectedElements) {
			found = false;
			for (String ae : actualElements) {
				if (StringUtil.trim(ee).equals(StringUtil.trim(ae))) {
					found = true;
					break;
				}
			}

			if (!found) {
				unmatchedElements.add(ee);
			}
		}

		if (unmatchedElements.size() > 0) {
			Assert.fail("Field '" + fieldName + "' does not contain the following elements: " + unmatchedElements);
		}
	}

	/**
	 * Validate field contains exactly same elements as in expected elements and in
	 * the same order. Also removes leading and trailing spaces.
	 * 
	 * @param fieldName
	 * @param actualElements
	 * @param expectedElements
	 */
	public static void validateFieldContainsExactlySameElementsInOrderAsExpected(String fieldName,
			List<String> actualElements, List<String> expectedElements) {
		validateFieldLengthAsOfExpectedLength(fieldName, actualElements, expectedElements);

		Map<Integer, String> unmatchedElements = new LinkedHashMap<Integer, String>();
		String ee = null, ae = null;
		for (int i = 0; i < expectedElements.size(); i++) {
			ee = StringUtil.trim(expectedElements.get(i));
			try {
				ae = StringUtil.trim(actualElements.get(i));
				if (!ee.equals(ae)) {
					unmatchedElements.put(i + 1, ee);
				}
			} catch (Exception ex) {
				unmatchedElements.put(i + 1, ee);
			}
		}

		if (unmatchedElements.size() > 0) {
			Assert.fail("Field '" + fieldName + "' does not contain the following elements in order as expected: "
					+ unmatchedElements);
		}
	}

	/**
	 * Used to fail the step.
	 * 
	 * @param message
	 */
	public static void fail(String message) {
		Assert.fail(message);
	}

}
