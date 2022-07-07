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

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.testng.Assert;

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

}
