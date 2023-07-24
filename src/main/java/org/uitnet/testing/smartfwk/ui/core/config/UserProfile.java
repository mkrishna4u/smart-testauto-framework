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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class UserProfile {
	private String appName;
	private String envFileName;
	private String profileName;
	private String appLoginUserId;
	private String appLoginUserPassword;
	private String userAccountType;
	private String userAccountTypeCode;
	private List<String> userRoles;
	private Map<String, String> additionalProps;

	public UserProfile() {
		userRoles = new LinkedList<String>();
		additionalProps = new HashMap<String, String>();
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getEnvFileName() {
		return envFileName;
	}

	public void setEnvFileName(String envFileName) {
		this.envFileName = envFileName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getAppLoginUserId() {
		return appLoginUserId;
	}

	public void setAppLoginUserId(String appLoginUserId) {
		this.appLoginUserId = appLoginUserId;
	}

	public String getAppLoginUserPassword() {
		return appLoginUserPassword;
	}

	public void setAppLoginUserPassword(String appLoginUserPassword) {
		this.appLoginUserPassword = appLoginUserPassword;
	}

	public String getUserAccountType() {
		return userAccountType;
	}

	public void setUserAccountType(String userAccountType) {
		this.userAccountType = userAccountType;
	}

	public String getUserAccountTypeCode() {
		return userAccountTypeCode;
	}

	public void setUserAccountTypeCode(String userAccountTypeCode) {
		this.userAccountTypeCode = userAccountTypeCode;
	}

	public List<String> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(List<String> userRoles) {
		this.userRoles = userRoles;
	}

	public Map<String, String> getAdditionalProps() {
		return additionalProps;
	}

	public void setAdditionalProps(Map<String, String> additionalProps) {
		this.additionalProps = additionalProps;
	}

	public void validateInfo() {
		if(StringUtil.isEmptyAfterTrim(profileName)) {
			Assert.fail("FATAL: 'profileName' property value cannot be empty. AppName - '"
					+ appName + "'. Exiting ...");
			System.exit(1);
		}
		
		if(StringUtil.isEmptyAfterTrim(appLoginUserId)) {
			Assert.fail("FATAL: 'appLoginUserId' property value cannot be empty. AppName - '"
					+ appName + "', UserProfileName - '" + profileName + "'. Exiting ...");
			System.exit(1);
		}
		
		if(StringUtil.isEmptyAfterTrim(appLoginUserPassword)) {
			Assert.fail("FATAL: 'appLoginUserPassword' property value cannot be empty. AppName - '"
					+ appName + "', UserProfileName - '" + profileName + "'. Exiting ...");
			System.exit(1);
		}
	}

	public String getAdditionalPropertyValue(String propName) {
		Assert.assertTrue(additionalProps.containsKey(propName), "Please specify the additional property '" + propName
				+ "' in application '" + appName + "' user profile '" + profileName + ".yaml' file.");
		return additionalProps.get(propName);
	}
}
