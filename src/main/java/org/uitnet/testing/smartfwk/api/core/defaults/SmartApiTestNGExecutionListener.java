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

import java.util.Map;

import org.testng.IExecutionListener;
import org.testng.Reporter;
import org.uitnet.testing.smartfwk.api.core.AbstractApiTestHelper;
import org.uitnet.testing.smartfwk.ui.core.config.TestConfigManager;

/**
 * 
 * @author Madhav Krishna
 *
 */
public class SmartApiTestNGExecutionListener implements IExecutionListener {
	@Override
	public void onExecutionStart() {

	}

	@Override
	public void onExecutionFinish() {
		Reporter.log("Going to close all opened API connections.", true);
		if (!TestConfigManager.getInstance().isParallelMode()) {
			for(Map.Entry<String, AbstractApiTestHelper> entry : SingletonApiTestHelperMap.getInstance().getMap().entrySet()) {
				entry.getValue().logout();
			}
		}
	}
}
