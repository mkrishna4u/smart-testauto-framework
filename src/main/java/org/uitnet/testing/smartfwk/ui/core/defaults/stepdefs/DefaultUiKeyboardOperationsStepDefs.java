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

import org.uitnet.testing.smartfwk.api.core.support.PageObjectInfo;
import org.uitnet.testing.smartfwk.ui.core.AbstractAppConnector;
import org.uitnet.testing.smartfwk.ui.core.appdriver.SmartAppDriver;
import org.uitnet.testing.smartfwk.ui.core.cache.DefaultSmartCache;
import org.uitnet.testing.smartfwk.ui.core.cache.SmartCache;
import org.uitnet.testing.smartfwk.ui.core.cache.SmartCacheSubscriber;
import org.uitnet.testing.smartfwk.ui.core.utils.PageObjectUtil;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.When;

/**
 * Step definitions for Home page.
 * 
 * @author Madhav Krishna
 *
 */
public class DefaultUiKeyboardOperationsStepDefs {
	// ------------- Common Code for step definition - START -------
	private AbstractAppConnector appConnector;
	private Scenario runningScenario;
	private SmartAppDriver appDriver;
	private SmartCache globalCache;

	/**
	 * Constructor
	 */
	public DefaultUiKeyboardOperationsStepDefs() {
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
	
	/**
	 * Example: "ctrl a", "ctrl tab", "ctrl shift tab", "enter", "alt tab", "esc", "f12", "ctrl f4", "pagedown", "pageup", "home", "end",
	 * 	"insert", "numlock", "arrowup", "arrowdown", "arrowleft", "arrowright", "ctrl spacebar"
	 * @param keys - space separated keys.
	 */
	@When("Press {string} key(s) on {string} element.")
	@When("Press {string} key(s) on {string} page object.")
	public void press_keys(String keys, String po) {
		PageObjectInfo poInfo = PageObjectUtil.getPageObjectInfo(po);
		
		//TODO
	}
}
