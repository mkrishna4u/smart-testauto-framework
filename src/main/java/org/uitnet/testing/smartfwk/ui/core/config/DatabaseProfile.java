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

import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class DatabaseProfile {
	private String appName;
	private String profileName;
	private String envFileName;
	private String databaseHandlerClass;
	private Map<String, Object> additionalProps;

	public DatabaseProfile() {

	}

	public DatabaseProfile(String appName, String profileName) {
		this.appName = appName;
		this.profileName = profileName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getEnvFileName() {
		return envFileName;
	}

	public void setEnvFileName(String envFileName) {
		this.envFileName = envFileName;
	}

	public Map<String, Object> getAdditionalProps() {
		return additionalProps;
	}

	public void setAdditionalProps(Map<String, Object> additionalProps) {
		this.additionalProps = additionalProps;
	}
	
	public String getDatabaseHandlerClass() {
		return databaseHandlerClass;
	}

	public void setDatabaseHandlerClass(String databaseHandlerClass) {
		this.databaseHandlerClass = databaseHandlerClass;
	}
	
	public void validateInfo() {
		if(StringUtil.isEmptyAfterTrim(profileName)) {
			Assert.fail("FATAL: 'profileName' property value cannot be empty in database profile file '" + envFileName + "'. AppName - '"
					+ appName + "'. Exiting ...");
			System.exit(1);
		}
	}
	
	public <T> T getAdditionalPropertyValue(String propName, Class<T> clazz) {
		Assert.assertTrue(additionalProps.containsKey(propName), "Please specify the additional property '" + propName
				+ "' in application '" + appName + "' database profile '" + profileName + ".yaml' file.");
		return clazz.cast(additionalProps.get(propName));
	}

}
