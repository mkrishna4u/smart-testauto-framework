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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.sikuli.basics.Settings;
import org.sikuli.script.OCR;
import org.sikuli.script.OCR.Options;
import org.testng.Assert;

public class SikuliSettings {
	private String sikuliSettingsFilePath;
	private String ocrDataPath;
	private Properties props;

	public SikuliSettings(String sikuliSettingsFilePath, Properties props) {
		this.sikuliSettingsFilePath = sikuliSettingsFilePath;
		this.props = props;
		this.ocrDataPath = sikuliSettingsFilePath + File.separator + "tessdata";
		initializeSikuli();
	}

	public String getOcrDataPath() {
		return ocrDataPath;
	}

	public String getPropertyValue(String propName) {
		if (!(propName.startsWith("_") || propName.startsWith("Settings."))) {
			Assert.fail("Property name should start with prefix underscore ( _ ) or Settings.");
		}

		if (!this.props.containsKey(propName)) {
			Assert.fail("Please specify the property '" + propName + "' in '" + sikuliSettingsFilePath + "' file.");
		}
		return this.props.getProperty(propName);
	}

	public Map<String, String> getAllSettings() {
		Map<String, String> keyValuePairs = new LinkedHashMap<String, String>();
		for (Object key : props.keySet()) {
			String strKey = (String) key;
			if (strKey.startsWith("Settings.")) {
				keyValuePairs.put(strKey.substring("Settings.".length()), props.getProperty(strKey));
			}
		}

		return keyValuePairs;
	}

	protected void initializeSikuli() {
		try {
			Map<String, String> settings = getAllSettings();
			String value;
			for (String name : settings.keySet()) {
				value = settings.get(name);

				Field f = Settings.class.getDeclaredField(name);
				if (f.isAccessible()) {
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
