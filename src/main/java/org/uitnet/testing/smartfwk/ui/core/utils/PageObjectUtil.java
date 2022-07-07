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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.SmartCucumberUiScenarioContext;
import org.uitnet.testing.smartfwk.ui.core.commons.FieldValue;
import org.uitnet.testing.smartfwk.ui.core.defaults.DefaultInfo;

import com.jayway.jsonpath.DocumentContext;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class PageObjectUtil {

	private PageObjectUtil() {
		// do nothing
	}

	/**
	 * Page object should be specified using the format. When POs are in
	 * ./src/main/page_objects/ directory:
	 * "<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 10, params: {p1: 'v1', p2: '${variable1}'} }" When POs are in sub
	 * directory of ./src/main/page_objects/ directory:
	 * "<doted-relative-package-path-to-page_objects>.<PO-classname>.<field-name>{maxTimeToWaitInSeconds:
	 * 10}"
	 * 
	 * @param pageObject
	 * @return
	 */
	public static PageObjectInfo getPageObjectInfo(String pageObject) {
		int firstIndexOfLeftBrace = pageObject.indexOf("{");
		DocumentContext jsonParams = null;
		if (firstIndexOfLeftBrace >= 0) {
			String jsonParamStr = pageObject.substring(firstIndexOfLeftBrace, pageObject.length());
			pageObject = pageObject.substring(0, firstIndexOfLeftBrace);

			JsonDocumentReader jsonDocReader = new JsonDocumentReader(jsonParamStr);
			jsonParams = jsonDocReader.getDocumentContext();
		}

		int lastIndexOfDot = pageObject.lastIndexOf(".");
		String clazzName = pageObject.substring(0, lastIndexOfDot).trim();
		String qualifiedClazzName = DefaultInfo.DEFAULT_PAGE_OBJECTS_PACKAGE + "." + clazzName;

		String objectName = pageObject.substring(lastIndexOfDot + 1, pageObject.length()).trim();

		return new PageObjectInfo(qualifiedClazzName, objectName, jsonParams);
	}

	public static FieldValue getPageObject(PageObjectInfo poInfo) {
		Field f = null;
		Object value = null;
		try {
			Class<?> clazz = Class.forName(poInfo.getPoClassName());
			f = clazz.getField(poInfo.getPoObjectName());
			value = f.get(clazz);
		} catch (Exception ex) {
			Assert.fail("Failed to load the page object '" + poInfo.getPoClassName() + "." + poInfo.getPoObjectName()
					+ "'. Reason: " + ex.getCause().getLocalizedMessage(), ex);
		}
		return new FieldValue(f, value);
	}

	public static Object getPageObjectValidator(PageObjectInfo poInfo, SmartCucumberUiScenarioContext scenarioContext) {
		FieldValue fv = getPageObject(poInfo);
		Object validatorObj = null;
		try {
			applyParamsToLocator(fv, poInfo.getLocatorParams(), scenarioContext);
			Method method = fv.getField().getType().getMethod("getValidator", SmartCucumberUiScenarioContext.class,
					Region.class);
			validatorObj = method.invoke(fv.getValue(), scenarioContext, null);
		} catch (Exception ex) {
			Assert.fail("Failed to get validator for the page object '" + poInfo.getPoClassName() + "."
					+ poInfo.getPoObjectName() + "'. Reason: " + ex.getCause().getLocalizedMessage(), ex);
		}
		return validatorObj;
	}

	public static Object invokeValidatorMethod(String methodName, Class<?>[] methodArgTypes, Object[] methodArgValues,
			PageObjectInfo poInfo, SmartCucumberUiScenarioContext scenarioContext) {
		Object validatorObj = getPageObjectValidator(poInfo, scenarioContext);
		Method method = ObjectUtil.findClassMethod(validatorObj.getClass(), methodName, methodArgTypes);
		try {
			return ObjectUtil.invokeMethod(validatorObj, method, methodArgValues);
		} catch (Exception | Error ex) {
			Assert.fail("'" + methodName + "' operation is failed for page object '" + poInfo.getPoClassName() + "."
					+ poInfo.getPoObjectName() + "'. Reason: " + ex.getCause().getLocalizedMessage(), ex);
		}
		return null;
	}
	
	protected static void applyParamsToLocator(FieldValue fv, Map<String, String> locatorParams, SmartCucumberUiScenarioContext scenarioContext) {
		if(!locatorParams.isEmpty()) {
			try {
				for(Map.Entry<String, String> param: locatorParams.entrySet()) {
					Method method = fv.getField().getType().getMethod("updateLocatorParameterWithValue", String.class,
							String.class);
					Object newObj = method.invoke(fv.getValue(), param.getKey(), prepareParamValue(param.getValue(), scenarioContext));
					fv.setValue(newObj);
				}
			} catch(Exception | Error ex) {
				Assert.fail("Failed to update locator input parameters.", ex);
			}			
		}
		
	}
	
	/**
	 * paramValue may contain the variable like ${variableName} and this variable value should be extracted from scenarioContext.
	 * 
	 * @param paramValue
	 * @param scenarioContext
	 * @return
	 */
	public static String prepareParamValue(String paramValue, SmartCucumberUiScenarioContext scenarioContext) {
		if(paramValue != null) {
			if(StringUtil.startsWithText(paramValue.trim(), "${") && StringUtil.endsWithText(paramValue.trim(), "}")) {
				paramValue = paramValue.trim();
				paramValue = paramValue.substring(2, paramValue.length() - 1).trim();
				return String.valueOf(scenarioContext.getParamValue(paramValue));
			} else if(StringUtil.startsWithText(paramValue.trim(), "\\${") && StringUtil.endsWithText(paramValue.trim(), "}")) {
				return paramValue.replaceFirst("\\", "");
			}			
		}
		
		return paramValue;
	}
	
	
}
