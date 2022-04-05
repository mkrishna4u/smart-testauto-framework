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
package org.uitnet.testing.smartfwk.ui.core.defaults.stepdefs;

import java.util.Set;

import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.AbstractAppConnector;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.cache.DefaultSmartCache;
import org.uitnet.testing.smartfwk.ui.core.cache.SmartCache;
import org.uitnet.testing.smartfwk.ui.core.cache.SmartCacheSubscriber;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

/**
 * Step definitions for Home page.
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultUiWindowAndFrameOperationsStepDefs {
	// ------------- Common Code for step definition - START -------
	private AbstractAppConnector appConnector;
	private Scenario runningScenario;
	private SmartAppDriver appDriver;
	private SmartCache globalCache;

	/**
	 * Constructor
	 */
	public DefaultUiWindowAndFrameOperationsStepDefs() {
		globalCache = DefaultSmartCache.getInstance();

		appConnector = globalCache.getAppConnector();
		runningScenario = globalCache.getRunningScenario();
		appDriver = globalCache.getAppDriver();

		// Subscribe to the the cache to get the latest data
		globalCache.subscribe(new SmartCacheSubscriber() {
			@Override
			protected void onMessage(SmartCache message) {
				appConnector = message.getAppConnector();
				runningScenario = message.getRunningScenario();
				appDriver = message.getAppDriver();
			}
		});
	}

	// ------------- Common Code for step definition - END -------

	// ------------- Step definition starts here -----------------
	
	
	@When("Switch to {string} window.")
	@When("Focus {string} window.")
	public void switch_to_window(String windowHandleName) {
		Set<String> windowHandles = appDriver.getWebDriver().getWindowHandles();
		runningScenario.log("AVAILABLE WINDOW HANDLE NAMES: " + windowHandles);
		appDriver.getWebDriver().switchTo().window(windowHandleName);
	}
	
	@When("Switch to default content.")
	public void switch_to_default_content() {
		appDriver.getWebDriver().switchTo().defaultContent();
	}
	
	@When("Switch to {string} frame.")
	public void switch_to_frame(String frameNameOrId) {
		if("parent".equals(frameNameOrId)) {
			appDriver.getWebDriver().switchTo().parentFrame();
		} else if(frameNameOrId.startsWith("INDEX:")){
			appDriver.getWebDriver().switchTo().frame(Integer.valueOf(frameNameOrId.substring("INDEX:".length(), frameNameOrId.length()).trim()));
		} else {
			appDriver.getWebDriver().switchTo().frame(frameNameOrId);
		}
	}
	
	@When("Switch to frame number {int}.")
	public void switch_to_frame_number(Integer frameNumber) {
		appDriver.getWebDriver().switchTo().frame(frameNumber);
	}
	
}
