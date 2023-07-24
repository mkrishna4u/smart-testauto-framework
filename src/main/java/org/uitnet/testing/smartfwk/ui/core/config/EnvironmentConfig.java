/*
 * SmartTestAutoFramework
 * Copyright 2021 and beyond [Madhav Krishna]
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

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.reader.YamlDocumentReader;
import org.uitnet.testing.smartfwk.ui.core.commons.Locations;

import com.jayway.jsonpath.DocumentContext;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class EnvironmentConfig {
	private String name;
	private DocumentContext docContext;

	public EnvironmentConfig(String appName, String environmentName) {
		this.name = environmentName;
		if (!(environmentName == null || "".equals(environmentName.trim()))) {
			init(appName);
		} else {
			this.name = "Default";
		}
	}

	private void init(String appName) {
		String activeEnvConfigPath = Locations.getConfigDirPath() + File.separator + "apps-config" + File.separator
				+ appName + File.separator + "environments" + File.separator + name + ".yaml";
		Assert.assertTrue(new File(activeEnvConfigPath).exists(),
				"Missing '" + activeEnvConfigPath + "' environment file in '" + appName + "' application.");
		try {
			YamlDocumentReader reader = new YamlDocumentReader(new File(activeEnvConfigPath), true);
			docContext = reader.getDocumentContext();
		} catch (Exception ex) {
			Assert.fail("Failed to read property file - " + activeEnvConfigPath + ". Going to exit...", ex);
			System.exit(1);
		}
	}

	public String getName() {
		return name;
	}

	public DocumentContext getDocumentContext() {
		return docContext;
	}

}
