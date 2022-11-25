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
package org.uitnet.testing.smartfwk.core.validator;

import static org.testng.Assert.assertNotNull;

import org.uitnet.testing.smartfwk.core.validator.xml.XmlDocumentValidator;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;
import org.uitnet.testing.smartfwk.validator.ParameterValidator;
import org.w3c.dom.Document;

import com.jayway.jsonpath.DocumentContext;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartDataValidator {

	private SmartDataValidator() {
		// do nothing
	}

	public static void validateJsonOrYamlData(DocumentContext jsonDoc, String paramPath, String operator,
			String expectedInfo) {
		assertNotNull(paramPath, "Parameter path cannot be null.");
		assertNotNull(operator, "Operator cannot be empty.");

		ParamPath pPath = JsonYamlUtil.parseParamPath(paramPath);
		ExpectedInfo eInfo = JsonYamlUtil.parseExpectedInfo(expectedInfo);
		Object actualValue;

		actualValue = JsonYamlUtil.readJsonPath(pPath.getPath(), pPath.getValueType(), jsonDoc);
		ParameterValidator.validateParamValueAsExpectedInfo(false, pPath, actualValue, operator, eInfo);
	}
	
	public static void validateXmlData(Document xmlDoc, String paramPath, String operator,
			String expectedInfo) {
		assertNotNull(paramPath, "Parameter path cannot be null.");
		assertNotNull(operator, "Operator cannot be empty.");

		ParamPath pPath = JsonYamlUtil.parseParamPath(paramPath);
		ExpectedInfo eInfo = JsonYamlUtil.parseExpectedInfo(expectedInfo);
		Object actualValue;

		XmlDocumentValidator validator = new XmlDocumentValidator(xmlDoc);
		
		actualValue = validator.findAttributeOrTextValues(pPath.getPath(), pPath);
		ParameterValidator.validateParamValueAsExpectedInfo(false, pPath, actualValue, operator, eInfo);
	}
	
}
