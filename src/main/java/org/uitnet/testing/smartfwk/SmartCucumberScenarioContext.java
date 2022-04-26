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

import org.uitnet.testing.smartfwk.ui.core.config.AppConfig;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

import io.cucumber.java.Scenario;

/**
 * Generic interface for cucumber scenario context.
 * 
 * @author Madhav Krishna
 *
 */
public interface SmartCucumberScenarioContext {
	public Scenario getScenario();
	public void setScenario(Scenario scenario);
	public String getActiveAppName();
	public TestConfigManager getTestConfigManager();
	public AppConfig getActiveAppConfig();
	public AppConfig getAppConfig(String appName);
	public void log(String message);
	public void close();
	public void addParamValue(String paramName, Object value);
	public Object getParamValue(String paramName);
	public void removeParam(String paramName);
}
