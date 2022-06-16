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
package org.uitnet.testing.smartfwk.api.core.validator.json;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.config.DatabaseProfile;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class JsonDocumentValidator {
	protected JsonDocumentReader jsonDocReader;

	public JsonDocumentValidator(String jsonAsString) {
		jsonDocReader = new JsonDocumentReader(jsonAsString);

	}

	public JsonDocumentValidator(File jsonFilePath) {
		jsonDocReader = new JsonDocumentReader(jsonFilePath);
	}
	
	public DocumentContext prepareDocumentContext(Object obj) {
		return jsonDocReader.prepareDocumentContext(obj);
	}

	public DocumentContext getDocumentContext() {
		return jsonDocReader.getDocumentContext();
	}

	public <T> List<T> readValuesAsList(String jsonPath) {
		return jsonDocReader.readValuesAsList(jsonPath);
	}

	public <T> Set<T> readValuesAsSet(String jsonPath) {
		return jsonDocReader.readValuesAsSet(jsonPath);
	}

	public <T> T readSingleValue(String jsonPath) {
		return jsonDocReader.readSingleValue(jsonPath);
	}
	
	public <T> T readValueAsObject(String yamlPath, Class<T> clazz) {
		return jsonDocReader.readValueAsObject(yamlPath, clazz);
	}

	public void validatePathPresent(String elementName, String jsonPath) {
		List<?> elems = jsonDocReader.getDocumentContext().read(jsonPath);
		Assert.assertTrue(elems != null && elems.size() > 0,
				"Element '" + elementName + "' does not exist on JSON Path '" + jsonPath + "'.");
	}

	public void validateExpectedNRecordsPresent(String elementName, String jsonPath, int expectedN) {
		List<?> result = null;
		try {
			result = jsonDocReader.getDocumentContext().read(jsonPath);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect JSON Path '" + jsonPath + "'.", e);
		}

		Assert.assertNotNull(result, "Element '" + elementName + "' does not exist on JSON Path '" + jsonPath + "'.");
		Assert.assertTrue(result.size() == expectedN, "Element '" + elementName + "' does not have exact '" + expectedN
				+ "' records on JSON Path '" + jsonPath + "'. Found: " + result.size());
	}

	public void validateAtleastNRecordsPresent(String elementName, String jsonPath, int atleastN) {
		List<?> result = null;
		try {
			result = jsonDocReader.getDocumentContext().read(jsonPath);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect JSON Path '" + jsonPath + "'.", e);
		}

		Assert.assertNotNull(result, "Element '" + elementName + "' does not exist on JSON Path '" + jsonPath + "'.");
		Assert.assertTrue(result.size() >= atleastN, "Element '" + elementName + "' does not have atleast '" + atleastN
				+ "' records on JSON Path '" + jsonPath + "'. Found: " + result.size());
	}

	public <T> void validateValuesPresent(String elementName, String jsonPath, T[] values) {
		TypeRef<Set<T>> typeRef = new TypeRef<Set<T>>() {
		};
		Set<T> elems = jsonDocReader.getDocumentContext().read(jsonPath, typeRef);
		Assert.assertFalse(elems.size() == 0, "No record found for Element '" + elementName + "' on JSON path '"
				+ jsonPath + "'. Expects records with values: " + Arrays.asList(values) + ".");

		Set<T> valuesAsSet = new HashSet<>(Arrays.asList(values));
		valuesAsSet.removeAll(elems);

		Assert.assertTrue(valuesAsSet.size() == 0, "Following values " + valuesAsSet + " do not exist for Element '"
				+ elementName + "' on JSON Path '" + jsonPath + "'. JSON element values: " + elems);
	}

	public <T> void validateExactMatchForValues(String elementName, String jsonPath, T[] values) {
		TypeRef<Set<T>> typeRef = new TypeRef<Set<T>>() {
		};
		Set<T> elems = jsonDocReader.getDocumentContext().read(jsonPath, typeRef);
		Assert.assertFalse(elems.size() == 0, "No record found for Element '" + elementName + "' on JSON path '"
				+ jsonPath + "'. Expects records with values: " + Arrays.asList(values) + ".");

		Set<T> valuesAsSet1 = new HashSet<>(Arrays.asList(values));
		Set<T> valuesAsSet2 = new HashSet<>(Arrays.asList(values));
		valuesAsSet1.removeAll(elems);
		elems.removeAll(valuesAsSet2);

		Assert.assertTrue(valuesAsSet1.size() == 0, "Values " + valuesAsSet1 + " are not present for Element '"
				+ elementName + "' on JSON Path '" + jsonPath + "'. Expecting JSON path values: " + valuesAsSet2 + ".");
		Assert.assertTrue(elems.size() == 0, "Values " + elems + " are extra for Element '" + elementName
				+ "' on JSON Path '" + jsonPath + "'. Expecting JSON path values: " + valuesAsSet2 + ".");
	}

	public <T> void validateSingleValueMatch(String elementName, String jsonPath, Class<T> valueClazz, T value) {
		T elemValue = jsonDocReader.getDocumentContext().read(jsonPath, valueClazz);
		Assert.assertEquals(elemValue, value,
				"Element '" + elementName + "' value does not match at JSON path '" + jsonPath + "'.");
	}

	public static void main(String[] args) {
		String jsonString = sampleJson();
		JsonDocumentValidator validator = new JsonDocumentValidator(jsonString);
		validator.validatePathPresent("Price Range", "$..price_range");
		validator.validateValuesPresent("Price", "$.book[*].price", new Double[] { 49.99, 29.99d, 8.99 });
		validator.validateExactMatchForValues("Price", "$.book[*].price", new Double[] { 49.99, 29.99, 8.99, 6.00 });
		validator.validateSingleValueMatch("First Book Author", "$.book[0].author", String.class, "Ben Smith");
		validator.validateSingleValueMatch("First Book Price", "$.book[0].price", Double.class, 49.99);
		Double price = validator.readSingleValue("$.book[0].price");
		System.out.println("Double price: " + price);
		DatabaseProfile objectInfo = validator.readSingleValue("$.book[0]");
		System.out.println("Map value: " + objectInfo);
	}

	private static String sampleJson() {
		String json = "{\r\n" + "    \"book\": \r\n" + "    [\r\n" + "        {\r\n"
				+ "            \"title\": \"Beginning JSON\",\r\n" + "            \"author\": \"Ben Smith\",\r\n"
				+ "            \"price\": 49.99\r\n" + "        },\r\n" + "\r\n" + "        {\r\n"
				+ "            \"title\": \"JSON at Work\",\r\n" + "            \"author\": \"Tom Marrs\",\r\n"
				+ "            \"price\": 29.99\r\n" + "        },\r\n" + "\r\n" + "        {\r\n"
				+ "            \"title\": \"Learn JSON in a DAY\",\r\n" + "            \"author\": \"Acodemy\",\r\n"
				+ "            \"price\": 8.99\r\n" + "        },\r\n" + "\r\n" + "        {\r\n"
				+ "            \"title\": \"JSON: Questions and Answers\",\r\n"
				+ "            \"author\": \"George Duckett\",\r\n" + "            \"price\": 6.00\r\n"
				+ "        }\r\n" + "    ],\r\n" + "\r\n" + "    \"price_range\": \r\n" + "    {\r\n"
				+ "        \"cheap\": 10.00,\r\n" + "        \"medium\": 20.00\r\n" + "    }\r\n" + "}";
		return json;
	}
}
