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
package org.uitnet.testing.smartfwk;

import java.util.HashMap;
import java.util.Map;

import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

import io.cucumber.java.Scenario;

/**
 * Generic interface for cucumber scenario context.
 * 
 * @author Madhav Krishna
 *
 */
public class SmartCucumberScenarioContext {
	protected Map<String, Object> params;
	protected Scenario scenario = null;

	protected String activeAppName = null;

	public SmartCucumberScenarioContext() {
		params = new HashMap<>(8);
	}

	public Scenario getScenario() {
		return this.scenario;
	}

	public void setScenario(Scenario scenario) {
		this.scenario = scenario;
	}

	public String getActiveAppName() {
		return activeAppName;
	}

	public TestConfigManager getTestConfigManager() {
		return TestConfigManager.getInstance();
	}

	public AppConfig getActiveAppConfig() {
		return getTestConfigManager().getAppConfig(activeAppName);
	}

	public AppConfig getAppConfig(String appName) {
		return getTestConfigManager().getAppConfig(appName);
	}

	public void log(String message) {
		scenario.log(message);
	}

	public void close() {
		// must be overridden by child class for cleanup
	}

	public void addParamValue(String paramName, Object value) {
		params.put(paramName, value);
	}

	/**
	 * This method returns param value. If does not exist then returns as null.
	 * 
	 * @param paramName
	 * @return
	 */
	public Object getParamValue(String paramName) {
		return params.get(paramName);
	}

	public Map<String, Object> getAllParams() {
		return params;
	}

	/**
	 * This method returns param value. If does not exist then returns paramName as
	 * value.
	 * 
	 * @param paramName
	 * @return
	 */
	public Object getParamValueNullAsParamName(String paramName) {
		Object val = params.get(paramName);
		if (val == null) {
			return paramName;
		}
		return val;
	}

	public void removeParam(String paramName) {
		params.remove(paramName);
	}

	/**
	 * It will apply all params value to the text. It will convert param value to
	 * string then apply.
	 * 
	 * @param text
	 * @return the updated text
	 */
	public String applyParamsValueOnText(String text) {
		for (Map.Entry<String, Object> e : params.entrySet()) {
			text = text.replace(e.getKey(), "" + e.getValue());
		}

		return text;
	}
}
