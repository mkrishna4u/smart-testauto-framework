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
		Assert.assertNull(actualValue, "Field '" + fieldName + "' value should be null. Actual Value: " + actualValue);
	}

	/**
	 * Validates field value as not null.
	 * 
	 * @param fieldName
	 * @param actualValue
	 */
	public static void validateFieldValueAsNotNull(String fieldName, Object actualValue) {
		Assert.assertNotNull(actualValue,
				"Field '" + fieldName + "' value should not be null. Actual Value: " + actualValue);
	}

	/**
	 * Considers null and empty value as empty. Also multiple whitespaces are
	 * considered as empty.
	 * 
	 * @param fieldName
	 * @param actualValue
	 */
	public static void validateFieldValueAsEmpty(String fieldName, String actualValue) {
		Assert.assertTrue(StringUtil.isEmptyAfterTrim(actualValue),
				"Field '" + fieldName + "' value should be empty. Actual Value: " + actualValue);
	}

	/**
	 * Considers null and empty value as empty. Also multiple whitespaces are
	 * considered as empty.
	 * 
	 * @param fieldName
	 * @param actualValue
	 */
	public static void validateFieldValueAsNonEmpty(String fieldName, String actualValue) {
		Assert.assertFalse(StringUtil.isEmptyAfterTrim(actualValue),
				"Field '" + fieldName + "' value should not be empty. Actual Value: " + actualValue);
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
	 * Validates field value as expected value.
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
	 * mechanism.
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
	 * Validates field value as not expected value.
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
	public static void validateFieldValueAsOfExpectedLength(String fieldName, Object actualValue, int expectedLength) {
		validateFieldValueAsNotNull(fieldName, actualValue);
		Assert.assertEquals(("" + actualValue).length(), expectedLength,
				"Field '" + fieldName
						+ "' value does not contain same number of character count as expected count. Actual count: "
						+ ("" + actualValue).length() + ", Expected count: " + expectedLength + ".");
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

}
