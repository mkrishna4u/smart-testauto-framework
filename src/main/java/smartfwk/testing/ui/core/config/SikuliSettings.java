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
package smartfwk.testing.ui.core.config;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.testng.Assert;

public class SikuliSettings {
	private String sikuliSettingsFilePath;
	private String ocrDataPath;
	private Properties props;

	public SikuliSettings(String sikuliSettingsFilePath, Properties props) {
		this.sikuliSettingsFilePath = sikuliSettingsFilePath;
		this.props = props;
		this.ocrDataPath = sikuliSettingsFilePath + File.separator + "tessdata";
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
}
