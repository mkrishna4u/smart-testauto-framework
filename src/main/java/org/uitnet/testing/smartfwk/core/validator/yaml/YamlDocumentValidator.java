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
package org.uitnet.testing.smartfwk.core.validator.yaml;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.YamlDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

/**
 * This class is used to validate the YAML document using JSON/YAML path mechanism.
 * 
 * NOTE: YAML path and JSON path mechanism is same.
 * 
 * @author Madhav Krishna
 *
 */
public class YamlDocumentValidator {
	protected YamlDocumentReader yamlDocReader;

	public YamlDocumentValidator(String yamlAsString, boolean updateSystemVariablesValue) {
		yamlDocReader = new YamlDocumentReader(yamlAsString, updateSystemVariablesValue);

	}

	public YamlDocumentValidator(File yamlFilePath, boolean updateSystemVariablesValue) {
		yamlDocReader = new YamlDocumentReader(yamlFilePath, updateSystemVariablesValue);
	}
	
	public DocumentContext prepareDocumentContext(Object obj) {
		return yamlDocReader.prepareDocumentContext(obj);
	}

	public DocumentContext getDocumentContext() {
		return yamlDocReader.getDocumentContext();
	}

	public <T> List<T> readValuesAsList(String yamlPath) {
		return yamlDocReader.readValuesAsList(yamlPath);
	}

	public <T> Set<T> readValuesAsSet(String yamlPath) {
		return yamlDocReader.readValuesAsSet(yamlPath);
	}

	public <T> T readSingleValue(String yamlPath) {
		return yamlDocReader.readSingleValue(yamlPath);
	}
	
	public <T> T readValueAsObject(String yamlPath, Class<T> clazz) {
		return yamlDocReader.readValueAsObject(yamlPath, clazz);
	}

	public void validatePathPresent(String elementName, String yamlPath) {
		List<?> elems = yamlDocReader.getDocumentContext().read(yamlPath);
		Assert.assertTrue(elems != null && elems.size() > 0,
				"Element '" + elementName + "' does not exist on YAML Path '" + yamlPath + "'.");
	}

	public void validateExpectedNRecordsPresent(String elementName, String yamlPath, int expectedN) {
		List<?> result = null;
		try {
			result = yamlDocReader.getDocumentContext().read(yamlPath);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect YAML Path '" + yamlPath + "'.", e);
		}

		Assert.assertNotNull(result, "Element '" + elementName + "' does not exist on YAML Path '" + yamlPath + "'.");
		Assert.assertTrue(result.size() == expectedN, "Element '" + elementName + "' does not have exact '" + expectedN
				+ "' records on YAML Path '" + yamlPath + "'. Found: " + result.size());
	}

	public void validateAtleastNRecordsPresent(String elementName, String yamlPath, int atleastN) {
		List<?> result = null;
		try {
			result = yamlDocReader.getDocumentContext().read(yamlPath);
		} catch (Exception e) {
			Assert.fail("Element '" + elementName + "' has incorrect YAML Path '" + yamlPath + "'.", e);
		}

		Assert.assertNotNull(result, "Element '" + elementName + "' does not exist on YAML Path '" + yamlPath + "'.");
		Assert.assertTrue(result.size() >= atleastN, "Element '" + elementName + "' does not have atleast '" + atleastN
				+ "' records on YAML Path '" + yamlPath + "'. Found: " + result.size());
	}

	public <T> void validateValuesPresent(String elementName, String yamlPath, T[] values) {
		TypeRef<Set<T>> typeRef = new TypeRef<Set<T>>() {
		};
		Set<T> elems = yamlDocReader.getDocumentContext().read(yamlPath, typeRef);
		Assert.assertFalse(elems.size() == 0, "No record found for Element '" + elementName + "' on YAML path '"
				+ yamlPath + "'. Expects records with values: " + Arrays.asList(values) + ".");

		Set<T> valuesAsSet = new HashSet<>(Arrays.asList(values));
		valuesAsSet.removeAll(elems);

		Assert.assertTrue(valuesAsSet.size() == 0, "Following values " + valuesAsSet + " do not exist for Element '"
				+ elementName + "' on YAML Path '" + yamlPath + "'. YAML element values: " + elems);
	}

	public <T> void validateExactMatchForValues(String elementName, String yamlPath, T[] values) {
		TypeRef<Set<T>> typeRef = new TypeRef<Set<T>>() {
		};
		Set<T> elems = yamlDocReader.getDocumentContext().read(yamlPath, typeRef);
		Assert.assertFalse(elems.size() == 0, "No record found for Element '" + elementName + "' on YAML path '"
				+ yamlPath + "'. Expects records with values: " + Arrays.asList(values) + ".");

		Set<T> valuesAsSet1 = new HashSet<>(Arrays.asList(values));
		Set<T> valuesAsSet2 = new HashSet<>(Arrays.asList(values));
		valuesAsSet1.removeAll(elems);
		elems.removeAll(valuesAsSet2);

		Assert.assertTrue(valuesAsSet1.size() == 0, "Values " + valuesAsSet1 + " are not present for Element '"
				+ elementName + "' on YAML Path '" + yamlPath + "'. Expecting YAML path values: " + valuesAsSet2 + ".");
		Assert.assertTrue(elems.size() == 0, "Values " + elems + " are extra for Element '" + elementName
				+ "' on YAML Path '" + yamlPath + "'. Expecting YAML path values: " + valuesAsSet2 + ".");
	}

	public <T> void validateSingleValueMatch(String elementName, String yamlPath, Class<T> valueClazz, T value) {
		T elemValue = yamlDocReader.getDocumentContext().read(yamlPath, valueClazz);
		Assert.assertEquals(elemValue, value,
				"Element '" + elementName + "' value does not match at YAML path '" + yamlPath + "'.");
	}

	public static void main(String[] args) {
		String yamlString = sampleJson();
		YamlDocumentValidator validator = new YamlDocumentValidator(yamlString, true);
		/* validator.validatePathPresent("Price Range", "$..price_range");
		validator.validateValuesPresent("Price", "$.book[*].price", new Double[] { 49.99, 29.99d, 8.99 });
		validator.validateExactMatchForValues("Price", "$.book[*].price", new Double[] { 49.99, 29.99, 8.99, 6.00 });
		validator.validateSingleValueMatch("First Book Author", "$.book[0].author", String.class, "Ben Smith");
		validator.validateSingleValueMatch("First Book Price", "$.book[0].price", Double.class, 49.99);
		Double price = validator.readSingleValue("$.book[0].price");
		System.out.println("Double price: " + price);
		System.out.println("Author: " + validator.readSingleValue("$.book[0].author"));
		System.out.println("Cities: " + validator.readValuesAsList("$.cities")); */
//		DatabaseProfile dbProfile = validator.readValueAsObject("$", DatabaseProfile.class);
//		System.out.println("dbProfile: " + dbProfile);
		DocumentContext docCtx =  validator.getDocumentContext();
		TypeRef<HashMap<String, Object>> tRef = new TypeRef<HashMap<String, Object>>() {};
		HashMap<String, Object> additionalProps = docCtx.read("$.additionalProps", tRef);
		//String value = additionalProps.get("prop2");
		System.out.println("additionalProps: " + additionalProps);
		System.out.println("TTT:" + JsonYamlUtil.readNoException("$.text", String.class, docCtx));
		System.out.println("TTT:" + JsonYamlUtil.readNoException("$.additionalPropsNone", (new TypeRef<Map<String, Object>>() {}), docCtx));
		
//		System.out.println("readAllProps: " + docCtx.read("$"));
//		System.out.println("readAllProps: " + docCtx.read("$.gg"));
	}

	private static String sampleJson() {
		String yaml = 
				"profileName: test\n"
				+ "appName: test-app\n"
				+ "hostName: uitnet.org\n"
				+ "port: 1921\n"
				+ "databaseName: test-db\n"
				+ "username: test username\n"
				+ "password: test password\n"
				+ "additionalPropsNone:\n"
				+ "additionalProps:\n"
				+ "  prop1:\n"
				+ "    cprop1: false\n"
				+ "  prop2.prop3: 98.5\n"
				+ "  prop2.prop4: 98.5"
				/* 
				  "book:\n" 
				  + "  - { title: Beginning YAML, author: Ben Smith, price: 49.99}\n"
				  + "  - title: YAML at Work\n"
				  + "    author: Tom Marrs\n"
				  + "    price: 29.99\n"
				  + "  - title: Learn YAML in a DAY\n"
				  + "    author: Acodemy\n"
				  + "    price: 8.99\n"
				  + "  - title: 'YAML: Questions and Answers'\n"
				  + "    author: George Duckett\n"
				  + "    price: 6.00\n"
				  + "price_range:\n" 
				  + "  cheap: 10.00\n" 
				  + "  medium: 20.00\n"
				  + "cities: ["
				  + "  aa, "
				  + "  bb, "
				  + "  cc"
				  + "]" */
				  ;
		return yaml;
	}
}
