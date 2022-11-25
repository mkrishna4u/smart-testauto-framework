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
package org.uitnet.testing.smartfwk.ui.core.utils;

import java.util.Arrays;
import java.util.List;

import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class StringUtil {
	private StringUtil() {
		// do nothing
	}

	/**
	 * Trims the leading and trailing spaces
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		if (str == null) {
			return null;
		}
		return str.trim();
	}
	
	public static String trimNullAsEmpty(String str) {
		if (str == null) {
			return "";
		}
		return str.trim();
	}

	public static boolean isEmptyAfterTrim(String str) {
		if (str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public static boolean isEmptyNoTrim(String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		return false;
	}
	
	public static String trimNullObjAsEmptyStr(Object obj) {
		if (obj == null) {
			return "";
		}
		return String.valueOf(obj).trim();
	}

	/**
	 * Checks whether the text exist in string or not.
	 * 
	 * @param str
	 * @param text
	 * @return
	 */
	public static boolean containsText(String str, String text) {
		if (str == null) {
			return false;
		}
		return str.contains(text);
	}

	/**
	 * Checks if the string starts with text.
	 * 
	 * @param str
	 * @param text
	 * @return
	 */
	public static boolean startsWithText(String str, String text) {
		if (str == null) {
			return false;
		}
		return str.startsWith(text);
	}

	/**
	 * Checks if string ends with a text.
	 * 
	 * @param str
	 * @param text
	 * @return
	 */
	public static boolean endsWithText(String str, String text) {
		if (str == null) {
			return false;
		}
		return str.endsWith(text);
	}

	/**
	 * Checks whether a word present in a string.
	 * 
	 * @param str
	 * @param word
	 * @return
	 */
	public static boolean containsWord(String str, String word) {
		if (str == null || word == null) {
			return false;
		}
		String[] words = str.split(" ");
		for (String word1 : words) {
			if (word1.trim().equals(word.trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the text matches with expected value based on text match mechanism.
	 * @param actualValue
	 * @param expectedValue
	 * @param textMatchMechanism
	 * @return if matches then returns true else returns false.
	 */
	public static boolean isTextMatchedWithExpectedValue(String text, String expectedValue,
			TextMatchMechanism textMatchMechanism) {
		return DataMatchUtil.matchTextValue(text, expectedValue, textMatchMechanism);
	}
	
	/**
	 * 
	 * @param text
	 * @param expectedValue
	 * @param textMatchMechanism
	 * @return the index of expected value in text. -1 is returned when value is not matched.
	 */
	public static int indexOfExpectedValue(String text, String expectedValue,
			TextMatchMechanism textMatchMechanism) {
		return DataMatchUtil.indexOfExpectedValue(text, expectedValue, textMatchMechanism);
	}
	
	public static String removeItemsFromText(String text, String itemSeparator, String... itemsToRemove) {
		if(text == null) { return null; }
		String[] items = text.split(itemSeparator);
		String newText = "";
		List<String> itemsToRemoveList = Arrays.asList(itemsToRemove);
		for(String item : items) {
			if(itemsToRemoveList.contains(item.trim())) {
				continue;
			}
			
			if("".equals(newText)) {
				newText = item;
			} else {
				newText = newText + itemSeparator + item;
			}
		}
		
		return newText;
	}
	
	public static void main(String[] args) {
		System.out.println(removeItemsFromText("bcdefg,mmm", ",", "efg", "bcd", "mmm"));
	}
}
