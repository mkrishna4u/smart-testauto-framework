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

import java.util.Properties;

import org.testng.Assert;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class ApiConfig {
	private String apiConfigFilePath;
	private Properties props;

	public ApiConfig(String apiConfigFilePath, Properties props) {
		this.apiConfigFilePath = apiConfigFilePath;
		this.props = props;
	}

	public String getPropertyValue(String propName) {
		if (!propName.startsWith("_")) {
			Assert.fail("Property name should start with prefix underscore ( _ ).");
		}

		if (!this.props.containsKey(propName)) {
			Assert.fail("Please specify the property '" + propName + "' in '" + apiConfigFilePath + "' file.");
		}
		return this.props.getProperty(propName);
	}

}
