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

import org.sikuli.script.Region;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
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
	 * "<PO-classname>.<field-name>{maxTimeToWaitInSeconds: 10}" When POs are in sub
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
					+ "'.", ex);
		}
		return new FieldValue(f, value);
	}

	public static Object getPageObjectValidator(PageObjectInfo poInfo, SmartAppDriver appDriver) {
		FieldValue fv = getPageObject(poInfo);
		Object validatorObj = null;
		try {
			Method method = fv.getField().getType().getMethod("getValidator", SmartAppDriver.class, Region.class);
			validatorObj = method.invoke(fv.getValue(), appDriver, null);
		} catch (Exception ex) {
			Assert.fail("Failed to get validator for the page object '" + poInfo.getPoClassName() + "."
					+ poInfo.getPoObjectName() + "'.", ex);
		}
		return validatorObj;
	}

	public static Object invokeValidatorMethod(String methodName, Class<?>[] methodArgTypes, Object[] methodArgValues,
			PageObjectInfo poInfo, SmartAppDriver appDriver) {
		Object validatorObj = getPageObjectValidator(poInfo, appDriver);
		Method method = ObjectUtil.findClassMethod(validatorObj.getClass(), methodName, methodArgTypes);
		try {
			return ObjectUtil.invokeMethod(validatorObj, method, methodArgValues);
		} catch (Exception ex) {
			Assert.fail("Failed to perform '" + methodName + "' operation using page object '" + poInfo.getPoClassName()
					+ "." + poInfo.getPoObjectName() + "'.", ex);
		}
		return null;
	}

}
