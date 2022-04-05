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
package org.uitnet.testing.smartfwk.ui.core.defaults.stepdefs;

import java.util.List;

import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.AbstractAppConnector;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.cache.DefaultSmartCache;
import org.uitnet.testing.smartfwk.ui.core.cache.SmartCache;
import org.uitnet.testing.smartfwk.ui.core.cache.SmartCacheSubscriber;
import org.uitnet.testing.smartfwk.ui.core.objects.DOMObjectValidator;
import org.uitnet.testing.smartfwk.ui.core.objects.NewTextLocation;
import org.uitnet.testing.smartfwk.ui.core.objects.validator.mechanisms.TextMatchMechanism;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;
import org.uitnet.testing.smartfwk.validator.FieldValidator;

import io.cucumber.datatable.DataTable;
import io.cucumber.docstring.DocString;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Step definitions for Home page.
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultUiFormElementOperationsStepDefs {
	// ------------- Common Code for step definition - START -------
	private AbstractAppConnector appConnector;
	private Scenario runningScenario;
	private SmartAppDriver appDriver;
	private SmartCache globalCache;

	/**
	 * Constructor
	 */
	public DefaultUiFormElementOperationsStepDefs() {
		globalCache = DefaultSmartCache.getInstance();

		appConnector = globalCache.getAppConnector();
		runningScenario = globalCache.getRunningScenario();
		appDriver = globalCache.getAppDriver();

		// Subscribe to the the cache to get the latest data
		globalCache.subscribe(new SmartCacheSubscriber() {
			@Override
			protected void onMessage(SmartCache message) {
				appConnector = message.getAppConnector();
				runningScenario = message.getRunningScenario();
				appDriver = message.getAppDriver();
			}
		});
	}

	// ------------- Common Code for step definition - END -------

	// ------------- Step definition starts here -----------------
	/**
	 * Data table will have the following columns, first row will be ignored due to column header. example below:
	 * | Page Element | 
	 * | PO.poObject{maxTimeToWaitInSeconds: 6} |
	 * | PO.poObject |
	 * @param dataTable
	 */
	@Then("Verify that the following elements are visible:")
	@Then("Verify that the following page objects are visible:")
	@Then("Verify that the following page elements are visible:")
	public void verify_that_the_following_elements_are_visible(DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String po =  row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
			PageObjectUtil.invokeValidatorMethod("validateVisible", new Class<?>[] {Integer.TYPE}, 
					new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
		}
	}
	
	/**
	 * 
	 * @param po
	 */
	@Then("Verify {string} element is visible.")
	@Then("Verify {string} page object is visible.")
	@Then("Verify that {string} element is visible.")
	@Then("Verify that {string} page object is visible.")
	@Then("Verify that {string} page element is visible.")
	public void verify_that_the_page_element_is_visible(String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateVisible", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	/**
	 * Data table will have the following columns, first row will be ignored due to column header. example below:
	 * | Page Element | 
	 * | PO.poObject{maxTimeToWaitInSeconds: 6} |
	 * | PO.poObject |
	 * @param dataTable
	 */
	@Then("Verify that the following elements are hidden:")
	@Then("Verify that the following page objects are hidden:")
	@Then("Verify that the following page elements are hidden:")
	public void verify_that_the_following_elements_are_hidden(DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String po =  row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
			PageObjectUtil.invokeValidatorMethod("validateHidden", new Class<?>[] {Integer.TYPE}, 
					new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
		}
	}
	
	/**
	 * 
	 * @param po
	 */
	@Then("Verify that {string} element is hidden.")
	@Then("Verify that {string} page object is hidden.")
	@Then("Verify that {string} page element is hidden.")
	public void verify_that_the_page_element_is_hidden(String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateHidden", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	/**
	 * Data table will have the following columns, first row will be ignored due to column header. example below:
	 * | Page Element | 
	 * | PO.poObject{maxTimeToWaitInSeconds: 6} |
	 * | PO.poObject |
	 * @param dataTable
	 */
	@Then("Verify that the following elements are disabled:")
	@Then("Verify that the following page objects are disabled:")
	@Then("Verify that the following page elements are disabled:")
	public void verify_that_the_following_elements_are_disabled(DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String po =  row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
			PageObjectUtil.invokeValidatorMethod("validateDisabled", new Class<?>[] {Integer.TYPE}, 
					new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
		}
	}
	
	/**
	 * 
	 * @param po
	 */
	@Then("Verify that {string} element is disabled.")
	@Then("Verify that {string} page object is disabled.")
	@Then("Verify that {string} page element is disabled.")
	public void verify_that_the_page_element_is_disabled(String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateDisabled", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	/**
	 * Data table will have the following columns, first row will be ignored due to column header. example below:
	 * | Page Element | 
	 * | PO.poObject{maxTimeToWaitInSeconds: 6} |
	 * | PO.poObject |
	 * @param dataTable
	 */
	@Then("Verify that the following elements are enabled:")
	@Then("Verify that the following page objects are enabled:")
	@Then("Verify that the following page elements are enabled:")
	public void verify_that_the_following_elements_are_enabled(DataTable dataTable) {
		List<List<String>> rows = dataTable.asLists();
		List<String> row = null;
		for(int i = 1; i < rows.size(); i++) {
			row = rows.get(i);
			String po =  row.get(0); // Page object
			PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
			PageObjectUtil.invokeValidatorMethod("validateEnabled", new Class<?>[] {Integer.TYPE}, 
					new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
		}
	}
	
	/**
	 * 
	 * @param po
	 */
	@Then("Verify that {string} element is enabled.")
	@Then("Verify that {string} page object is enabled.")
	@Then("Verify that {string} page element is enabled.")
	public void verify_that_the_page_element_is_enabled(String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateEnabled", new Class<?>[] {Integer.TYPE}, 
				new Object[] {poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	@When("Type {string} text in {string} element.")
	@When("Type {string} text in {string} page object.")
	@When("Type {string} text in {string} page element.")
	public void type_text_in_element(String textToType, String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("typeText", new Class<?>[] {String.class, NewTextLocation.class, Integer.TYPE}, 
				new Object[] {textToType, NewTextLocation.replace, poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	@When("Type the following text in {string} element:")
	@When("Type the following text in {string} page object:")
	@When("Type the following text in {string} page element:")
	public void type_the_following_text_in_element(String po, DocString textToType) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("typeText", new Class<?>[] {String.class, NewTextLocation.class, Integer.TYPE}, 
				new Object[] {textToType.getContent(), NewTextLocation.replace, poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	@Then("Verify {string} element displays {string} value.")
	public void verify_element_displays_value(String po, String value) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateValue", new Class<?>[] {String.class, TextMatchMechanism.class, Integer.TYPE}, 
				new Object[] {value, TextMatchMechanism.exactMatchWithExpectedValueWithRemovedWhiteSpace, poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	@Then("Verify {string} element displays following value:")
	public void verify_element_displays_value(String po, DocString value) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateValue", new Class<?>[] {String.class, TextMatchMechanism.class, Integer.TYPE}, 
				new Object[] {value.getContent(), TextMatchMechanism.exactMatchWithExpectedValueWithRemovedWhiteSpace, poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	@Then("Verify {string} element contains {string} substring.")
	@Then("Verify {string} element contains {string} sub text.")
	@Then("Verify the value of {string} element contains {string} substring.")
	@Then("Verify the value of {string} element contains {string} sub text.")
	public void verify_element_contains_value(String po, String substring) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateValue", new Class<?>[] {String.class, TextMatchMechanism.class, Integer.TYPE}, 
				new Object[] {substring, TextMatchMechanism.containsExpectedValue, poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	@Then("Verify {string} element value starts with {string} text.")
	public void verify_element_value_starts_with(String po, String value) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateValue", new Class<?>[] {String.class, TextMatchMechanism.class, Integer.TYPE}, 
				new Object[] {value, TextMatchMechanism.startsWithExpectedValue, poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	@Then("Verify {string} element value ends with {string} text.")
	public void verify_element_value_ends_with(String po, String value) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		PageObjectUtil.invokeValidatorMethod("validateValue", new Class<?>[] {String.class, TextMatchMechanism.class, Integer.TYPE}, 
				new Object[] {value, TextMatchMechanism.endsWithExpectedValue, poInfo.getMaxIterationsToLocateElements()}, poInfo, appDriver);
	}
	
	@Then("Verify that the attribute {string} of {string} element contains {string} value.")
	public void verify_that_the_attribute_of_element_contains_value(String attributeName, String po, String value) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", null, null, poInfo, appDriver);
		String attrValue = domObjectValidator.getAttributeValue(attributeName, poInfo.getMaxIterationsToLocateElements());
		FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPoObjectName() + "->" + attributeName, attrValue, value, TextMatchMechanism.exactMatchWithExpectedValueWithRemovedWhiteSpace);
	}
	
	@Then("Verify that the attribute {string} of {string} element contains {string} substring.")
	@Then("Verify that the attribute {string} of {string} element contains {string} sub text.")
	public void verify_that_the_attribute_of_element_contains_substring(String attributeName, String po, String substring) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", null, null, poInfo, appDriver);
		String attrValue = domObjectValidator.getAttributeValue(attributeName, poInfo.getMaxIterationsToLocateElements());
		FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPoObjectName() + "->" + attributeName, attrValue, substring, TextMatchMechanism.containsExpectedValue);
	}
	
	@Then("Verify that the attribute {string} value of {string} element starts with {string} text.")
	public void verify_that_the_attribute_value_of_element_starts_with_text(String attributeName, String po, String text) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", null, null, poInfo, appDriver);
		String attrValue = domObjectValidator.getAttributeValue(attributeName, poInfo.getMaxIterationsToLocateElements());
		FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPoObjectName() + "->" + attributeName, attrValue, text, TextMatchMechanism.startsWithExpectedValue);
	}
	
	@Then("Verify that the attribute {string} value of {string} element ends with {string} text.")
	public void verify_that_the_attribute_value_of_element_ends_with_text(String attributeName, String po, String text) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", null, null, poInfo, appDriver);
		String attrValue = domObjectValidator.getAttributeValue(attributeName, poInfo.getMaxIterationsToLocateElements());
		FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPoObjectName() + "->" + attributeName, attrValue, text, TextMatchMechanism.endsWithExpectedValue);
	}
	
	@Then("Verify that the text part of {string} element contains {string} text.")
	public void verify_that_the_text_part_of_element_contains_text(String po, String text) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", null, null, poInfo, appDriver);
		String textValue = domObjectValidator.getText(poInfo.getMaxIterationsToLocateElements());
		FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPoObjectName() + "->text", textValue, text, TextMatchMechanism.exactMatchWithExpectedValueWithRemovedWhiteSpace);
	}
	
	@Then("Verify that the text part of {string} element contains {string} substring.")
	@Then("Verify that the text part of {string} element contains {string} sub text.")
	public void verify_that_the_text_part_of_element_contains_substring(String po, String substring) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", null, null, poInfo, appDriver);
		String textValue = domObjectValidator.getText(poInfo.getMaxIterationsToLocateElements());
		FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPoObjectName() + "->text", textValue, substring, TextMatchMechanism.containsExpectedValue);
	}
	
	@Then("Verify that the text part of {string} element starts with {string} text.")
	public void verify_that_the_text_part_of_element_starts_with_text(String po, String text) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", null, null, poInfo, appDriver);
		String textValue = domObjectValidator.getText(poInfo.getMaxIterationsToLocateElements());
		FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPoObjectName() + "->text", textValue, text, TextMatchMechanism.startsWithExpectedValue);
	}
	
	@Then("Verify that the text part of {string} element ends with {string} text.")
	public void verify_that_the_text_part_of_element_ends_with_text(String po, String text) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		DOMObjectValidator domObjectValidator = (DOMObjectValidator) PageObjectUtil.invokeValidatorMethod("getDOMObjectValidator", null, null, poInfo, appDriver);
		String textValue = domObjectValidator.getText(poInfo.getMaxIterationsToLocateElements());
		FieldValidator.validateFieldValueAsExpectedValue(poInfo.getPoObjectName() + "->text", textValue, text, TextMatchMechanism.endsWithExpectedValue);
	}
}
