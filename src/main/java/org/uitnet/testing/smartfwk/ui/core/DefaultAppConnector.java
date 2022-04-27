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
package org.uitnet.testing.smartfwk.ui.core;

import io.cucumber.java.Scenario;

/**
 * 
 * @author Madhav Krishna
 *
 */
/**
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultAppConnector extends AbstractAppConnector {

	public DefaultAppConnector(String appName) {
		super(appName);
	}

	public void beforeScenario(Scenario scenario) {
		logger.info("Feature Scenario Started. TestClass: " + scenario.getName());
		super.scenarioSetup();
		logger.info("Feature Scenario Done. TestClass: " + scenario);

	}

	public void afterScenario(Scenario scenario) {
		logger.info("Feature Scenario TearDown Started. TestClass: " + this.getClass());
		super.scenarioTearDown();
		logger.info("Feature Scenario TearDown Done. TestClass: " + this.getClass());
	}

}
