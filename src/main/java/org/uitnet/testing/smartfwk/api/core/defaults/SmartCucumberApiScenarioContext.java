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
import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

import io.cucumber.java.Scenario;

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
public class SmartCucumberApiScenarioContext  implements SmartCucumberScenarioContext {
	private Map<String, Object> params = new HashMap<>(8);
	private Scenario scenario = null;

	// Key: <appName>:<targetServerName>, Value: AbstractApiActionHandler
	private Map<String, AbstractApiActionHandler> appActionHandlers;

	private String activeAppName = null;
	private String activeTargetServerName = null;

	public SmartCucumberApiScenarioContext() {
		if (getTestConfigManager().isParallelMode()) {
			appActionHandlers = new HashMap<>();
		} else {
			appActionHandlers = SingletonApiActionHandlerMap.getInstance().getMap();
		}
	}

	@Override
	public Scenario getScenario() {
		return this.scenario;
	}

	@Override
	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public AbstractApiActionHandler getActionHandler(String appName, String targetServerName) {
		return appActionHandlers.get(prepareKey(appName, targetServerName));
	}

	public AbstractApiActionHandler switchTargetServer(String appName, String targetServerName, String userProfileName) {
		return login(appName, targetServerName, userProfileName);
	}

	public AbstractApiActionHandler login(String appName, String targetServerName, String userProfileName) {
		AbstractApiActionHandler actionHandler = getActionHandler(appName, targetServerName);
		if (actionHandler == null) {
			actionHandler = SmartApiTestManager.getInstance().getActionHandler(appName, targetServerName, userProfileName);
		}

		this.activeAppName = appName;
		this.activeTargetServerName = targetServerName;

		appActionHandlers.put(prepareKey(appName, targetServerName), actionHandler);

		return actionHandler;
	}

	@Override
	public String getActiveAppName() {
		return activeAppName;
	}

	public AbstractApiActionHandler setActiveUserProfile(String appName, String targetServerName, String userProfileName) {
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

	@Override
	public TestConfigManager getTestConfigManager() {
		return TestConfigManager.getInstance();
	}

	@Override
	public AppConfig getActiveAppConfig() {
		return getTestConfigManager().getAppConfig(activeAppName);
	}

	public ApiConfig getActiveAppApiConfig() {
		return getTestConfigManager().getAppConfig(activeAppName).getApiConfig();
	}

	@Override
	public AppConfig getAppConfig(String appName) {
		return getTestConfigManager().getAppConfig(appName);
	}

	@Override
	public void log(String message) {
		if (scenario != null) {
			scenario.log(message);
		}
	}

	@Override
	public void close() {
		for (AbstractApiActionHandler actionHandlers : appActionHandlers.values()) {
			actionHandlers.logout();
		}
		appActionHandlers.clear();
	}

	@Override
	public void addParamValue(String paramName, Object value) {
		params.put(paramName, value);
	}

	@Override
	public Object getParamValue(String paramName) {
		return params.get(paramName);
	}
	
	@Override
	public void removeParam(String paramName) {
		params.remove(paramName);
	}

	private String prepareKey(String appName, String targetServerName) {
		return appName + ":" + targetServerName;
	}
}
