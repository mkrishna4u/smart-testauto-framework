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
package org.uitnet.testing.smartfwk.api.core.reader;

import java.io.File;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.testng.Assert;

import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import com.jayway.jsonpath.TypeRef;
import com.jayway.jsonpath.spi.json.JacksonJsonProvider;
import com.jayway.jsonpath.spi.json.JsonProvider;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import com.jayway.jsonpath.spi.mapper.MappingProvider;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class JsonDocumentReader {
	protected DocumentContext jsonDocCtx;

	public JsonDocumentReader(File jsonFilePath) {
		try {
			init();
			Assert.assertNotNull(jsonFilePath, "JSON file path cannot be null.");
			ObjectMapper objectMapper = createObjectMapper();
			JacksonJsonProvider provider = new JacksonJsonProvider(objectMapper);
			jsonDocCtx = JsonPath.using(Configuration.builder().jsonProvider(provider).build()).parse(jsonFilePath);
		} catch (Exception ex) {
			Assert.fail("Failed to parse JSON document.", ex);
		}
	}

	public JsonDocumentReader(String jsonAsString) {
		try {
			init();
			Assert.assertNotNull(jsonAsString, "JSON document cannot be null.");
			Assert.assertNotEquals(jsonAsString.trim(), "", "JSON document cannot be empty.");
			ObjectMapper objectMapper = createObjectMapper();
			JacksonJsonProvider provider = new JacksonJsonProvider(objectMapper);
			jsonDocCtx = JsonPath.using(Configuration.builder().jsonProvider(provider).build()).parse(jsonAsString);
		} catch (Exception ex) {
			Assert.fail("Failed to parse JSON document.", ex);
		}
	}
	
	public DocumentContext prepareDocumentContext(Object obj) {
		try {
			ObjectMapper objectMapper = createObjectMapper();
			JacksonJsonProvider provider = new JacksonJsonProvider(objectMapper);
			return JsonPath.using(Configuration.builder().jsonProvider(provider).build())
					.parse(createObjectMapper().writeValueAsString(obj));
		} catch (Exception ex) {
			Assert.fail("Failed to covert object into DocumentContext.", ex);
		}
		return null;
	}

	public DocumentContext getDocumentContext() {
		return jsonDocCtx;
	}

	public <T> List<T> readValuesAsList(String jsonPath) {
		TypeRef<List<T>> typeRef = new TypeRef<List<T>>() {
		};
		return jsonDocCtx.read(jsonPath, typeRef);
	}

	public <T> Set<T> readValuesAsSet(String jsonPath) {
		TypeRef<Set<T>> typeRef = new TypeRef<Set<T>>() {
		};
		return jsonDocCtx.read(jsonPath, typeRef);
	}

	public <T> T readSingleValue(String jsonPath) {
		TypeRef<T> typeRef = new TypeRef<T>() {
		};
		return jsonDocCtx.read(jsonPath, typeRef);
	}
	
	public <T> T readValueAsObject(String yamlPath, Class<T> clazz) {
		try {
			Object obj = jsonDocCtx.read(yamlPath, Object.class);
			
			ObjectMapper omapper = createObjectMapper();
			String jsonStr = omapper.writeValueAsString(obj);
			
			omapper = createObjectMapper();
			return omapper.readValue(jsonStr, clazz);
		} catch (Exception ex) {
			Assert.fail("Failed to read yaml path " + yamlPath + " as class object.", ex);
		}
		return null;
		
	}
	
	protected ObjectMapper createObjectMapper() {
		ObjectMapper objectMapper = JsonMapper.builder().enable(JsonReadFeature.ALLOW_SINGLE_QUOTES)
				.enable(JsonReadFeature.ALLOW_UNQUOTED_FIELD_NAMES)
				.disable(JsonWriteFeature.QUOTE_FIELD_NAMES).build();
		return objectMapper;
	}

	protected void init() {
		Configuration.setDefaults(new Configuration.Defaults() {
			private ObjectMapper objectMapper = createObjectMapper();
					
			@Override
			public JsonProvider jsonProvider() {
				JsonProvider jsonProvider = new JacksonJsonProvider(objectMapper);
				return jsonProvider;
			}

			@Override
			public MappingProvider mappingProvider() {
				return new JacksonMappingProvider(objectMapper);
			}

			@Override
			public Set<Option> options() {
				return EnumSet.noneOf(Option.class);
			}
		});
	}
}
