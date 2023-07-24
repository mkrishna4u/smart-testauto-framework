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
package org.uitnet.testing.smartfwk.api.core.defaults;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.uitnet.testing.smartfwk.api.core.AbstractApiActionHandler;
import org.uitnet.testing.smartfwk.api.core.ApiAuthenticationProvider;
import org.uitnet.testing.smartfwk.ui.core.config.ApiConfig;
import org.uitnet.testing.smartfwk.ui.core.config.ApiTargetServer;
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;
import org.uitnet.testing.smartfwk.ui.core.utils.ObjectUtil;
import org.uitnet.testing.smartfwk.ui.core.utils.StringUtil;

/**
 * This class is used to register all Test Helpers on all the target servers.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartApiTestManager implements ApiTestManager {
	private static SmartApiTestManager instance;

	// Registered action handlers. Key: appName, Value: AbstractApiActionHandler
	private Map<String, AbstractApiActionHandler> registeredActionHandlers;

	// Key: appName, Value: AbstractApiActionHandler
	private Map<String, AbstractApiActionHandler> activeActionHandlers;

	// Key: appName:targetServerName:userProfileName, Value:
	// ApiAuthenticationProvider
	private Map<String, ApiAuthenticationProvider> appAuthProviders;

	private SmartApiTestManager() {
		registeredActionHandlers = new HashMap<>();
		activeActionHandlers = new HashMap<>();
		appAuthProviders = new HashMap<>();
		initActionHandlers();
	}

	public static ApiTestManager getInstance() {
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

	private void initActionHandlers() {
		Collection<AppConfig> appConfigs = TestConfigManager.getInstance().getAppConfigs();
		if (appConfigs != null) {
			Collection<ApiTargetServer> targetServers = null;
			for (AppConfig appConfig : appConfigs) {
				ApiConfig apiConfig = appConfig.getApiConfig();
				if (apiConfig != null) {
					targetServers = apiConfig.getTargetServers();
					if (targetServers != null) {
						for (ApiTargetServer targetServer : targetServers) {
							try {
								if (!StringUtil.isEmptyAfterTrim(targetServer.getActionHandlerClass())
										&& !StringUtil.isEmptyAfterTrim(targetServer.getName())) {
									Class<?> clazz = Class.forName(targetServer.getActionHandlerClass());
									AbstractApiActionHandler obj = (AbstractApiActionHandler) ObjectUtil
											.findClassConstructor(clazz,
													new Class[] { String.class, int.class, String.class })
											.newInstance(apiConfig.getAppName(),
													targetServer.getSessionExpiryDurationInSeconds(),
													targetServer.getName());
									registerActionHandler(appConfig.getAppName(), targetServer.getName(), obj);
								}
							} catch (Exception ex) {
								throw new RuntimeException(ex);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Used to register new Test helper.
	 * 
	 * @param appName
	 * @param targetServerName
	 * @param actionHandler
	 */
	private void registerActionHandler(String appName, String targetServerName,
			AbstractApiActionHandler actionHandler) {
		registeredActionHandlers.put(prepareKey(appName, targetServerName), actionHandler);
	}

	@Override
	public AbstractApiActionHandler getActionHandler(String appName, String targetServerName, String userProfileName) {
		String key = prepareAuthProviderMapKey(appName, targetServerName, userProfileName);
		AbstractApiActionHandler handler = activeActionHandlers.get(key);
		if (handler == null) {
			synchronized (SmartApiTestManager.class) {
				handler = activeActionHandlers.get(key);
				if (handler == null) {
					handler = getRegisteredActionHandler(appName, targetServerName);
					handler.setActiveProfileName(userProfileName);
					activeActionHandlers.put(key, handler);
				}
			}
		}

		return handler;
	}

	private AbstractApiActionHandler getRegisteredActionHandler(String appName, String targetServerName) {
		AbstractApiActionHandler actionHandler = registeredActionHandlers.get(prepareKey(appName, targetServerName));
		Assert.assertNotNull(actionHandler, "No test helper registered with SmartApiTestManager class for appName = "
				+ appName + " and targetServerName = " + targetServerName
				+ ". This must be registered in cucumber step definition method that is annotated with @BeforeAll.");

		AbstractApiActionHandler newActionHandler = actionHandler.clone();
		newActionHandler.setApiTestManager(this);

		return newActionHandler;
	}

	@Override
	public ApiAuthenticationProvider getAuthenticationProvider(String appName, String targetServerName,
			String userProfileName) {
		String mapKey = prepareAuthProviderMapKey(appName, targetServerName, userProfileName);
		ApiAuthenticationProvider authProvider = appAuthProviders.get(mapKey);

		if (authProvider != null) {
			return authProvider;
		}

		synchronized (SmartApiTestManager.class) {
			authProvider = appAuthProviders.get(mapKey);
			if (authProvider != null) {
				return authProvider;
			}

			authProvider = getRegisteredActionHandler(appName, targetServerName);
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

	@Override
	public synchronized void deregisterAll() {
		registeredActionHandlers.clear();
		activeActionHandlers.clear();

		for (ApiAuthenticationProvider aap : appAuthProviders.values()) {
			aap.logout();
		}

		appAuthProviders.clear();
	}
}
