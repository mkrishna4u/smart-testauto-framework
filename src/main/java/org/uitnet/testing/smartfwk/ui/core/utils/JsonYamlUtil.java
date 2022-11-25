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

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.fail;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.JsonDocumentReader;
import org.uitnet.testing.smartfwk.core.validator.ExpectedInfo;
import org.uitnet.testing.smartfwk.core.validator.InputValue;
import org.uitnet.testing.smartfwk.core.validator.InputValueType;
import org.uitnet.testing.smartfwk.core.validator.ParamPath;
import org.uitnet.testing.smartfwk.core.validator.ParamValue;
import org.uitnet.testing.smartfwk.core.validator.ParamValueType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class JsonYamlUtil {
	private JsonYamlUtil() {
		// do nothing
	}

	public static <T> T readNoException(String path, Class<T> type, DocumentContext yamlDocCtx) {
		try {
			return yamlDocCtx.read(path, type);
		} catch (Exception ex) {
			// do nothing
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T readNoException(String path, TypeRef<T> type, DocumentContext yamlDocCtx) {
		ObjectMapper mapper = new ObjectMapper();
		JavaType type2 = null;
		try {

			type2 = mapper.getTypeFactory().constructType(type.getType());

			T obj = yamlDocCtx.read(path, type);

			if (obj == null) {
				if (type2.getRawClass().getName().equals(Map.class.getName())) {
					return (T) Collections.EMPTY_MAP;
				} else if (type2.getRawClass().getName().equals(List.class.getName())) {
					return (T) Collections.EMPTY_LIST;
				} else if (type2.getRawClass().getName().equals(Set.class.getName())) {
					return (T) Collections.EMPTY_SET;
				}
			}

			return obj;
		} catch (Exception ex) {
			try {
				if (type2.getRawClass().getName().equals(Map.class.getName())) {
					return (T) Collections.EMPTY_MAP;
				} else if (type2.getRawClass().getName().equals(List.class.getName())) {
					return (T) Collections.EMPTY_LIST;
				} else if (type2.getRawClass().getName().equals(Set.class.getName())) {
					return (T) Collections.EMPTY_SET;
				}
			} catch (Exception e1) {
				Assert.fail("Failed to read '" + path + "' path of type '" + type.getType() + "'.", e1);
			}
		}

		return null;
	}

	/**
	 * This method first read the overridden property if that does not exist in
	 * overridenProperties then read it from defaultProperties.
	 * 
	 * @param <T>
	 * @param yamlPath
	 * @param clazz
	 * @param defaultProperties
	 * @param overriddenProperties
	 * @return
	 */
	public static <T> T readNoException(String yamlPath, Class<T> clazz, DocumentContext defaultProperties,
			DocumentContext overriddenProperties) {
		if (overriddenProperties == null) {
			return JsonYamlUtil.readNoException(yamlPath, clazz, defaultProperties);
		}

		T value = null;

		try {
			value = overriddenProperties.read(yamlPath, clazz);
		} catch (Exception e) {
			value = JsonYamlUtil.readNoException(yamlPath, clazz, defaultProperties);
		}

		return value;
	}

	/**
	 * This method first read the overridden property if that does not exist in
	 * overridenProperties then read it from defaultProperties.
	 * 
	 * @param <T>
	 * @param yamlPath
	 * @param typeRef
	 * @param defaultProperties
	 * @param overriddenProperties
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> T readNoException(String yamlPath, TypeRef<T> typeRef, DocumentContext defaultProperties,
			DocumentContext overriddenProperties) {
		if (overriddenProperties == null) {
			return JsonYamlUtil.readNoException(yamlPath, typeRef, defaultProperties);
		}

		T value = null;

		try {
			value = JsonYamlUtil.readNoException(yamlPath, typeRef, overriddenProperties);
			Map map = null;
			if (value instanceof Map) {
				map = (Map) value;
			}

			T defValue = JsonYamlUtil.readNoException(yamlPath, typeRef, defaultProperties);
			if (defValue instanceof Map) {
				Map map2 = (Map) defValue;
				if (map != null) {
					map2.putAll(map);
				}
				value = (T) map2;
			} else {
				if (value == null) {
					value = (T) defValue;
				} else if (value instanceof List || value instanceof Set) {
					if (((Collection) value).isEmpty()) {
						value = (T) defValue;
					}
				}
			}
		} catch (Exception e) {
			value = JsonYamlUtil.readNoException(yamlPath, typeRef, defaultProperties);
		}

		return value;
	}

	public static String convertObjectToJsonString(Object obj) throws JsonProcessingException {
		if (obj == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(obj);
	}

	/**
	 * Read the values at JSON path in the given JSON document.
	 * 
	 * @param jsonPath   - path for which values to be read.
	 * @param valueType  - type of values at the specified JSON path. If value type
	 *                   is empty then it will read as String value.
	 * @param jsonDocCtx - JSON document from where the value to be read.
	 * @return the values based on valueType.
	 */
	public static Object readJsonPath(String jsonPath, ParamValueType valueType, DocumentContext jsonDocCtx) {
		assertNotNull(jsonDocCtx, "JSON document cannot be null.");
		if (valueType == null) {
			valueType = ParamValueType.STRING;
		}

		switch (valueType) {
		case STRING: {
			return jsonDocCtx.read(jsonPath, String.class);
		}
		case STRING_LIST: {
			return jsonDocCtx.read(jsonPath, new TypeRef<List<String>>() {
			});
		}
		case INTEGER: {
			return jsonDocCtx.read(jsonPath, Long.class);
		}
		case INTEGER_LIST: {
			return jsonDocCtx.read(jsonPath, new TypeRef<List<Long>>() {
			});
		}
		case DECIMAL: {
			return jsonDocCtx.read(jsonPath, Double.class);
		}
		case DECIMAL_LIST: {
			return jsonDocCtx.read(jsonPath, new TypeRef<List<Double>>() {
			});
		}
		case BOOLEAN: {
			return jsonDocCtx.read(jsonPath, Boolean.class);
		}
		case BOOLEAN_LIST: {
			return jsonDocCtx.read(jsonPath, new TypeRef<List<Boolean>>() {
			});
		}
		default:
			fail("'" + valueType + "' value type is not supported.");
		}

		return null;
	}
	
	public static ParamPath parseParamPath(String paramPath) {
		assertNotNull(paramPath, "Parameter path cannot be null.");
		paramPath = paramPath.trim();
		ParamPath p = null;
		if(paramPath.startsWith("{") && paramPath.endsWith("}")) {
			JsonDocumentReader r = new JsonDocumentReader(paramPath, false);
			p = r.readValueAsObject("$", ParamPath.class);
		} else {
			p = new ParamPath();
			p.setPath(paramPath);
			p.setValueType("string");
		}
		
		return p;
	}
	
	public static ExpectedInfo parseExpectedInfo(String expectedInfo) {
		ExpectedInfo expInfo = null;
		if(StringUtil.isEmptyAfterTrim(expectedInfo)) {
			expInfo = new ExpectedInfo();
			expInfo.setEv("");
			expInfo.setValueType("string");
			return expInfo;
		} 
		
		expectedInfo = expectedInfo.trim();
		
		if(expectedInfo.startsWith("{") && expectedInfo.endsWith("}")) {
			JsonDocumentReader r = new JsonDocumentReader(expectedInfo, false);
			expInfo = r.readValueAsObject("$", ExpectedInfo.class);
			return expInfo;
		} else {
			expInfo = new ExpectedInfo();
			if("null".equalsIgnoreCase(expectedInfo)) {
				expInfo.setEv(null);
			} else {
				expInfo.setEv(expectedInfo);
			}
			
			expInfo.setValueType("string");
			return expInfo;
		}
		
	}
	
	public static InputValue parseInputValue(String inputValue) {
		InputValue inputV = null;
		if(StringUtil.isEmptyAfterTrim(inputValue)) {
			inputV = new InputValue();
			inputV.setValue("");
			inputV.setValueType("string");
			return inputV;
		} 
		
		inputValue = inputValue.trim();
		
		if(inputValue.startsWith("{") && inputValue.endsWith("}")) {
			JsonDocumentReader r = new JsonDocumentReader(inputValue, false);
			inputV = r.readValueAsObject("$", InputValue.class);
			if(inputV.getValueType() == null) {
				inputV.setValueType(InputValueType.STRING.getType());
			}
			
			return inputV;
		} else {
			inputV = new InputValue();
			if("null".equalsIgnoreCase(inputValue)) {
				inputV.setValue(null);
			} else {
				inputV.setValue(inputValue);
			}
			
			inputV.setValueType(InputValueType.STRING.getType());
			return inputV;
		}
		
	}
	
	public static Object convertTextToTypedValue(String paramName, ParamValueType valueType, String text) {
		if (valueType == null) {
			valueType = ParamValueType.STRING;
		}
		
		if(text == null) {
			return null;
		}
		
		text = text.trim();
		boolean isJsonData = false;
		boolean isListData = false;
		if(text.startsWith("{") && text.endsWith("}")) {
			isJsonData = true;
		} else if(text.startsWith("[") && text.endsWith("]")) {
			isListData = true;
		}

		switch (valueType) {
		case STRING: {
			if(isJsonData) {
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				ParamValue pv = reader.readValueAsObject("$", ParamValue.class);
				return ((pv == null) ?  null :  "" + pv.getV());
			} else {
				return text;
			}
		}
		case STRING_LIST: {
			if(isJsonData) {
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				ParamValue pv = reader.readValueAsObject("$", ParamValue.class);
				if(pv.getV() != null && !(pv.getV() instanceof List)) {
					Assert.fail("Found non-list data: '" + text + "'.");
				}
				return ((pv == null) ?  null : pv.getV());
			} else if(isListData) { 
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				List<String> pv = reader.readValuesAsList("$");
				return pv;
			} else {
				Assert.fail("Found non-list data: '" + text + "'.");
				return text;
			}
		}
		case INTEGER: {
			if(isJsonData) {
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				ParamValue pv = reader.readValueAsObject("$", ParamValue.class);
				return ((pv == null) ?  null : Long.parseLong("" + pv.getV()));
			} else {
				try {
					return Long.parseLong(text);
				} catch(Exception ex) {
					Assert.fail("Found non-numeric data: '" + text + "'.");
				}
				return null;
			}
		}
		case INTEGER_LIST: {
			if(isJsonData) {
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				ParamValue pv = reader.readValueAsObject("$", ParamValue.class);
				if(pv.getV() != null && !(pv.getV() instanceof List)) {
					Assert.fail("Found non-list data: '" + text + "'.");
				}
				return ((pv == null) ?  null : Long.parseLong("" + pv.getV()));
			} else if(isListData) { 
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				List<Integer> pv = reader.readValuesAsList("$");
				return pv;
			} else {
				Assert.fail("Found non-list data: '" + text + "'.");
				return null;
			}
		}
		case DECIMAL: {
			if(isJsonData) {
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				ParamValue pv = reader.readValueAsObject("$", ParamValue.class);
				return ((pv == null) ?  null : Double.parseDouble("" + pv.getV()));
			} else {
				try {
					return Double.parseDouble(text);
				} catch(Exception ex) {
					Assert.fail("Found non-numeric data: '" + text + "'.");
				}
				return null;
			}
		}
		case DECIMAL_LIST: {
			if(isJsonData) {
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				ParamValue pv = reader.readValueAsObject("$", ParamValue.class);
				if(pv.getV() != null && !(pv.getV() instanceof List)) {
					Assert.fail("Found non-list data: '" + text + "'.");
				}
				return ((pv == null) ?  null :  Double.parseDouble("" + pv.getV()));
			} else if(isListData) { 
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				List<Integer> pv = reader.readValuesAsList("$");
				return pv;
			} else {
				Assert.fail("Found non-list data: '" + text + "'.");
				return null;
			}
		}
		case BOOLEAN: {
			if(isJsonData) {
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				ParamValue pv = reader.readValueAsObject("$", ParamValue.class);
				return ((pv == null) ?  null : Boolean.parseBoolean("" + pv.getV()));
			} else {
				try {
					return Boolean.parseBoolean(text);
				} catch(Exception ex) {
					Assert.fail("Found non-boolean data: '" + text + "'. Value must be either true or false.");
				}
				return null;
			}
		}
		case BOOLEAN_LIST: {
			if(isJsonData) {
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				ParamValue pv = reader.readValueAsObject("$", ParamValue.class);
				if(pv.getV() != null && !(pv.getV() instanceof List)) {
					Assert.fail("Found non-list data: '" + text + "'.");
				}
				return ((pv == null) ?  null : pv.getV());
			} else if(isListData) { 
				JsonDocumentReader reader = new JsonDocumentReader(text, false);
				List<Integer> pv = reader.readValuesAsList("$");
				return pv;
			} else {
				Assert.fail("Found non-list data: '" + text + "'.");
				return null;
			}
		}
		default:
			fail("'" + valueType + "' value type is not supported.");
		}

		return null;
	}
}
