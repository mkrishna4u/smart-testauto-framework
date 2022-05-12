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
package org.uitnet.testing.smartfwk.api.core.defaults;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.AbstractApiTestHelper;
import org.uitnet.testing.smartfwk.api.core.ApiAuthenticationProvider;

/**
 * This class is used to register all Test Helpers on all the target servers.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartApiTestManager implements ApiTestManager {
	private static SmartApiTestManager instance;

	// Key: appName, Value: AbstractApiTestHelper
	private Map<String, AbstractApiTestHelper> appTestHelpers;
	
	// Key: appName:targetServerName:userProfileName, Value: ApiAuthenticationProvider
	private Map<String, ApiAuthenticationProvider> appAuthProviders;

	private SmartApiTestManager() {
		appTestHelpers = new HashMap<>();
		appAuthProviders = new HashMap<>();
	}

	public static SmartApiTestManager getInstance() {
		if (instance != null) {
			return instance;
		}

		synchronized (SmartApiTestManager.class) {
			if (instance == null) {
				instance = new SmartApiTestManager();
			}
		}

		return instance;
	}

	/**
	 * Used to register new Test helper.
	 * 
	 * @param appName
	 * @param targetServerName
	 * @param testHelper
	 */
	public void registerTestHelper(String appName, String targetServerName, AbstractApiTestHelper testHelper) {
		appTestHelpers.put(prepareKey(appName, targetServerName), testHelper);
	}

	public AbstractApiTestHelper getRegisteredTestHelper(String appName, String targetServerName) {
		AbstractApiTestHelper testHelper = appTestHelpers.get(prepareKey(appName, targetServerName));
		Assert.assertNotNull(testHelper, "No test helper registered with SmartApiTestManager class for appName = " + appName
				+ " and targetServerName = " + targetServerName + ". This must be registered in cucumber step definition method that is annotated with @BeforeAll.");

		AbstractApiTestHelper newTestHelper = testHelper.clone();
		newTestHelper.setApiTestManager(this);
		newTestHelper.setTargetServerName(targetServerName);
		
		return newTestHelper;
	}
	
	public ApiAuthenticationProvider getAuthenticationProvider(String appName, String targetServerName, String userProfileName) {
		String mapKey = prepareAuthProviderMapKey(appName, targetServerName, userProfileName);
		ApiAuthenticationProvider authProvider = appAuthProviders.get(mapKey);
		
		if(authProvider != null) { return authProvider; }
		
		synchronized(SmartApiTestManager.class) {
			authProvider = appAuthProviders.get(mapKey);
			if(authProvider != null) { return authProvider; }
			
			authProvider = getRegisteredTestHelper(appName, targetServerName);
			appAuthProviders.put(mapKey, authProvider);
			
			return authProvider;
		}
	}
	
	private String prepareAuthProviderMapKey(String appName, String targetServerName, String userProfileName) {
		return appName + ":" + targetServerName + ":" + userProfileName;
	}

	private String prepareKey(String appName, String targetServerName) {
		return appName + ":" + targetServerName;
	}
	
	public void close() {
		appTestHelpers.clear();
		
		for(ApiAuthenticationProvider aap : appAuthProviders.values()) {
			aap.logout();
		}
	}
}
