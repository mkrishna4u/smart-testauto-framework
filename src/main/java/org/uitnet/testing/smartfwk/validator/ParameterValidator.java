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

import static org.testng.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.IgnoreCase;
import org.uitnet.testing.smartfwk.core.validator.InOrder;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;
import org.uitnet.testing.smartfwk.core.validator.ValueMatchOperator;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.DataMatchUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * Used to validate parameter values from JSON, YAML, XML documents.
 * 
 * @author Madhav Krishna
 *
 */
public class ParameterValidator {

	private ParameterValidator() {
		// Do nothing
	}

	/**
	 * Validates field value as null.
	 * 
	 * @param paramName
	 * @param actualValue
	 */
	public static void validateParamValueAsNull(String paramName, Object actualValue) {
		if (actualValue != null) {
			Assert.fail("Param '" + paramName + "' value should be null.");
		}
	}

	/**
	 * Validates field value as not null.
	 * 
	 * @param paramName
	 * @param actualValue
	 */
	public static void validateParamValueAsNotNull(String paramName, Object actualValue) {
		if (actualValue == null) {
			Assert.fail("Param '" + paramName + "' value should not be null.");
		}
	}

	/**
	 * Considers null and empty value as empty. It removes all leading and trailing
	 * whitespaces to check empty.
	 * 
	 * @param paramName
	 * @param actualValue
	 */
	@SuppressWarnings("rawtypes")
	public static void validateParamValueAsEmpty(String paramName, Object actualValue) {
		if (actualValue != null) {
			if (actualValue instanceof String) {
				if (!StringUtil.isEmptyAfterTrim((String) actualValue)) {
					Assert.fail("Param '" + paramName + "' value is not empty. Found " + ((String) actualValue).length()
							+ " characters. Text: " + ((String) actualValue));
				}
			} else if (actualValue instanceof Collection) {
				if (((Collection) actualValue).size() != 0) {
					Assert.fail("Param '" + paramName + "' value is not empty. Found "
							+ ((Collection) actualValue).size() + " elements. Elems: " + ((Collection) actualValue));
				}
			} else if (actualValue instanceof Map) {
				if (((Map) actualValue).size() != 0) {
					Assert.fail("Param '" + paramName + "' value is not empty. Found " + ((Map) actualValue).size()
							+ " elements. Elems: " + ((Map) actualValue));
				}
			} else if (actualValue.getClass().isArray()) {
				if (((Object[]) actualValue).length != 0) {
					Assert.fail("Param '" + paramName + "' value is not empty. Found " + ((Object[]) actualValue).length
							+ " elements. Elems: " + Arrays.asList((Object[]) actualValue));
				}
			} else {
				Assert.fail("Param '" + paramName + "' value is not empty. Found "
						+ ((String) ("" + actualValue)).length() + " characters. Text: " + actualValue);
			}
		}

	}

	/**
	 * Considers null and empty value as empty. It removes all leading and trailing
	 * whitespaces to check empty.
	 * 
	 * @param paramName
	 * @param actualValue
	 */
	@SuppressWarnings("rawtypes")
	public static void validateParamValueAsNonEmpty(String paramName, Object actualValue) {
		validateParamValueAsNotNull(paramName, actualValue);

		if (actualValue instanceof String) {
			if (StringUtil.isEmptyAfterTrim((String) actualValue)) {
				Assert.fail("Param '" + paramName + "' value is empty.");
			}
		} else if (actualValue instanceof Collection) {
			if (((Collection) actualValue).size() == 0) {
				Assert.fail("Param '" + paramName + "' does not contain any element.");
			}
		} else if (actualValue instanceof Map) {
			if (((Map) actualValue).size() == 0) {
				Assert.fail("Param '" + paramName + "' does not contain any element.");
			}
		} else if (actualValue.getClass().isArray()) {
			if (((Object[]) actualValue).length == 0) {
				Assert.fail("Param '" + paramName + "' does not contain any element.");
			}
		}
	}

	/**
	 * Validates field value as numeric.
	 * 
	 * @param paramName
	 * @param actualValue
	 * @param removeComma
	 */
	public static void validateParamValueAsNumeric(String paramName, String actualValue, boolean removeComma) {
		if (!StringUtil.isEmptyNoTrim(actualValue)) {
			try {
				String actualValue2 = removeComma ? actualValue.replaceAll(",", "") : actualValue;
				Double.parseDouble(actualValue2);
			} catch (Exception | Error e) {
				Assert.fail("Param '" + paramName + "' value should be numeric. Actual Value: " + actualValue);
			}
		}
	}

	/**
	 * Validates field value as numeric but not decimal.
	 * 
	 * @param paramName
	 * @param actualValue
	 * @param removeComma
	 */
	public static void validateParamValueAsNumericButNotDecimal(String paramName, String actualValue,
			boolean removeComma) {
		if (!StringUtil.isEmptyNoTrim(actualValue)) {
			try {
				String actualValue2 = removeComma ? actualValue.replaceAll(",", "") : actualValue;
				Long.parseLong(actualValue2);
			} catch (Exception | Error e) {
				Assert.fail("Param '" + paramName + "' value should be non-decimal. Actual Value: " + actualValue);
			}
		}
	}

	/**
	 * Validates field value as non-numeric.
	 * 
	 * @param paramName
	 * @param actualValue
	 * @param removeComma
	 */
	public static void validateParamValueAsNonNumeric(String paramName, String actualValue, boolean removeComma) {
		if (!StringUtil.isEmptyNoTrim(actualValue)) {
			String actualValue2 = removeComma ? actualValue.replaceAll(",", "") : actualValue;
			Double.parseDouble(actualValue2);
			Assert.fail("Param '" + paramName + "' value should not be numeric. Actual Value: " + actualValue);
		}
	}

	/**
	 * Validates field value as expected value. Only numeric and String arguments
	 * are allowed.
	 * 
	 * @param paramName
	 * @param actualValue
	 * @param expectedValue
	 */
	public static void validateParamValueAsExpectedValue(String paramName, Object actualValue, Object expectedValue) {

		if (actualValue == null || expectedValue == null) {
			if (actualValue != expectedValue) {
				Assert.fail("Param '" + paramName + "' value is not matched with expected value. Actual Value: "
						+ actualValue + ", Expected Value: " + expectedValue);
			}
		} else if (!("" + actualValue).equals(("" + expectedValue))) {
			Assert.fail("Param '" + paramName + "' value is not matched with expected value. Actual Value: "
					+ actualValue + ", Expected Value: " + expectedValue);
		}
	}

	/**
	 * Validates field value should match with expected value based on text match
	 * mechanism. Only numeric and String arguments are allowed.
	 * 
	 * @param paramName
	 * @param actualValue
	 * @param expectedValue
	 * @param validationMechanism
	 */
	public static void validateParamValueAsExpectedValue(String paramName, Object actualValue, Object expectedValue,
			TextMatchMechanism textMatchMechanism) {
		if (actualValue == null || expectedValue == null) {
			if (actualValue != expectedValue) {
				Assert.fail("Param '" + paramName + "' value is not matched with expected value. Actual Value: "
						+ actualValue + ", Expected Value: " + expectedValue);
			}
		} else if (!DataMatchUtil.matchTextValue("" + actualValue, "" + expectedValue, textMatchMechanism)) {
			Assert.fail("Param '" + paramName + "' value is not matched with expected value. Actual Value: "
					+ actualValue + ", Expected value: " + expectedValue + ", Text Match Mechanism: "
					+ textMatchMechanism.name());
		}
	}

	/**
	 * Validates field value as not expected value. Only numeric and String
	 * arguments are allowed.
	 * 
	 * @param paramName
	 * @param actualValue
	 * @param expectedValue
	 */
	public static void validateParamValueAsNotExpectedValue(String paramName, Object actualValue,
			Object expectedValue) {
		if (actualValue == null || expectedValue == null) {
			if (actualValue == expectedValue) {
				Assert.fail("Param '" + paramName + "' value should not match with expected value. Actual Value: "
						+ actualValue + ", Expected Value: " + expectedValue);
			}
		} else if (("" + actualValue).equals(("" + expectedValue))) {
			Assert.fail("Param '" + paramName + "' value should not match with expected value. Actual Value: "
					+ actualValue + ", Expected Value: " + expectedValue);
		}
	}

	/**
	 * Validates Date Time format for the Date Time field. Date time format
	 * examples: 1. MM/dd/yyyy 2. MM/dd/yyyy HH:mm:ss 3. MM/dd/yyyy HH:mm:ss.SSS z
	 * 
	 * @param paramName
	 * @param actualValue
	 * @param expectedDateFormat
	 */
	public static void validateParamValueAsExpectedDateTimeFormat(String paramName, String actualValue,
			String expectedDateFormat) {
		try {
			if (!StringUtil.isEmptyAfterTrim(actualValue)) {
				SimpleDateFormat formatter = new SimpleDateFormat(expectedDateFormat);
				formatter.setLenient(true);
				formatter.parse(actualValue.trim());
			}
		} catch (Exception ex) {
			Assert.fail("Param '" + paramName + "' value (" + actualValue + ") is not in the expected format ("
					+ expectedDateFormat + ").");
		}
	}

	/**
	 * Validates field value contains the same number of characters as expected
	 * count.
	 * 
	 * @param paramName      - field name
	 * @param actualValue    - field value
	 * @param expectedLength - expected char count.
	 */
	@SuppressWarnings("rawtypes")
	public static void validateParamValueAsOfExpectedLength(String paramName, Object actualValue, int expectedLength) {
		validateParamValueAsNotNull(paramName, actualValue);

		int actualLength = 0;
		if (actualValue instanceof Collection) {
			actualLength = ((Collection) actualValue).size();
			if (actualLength != expectedLength) {
				Assert.fail("Param '" + paramName
						+ "' does not contain same number of elements as expected. Expected Length: " + expectedLength
						+ ", Actual Length: " + actualLength + ". Actual Elements: " + ((Collection) actualValue));
			}
		} else if (actualValue instanceof Map) {
			actualLength = ((Map) actualValue).size();
			if (actualLength != expectedLength) {
				Assert.fail("Param '" + paramName
						+ "' does not contain same number of elements as expected. Expected Length: " + expectedLength
						+ ", Actual Length: " + actualLength + ". Actual Elements: " + ((Map) actualValue));
			}
		} else if (actualValue.getClass().isArray()) {
			actualLength = ((Object[]) actualValue).length;
			if (actualLength != expectedLength) {
				Assert.fail("Param '" + paramName
						+ "' does not contain same number of elements as expected. Expected Length: " + expectedLength
						+ ", Actual Length: " + actualLength + ". Actual Elements: "
						+ Arrays.asList((Object[]) actualValue));
			}
		} else {
			actualLength = ("" + actualValue).length();
			if (actualLength != expectedLength) {
				Assert.fail("Param '" + paramName
						+ "' value does not contain same number of character count as expected. Expected Length: "
						+ expectedLength + ", Actual Length: " + actualLength + ". Actual Text: " + actualValue);
			}
		}
	}

	/**
	 * Validate field value as a valid email.
	 * 
	 * @param paramName
	 * @param actualValue
	 */
	public static void validateParamValueAsEmail(String paramName, String actualValue) {
		validateParamValueAsNotNull(paramName, actualValue);

		String actualValue2 = actualValue.trim();

		if (actualValue2.contains(" ")) {
			Assert.fail("Param '" + paramName
					+ "' value is not a valid email. It should not contain whitespace. Actual value: " + actualValue);
		}

		String[] parts = actualValue2.split("@");

		if (parts.length < 2) {
			Assert.fail("Param '" + paramName
					+ "' value is not a valid email. It does not contain domain part after '@' symbol. Actual value: "
					+ actualValue);
		}

		if (parts.length > 2) {
			Assert.fail("Param '" + paramName
					+ "' value is not a valid email. It contains multiple '@' symbols. Actual value: " + actualValue);
		}

		if (parts[0].length() == 0) {
			Assert.fail("Param '" + paramName
					+ "' value is not a valid email. It does not contain left part before '@' symbol. Actual value: "
					+ actualValue);
		}

		if (!parts[1].contains(".")) {
			Assert.fail("Param '" + paramName
					+ "' value is not a valid email. It does not contain period(.) after '@' symbol. Actual value: "
					+ actualValue);
		}

		if (parts[1].startsWith(".")) {
			Assert.fail("Param '" + paramName
					+ "' value is not a valid email. It should not start with period(.) after '@' symbol. Actual value: "
					+ actualValue);
		}

		if (parts[1].endsWith(".")) {
			Assert.fail("Param '" + paramName
					+ "' value is not a valid email. It should not ends with period(.) after '@' symbol. Actual value: "
					+ actualValue);
		}
	}

	/**
	 * Validate field value in expected range.
	 * 
	 * @param paramName
	 * @param value
	 * @param min
	 * @param max
	 */
	public static void validateParamValueInExpectedRange(String paramName, Double value, double min, double max) {
		validateParamValueAsNotNull(paramName, value);
		if (value < min || value > max) {
			Assert.fail("Param '" + paramName + "' value '" + value + "' should be in the range (" + min + ", " + max
					+ ").");
		}
	}

	/**
	 * Validate field value in expected range.
	 * 
	 * @param paramName
	 * @param value
	 * @param min
	 * @param max
	 */
	public static void validateParamValueInExpectedRange(String paramName, Integer value, int min, int max) {
		validateParamValueAsNotNull(paramName, value);
		if (value < min || value > max) {
			Assert.fail("Param '" + paramName + "' value '" + value + "' should be in the range (" + min + ", " + max
					+ ").");
		}
	}

	/**
	 * Validate field value in expected range.
	 * 
	 * @param paramName
	 * @param value
	 * @param min
	 * @param max
	 */
	public static void validateParamValueInExpectedRange(String paramName, Long value, long min, long max) {
		validateParamValueAsNotNull(paramName, value);
		if (value < min || value > max) {
			Assert.fail("Param '" + paramName + "' value '" + value + "' should be in the range (" + min + ", " + max
					+ ").");
		}
	}

	/**
	 * This checks the actualElements length is same as expectedElements length. If
	 * not then throw error with differential information.
	 * 
	 * @param paramName
	 * @param actualElements
	 * @param expectedElements
	 */
	public static void validateParamLengthAsOfExpectedLength(String paramName, Collection<String> actualElements,
			Collection<String> expectedElements) {
		validateParamValueAsNotNull(paramName, actualElements);
		validateParamValueAsNotNull(paramName, expectedElements);

		Collection<String> diffList = new LinkedList<String>();
		if (expectedElements.size() > actualElements.size()) {
			diffList.addAll(expectedElements);
			diffList.removeAll(actualElements);
			if (diffList.size() > 0) {
				Assert.fail("Param '" + paramName + "' length is not same as expected length. Expected length: "
						+ expectedElements.size() + ", Actual length: " + actualElements.size()
						+ ", Additional Elements (Expected): " + diffList);
			}
		} else if (actualElements.size() > expectedElements.size()) {
			diffList.addAll(actualElements);
			diffList.removeAll(expectedElements);
			if (diffList.size() > 0) {
				Assert.fail("Param '" + paramName + "' length is not same as expected length. Expected length: "
						+ expectedElements.size() + ", Actual length: " + actualElements.size()
						+ ", Additional Elements (Actual): " + diffList);
			}
		}
	}

	/**
	 * Validate field contains exactly same elements as in expected elements. Order
	 * does not match. Also removes leading and trailing spaces.
	 * 
	 * @param paramName
	 * @param actualElements
	 * @param expectedElements
	 */
	public static void validateParamContainsExactlySameElementsAsExpected(String paramName,
			Collection<String> actualElements, Collection<String> expectedElements) {
		validateParamLengthAsOfExpectedLength(paramName, actualElements, expectedElements);

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
			Assert.fail("Param '" + paramName + "' does not contain the following elements: " + unmatchedElements);
		}
	}

	/**
	 * Validate field contains exactly same elements as in expected elements and in
	 * the same order. Also removes leading and trailing spaces.
	 * 
	 * @param paramName
	 * @param actualElements
	 * @param expectedElements
	 */
	public static void validateParamContainsExactlySameElementsInOrderAsExpected(String paramName,
			List<String> actualElements, List<String> expectedElements) {
		validateParamLengthAsOfExpectedLength(paramName, actualElements, expectedElements);

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
			Assert.fail("Param '" + paramName + "' does not contain the following elements in order as expected: "
					+ unmatchedElements);
		}
	}

	@SuppressWarnings("unchecked")
	public static void validateParamValueAsExpectedInfo(boolean isField, ParamPath param, Object actualValue, String operator,
			ExpectedInfo expectedInfo) {
		String paramOrFieldTxt = isField ? "Field '" : "Param '";
		ValueMatchOperator op = ValueMatchOperator.valueOf2(operator);
		
		actualValue = ObjectUtil.fixObjectValueAsPerItsType(actualValue, param.getValueType());
		expectedInfo.setEv(ObjectUtil.fixObjectValueAsPerItsType(expectedInfo.getEv(), expectedInfo.getValueType()));

		/* TODO: correct textMatchMechanism based on ignoreCase value.
		if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
			if(!expectedInfo.getTextMatchMechanism().startsWith("ic")) {
				expectedInfo.setTextMatchMechanism(TextMatchMechanism.icStartsWithExpectedValue.name());
			} 
		} else {
			expectedInfo.setTextMatchMechanism(TextMatchMechanism.startsWithExpectedValue.name());
		} */
		
		switch (op) {
		case EQUAL_TO: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();
				if (!StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
						expectedInfo.getTextMatchMechanism())) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not match with expected value. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}
				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST && expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "' or '" + ParamValueType.STRING.getType() + "'.");
				}
				
				List<String> aListValue = (List<String>) actualValue;
				String a1 = "", e1 = "";
				if(expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					List<String> eListValue = (List<String>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
	
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						e1 = eListValue.get(i) == null ? "" : eListValue.get(i);
						if (!StringUtil.isTextMatchedWithExpectedValue(a1, e1,
								expectedInfo.getTextMatchMechanism())) {
							fail(paramOrFieldTxt + param.getPath() + "' value does not match with expected value. ActualValue: "
									+ aListValue.get(i) + ", ExpectedValue: " + eListValue.get(i) + ", TextMatchMechanism: "
									+ expectedInfo.getTextMatchMechanism().name() + ".");
						}
					}
				} else if(expectedInfo.getValueType() != ParamValueType.STRING) {
					String eValue = (String) expectedInfo.getEv();
					e1 = eValue == null ? "" : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						Assert.assertEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}

				break;
			}
			case INTEGER: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER.getType() + "'.");
				}

				Long aValue = (Long) actualValue;
				Long eValue = (Long) expectedInfo.getEv();

				assertEquals(aValue, eValue,
						paramOrFieldTxt + param.getPath() + "' value does not match with expected value. ActualValue: "
								+ aValue + ", ExpectedValue: " + eValue + ".");
				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST && expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "' or '" + ParamValueType.INTEGER.getType() + "'.");
				}
				
				List<Long> aListValue = (List<Long>) actualValue;
				Long a1 = 0l, e1 = 0l;
				if(expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					List<Long> eListValue = (List<Long>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
	
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0l : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0l : eListValue.get(i);
						Assert.assertEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " does not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.INTEGER) {
					Long eValue = (Long) expectedInfo.getEv();
	
					e1 = eValue == null ? 0l : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0l : aListValue.get(i);
						Assert.assertEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " does not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				
				break;
			}
			case BOOLEAN: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN.getType() + "'.");
				}

				Boolean aValue = (Boolean) actualValue;
				Boolean eValue = (Boolean) expectedInfo.getEv();

				assertEquals(aValue, eValue,
						paramOrFieldTxt + param.getPath() + "' value does not match with expected value. ActualValue: "
								+ aValue + ", ExpectedValue: " + eValue + ".");
				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST && expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "' or '" + ParamValueType.BOOLEAN.getType() + "'.");
				}
				
				List<Boolean> aListValue = (List<Boolean>) actualValue;
				
				Boolean a1 = false, e1 = false;
				if(expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
	
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						e1 = eListValue.get(i) == null ? false : eListValue.get(i);
						Assert.assertEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " does not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					Boolean eValue = (Boolean) expectedInfo.getEv();
	
					e1 = eValue == null ? false : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						Assert.assertEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " does not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}

				break;
			}
			case DECIMAL: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				Double aValue = (Double) actualValue;
				Double eValue = (Double) expectedInfo.getEv();

				assertEquals(aValue, eValue,
						paramOrFieldTxt + param.getPath() + "' value does not match with expected value. ActualValue: "
								+ aValue + ", ExpectedValue: " + eValue + ".");
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST && expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "' or '" + ParamValueType.DECIMAL.getType() + "'.");
				}
				
				List<Double> aListValue = (List<Double>) actualValue;
				Double a1 = 0d, e1 = 0d;
				if(expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					List<Double> eListValue = (List<Double>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
	
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0d : eListValue.get(i);
						Assert.assertEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " does not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					Double eValue = (Double) expectedInfo.getEv();
	
					e1 = eValue == null ? 0d : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						Assert.assertEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " does not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case NOT_EQUAL_TO: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();
				if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
						expectedInfo.getTextMatchMechanism())) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not match with expected value. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}
				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST && expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "' or '" + ParamValueType.STRING.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				
				String a1 = "", e1 = "";
				if(expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					List<String> eListValue = (List<String>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
	
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						e1 = eListValue.get(i) == null ? "" : eListValue.get(i);
						Assert.assertNotEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.INTEGER) {
					String eValue = (String) expectedInfo.getEv();
	
					e1 = eValue == null ? "" : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						Assert.assertNotEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case INTEGER: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER.getType() + "'.");
				}

				Long aValue = (Long) actualValue;
				Long eValue = (Long) expectedInfo.getEv();

				Assert.assertNotEquals(aValue, eValue,
						paramOrFieldTxt + param.getPath() + "' value should not match with expected value. ActualValue: "
								+ aValue + ", ExpectedValue: " + eValue + ".");
				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST && expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "' or '" + ParamValueType.INTEGER.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				Long a1 = 0l, e1 = 0l;
				if(expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					List<Long> eListValue = (List<Long>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
	
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0l : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0l : eListValue.get(i);
						Assert.assertNotEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.INTEGER) {
					Long eValue = (Long) expectedInfo.getEv();
	
					e1 = eValue == null ? 0l : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0l : aListValue.get(i);
						Assert.assertNotEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case BOOLEAN: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN.getType() + "'.");
				}

				Boolean aValue = (Boolean) actualValue;
				Boolean eValue = (Boolean) expectedInfo.getEv();

				Assert.assertNotEquals(aValue, eValue,
						paramOrFieldTxt + param.getPath() + "' value should not match with expected value. ActualValue: "
								+ aValue + ", ExpectedValue: " + eValue + ".");
				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST && expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "' or '" + ParamValueType.BOOLEAN.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				
				Boolean a1 = false, e1 = false;
				if(expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
	
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						e1 = eListValue.get(i) == null ? false : eListValue.get(i);
						Assert.assertNotEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					Boolean eValue = (Boolean) expectedInfo.getEv();
	
					e1 = eValue == null ? false : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						Assert.assertNotEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case DECIMAL: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				Double aValue = (Double) actualValue;
				Double eValue = (Double) expectedInfo.getEv();

				Assert.assertNotEquals(aValue, eValue,
						paramOrFieldTxt + param.getPath() + "' value should not match with expected value. ActualValue: "
								+ aValue + ", ExpectedValue: " + eValue + ".");
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST && expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "' or '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				Double a1 = 0d, e1 = 0d;
				if(expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					List<Double> eListValue = (List<Double>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
	
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0d : eListValue.get(i);
						Assert.assertNotEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					Double eValue = (Double) expectedInfo.getEv();
	
					e1 = eValue == null ? 0d : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						Assert.assertNotEquals(a1, e1,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should not match with expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case GREATER_THAN: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.length() <= eValue.length())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value length should be greater than the expected value length. ActualValue: " + aValue
							+ ", ExpectedValue: " + eValue + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST && expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "' or '" + ParamValueType.STRING.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				String a1 = "", e1 = "";
				if(expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					List<String> eListValue = (List<String>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						e1 = eListValue.get(i) == null ? "" : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) > 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.STRING) {
					String eValue = (String) expectedInfo.getEv();
					
					e1 = eValue == null ? "" : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) > 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case INTEGER: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER.getType() + "'.");
				}

				Long aValue = (Long) actualValue;
				Long eValue = (Long) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.longValue() <= eValue.longValue())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should be greater than the expected value. ActualValue: " + aValue
							+ ", ExpectedValue: " + eValue + ".");
				}

				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST && expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "' or '" + ParamValueType.INTEGER.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				Long a1 = 0l, e1 = 0l;
				if(expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					List<Long> eListValue = (List<Long>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0 : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) > 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.INTEGER) {
					Long eValue = (Long) expectedInfo.getEv();
					
					e1 = eValue == null ? 0 : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) > 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case BOOLEAN: {
				fail("Operator '>' is not supported for boolean value.");

				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST && expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "' or '" + ParamValueType.BOOLEAN.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				Boolean a1 = false, e1 = false;
				if(expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						e1 = eListValue.get(i) == null ? false : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) > 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					Boolean eValue = (Boolean) expectedInfo.getEv();
					
					e1 = eValue == null ? false : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) > 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case DECIMAL: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				Double aValue = (Double) actualValue;
				Double eValue = (Double) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.doubleValue() <= eValue.doubleValue())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should be greater than the expected value. ActualValue: " + aValue
							+ ", ExpectedValue: " + eValue + ".");
				}
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST && expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "' or '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				Double a1 = 0d, e1 = 0d;
				if(expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					List<Double> eListValue = (List<Double>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0d : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) > 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					Double eValue = (Double) expectedInfo.getEv();
					
					e1 = eValue == null ? 0d : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) > 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case GREATER_THAN_EQUAL_TO: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.length() < eValue.length())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value length should be greater than equal to the expected value length. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST && expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "' or '" + ParamValueType.STRING.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				String a1 = "", e1 = "";
				if(expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					List<String> eListValue = (List<String>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						e1 = eListValue.get(i) == null ? "" : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) >= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.STRING) {
					String eValue = (String) expectedInfo.getEv();
					
					e1 = eValue == null ? "" : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) >= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case INTEGER: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER.getType() + "'.");
				}

				Long aValue = (Long) actualValue;
				Long eValue = (Long) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.longValue() < eValue.longValue())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should be greater than equal to the expected value. ActualValue: " + aValue
							+ ", ExpectedValue: " + eValue + ".");
				}

				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST && expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "' or '" + ParamValueType.INTEGER.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				Long a1 = 0l, e1 = 0l;
				if(expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					List<Long> eListValue = (List<Long>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0 : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) >= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.INTEGER) {
					Long eValue = (Long) expectedInfo.getEv();
					
					e1 = eValue == null ? 0 : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) >= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case BOOLEAN: {
				fail("Operator '>=' is not supported for boolean value.");

				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST && expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "' or '" + ParamValueType.BOOLEAN.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				Boolean a1 = false, e1 = false;
				if(expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						e1 = eListValue.get(i) == null ? false : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) >= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					Boolean eValue = (Boolean) expectedInfo.getEv();
					
					e1 = eValue == null ? false : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) >= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case DECIMAL: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				Double aValue = (Double) actualValue;
				Double eValue = (Double) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.doubleValue() < eValue.doubleValue())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should be greater than equal to the expected value. ActualValue: " + aValue
							+ ", ExpectedValue: " + eValue + ".");
				}
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST && expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "' or '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				Double a1 = 0d, e1 = 0d;
				if(expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					List<Double> eListValue = (List<Double>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0d : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) >= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					Double eValue = (Double) expectedInfo.getEv();
					
					e1 = eValue == null ? 0d : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) >= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be greater than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case LESS_THAN: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.length() >= eValue.length())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value length should be less than the expected value length. ActualValue: " + aValue
							+ ", ExpectedValue: " + eValue + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST && expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "' or '" + ParamValueType.STRING.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				String a1 = "", e1 = "";
				if(expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					List<String> eListValue = (List<String>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						e1 = eListValue.get(i) == null ? "" : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) < 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.STRING) {
					String eValue = (String) expectedInfo.getEv();
					
					e1 = eValue == null ? "" : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) < 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case INTEGER: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER.getType() + "'.");
				}

				Long aValue = (Long) actualValue;
				Long eValue = (Long) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.longValue() >= eValue.longValue())) {
					fail(paramOrFieldTxt + param.getPath() + "' value should be less than the expected value. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ".");
				}

				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST && expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "' or '" + ParamValueType.INTEGER.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				Long a1 = 0l, e1 = 0l;
				if(expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					List<Long> eListValue = (List<Long>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0 : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) < 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.INTEGER) {
					Long eValue = (Long) expectedInfo.getEv();
					
					e1 = eValue == null ? 0 : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) < 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case BOOLEAN: {
				fail("Operator '>' is not supported for boolean value.");

				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST && expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "' or '" + ParamValueType.BOOLEAN.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				Boolean a1 = false, e1 = false;
				if(expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						e1 = eListValue.get(i) == null ? false : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) < 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					Boolean eValue = (Boolean) expectedInfo.getEv();
					
					e1 = eValue == null ? false : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) < 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case DECIMAL: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				Double aValue = (Double) actualValue;
				Double eValue = (Double) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.doubleValue() >= eValue.doubleValue())) {
					fail(paramOrFieldTxt + param.getPath() + "' value should be less than the expected value. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ".");
				}
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST && expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "' or '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				Double a1 = 0d, e1 = 0d;
				if(expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					List<Double> eListValue = (List<Double>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0d : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) < 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					Double eValue = (Double) expectedInfo.getEv();
					
					e1 = eValue == null ? 0d : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) < 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case LESS_THAN_EQUAL_TO: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.length() > eValue.length())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value length should be less than equal to the expected value length. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST && expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "' or '" + ParamValueType.STRING.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				String a1 = "", e1 = "";
				if(expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					List<String> eListValue = (List<String>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						e1 = eListValue.get(i) == null ? "" : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) <= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.STRING) {
					String eValue = (String) expectedInfo.getEv();
					
					e1 = eValue == null ? "" : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? "" : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) <= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case INTEGER: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER.getType() + "'.");
				}

				Long aValue = (Long) actualValue;
				Long eValue = (Long) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.longValue() > eValue.longValue())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should be less than equal to the expected value. ActualValue: " + aValue
							+ ", ExpectedValue: " + eValue + ".");
				}

				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST && expectedInfo.getValueType() != ParamValueType.INTEGER) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "' or '" + ParamValueType.INTEGER.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				Long a1 = 0l, e1 = 0l;
				if(expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					List<Long> eListValue = (List<Long>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0 : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) <= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.INTEGER) {
					Long eValue = (Long) expectedInfo.getEv();
					
					e1 = eValue == null ? 0 : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0 : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) <= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case BOOLEAN: {
				fail("Operator '>' is not supported for boolean value.");

				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST && expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "' or '" + ParamValueType.BOOLEAN.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				Boolean a1 = false, e1 = false;
				if(expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						e1 = eListValue.get(i) == null ? false : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) <= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.BOOLEAN) {
					Boolean eValue = (Boolean) expectedInfo.getEv();
					
					e1 = eValue == null ? false : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? false : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) <= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			case DECIMAL: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				Double aValue = (Double) actualValue;
				Double eValue = (Double) expectedInfo.getEv();

				if ((aValue == null) || (eValue != null && aValue.doubleValue() > eValue.doubleValue())) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should be less than equal to the expected value. ActualValue: " + aValue
							+ ", ExpectedValue: " + eValue + ".");
				}
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST && expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "' or '" + ParamValueType.DECIMAL.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				Double a1 = 0d, e1 = 0d;
				if(expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					List<Double> eListValue = (List<Double>) expectedInfo.getEv();
	
					validateParamValueAsOfExpectedLength(param.getPath(), actualValue, eListValue.size());
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						e1 = eListValue.get(i) == null ? 0d : eListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) <= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eListValue.get(i) + ".");
					}
				} else if(expectedInfo.getValueType() != ParamValueType.DECIMAL) {
					Double eValue = (Double) expectedInfo.getEv();
					
					e1 = eValue == null ? 0d : eValue;
					for (int i = 0; i < aListValue.size(); i++) {
						a1 = aListValue.get(i) == null ? 0d : aListValue.get(i);
						Assert.assertTrue(a1.compareTo(e1) <= 0,
								paramOrFieldTxt + param.getPath() + "' value for record " + (i + 1)
										+ " should be less than equal to the expected value. ActualValue: " + aListValue.get(i)
										+ ", ExpectedValue: " + eValue + ".");
					}
				}
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case IN: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				String aValue = (String) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				boolean found = false;
				for (String eValue : eListValue) {
					if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
							expectedInfo.getTextMatchMechanism())) {
						found = true;
						break;
					}
				}

				if (!found) {
					fail(paramOrFieldTxt + param.getPath() + "' value is not found in expected list. ActualValue: " + aValue
							+ ", ExpectedElems: " + eListValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				List<String> notFoundElems = new LinkedList<>();
				for (String aValue : aListValue) {
					boolean found = false;
					for (String eValue : eListValue) {
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							found = true;
							break;
						}
					}

					if (!found) {
						if (!notFoundElems.contains(aValue)) {
							notFoundElems.add(aValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' values are not found in expected list. NotFoundElems: "
							+ notFoundElems + ", ExpectedElems: " + eListValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "'.");
				}

				Long aValue = (Long) actualValue;
				List<Long> eListValue = (List<Long>) expectedInfo.getEv();

				boolean found = false;
				for (Long eValue : eListValue) {
					if ((aValue == null && eValue == null)
							|| (aValue != null && eValue != null && aValue.longValue() == eValue.longValue())) {
						found = true;
						break;
					}
				}

				if (!found) {
					fail(paramOrFieldTxt + param.getPath() + "' value is not found in expected list. ActualValue: " + aValue
							+ ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				List<Long> eListValue = (List<Long>) expectedInfo.getEv();

				List<Long> notFoundElems = new LinkedList<>();
				for (Long aValue : aListValue) {
					boolean found = false;
					for (Long eValue : eListValue) {
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.longValue() == eValue.longValue())) {
							found = true;
							break;
						}
					}

					if (!found) {
						if (!notFoundElems.contains(aValue)) {
							notFoundElems.add(aValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' values are not found in expected list. NotFoundElems: "
							+ notFoundElems + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case BOOLEAN: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "'.");
				}

				Boolean aValue = (Boolean) actualValue;
				List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();

				boolean found = false;
				for (Boolean eValue : eListValue) {
					if ((aValue == null && eValue == null)
							|| (aValue != null && eValue != null && aValue.booleanValue() == eValue.booleanValue())) {
						found = true;
						break;
					}
				}

				if (!found) {
					fail(paramOrFieldTxt + param.getPath() + "' value is not found in expected list. ActualValue: " + aValue
							+ ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();

				List<Boolean> notFoundElems = new LinkedList<>();
				for (Boolean aValue : aListValue) {
					boolean found = false;
					for (Boolean eValue : eListValue) {
						if ((aValue == null && eValue == null) || (aValue != null && eValue != null
								&& aValue.booleanValue() == eValue.booleanValue())) {
							found = true;
							break;
						}
					}

					if (!found) {
						if (!notFoundElems.contains(aValue)) {
							notFoundElems.add(aValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' values are not found in expected list. NotFoundElems: "
							+ notFoundElems + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case DECIMAL: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "'.");
				}

				Double aValue = (Double) actualValue;
				List<Double> eListValue = (List<Double>) expectedInfo.getEv();

				boolean found = false;
				for (Double eValue : eListValue) {
					if ((aValue == null && eValue == null)
							|| (aValue != null && eValue != null && aValue.doubleValue() == eValue.doubleValue())) {
						found = true;
						break;
					}
				}

				if (!found) {
					fail(paramOrFieldTxt + param.getPath() + "' value is not found in expected list. ActualValue: " + aValue
							+ ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				List<Double> eListValue = (List<Double>) expectedInfo.getEv();

				List<Double> notFoundElems = new LinkedList<>();
				for (Double aValue : aListValue) {
					boolean found = false;
					for (Double eValue : eListValue) {
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.doubleValue() == eValue.doubleValue())) {
							found = true;
							break;
						}
					}

					if (!found) {
						if (!notFoundElems.contains(aValue)) {
							notFoundElems.add(aValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' values are not found in expected list. NotFoundElems: "
							+ notFoundElems + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case NOT_IN: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				String aValue = (String) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				boolean found = false;
				for (String eValue : eListValue) {
					if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
							expectedInfo.getTextMatchMechanism())) {
						found = true;
						break;
					}
				}

				if (found) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not be present in expected list. ActualValue: "
							+ aValue + ", ExpectedElems: " + eListValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				List<String> foundElems = new LinkedList<>();
				for (String aValue : aListValue) {
					boolean found = false;
					for (String eValue : eListValue) {
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							found = true;
							break;
						}
					}

					if (found) {
						if (!foundElems.contains(aValue)) {
							foundElems.add(aValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' values should not be present in expected list. FoundElems: "
							+ foundElems + ", ExpectedElems: " + eListValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "'.");
				}

				Long aValue = (Long) actualValue;
				List<Long> eListValue = (List<Long>) expectedInfo.getEv();

				boolean found = false;
				for (Long eValue : eListValue) {
					if ((aValue == null && eValue == null)
							|| (aValue != null && eValue != null && aValue.longValue() == eValue.longValue())) {
						found = true;
						break;
					}
				}

				if (found) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not be present in expected list. ActualValue: "
							+ aValue + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				List<Long> eListValue = (List<Long>) expectedInfo.getEv();

				List<Long> foundElems = new LinkedList<>();
				for (Long aValue : aListValue) {
					boolean found = false;
					for (Long eValue : eListValue) {
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.longValue() == eValue.longValue())) {
							found = true;
							break;
						}
					}

					if (found) {
						if (!foundElems.contains(aValue)) {
							foundElems.add(aValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' values should not be present in expected list. FoundElems: "
							+ foundElems + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case BOOLEAN: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "'.");
				}

				Boolean aValue = (Boolean) actualValue;
				List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();

				boolean found = false;
				for (Boolean eValue : eListValue) {
					if ((aValue == null && eValue == null)
							|| (aValue != null && eValue != null && aValue.booleanValue() == eValue.booleanValue())) {
						found = true;
						break;
					}
				}

				if (found) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not be present in expected list. ActualValue: "
							+ aValue + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();

				List<Boolean> foundElems = new LinkedList<>();
				for (Boolean aValue : aListValue) {
					boolean found = false;
					for (Boolean eValue : eListValue) {
						if ((aValue == null && eValue == null) || (aValue != null && eValue != null
								&& aValue.booleanValue() == eValue.booleanValue())) {
							found = true;
							break;
						}
					}

					if (found) {
						if (!foundElems.contains(aValue)) {
							foundElems.add(aValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' values should not be present in expected list. FoundElems: "
							+ foundElems + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case DECIMAL: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "'.");
				}

				Double aValue = (Double) actualValue;
				List<Double> eListValue = (List<Double>) expectedInfo.getEv();

				boolean found = false;
				for (Double eValue : eListValue) {
					if ((aValue == null && eValue == null)
							|| (aValue != null && eValue != null && aValue.doubleValue() == eValue.doubleValue())) {
						found = true;
						break;
					}
				}

				if (found) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not be present in expected list. ActualValue: "
							+ aValue + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				List<Double> eListValue = (List<Double>) expectedInfo.getEv();

				List<Double> foundElems = new LinkedList<>();
				for (Double aValue : aListValue) {
					boolean found = false;
					for (Double eValue : eListValue) {
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.doubleValue() == eValue.doubleValue())) {
							found = true;
							break;
						}
					}

					if (found) {
						if (!foundElems.contains(aValue)) {
							foundElems.add(aValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' values are not found in expected list. FoundElems: "
							+ foundElems + ", ExpectedElems: " + eListValue + ".");
				}
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case CONTAINS: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				String aValue = (String) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				if (aValue == null) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aValue
							+ ", ExpectedElems: " + eListValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				boolean found = false;
				String eValue;
				int lastFoundIndex = -1;
				int index;
				List<String> notFoundElems = new LinkedList<>();
				for (int i = 0; i < eListValue.size(); i++) {
					eValue = eListValue.get(i);
					index = StringUtil.indexOfExpectedValue(aValue, eValue, expectedInfo.getTextMatchMechanism());
					found = false;
					if (index >= 0) {
						if (expectedInfo.getInOrder() == InOrder.YES) {
							if (lastFoundIndex < 0) {
								found = true;
								lastFoundIndex = index;
							} else if (index >= lastFoundIndex) {
								found = true;
								lastFoundIndex = index;
							}
						} else {
							found = true;
						}
					}

					if (!found) {
						if (!notFoundElems.contains(eValue)) {
							notFoundElems.add(eValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aValue
							+ ", NotFoundElems: " + notFoundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<String> notFoundElems = new LinkedList<>();
				for (String eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						String aValue = aListValue.get(i);
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (!found) {
						if (!notFoundElems.contains(eValue)) {
							notFoundElems.add(eValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aListValue
							+ ", NotFoundElems: " + notFoundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				List<Long> eListValue = (List<Long>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Long> notFoundElems = new LinkedList<>();
				for (Long eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Long aValue = aListValue.get(i);
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.longValue() == eValue.longValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (!found) {
						if (!notFoundElems.contains(eValue)) {
							notFoundElems.add(eValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aListValue
							+ ", NotFoundElems: " + notFoundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case BOOLEAN: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Boolean> notFoundElems = new LinkedList<>();
				for (Boolean eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Boolean aValue = aListValue.get(i);
						if ((aValue == null && eValue == null) || (aValue != null && eValue != null
								&& aValue.booleanValue() == eValue.booleanValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (!found) {
						if (!notFoundElems.contains(eValue)) {
							notFoundElems.add(eValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aListValue
							+ ", NotFoundElems: " + notFoundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case DECIMAL: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				List<Double> eListValue = (List<Double>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Double> notFoundElems = new LinkedList<>();
				for (Double eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Double aValue = aListValue.get(i);
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.doubleValue() == eValue.doubleValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (!found) {
						if (!notFoundElems.contains(eValue)) {
							notFoundElems.add(eValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aListValue
							+ ", NotFoundElems: " + notFoundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case NOT_CONTAINS: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				String aValue = (String) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				if (aValue == null) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aValue
							+ ", ExpectedElems: " + eListValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				boolean found = false;
				String eValue;
				int lastFoundIndex = -1;
				int index;
				List<String> foundElems = new LinkedList<>();
				for (int i = 0; i < eListValue.size(); i++) {
					eValue = eListValue.get(i);
					index = StringUtil.indexOfExpectedValue(aValue, eValue, expectedInfo.getTextMatchMechanism());
					found = false;
					if (index >= 0) {
						if (expectedInfo.getInOrder() == InOrder.YES) {
							if (lastFoundIndex < 0) {
								found = true;
								lastFoundIndex = index;
							} else if (index >= lastFoundIndex) {
								found = true;
								lastFoundIndex = index;
							}
						} else {
							found = true;
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should not have matching elements of expected list. ActualValue: " + aValue
							+ ", FoundElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<String> foundElems = new LinkedList<>();
				for (String eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						String aValue = aListValue.get(i);
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should not have matching elements of expected list. ActualValue: " + aListValue
							+ ", FoundElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				fail("!Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				List<Long> eListValue = (List<Long>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Long> foundElems = new LinkedList<>();
				for (Long eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Long aValue = aListValue.get(i);
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.longValue() == eValue.longValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should not have matching elements of expected list. ActualValue: " + aListValue
							+ ", FoundElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case BOOLEAN: {
				fail("!Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Boolean> foundElems = new LinkedList<>();
				for (Boolean eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Boolean aValue = aListValue.get(i);
						if ((aValue == null && eValue == null) || (aValue != null && eValue != null
								&& aValue.booleanValue() == eValue.booleanValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should not have matching elements of expected list. ActualValue: " + aListValue
							+ ", FoundElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case DECIMAL: {
				fail("!Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				List<Double> eListValue = (List<Double>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Double> foundElems = new LinkedList<>();
				for (Double eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Double aValue = aListValue.get(i);
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.doubleValue() == eValue.doubleValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value should not have matching elements of expected list. ActualValue: " + aListValue
							+ ", FoundElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case CONTAINS_ATLEAST_N: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				String aValue = (String) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				if (aValue == null) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aValue
							+ ", ExpectedElems: " + eListValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				boolean found = false;
				String eValue;
				int lastFoundIndex = -1;
				int index;
				List<String> foundElems = new LinkedList<>();
				for (int i = 0; i < eListValue.size(); i++) {
					eValue = eListValue.get(i);
					index = StringUtil.indexOfExpectedValue(aValue, eValue, expectedInfo.getTextMatchMechanism());
					if (index >= 0) {
						if (expectedInfo.getInOrder() == InOrder.YES) {
							if (lastFoundIndex < 0) {
								found = true;
								lastFoundIndex = index;
							} else if (index >= lastFoundIndex) {
								found = true;
								lastFoundIndex = index;
							}
						} else {
							found = true;
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() < expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at least "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: " + aValue
							+ ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<String> foundElems = new LinkedList<>();
				for (String eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						String aValue = aListValue.get(i);
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() < expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at least "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: "
							+ aListValue + ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				List<Long> eListValue = (List<Long>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Long> foundElems = new LinkedList<>();
				for (Long eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Long aValue = aListValue.get(i);
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.longValue() == eValue.longValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() < expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at least "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: "
							+ aListValue + ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case BOOLEAN: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Boolean> foundElems = new LinkedList<>();
				for (Boolean eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Boolean aValue = aListValue.get(i);
						if ((aValue == null && eValue == null) || (aValue != null && eValue != null
								&& aValue.booleanValue() == eValue.booleanValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() < expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at least "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: "
							+ aListValue + ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case DECIMAL: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				List<Double> eListValue = (List<Double>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Double> foundElems = new LinkedList<>();
				for (Double eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Double aValue = aListValue.get(i);
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.doubleValue() == eValue.doubleValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() < expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at least "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: "
							+ aListValue + ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case CONTAINS_ATMOST_N: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				String aValue = (String) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				if (aValue == null) {
					fail(paramOrFieldTxt + param.getPath()
							+ "' value does not contain all the elements of expected list. ActualValue: " + aValue
							+ ", ExpectedElems: " + eListValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				boolean found = false;
				String eValue;
				int lastFoundIndex = -1;
				int index;
				List<String> foundElems = new LinkedList<>();
				for (int i = 0; i < eListValue.size(); i++) {
					eValue = eListValue.get(i);
					index = StringUtil.indexOfExpectedValue(aValue, eValue, expectedInfo.getTextMatchMechanism());
					if (index >= 0) {
						if (expectedInfo.getInOrder() == InOrder.YES) {
							if (lastFoundIndex < 0) {
								found = true;
								lastFoundIndex = index;
							} else if (index >= lastFoundIndex) {
								found = true;
								lastFoundIndex = index;
							}
						} else {
							found = true;
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at most "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: " + aValue
							+ ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<String> foundElems = new LinkedList<>();
				for (String eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						String aValue = aListValue.get(i);
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at most "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: "
							+ aListValue + ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case INTEGER_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.INTEGER_LIST) {
					fail("Expected value type must be of '" + ParamValueType.INTEGER_LIST.getType() + "'.");
				}

				List<Long> aListValue = (List<Long>) actualValue;
				List<Long> eListValue = (List<Long>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Long> foundElems = new LinkedList<>();
				for (Long eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Long aValue = aListValue.get(i);
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.longValue() == eValue.longValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at most "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: "
							+ aListValue + ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case BOOLEAN: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.BOOLEAN_LIST) {
					fail("Expected value type must be of '" + ParamValueType.BOOLEAN_LIST.getType() + "'.");
				}

				List<Boolean> aListValue = (List<Boolean>) actualValue;
				List<Boolean> eListValue = (List<Boolean>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Boolean> foundElems = new LinkedList<>();
				for (Boolean eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Boolean aValue = aListValue.get(i);
						if ((aValue == null && eValue == null) || (aValue != null && eValue != null
								&& aValue.booleanValue() == eValue.booleanValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at most "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: "
							+ aListValue + ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case DECIMAL: {
				fail("Contains operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.DECIMAL_LIST) {
					fail("Expected value type must be of '" + ParamValueType.DECIMAL_LIST.getType() + "'.");
				}

				List<Double> aListValue = (List<Double>) actualValue;
				List<Double> eListValue = (List<Double>) expectedInfo.getEv();

				int lastFoundIndex = -1;

				List<Double> foundElems = new LinkedList<>();
				for (Double eValue : eListValue) {
					boolean found = false;
					for (int i = 0; i < aListValue.size(); i++) {
						Double aValue = aListValue.get(i);
						if ((aValue == null && eValue == null)
								|| (aValue != null && eValue != null && aValue.doubleValue() == eValue.doubleValue())) {
							if (expectedInfo.getInOrder() == InOrder.YES) {
								if (lastFoundIndex < 0) {
									found = true;
									lastFoundIndex = i;
									break;
								} else if (i >= lastFoundIndex) {
									found = true;
									lastFoundIndex = i;
									break;
								}
							} else {
								found = true;
								break;
							}
						}
					}

					if (found) {
						if (!foundElems.contains(eValue)) {
							foundElems.add(eValue);
						}
					}
				}

				if (foundElems.size() > expectedInfo.getN().intValue()) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not contain at most" + "" + " "
							+ expectedInfo.getN().intValue() + " element(s) of expected list. ActualValue: "
							+ aListValue + ", MatchedElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case STARTS_WITH: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();

				if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.icStartsWithExpectedValue.name());
				} else {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.startsWithExpectedValue.name());
				}

				if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
						expectedInfo.getTextMatchMechanism())) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not starts with expected list. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.icStartsWithExpectedValue.name());
				} else {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.startsWithExpectedValue.name());
				}

				List<String> notFoundElems = new LinkedList<>();
				for (String aValue : aListValue) {
					boolean found = false;
					for (int i = 0; i < eListValue.size(); i++) {
						String eValue = eListValue.get(i);
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							found = true;
							break;
						}
					}

					if (!found) {
						if (!notFoundElems.contains(aValue)) {
							notFoundElems.add(aValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not starts with expected elems. ActualValue: "
							+ aListValue + ", NotFoundElems: " + notFoundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case INTEGER_LIST: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN_LIST: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL_LIST: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case NOT_STARTS_WITH: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();

				if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.icStartsWithExpectedValue.name());
				} else {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.startsWithExpectedValue.name());
				}

				if (!StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
						expectedInfo.getTextMatchMechanism())) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not starts with expected value. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.icStartsWithExpectedValue.name());
				} else {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.startsWithExpectedValue.name());
				}

				List<String> foundElems = new LinkedList<>();
				for (String aValue : aListValue) {
					boolean found = false;
					for (int i = 0; i < eListValue.size(); i++) {
						String eValue = eListValue.get(i);
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							found = true;
							break;
						}
					}

					if (found) {
						if (!foundElems.contains(aValue)) {
							foundElems.add(aValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not starts with expected elems. ActualValue: "
							+ aListValue + ", FoundElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case INTEGER_LIST: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN_LIST: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL_LIST: {
				fail("starts-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case ENDS_WITH: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();

				if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.icEndsWithExpectedValue.name());
				} else {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.endsWithExpectedValue.name());
				}

				if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
						expectedInfo.getTextMatchMechanism())) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not ends with expected list. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.icEndsWithExpectedValue.name());
				} else {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.endsWithExpectedValue.name());
				}

				List<String> notFoundElems = new LinkedList<>();
				for (String aValue : aListValue) {
					boolean found = false;
					for (int i = 0; i < eListValue.size(); i++) {
						String eValue = eListValue.get(i);
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							found = true;
							break;
						}
					}

					if (!found) {
						if (!notFoundElems.contains(aValue)) {
							notFoundElems.add(aValue);
						}
					}
				}

				if (notFoundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' value does not ends with expected elems. ActualValue: "
							+ aListValue + ", NotFoundElems: " + notFoundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				fail("ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case INTEGER_LIST: {
				fail("ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN: {
				fail("ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN_LIST: {
				fail("ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL: {
				fail("ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL_LIST: {
				fail("ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case NOT_ENDS_WITH: {
			switch (param.getValueType()) {
			case STRING: {
				if (expectedInfo.getValueType() != ParamValueType.STRING) {
					fail("Expected value type must be of '" + ParamValueType.STRING.getType() + "'.");
				}

				String aValue = (String) actualValue;
				String eValue = (String) expectedInfo.getEv();

				if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.icEndsWithExpectedValue.name());
				} else {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.endsWithExpectedValue.name());
				}

				if (!StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
						expectedInfo.getTextMatchMechanism())) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not ends with expected list. ActualValue: "
							+ aValue + ", ExpectedValue: " + eValue + ", TextMatchMechanism: "
							+ expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case STRING_LIST: {
				if (expectedInfo.getValueType() != ParamValueType.STRING_LIST) {
					fail("Expected value type must be of '" + ParamValueType.STRING_LIST.getType() + "'.");
				}

				List<String> aListValue = (List<String>) actualValue;
				List<String> eListValue = (List<String>) expectedInfo.getEv();

				if (expectedInfo.getIgnoreCase() == IgnoreCase.YES) {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.icEndsWithExpectedValue.name());
				} else {
					expectedInfo.setTextMatchMechanism(TextMatchMechanism.endsWithExpectedValue.name());
				}

				List<String> foundElems = new LinkedList<>();
				for (String aValue : aListValue) {
					boolean found = false;
					for (int i = 0; i < eListValue.size(); i++) {
						String eValue = eListValue.get(i);
						if (StringUtil.isTextMatchedWithExpectedValue(aValue, eValue,
								expectedInfo.getTextMatchMechanism())) {
							found = true;
							break;
						}
					}

					if (found) {
						if (!foundElems.contains(aValue)) {
							foundElems.add(aValue);
						}
					}
				}

				if (foundElems.size() > 0) {
					fail(paramOrFieldTxt + param.getPath() + "' value should not ends with expected elems. ActualValue: "
							+ aListValue + ", NotFoundElems: " + foundElems + ", ExpectedElems: " + eListValue
							+ ", TextMatchMechanism: " + expectedInfo.getTextMatchMechanism().name() + ".");
				}

				break;
			}
			case INTEGER: {
				fail("!ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case INTEGER_LIST: {
				fail("!ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN: {
				fail("!ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case BOOLEAN_LIST: {
				fail("!ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL: {
				fail("!ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			case DECIMAL_LIST: {
				fail("!ends-with operator is not supported for '" + param.getValueType().getType() + "' value.");
				break;
			}
			default:
				fail("Value type '" + param.getValueType().getType() + "' is not supported.");
				break;
			}

			break;
		}
		case PRESENT: {
			if (actualValue == null) {
				fail(paramOrFieldTxt + param.getPath() + "' or it's value does not exist. ActualValue: " + actualValue + ".");
			}
			break;
		}
		case NOT_PRESENT: {
			if (actualValue != null) {
				fail(paramOrFieldTxt + param.getPath() + "' or it's value should not exist. ActualValue: " + actualValue
						+ ".");
			}
			break;
		}
		default:
			fail("'" + op.getOperator() + "' operator is not supported.");
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
