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
package org.uitnet.testing.smartfwk.ui.core.config;

import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

import org.sikuli.basics.Settings;
import org.sikuli.script.OCR;
import org.sikuli.script.OCR.Options;
import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.utils.JsonYamlUtil;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.TypeRef;

public class SikuliSettings {
	private String ocrDataPath;
	private Map<String, String> settings;
	private Map<String, Object> additionalProps;

	public SikuliSettings(String sikuliSettingsFilePath, DocumentContext yamlDoc) {
		this.ocrDataPath = sikuliSettingsFilePath + File.separator + "tessdata";
		initializeSikuli(yamlDoc);
	}

	public String getOcrDataPath() {
		return ocrDataPath;
	}

	public String getSettings(String propName) {
		return this.settings.get(propName);
	}

	public <T> T getAdditionalProperty(String propName, Class<T> clazz) {
		return clazz.cast(this.additionalProps.get(propName));
	}

	protected void initializeSikuli(DocumentContext yamlDoc) {
		try {
			String ocrTessdataLoc = JsonYamlUtil.readNoException("$.ocr-tessdata-location", String.class, yamlDoc);
			if(ocrTessdataLoc != null && !ocrTessdataLoc.equals(".")) {
				ocrDataPath = ocrTessdataLoc;
			}
			
			settings = JsonYamlUtil.readNoException("$.settings", (new TypeRef<Map<String, String>>() {}), yamlDoc);
			String value;
			for (String name : settings.keySet()) {
				value = settings.get(name);

				Field f = Settings.class.getDeclaredField(name);
				if (f.canAccess(null)) {
					f.set(null, createObjectFromTypedValue(name, value));
				} else {
					f.setAccessible(true);
					f.set(null, createObjectFromTypedValue(name, value));
					f.setAccessible(false);
				}
			}

			System.out.println("Sikuli OCRDataPath set to: " + getOcrDataPath());
			Options ocrOptions = OCR.globalOptions();
			ocrOptions.dataPath(getOcrDataPath());

			additionalProps = JsonYamlUtil.readNoException("$.additionalProps", (new TypeRef<Map<String, Object>>() {
			}), yamlDoc);
		} catch (Throwable th) {
			Assert.fail("Failed to initialize the sikuli driver.", th);
		}
	}

	protected Object createObjectFromTypedValue(String propertyName, String typedValue) {
		String typeValueArr[] = typedValue.split(":");
		Assert.assertTrue(typeValueArr.length > 1,
				"typedValue format is wrong for property '" + propertyName + "'. It should be <data-type>:<value>");

		switch (typeValueArr[0]) {
		case "integer":
			return Integer.parseInt(typeValueArr[1]);
		case "string":
			return typeValueArr[1];
		case "float":
			return Float.parseFloat(typeValueArr[1]);
		case "double":
			return Double.parseDouble(typeValueArr[1]);
		case "boolean":
			return Boolean.parseBoolean(typeValueArr[1]);
		}

		Assert.fail("'" + typeValueArr[0] + "' datatype is not supported for '" + propertyName + "' property.");
		return null;
	}

}
