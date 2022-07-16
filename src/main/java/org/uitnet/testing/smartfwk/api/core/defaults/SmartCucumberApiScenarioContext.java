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

import org.uitnet.testing.smartfwk.SmartCucumberScenarioContext;
import org.uitnet.testing.smartfwk.api.core.AbstractApiActionHandler;
import org.uitnet.testing.smartfwk.ui.core.config.ApiConfig;

/**
 * This class is used as cucumber scenario context to keep the information for
 * the running scenario. using this class we can pass the information from one
 * scenario to another scenario.
 * 
 * This class must be injected in the cucumber step definition constructor.
 * After that any step definition as part of one scenario can access the
 * information from this class.
 * 
 * NOTE: We must have cucumber-picocontainer jar dependency in our Maven or
 * gradle build configuration (as part of cucumber documentation).
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCucumberApiScenarioContext extends SmartCucumberScenarioContext {
	// Key: <appName>:<targetServerName>, Value: AbstractApiActionHandler
	private Map<String, AbstractApiActionHandler> appActionHandlers;

	private String activeTargetServerName = null;

	public SmartCucumberApiScenarioContext() {
		super();
		if (getTestConfigManager().isParallelMode()) {
			appActionHandlers = new HashMap<>();
		} else {
			appActionHandlers = SingletonApiActionHandlerMap.getInstance().getMap();
		}
	}

	public AbstractApiActionHandler getActionHandler(String appName, String targetServerName) {
		return appActionHandlers.get(prepareKey(appName, targetServerName));
	}

	public AbstractApiActionHandler switchTargetServer(String appName, String targetServerName,
			String userProfileName) {
		return login(appName, targetServerName, userProfileName);
	}

	public AbstractApiActionHandler login(String appName, String targetServerName, String userProfileName) {
		AbstractApiActionHandler actionHandler = getActionHandler(appName, targetServerName);
		if (actionHandler == null) {
			actionHandler = SmartApiTestManager.getInstance().getActionHandler(appName, targetServerName);
		}

		actionHandler.setActiveProfileName(userProfileName);
		this.activeAppName = appName;
		this.activeTargetServerName = targetServerName;

		appActionHandlers.put(prepareKey(appName, targetServerName), actionHandler);

		return actionHandler;
	}

	public AbstractApiActionHandler setActiveUserProfile(String appName, String targetServerName,
			String userProfileName) {
		return login(appName, targetServerName, userProfileName);
	}

	public AbstractApiActionHandler setActiveUserProfileOnActiveAppAndTargetServer(String userProfileName) {
		return login(activeAppName, activeTargetServerName, userProfileName);
	}

	public AbstractApiActionHandler getActiveActionHandler() {
		return appActionHandlers.get(prepareKey(activeAppName, activeTargetServerName));
	}

	public String getActiveUserProfileNameOfActiveActionHandler() {
		return getActiveActionHandler().getActiveProfileName();
	}

	public ApiConfig getActiveAppApiConfig() {
		return getTestConfigManager().getAppConfig(activeAppName).getApiConfig();
	}

	@Override
	public void close() {
		super.close();
		if (getTestConfigManager().isParallelMode()) {
			for (AbstractApiActionHandler actionHandlers : appActionHandlers.values()) {
				actionHandlers.logout();
			}
			appActionHandlers.clear();
		}
	}

	/**
	 * It will apply all params value to the text. It will convert param value to
	 * string then apply.
	 * 
	 * @param text
	 * @return
	 */
	@Override
	public String applyParamsValueOnText(String text) {
		for (Map.Entry<String, Object> e : params.entrySet()) {
			text = text.replace(e.getKey(), "" + e.getValue());
		}

		return text;
	}

	private String prepareKey(String appName, String targetServerName) {
		return appName + ":" + targetServerName;
	}
}
